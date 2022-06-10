import json

from bson.objectid import ObjectId
from flask import url_for

from dawdle.components.card.models import Card
from dawdle.components.column.models import Column
from dawdle.utils import to_ObjectId
from tests.test_base import TestBase


class TestCard(TestBase):

    @classmethod
    def setup_class(cls):
        super().setup_class()

        cls.board = cls.create_board()
        cls.column = cls.create_column(cls.board)

    #
    # index_POST tests.
    #

    def test_index_POST_no_column_id(self):
        data = self._get_mock_create_card_data()
        self._assert_index_POST_bad_request(data, 'Bad Request')

    def test_index_POST_column_does_not_exist(self):
        data = self._get_mock_create_card_data()
        self._assert_index_POST_not_found(data, ObjectId())

    def test_index_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_create_card_data()
        self._assert_index_POST_forbidden(data, self.column.id)

    def test_index_POST_user_without_permissions(self):
        data = self._get_mock_create_card_data()
        column = self.create_column(self.create_board(owner_id=ObjectId()))
        self._assert_index_POST_forbidden(data, column.id)

    def test_index_POST_no_name(self):
        data = self._get_mock_create_card_data()
        del data['name']
        self._assert_index_POST_bad_request(
            data,
            'Please enter a card name',
            column_id=self.column.id,
        )

    def test_index_POST_name_equal_to_min(self):
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_create_card_data(name=name)
        self._assert_index_POST_ok(data, self.column.id)

    def test_index_POST_name_equal_to_max(self):
        name = self.fake.pystr(min_chars=256, max_chars=256)
        data = self._get_mock_create_card_data(name=name)
        self._assert_index_POST_ok(data, self.column.id)

    def test_index_POST_name_greater_than_max(self):
        name = self.fake.pystr(min_chars=257, max_chars=257)
        data = self._get_mock_create_card_data(name=name)
        self._assert_index_POST_bad_request(
            data,
            'Card name must be between 1 and 256 characters',
            column_id=self.column.id,
        )

    def _get_mock_create_card_data(self, **kwargs):
        return {
            'name': kwargs.get('name', self.fake.sentence()),
        }

    def _send_index_POST_request(self, data, column_id=None):
        if column_id:
            url = url_for('card.index_POST', column_id=column_id)
        else:
            url = url_for('card.index_POST')
        return self.client.post(url, data=data)

    def _assert_index_POST_ok(self, data, column_id):
        response = self._send_index_POST_request(data, column_id)
        response_json = json.loads(response.data.decode())
        column = Column.objects(id=column_id).first()
        card = Card.objects(
            id=response_json['card']['_id']['$oid'],
        ).first()
        assert response.status_code == 201
        assert card in column.cards
        assert card.column_id == column.id
        assert card.created
        assert card.created_by == self.user.id
        assert card.name == data['name']

    def _assert_index_POST_bad_request(self,
                                       data,
                                       expected_text,
                                       column_id=None):
        response = self._send_index_POST_request(data, column_id)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    def _assert_index_POST_forbidden(self, data, column_id):
        response = self._send_index_POST_request(data, column_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_index_POST_not_found(self, data, column_id):
        response = self._send_index_POST_request(data, column_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # card_update_POST tests.
    #

    def test_card_update_POST_does_not_exist(self):
        self._assert_card_update_POST_not_found(ObjectId())

    def test_card_update_POST_not_authenticated(self):
        self.logout()
        card = self.create_card(self.column)
        self._assert_card_update_POST_forbidden(card.id)

    def test_card_update_POST_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        column = self.create_column(board)
        card = self.create_card(column)
        self._assert_card_update_POST_forbidden(card.id)

    def test_card_update_POST_no_name(self):
        data = self._get_mock_update_card_data()
        del data['name']
        self._assert_card_update_POST_bad_request(
            data,
            'Please enter a card name',
        )

    def test_card_update_POST_name_equal_to_min(self):
        card = self.create_card(self.column)
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_update_card_data(name=name)
        self._assert_card_update_POST_ok(card, data)

    def test_card_update_POST_name_equal_to_max(self):
        card = self.create_card(self.column)
        name = self.fake.pystr(min_chars=256, max_chars=256)
        data = self._get_mock_update_card_data(name=name)
        self._assert_card_update_POST_ok(card, data)

    def test_card_update_POST_name_greater_than_max(self):
        name = self.fake.pystr(min_chars=257, max_chars=257)
        data = self._get_mock_update_card_data(name=name)
        self._assert_card_update_POST_bad_request(
            data,
            'Card name must be between 1 and 256 characters',
        )

    def test_card_update_POST_success(self):
        card = self.create_card(self.column)
        data = self._get_mock_update_card_data()
        self._assert_card_update_POST_ok(card, data)

    def _get_mock_update_card_data(self, **kwargs):
        return {
            'name': kwargs.get('name', self.fake.name()),
        }

    def _send_card_update_POST_request(self, card_id, data=None):
        return self.client.post(
            url_for('card.card_update_POST', card_id=str(card_id)),
            data=data,
        )

    def _assert_card_update_POST_ok(self, card, data):
        response = self._send_card_update_POST_request(card.id, data)
        updated_card = Card.objects(id=card.id).first()
        assert response.status_code == 200
        assert updated_card.name == data['name']

    def _assert_card_update_POST_bad_request(self, data, expected_text):
        card = self.create_card(self.column)
        response = self._send_card_update_POST_request(card.id, data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    def _assert_card_update_POST_forbidden(self, card_id):
        response = self._send_card_update_POST_request(card_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_card_update_POST_not_found(self, card_id):
        response = self._send_card_update_POST_request(card_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # card_delete_POST tests.
    #

    def test_card_delete_POST_does_not_exist(self):
        self._assert_card_delete_POST_not_found(ObjectId())

    def test_card_delete_POST_not_authenticated(self):
        self.logout()
        card = self.create_card(self.column)
        self._assert_card_delete_POST_forbidden(card.id)

    def test_card_delete_POST_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        column = self.create_column(board)
        card = self.create_card(column)
        self._assert_card_delete_POST_forbidden(card.id)

    def test_card_delete_POST_success(self):
        card = self.create_card(self.column)
        self._assert_card_delete_POST_ok(card)

    def test_card_delete_POST_with_cards(self):
        card = self.create_card(self.column)
        self._assert_card_delete_POST_ok(card)

    def _send_card_delete_POST_request(self, card_id):
        return self.client.post(
            url_for('card.card_delete_POST', card_id=str(card_id)),
        )

    def _assert_card_delete_POST_ok(self, card):
        assert Card.objects(id=card.id).count() == 1
        response = self._send_card_delete_POST_request(card.id)
        response_json = json.loads(response.data.decode())
        assert Card.objects(id=card.id).count() == 0
        assert response.status_code == 200
        assert response_json['id'] == str(card.id)

    def _assert_card_delete_POST_forbidden(self, card_id):
        response = self._send_card_delete_POST_request(card_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_card_delete_POST_not_found(self, card_id):
        response = self._send_card_delete_POST_request(card_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # card_move_POST tests.
    #

    def test_card_move_POST_does_not_exist(self):
        self._assert_card_move_POST_not_found(ObjectId())

    def test_card_move_POST_not_authenticated(self):
        self.logout()
        card = self.create_card(self.column)
        self._assert_card_move_POST_forbidden(card.id)

    def test_card_move_POST_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        column = self.create_column(board)
        card = self.create_card(column)
        self._assert_card_move_POST_forbidden(card.id)

    def test_card_move_POST_new_column_does_not_exist(self):
        card = self.create_card(self.column)
        data = {'new_column_id': str(ObjectId())}
        self._assert_card_move_POST_not_found(card.id, data)

    def test_card_move_POST_add_to_new_column_no_prev_card(self):
        card = self.create_card(self.column)
        new_column = self.create_column(self.board)
        self.create_cards(new_column)
        data = {'new_column_id': str(new_column.id)}
        self._assert_card_move_POST_ok(card, data)

    def test_card_move_POST_add_to_new_column_with_prev_card(self):
        new_column = self.create_column(self.board)
        prev_card = self.create_card(new_column)
        self.create_cards(new_column)
        card = self.create_card(self.column)
        data = {
            'new_column_id': str(new_column.id),
            'prev_card_id': str(prev_card.id),
        }
        self._assert_card_move_POST_ok(card, data)

    def test_card_move_POST_add_to_new_column_prev_card_does_not_exist(self):
        new_column = self.create_column(self.board)
        self.create_cards(new_column)
        card = self.create_card(self.column)
        data = {
            'new_column_id': str(new_column.id),
            'prev_card_id': str(ObjectId()),
        }
        self._assert_card_move_POST_ok(card, data)

    def test_card_move_POST_add_to_same_column_no_prev_card(self):
        self.create_cards(self.column)
        card = self.create_card(self.column)
        self._assert_card_move_POST_ok(card)

    def test_card_move_POST_add_to_same_column_with_prev_card(self):
        prev_card = self.create_card(self.column)
        self.create_cards(self.column)
        card = self.create_card(self.column)
        data = {
            'prev_card_id': str(prev_card.id),
        }
        self._assert_card_move_POST_ok(card, data)

    def test_card_move_POST_add_to_same_column_prev_card_does_not_exist(self):
        self.create_cards(self.column)
        card = self.create_card(self.column)
        data = {
            'prev_card_id': str(ObjectId()),
        }
        self._assert_card_move_POST_ok(card, data)

    def _send_card_move_POST_request(self, card_id, data=None):
        data = {} if not data else data

        return self.client.post(
            url_for('card.card_move_POST', card_id=str(card_id)),
            data=json.dumps(data),
            content_type='application/json',
        )

    def _assert_card_move_POST_ok(self, card, data=None):
        data = {} if not data else data

        response = self._send_card_move_POST_request(card.id, data)

        old_column = Column.objects(id=card.column_id).first()

        if 'new_column_id' in data:
            new_column = \
                Column.objects(id=to_ObjectId(data['new_column_id'])).first()
        else:
            new_column = old_column

        updated_card = Card.objects(id=card.id).first()

        assert response.status_code == 200
        assert updated_card.column_id == new_column.id

        if old_column != new_column:
            assert card not in old_column.cards

        expected_index = 0
        if 'prev_card_id' in data:
            prev_card_id = to_ObjectId(data['prev_card_id'])
            found = False
            for i in range(len(new_column.cards)):
                if new_column.cards[i].id == prev_card_id:
                    expected_index = i + 1
                    found = True
                    break
            if not found:
                expected_index = len(new_column.cards) - 1

        assert new_column.cards[expected_index] == card

    def _assert_card_move_POST_forbidden(self, card_id):
        response = self._send_card_move_POST_request(card_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_card_move_POST_not_found(self, card_id, data=None):
        response = self._send_card_move_POST_request(card_id, data)
        assert response.status_code == 404
        assert b'Not Found' in response.data
