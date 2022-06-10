from functools import wraps

from flask import abort, request

from dawdle.components.column.models import Column
from dawdle.utils import to_ObjectId


def column_from_column_id(func):
    @wraps(func)
    def decorated_function(*args, **kwargs):
        if 'column_id' in kwargs:
            column_id = kwargs['column_id']
        elif 'column_id' in request.args:
            column_id = request.args['column_id']
        else:
            abort(400)

        column = Column.objects(id=to_ObjectId(column_id)).first()

        if not column:
            abort(404)

        kwargs['column'] = column

        return func(*args, **kwargs)
    return decorated_function


def board_id_from_column(func):
    @wraps(func)
    def decorated_function(*args, **kwargs):
        column = kwargs['column']

        kwargs['board_id'] = column.board_id

        return func(*args, **kwargs)
    return decorated_function
