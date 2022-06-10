from functools import wraps

from flask import abort

from dawdle.components.card.models import Card
from dawdle.utils import to_ObjectId


def card_from_card_id(func):
    @wraps(func)
    def decorated_function(*args, **kwargs):
        card_id = kwargs['card_id']

        card = Card.objects(id=to_ObjectId(card_id)).first()

        if not card:
            abort(404)

        kwargs['card'] = card

        return func(*args, **kwargs)
    return decorated_function


def column_id_from_card(func):
    @wraps(func)
    def decorated_function(*args, **kwargs):
        card = kwargs['card']

        kwargs['column_id'] = card.column_id

        return func(*args, **kwargs)
    return decorated_function
