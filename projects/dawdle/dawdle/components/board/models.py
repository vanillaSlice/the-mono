from collections import namedtuple
from datetime import datetime

from mongoengine import (PULL, DateTimeField, Document, ListField,
                         ObjectIdField, ReferenceField, StringField)

from dawdle.components.column.models import Column
from dawdle.utils import safely_delete_documents

_board_type = namedtuple('BoardType', 'id, display_name')


class BoardType:
    PERSONAL = _board_type('personal', 'Personal')
    # TODO add TEAM type # pylint: disable=fixme


BOARD_TYPES = [BoardType.PERSONAL]


_board_visibility = namedtuple('BoardVisibility', 'id, display_name')


class BoardVisibility:
    PRIVATE = _board_visibility('private', 'Private')
    # TODO add PUBLIC visibility # pylint: disable=fixme


BOARD_VISIBILITIES = [BoardVisibility.PRIVATE]


_board_permission = namedtuple('BoardPermission', 'id, display_name')


class BoardPermission:
    ADMIN = _board_permission('admin', 'Admin')
    READ = _board_permission('read', 'Read')
    WRITE = _board_permission('write', 'Write')


BOARD_PERMISSIONS = [
    BoardPermission.ADMIN,
    BoardPermission.READ,
    BoardPermission.WRITE,
]


class Board(Document):

    columns = ListField(ReferenceField(Column, reverse_delete_rule=PULL))
    created = DateTimeField(required=True, default=datetime.utcnow)
    created_by = ObjectIdField(required=True)
    name = StringField(required=True, min_length=1, max_length=256)
    # TODO add last updated field # pylint: disable=fixme
    owner_id = ObjectIdField(required=True)
    type = StringField(required=True)
    visibility = StringField(required=True)

    def delete(self, signal_kwargs=None, **write_concern):
        safely_delete_documents(self.columns)
        super().delete(signal_kwargs, **write_concern)
