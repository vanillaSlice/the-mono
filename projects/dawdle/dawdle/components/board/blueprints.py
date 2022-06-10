from flask import Blueprint, jsonify, render_template, request, url_for
from flask_login import current_user

from dawdle.components.board.forms import (CreateBoardForm, DeleteBoardForm,
                                           UpdateBoardForm)
from dawdle.components.board.models import Board, BoardPermission, BoardType
from dawdle.components.board.utils import (board_permissions_required,
                                           get_owner_from_id)
from dawdle.components.card.forms import (CreateCardForm, DeleteCardForm,
                                          UpdateCardForm)
from dawdle.components.column.forms import (CreateColumnForm, DeleteColumnForm,
                                            UpdateColumnForm)
from dawdle.utils import flash_params_message, no_cache

board_bp = Blueprint('board', __name__, url_prefix='/board')


@board_bp.route('/', methods=['POST'])
def index_POST():
    if not current_user.is_authenticated:
        return jsonify({
            'error': 'Could not create board because you are not logged in.',
        }), 401

    form = CreateBoardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    owner = get_owner_from_id(form.owner.data)

    board = Board()
    board.created_by = current_user.id
    board.name = form.name.data
    board.owner_id = owner.id
    board.type = BoardType.PERSONAL.id if owner.id == current_user.id \
        else BoardType.TEAM.id
    board.visibility = form.visibility.data
    board.save()

    owner.boards.append(board)
    owner.save()

    return jsonify({
        'id': str(board.id),
        'url': url_for(
            'board.board_GET',
            board_id=board.id,
            message='New board has been created.',
            category='success',
        ),
    }), 201


@board_bp.route('/<board_id>', methods=['GET'])
@board_permissions_required(BoardPermission.READ)
@no_cache
def board_GET(board, permissions, **_):
    flash_params_message()

    create_column_path = url_for('column.index_POST', board_id=board.id)
    create_column_form = CreateColumnForm(
        request.form,
        create_column_path=create_column_path,
    )

    update_column_form = UpdateColumnForm(request.form)

    delete_column_form = DeleteColumnForm(request.form)

    create_card_form = CreateCardForm(request.form)
    update_card_form = UpdateCardForm(request.form)
    delete_card_form = DeleteCardForm(request.form)

    update_board_path = url_for('board.board_update_POST', board_id=board.id)
    update_board_form = UpdateBoardForm(
        request.form,
        update_board_path=update_board_path,
        obj=board,
    )

    delete_board_path = url_for('board.board_delete_POST', board_id=board.id)
    delete_board_form = DeleteBoardForm(
        request.form,
        delete_board_path=delete_board_path,
    )

    return render_template(
        'board/index.html',
        board=board,
        permissions=permissions,
        create_column_form=create_column_form,
        update_column_form=update_column_form,
        delete_column_form=delete_column_form,
        create_card_form=create_card_form,
        update_card_form=update_card_form,
        delete_card_form=delete_card_form,
        update_board_form=update_board_form,
        delete_board_form=delete_board_form,
    )


@board_bp.route('/<board_id>', methods=['POST'])
@board_permissions_required(BoardPermission.ADMIN)
def board_update_POST(board, **_):
    form = UpdateBoardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    if not form.update_needed(board):
        return jsonify({
            'board': board,
            'flash': {
                'category': 'info',
                'message': 'No update needed.',
            }
        }), 200

    form.populate_obj(board)
    board.save()

    return jsonify({
        'board': board,
        'flash': {
            'category': 'success',
            'message': 'This board has been updated.',
        }
    }), 200


@board_bp.route('/<board_id>/delete', methods=['POST'])
@board_permissions_required(BoardPermission.ADMIN)
def board_delete_POST(board, **_):
    form = DeleteBoardForm(request.form)

    if not form.validate_on_submit():
        return jsonify(form.errors), 400

    board.delete()

    return jsonify({
        'url': url_for(
            'user.boards_GET',
            message='Board has been deleted.',
            category='success',
        ),
    }), 200
