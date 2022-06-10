from functools import wraps

from flask import abort, request
from flask_login import current_user

from dawdle.components.board.models import Board, BOARD_PERMISSIONS
from dawdle.components.user.models import User
from dawdle.utils import to_ObjectId


def get_owner_from_id(owner_id):
    return User.objects(id=to_ObjectId(owner_id)).first()


def board_permissions_required(*required_permissions):
    def decorator(func):
        @wraps(func)
        def decorated_function(*args, **kwargs):
            if 'board_id' in kwargs:
                board_id = kwargs['board_id']
            elif 'board_id' in request.args:
                board_id = request.args['board_id']
            else:
                abort(400)

            board = Board.objects(id=to_ObjectId(board_id)).first()

            if not board:
                abort(404)

            actual_permissions = get_board_permissions(board)

            for permission in required_permissions:
                if permission not in actual_permissions:
                    abort(403)

            kwargs['board'] = board
            kwargs['permissions'] = actual_permissions

            return func(*args, **kwargs)
        return decorated_function
    return decorator


def get_board_permissions(board):
    permissions = set()

    if current_user.is_authenticated and board.owner_id == current_user.id:
        permissions.update(BOARD_PERMISSIONS)

    return permissions
