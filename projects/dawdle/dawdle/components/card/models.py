from datetime import datetime

from mongoengine import DateTimeField, Document, ObjectIdField, StringField


class Card(Document):

    column_id = ObjectIdField(required=True)
    created = DateTimeField(required=True, default=datetime.utcnow)
    created_by = ObjectIdField(required=True)
    # TODO add last updated field
    name = StringField(required=True, min_length=1, max_length=256)
