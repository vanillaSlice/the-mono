from flask import abort, Blueprint, jsonify, request
from flask_login import current_user

from dawdle.components.board.models import BoardPermission
from dawdle.components.board.utils import board_permissions_required
from dawdle.components.card.forms import (CreateCardForm, DeleteCardForm,
                                          UpdateCardForm)
from dawdle.components.card.models import Card
from dawdle.components.card.utils import card_from_card_id, column_id_from_card
from dawdle.components.column.models import Column
from dawdle.components.column.utils import (board_id_from_column,
                                            column_from_column_id)
from dawdle.utils import to_ObjectId

card_bp = Blueprint('card', __name__, url_prefix='/card')


@card_bp.route('/', methods=['POST'])
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def index_POST(column, **_):
    form = CreateCardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    card = Card()
    form.populate_obj(card)
    card.column_id = column.id
    card.created_by = current_user.id
    card.save()

    column.cards.append(card)
    column.save()

    return jsonify({
        'card': card,
    }), 201


@card_bp.route('/<card_id>', methods=['POST'])
@card_from_card_id
@column_id_from_card
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def card_update_POST(card, **_):
    form = UpdateCardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    form.populate_obj(card)
    card.save()

    return jsonify({
        'card': card,
    }), 200


@card_bp.route('/<card_id>/delete', methods=['POST'])
@card_from_card_id
@column_id_from_card
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def card_delete_POST(card, card_id, **_):
    form = DeleteCardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    card.delete()

    return jsonify({
        'id': card_id,
    }), 200


@card_bp.route('/<card_id>/move', methods=['POST'])
@card_from_card_id
@column_id_from_card
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def card_move_POST(card, column, column_id, **_):
    payload = request.get_json(force=True)
    new_column_id = to_ObjectId(payload.get('new_column_id', column_id))
    prev_card_id_raw = payload.get('prev_card_id')
    prev_card_id = to_ObjectId(prev_card_id_raw) if prev_card_id_raw else None

    if column_id == new_column_id:
        new_column = column
    else:
        new_column = Column.objects(id=new_column_id).first()

    if not new_column:
        abort(404)

    card.column_id = new_column_id
    card.save()

    column.cards.remove(card)
    column.save()

    if not prev_card_id:
        new_column.cards.insert(0, card)
    else:
        added_card = False
        for i, new_column_card in enumerate(new_column.cards):
            if new_column_card.id == prev_card_id:
                new_column.cards.insert(i + 1, card)
                added_card = True
                break
        if not added_card:
            new_column.cards.append(card)
    new_column.save()

    return jsonify({
        'card': card,
    }), 200
