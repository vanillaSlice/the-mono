from flask import Blueprint, jsonify, request
from flask_login import current_user

from dawdle.components.board.models import BoardPermission
from dawdle.components.board.utils import board_permissions_required
from dawdle.components.column.forms import (CreateColumnForm, DeleteColumnForm,
                                            UpdateColumnForm)
from dawdle.components.column.models import Column
from dawdle.components.column.utils import (board_id_from_column,
                                            column_from_column_id)

column_bp = Blueprint('column', __name__, url_prefix='/column')


@column_bp.route('/', methods=['POST'])
@board_permissions_required(BoardPermission.WRITE)
def index_POST(board, **_):
    form = CreateColumnForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    column = Column()
    form.populate_obj(column)
    column.board_id = board.id
    column.created_by = current_user.id
    column.save()

    board.columns.append(column)
    board.save()

    return jsonify({
        'column': column,
    }), 201


@column_bp.route('/<column_id>', methods=['POST'])
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def column_update_POST(column, **_):
    form = UpdateColumnForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    form.populate_obj(column)
    column.save()

    return jsonify({
        'column': column,
    }), 200


@column_bp.route('/<column_id>/delete', methods=['POST'])
@column_from_column_id
@board_id_from_column
@board_permissions_required(BoardPermission.WRITE)
def column_delete_POST(column, column_id, **_):
    form = DeleteColumnForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    column.delete()

    return jsonify({
        'id': column_id,
    }), 200
