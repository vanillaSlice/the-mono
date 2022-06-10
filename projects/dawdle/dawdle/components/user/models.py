from datetime import datetime

from bson.objectid import ObjectId
from flask_login import UserMixin
from mongoengine import (BooleanField,
                         DateTimeField,
                         Document,
                         EmailField,
                         ListField,
                         ObjectIdField,
                         PULL,
                         ReferenceField,
                         StringField)
from passlib.hash import sha256_crypt

from dawdle.components.board.models import Board
from dawdle.utils import safely_delete_documents


class User(Document, UserMixin):

    active = BooleanField(required=True, default=False)
    auth_id = ObjectIdField(required=True, default=ObjectId, unique=True)
    boards = ListField(ReferenceField(Board, reverse_delete_rule=PULL))
    created = DateTimeField(required=True, default=datetime.utcnow)
    email = EmailField(required=True, unique=True)
    initials = StringField(required=True, min_length=1, max_length=4)
    last_updated = DateTimeField()
    name = StringField(required=True, min_length=1, max_length=50)
    password = StringField(required=True)

    @property
    def is_active(self):
        return self.active

    def get_id(self):
        return str(self.auth_id)

    @staticmethod
    def create_initials(name):
        return ''.join([c[0].upper() for c in name.split(' ')])[:4]

    @staticmethod
    def encrypt_password(password):
        return sha256_crypt.hash(password)

    def verify_password(self, password):
        if not password:
            return False

        return sha256_crypt.verify(password, self.password)

    def delete(self, signal_kwargs=None, **write_concern):
        safely_delete_documents(self.boards)
        super().delete(signal_kwargs, **write_concern)
