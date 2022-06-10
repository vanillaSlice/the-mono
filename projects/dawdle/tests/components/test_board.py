import json

from flask import url_for
from bson.objectid import ObjectId

from dawdle.components.board.models import Board, BoardType, BoardVisibility
from dawdle.components.board.utils import get_owner_from_id
from dawdle.components.column.models import Column
from tests.test_base import TestBase


class TestBoard(TestBase):

    #
    # index_POST tests.
    #

    def test_index_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_create_board_data()
        response = self._send_index_POST_request(data)
        assert response.status_code == 401
        assert b'Could not create board' in response.data

    def test_index_POST_no_name(self):
        data = self._get_mock_create_board_data()
        del data['name']
        self._assert_index_POST_bad_request(data, 'Please enter a board name')

    def test_index_POST_name_equal_to_min(self):
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_create_board_data(name=name)
        self._assert_index_POST_ok(data)

    def test_index_POST_name_equal_to_max(self):
        name = self.fake.pystr(min_chars=256, max_chars=256)
        data = self._get_mock_create_board_data(name=name)
        self._assert_index_POST_ok(data)

    def test_index_POST_name_greater_than_max(self):
        name = self.fake.pystr(min_chars=257, max_chars=257)
        data = self._get_mock_create_board_data(name=name)
        self._assert_index_POST_bad_request(
            data,
            'Board name must be between 1 and 256 characters',
        )

    def test_index_POST_no_owner(self):
        data = self._get_mock_create_board_data()
        data['owner'] = ''
        self._assert_index_POST_bad_request(data, 'Please select board owner')

    def test_index_POST_invalid_owner(self):
        data = self._get_mock_create_board_data(owner='invalid')
        self._assert_index_POST_bad_request(data, 'Not a valid choice')

    def test_index_POST_no_visibility(self):
        data = self._get_mock_create_board_data()
        data['visibility'] = ''
        self._assert_index_POST_bad_request(
            data,
            'Please select board visibility',
        )

    def test_index_POST_invalid_visibility(self):
        data = self._get_mock_create_board_data(visibility='invalid')
        self._assert_index_POST_bad_request(data, 'Not a valid choice')

    def test_index_POST_success(self):
        name = self.fake.pystr()
        data = self._get_mock_create_board_data(name=name)
        self._assert_index_POST_ok(data)

    def _get_mock_create_board_data(self, **kwargs):
        return {
            'name': kwargs.get('name', self.fake.sentence()),
            'owner': kwargs.get('owner', str(self.user.id)),
            'visibility': kwargs.get('visibility', BoardVisibility.PRIVATE.id),
        }

    def _send_index_POST_request(self, data):
        return self.client.post(
            url_for('board.index_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_index_POST_ok(self, data, expected_type=BoardType.PERSONAL):
        response = self._send_index_POST_request(data)
        response_json = json.loads(response.data.decode())
        board = Board.objects(id=response_json['id']).first()
        owner = get_owner_from_id(data['owner'])
        assert response.status_code == 201
        assert response_json['url'] == \
            f'/board/{board.id}?message=New+board+has+been+created.' \
            '&category=success'
        assert board.created_by == self.user.id
        assert board.name == data['name']
        assert str(board.owner_id) == data['owner']
        assert board.type == expected_type.id
        assert board.visibility == data['visibility']
        assert board in owner.boards

    def _assert_index_POST_bad_request(self, data, expected_text):
        response = self._send_index_POST_request(data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    #
    # board_GET tests.
    #

    def test_board_GET_board_does_not_exist(self):
        self._assert_board_GET_not_found(ObjectId())

    def test_board_GET_not_authenticated(self):
        self.logout()
        board = self.create_board()
        self._assert_board_GET_forbidden(board.id)

    def test_board_GET_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        self._assert_board_GET_forbidden(board.id)

    def test_board_GET_success(self):
        board = self.create_board()
        self._assert_board_GET_ok(board.id)

    def test_board_GET_with_message(self):
        board = self.create_board()
        self._assert_board_GET_ok(board.id, 'Hello World')

    def _send_board_GET_request(self, board_id, message=None):
        return self.client.get(
            url_for(
                'board.board_GET',
                board_id=str(board_id),
                message=message,
            ),
        )

    def _assert_board_GET_ok(self, board_id, message=None):
        response = self._send_board_GET_request(board_id, message)
        board = Board.objects(id=board_id).first()
        assert response.status_code == 200
        assert board.name.encode() in response.data
        if message:
            assert message.encode() in response.data

    def _assert_board_GET_forbidden(self, board_id):
        response = self._send_board_GET_request(board_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_board_GET_not_found(self, board_id):
        response = self._send_board_GET_request(board_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # board_update_POST tests.
    #

    def test_board_update_POST_does_not_exist(self):
        self._assert_board_update_POST_not_found(ObjectId())

    def test_board_update_POST_not_authenticated(self):
        self.logout()
        board = self.create_board()
        self._assert_board_update_POST_forbidden(board.id)

    def test_board_update_POST_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        self._assert_board_update_POST_forbidden(board.id)

    def test_board_update_POST_no_name(self):
        data = self._get_mock_update_board_data()
        del data['name']
        self._assert_board_update_POST_bad_request(
            data,
            'Please enter a board name',
        )

    def test_board_update_POST_name_equal_to_min(self):
        board = self.create_board()
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_update_board_data(name=name)
        self._assert_board_update_POST_ok(board, data)

    def test_board_update_POST_name_equal_to_max(self):
        board = self.create_board()
        name = self.fake.pystr(min_chars=256, max_chars=256)
        data = self._get_mock_update_board_data(name=name)
        self._assert_board_update_POST_ok(board, data)

    def test_board_update_POST_name_greater_than_max(self):
        name = self.fake.pystr(min_chars=257, max_chars=257)
        data = self._get_mock_update_board_data(name=name)
        self._assert_board_update_POST_bad_request(
            data,
            'Board name must be between 1 and 256 characters',
        )

    def test_board_update_POST_no_owner(self):
        data = self._get_mock_update_board_data()
        data['owner'] = ''
        self._assert_board_update_POST_bad_request(
            data,
            'Please select board owner',
        )

    def test_board_update_POST_invalid_owner(self):
        data = self._get_mock_update_board_data(owner='invalid')
        self._assert_board_update_POST_bad_request(data, 'Not a valid choice')

    def test_board_update_POST_no_visibility(self):
        data = self._get_mock_update_board_data()
        data['visibility'] = ''
        self._assert_board_update_POST_bad_request(
            data,
            'Please select board visibility',
        )

    def test_board_update_POST_invalid_visibility(self):
        data = self._get_mock_update_board_data(visibility='invalid')
        self._assert_board_update_POST_bad_request(data, 'Not a valid choice')

    def test_board_update_POST_no_update(self):
        board = self.create_board()
        data = self._get_mock_update_board_data(
            name=board.name,
            owner=board.owner_id,
            visibility=board.visibility,
        )
        self._assert_board_update_POST_ok(board, data, updated=False)

    def test_board_update_POST_success(self):
        board = self.create_board()
        data = self._get_mock_update_board_data()
        self._assert_board_update_POST_ok(board, data)

    def _get_mock_update_board_data(self, **kwargs):
        return {
            'name': kwargs.get('name', self.fake.name()),
            'owner': str(kwargs.get('owner', self.user.id)),
            'visibility': kwargs.get('visibility', 'private'),
        }

    def _send_board_update_POST_request(self, board_id, data=None):
        return self.client.post(
            url_for('board.board_update_POST', board_id=str(board_id)),
            data=data,
        )

    def _assert_board_update_POST_ok(self, board, data, updated=True):
        response = self._send_board_update_POST_request(board.id, data)
        updated_board = Board.objects(id=board.id).first()
        assert response.status_code == 200
        if updated:
            assert b'This board has been updated' in response.data
            assert updated_board.name == data['name']
            assert str(updated_board.owner_id) == data['owner']
            assert updated_board.visibility == data['visibility']
        else:
            assert b'No update needed.' in response.data
            assert board.name == updated_board.name
            assert board.owner_id == updated_board.owner_id
            assert board.visibility == updated_board.visibility

    def _assert_board_update_POST_bad_request(self, data, expected_text):
        board = self.create_board()
        response = self._send_board_update_POST_request(board.id, data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    def _assert_board_update_POST_forbidden(self, board_id):
        response = self._send_board_update_POST_request(board_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_board_update_POST_not_found(self, board_id):
        response = self._send_board_update_POST_request(board_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # board_delete_POST tests.
    #

    def test_board_delete_POST_does_not_exist(self):
        self._assert_board_delete_POST_not_found(ObjectId())

    def test_board_delete_POST_not_authenticated(self):
        self.logout()
        board = self.create_board()
        self._assert_board_delete_POST_forbidden(board.id)

    def test_board_delete_POST_user_without_permissions(self):
        board = self.create_board(owner_id=ObjectId())
        self._assert_board_delete_POST_forbidden(board.id)

    def test_board_delete_POST_success(self):
        board = self.create_board()
        self._assert_board_delete_POST_ok(board)

    def test_board_delete_POST_with_columns(self):
        board = self.create_board()
        self.create_columns(board)
        self._assert_board_delete_POST_ok(board)

    def _send_board_delete_POST_request(self, board_id):
        return self.client.post(
            url_for('board.board_delete_POST', board_id=str(board_id)),
        )

    def _assert_board_delete_POST_ok(self, board):
        assert Board.objects(id=board.id).count() == 1
        assert Column.objects(board_id=board.id).count() == len(board.columns)
        response = self._send_board_delete_POST_request(board.id)
        response_json = json.loads(response.data.decode())
        assert Board.objects(id=board.id).count() == 0
        assert Column.objects(board_id=board.id).count() == 0
        assert response.status_code == 200
        assert response_json['url'] == \
            '/user/boards?message=Board+has+been+deleted.&category=success'

    def _assert_board_delete_POST_forbidden(self, board_id):
        response = self._send_board_delete_POST_request(board_id)
        assert response.status_code == 403
        assert b'Not Authorised' in response.data

    def _assert_board_delete_POST_not_found(self, board_id):
        response = self._send_board_delete_POST_request(board_id)
        assert response.status_code == 404
        assert b'Not Found' in response.data
