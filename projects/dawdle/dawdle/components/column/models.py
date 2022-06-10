from datetime import datetime

from mongoengine import (PULL, DateTimeField, Document, ListField,
                         ObjectIdField, ReferenceField, StringField)

from dawdle.components.card.models import Card
from dawdle.utils import safely_delete_documents


class Column(Document):

    board_id = ObjectIdField(required=True)
    cards = ListField(ReferenceField(Card, reverse_delete_rule=PULL))
    created = DateTimeField(required=True, default=datetime.utcnow)
    created_by = ObjectIdField(required=True)
    # TODO add last updated field
    name = StringField(required=True, min_length=1, max_length=256)

    def delete(self, signal_kwargs=None, **write_concern):
        safely_delete_documents(self.cards)
        super().delete(signal_kwargs, **write_concern)
