from unittest import mock

from flask import url_for

from dawdle.components.board.models import Board
from dawdle.components.user.models import User
from tests.test_base import TestBase


class TestUser(TestBase):

    #
    # boards_GET tests.
    #

    def test_boards_GET_not_authenticated(self):
        self.logout()
        self._assert_boards_GET_ok('Log In to Dawdle')

    def test_boards_GET_authenticated(self):
        self._assert_boards_GET_ok('Personal Boards')

    def test_boards_GET_with_message(self):
        self._assert_boards_GET_ok('Personal Boards', 'Hello World')

    def _assert_boards_GET_ok(self, expected_text, message=None):
        response = self.client.get(
            url_for('user.boards_GET', message=message),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data
        if message:
            assert message.encode() in response.data

    #
    # settings_GET tests.
    #

    def test_settings_GET_not_authenticated(self):
        self.logout()
        self._assert_settings_GET_ok('Log In to Dawdle')

    def test_settings_GET_authenticated(self):
        self._assert_settings_GET_ok('Account Details | Dawdle')

    def _assert_settings_GET_ok(self, expected_text):
        response = self.client.get(
            url_for('user.settings_GET'),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data

    #
    # settings_account_details_GET tests.
    #

    def test_settings_account_details_GET_not_authenticated(self):
        self.logout()
        self._assert_settings_account_details_GET_ok('Log In to Dawdle')

    def test_settings_account_details_GET_authenticated(self):
        self._assert_settings_account_details_GET_ok(
            'Account Details | Dawdle',
        )

    def _assert_settings_account_details_GET_ok(self, expected_text):
        response = self.client.get(
            url_for('user.settings_account_details_GET'),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data

    #
    # settings_account_details_POST tests.
    #

    def test_settings_account_details_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_account_details_data()
        response = self._send_settings_account_details_POST_request(data)
        assert response.status_code == 200
        assert b'Log In to Dawdle' in response.data

    def test_settings_account_details_POST_name_equal_to_min(self):
        user, _ = self.as_new_user()
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_account_details_data(name=name)
        self._assert_settings_account_details_POST_ok(user.id, data)

    def test_settings_account_details_POST_name_equal_to_max(self):
        user, _ = self.as_new_user()
        name = self.fake.pystr(min_chars=50, max_chars=50)
        data = self._get_mock_account_details_data(name=name)
        self._assert_settings_account_details_POST_ok(user.id, data)

    def test_settings_account_details_POST_name_greater_than_max(self):
        user, _ = self.as_new_user()
        name = self.fake.pystr(min_chars=51, max_chars=51)
        data = self._get_mock_account_details_data(name=name)
        self._assert_settings_account_details_POST_bad_request(
            user.id,
            data,
            'Your name must be between 1 and 50 characters',
        )

    def test_settings_account_details_POST_initials_equal_to_min(self):
        user, _ = self.as_new_user()
        initials = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_account_details_data(initials=initials)
        self._assert_settings_account_details_POST_ok(user.id, data)

    def test_settings_account_details_POST_initials_equal_to_max(self):
        user, _ = self.as_new_user()
        initials = self.fake.pystr(min_chars=4, max_chars=4)
        data = self._get_mock_account_details_data(initials=initials)
        self._assert_settings_account_details_POST_ok(user.id, data)

    def test_settings_account_details_POST_initials_greater_than_max(self):
        user, _ = self.as_new_user()
        initials = self.fake.pystr(min_chars=5, max_chars=5)
        data = self._get_mock_account_details_data(initials=initials)
        self._assert_settings_account_details_POST_bad_request(
            user.id,
            data,
            'Your initials must be between 1 and 4 characters',
        )

    def test_settings_account_details_POST_no_update(self):
        user, _ = self.as_new_user()
        data = self._get_mock_account_details_data(
            name=user.name,
            initials=user.initials,
        )
        self._assert_settings_account_details_POST_ok(
            user.id,
            data,
            updated=False,
        )

    def test_settings_account_details_POST_success(self):
        user, _ = self.as_new_user()
        data = self._get_mock_account_details_data()
        self._assert_settings_account_details_POST_ok(user.id, data)

    def _get_mock_account_details_data(self, **kwargs):
        return {
            'name': kwargs.get('name', self.fake.name()),
            'initials': kwargs.get(
                'initials',
                self.fake.pystr(min_chars=1, max_chars=4),
            ),
        }

    def _send_settings_account_details_POST_request(self, data):
        return self.client.post(
            url_for('user.settings_account_details_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_settings_account_details_POST_ok(self,
                                                 user_id,
                                                 data,
                                                 updated=True):
        response = self._send_settings_account_details_POST_request(data)
        user = User.objects(id=user_id).first()
        assert response.status_code == 200
        assert user.initials == data['initials'].upper()
        assert user.name == data['name']
        if updated:
            assert b'Your account details have been updated' in response.data
            assert user.last_updated
        else:
            assert b'No update needed' in response.data
            assert not user.last_updated

    def _assert_settings_account_details_POST_bad_request(self,
                                                          user_id,
                                                          data,
                                                          expected_text):
        response = self._send_settings_account_details_POST_request(data)
        user = User.objects(id=user_id).first()
        assert response.status_code == 400
        assert expected_text.encode() in response.data
        assert not user.last_updated

    #
    # settings_update_email_GET tests.
    #

    def test_settings_update_email_GET_not_authenticated(self):
        self.logout()
        self._assert_settings_update_email_GET_ok('Log In to Dawdle')

    def test_settings_update_email_GET_authenticated(self):
        self._assert_settings_update_email_GET_ok('Update Email | Dawdle')

    def _assert_settings_update_email_GET_ok(self, expected_text):
        response = self.client.get(
            url_for('user.settings_update_email_GET'),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data

    #
    # settings_update_email_POST tests.
    #

    def test_settings_update_email_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_update_email_data()
        response = self._send_settings_update_email_POST_request(data)
        assert response.status_code == 200
        assert b'Log In to Dawdle' in response.data

    def test_settings_update_email_POST_invalid_email(self):
        user, password = self.as_new_user()
        email = self.fake.sentence()
        data = self._get_mock_update_email_data(email=email, password=password)
        self._assert_settings_update_email_POST_bad_request(
            user.id,
            user.auth_id,
            data,
            'Please enter a valid email',
        )

    def test_settings_update_email_POST_incorrect_password(self):
        user, _ = self.as_new_user()
        email = self.fake.email()
        data = self._get_mock_update_email_data(email=email, password='wrong')
        self._assert_settings_update_email_POST_bad_request(
            user.id,
            user.auth_id,
            data,
            'Incorrect password',
        )

    def test_settings_update_email_POST_account_already_exists(self):
        user, password = self.as_new_user()
        email = self.user.email
        data = self._get_mock_update_email_data(email=email, password=password)
        self._assert_settings_update_email_POST_bad_request(
            user.id,
            user.auth_id,
            data,
            'There is already an account with this email',
        )

    def test_settings_update_email_POST_no_update(self):
        user, password = self.as_new_user()
        email = user.email
        data = self._get_mock_update_email_data(email=email, password=password)
        self._assert_settings_update_email_POST_ok(
            user.id,
            user.auth_id,
            data,
            updated=False,
        )

    def test_settings_update_email_POST_success(self):
        user, password = self.as_new_user()
        email = self.fake.email()
        data = self._get_mock_update_email_data(email=email, password=password)
        self._assert_settings_update_email_POST_ok(user.id, user.auth_id, data)

    def _get_mock_update_email_data(self, **kwargs):
        return {
            'email': kwargs.get('email', self.fake.email()),
            'password': kwargs.get('password', self.fake.password()),
        }

    def _send_settings_update_email_POST_request(self, data):
        return self.client.post(
            url_for('user.settings_update_email_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_settings_update_email_POST_ok(self,
                                              user_id,
                                              auth_id,
                                              data,
                                              updated=True):
        response = self._send_settings_update_email_POST_request(data)
        user = User.objects(id=user_id).first()
        assert response.status_code == 200
        assert user.email == data['email']
        if updated:
            assert b'A verification email has been sent' in response.data
            assert not user.is_active
            assert user.auth_id != auth_id
            assert user.last_updated
        else:
            assert b'No update needed' in response.data
            assert user.is_active
            assert user.auth_id == auth_id
            assert not user.last_updated

    def _assert_settings_update_email_POST_bad_request(self,
                                                       user_id,
                                                       auth_id,
                                                       data,
                                                       expected_text):
        response = self._send_settings_update_email_POST_request(data)
        user = User.objects(id=user_id).first()
        assert response.status_code == 400
        assert expected_text.encode() in response.data
        assert user.is_active
        assert user.auth_id == auth_id
        assert user.email != data['email']
        assert not user.last_updated

    #
    # settings_update_password_GET tests.
    #

    def test_settings_update_password_GET_not_authenticated(self):
        self.logout()
        self._assert_settings_update_password_GET_ok('Log In to Dawdle')

    def test_settings_update_password_GET_authenticated(self):
        self._assert_settings_update_password_GET_ok(
            'Update Password | Dawdle',
        )

    def _assert_settings_update_password_GET_ok(self, expected_text):
        response = self.client.get(
            url_for('user.settings_update_password_GET'),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data

    #
    # settings_update_password_POST tests.
    #

    def test_settings_update_password_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_update_password_data()
        response = self._send_settings_update_password_POST_request(data)
        assert response.status_code == 200
        assert b'Log In to Dawdle' in response.data

    def test_settings_update_password_POST_incorrect_current_password(self):
        user, _ = self.as_new_user()
        current_password = 'wrong'
        new_password = self.fake.password()
        data = self._get_mock_update_password_data(
            current_password=current_password,
            new_password=new_password,
        )
        self._assert_settings_update_password_POST_bad_request(
            user.auth_id,
            data,
            'Incorrect current password',
        )

    def test_settings_update_password_POST_new_password_less_than_min(self):
        user, password = self.as_new_user()
        current_password = password
        new_password = self.fake.pystr(min_chars=7, max_chars=7)
        data = self._get_mock_update_password_data(
            current_password=current_password,
            new_password=new_password,
        )
        self._assert_settings_update_password_POST_bad_request(
            user.auth_id,
            data,
            'Your new password must be at least 8 characters',
        )

    def test_settings_update_password_POST_new_password_equal_to_min(self):
        user, password = self.as_new_user()
        current_password = password
        new_password = self.fake.pystr(min_chars=8, max_chars=8)
        data = self._get_mock_update_password_data(
            current_password=current_password,
            new_password=new_password,
        )
        self._assert_settings_update_password_POST_ok(
            user.id,
            user.auth_id,
            data,
        )

    def test_settings_update_password_POST_no_update(self):
        user, password = self.as_new_user()
        current_password = password
        new_password = password
        data = self._get_mock_update_password_data(
            current_password=current_password,
            new_password=new_password,
        )
        self._assert_settings_update_password_POST_ok(
            user.id,
            user.auth_id,
            data,
            updated=False,
        )

    def test_settings_update_password_POST_success(self):
        user, password = self.as_new_user()
        current_password = password
        new_password = self.fake.password()
        data = self._get_mock_update_password_data(
            current_password=current_password,
            new_password=new_password,
        )
        self._assert_settings_update_password_POST_ok(
            user.id,
            user.auth_id,
            data,
        )

    def _get_mock_update_password_data(self, **kwargs):
        return {
            'current_password': kwargs.get(
                'current_password',
                self.fake.password(),
            ),
            'new_password': kwargs.get('new_password', self.fake.password()),
        }

    def _send_settings_update_password_POST_request(self, data):
        return self.client.post(
            url_for('user.settings_update_password_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_settings_update_password_POST_ok(self,
                                                 user_id,
                                                 auth_id,
                                                 data,
                                                 updated=True):
        response = self._send_settings_update_password_POST_request(data)
        user = User.objects(id=user_id).first()
        assert response.status_code == 200
        assert user.verify_password(data['new_password'])
        if updated:
            assert b'Your password has been updated' in response.data
            assert user.auth_id != auth_id
            assert user.last_updated
        else:
            assert b'No update needed' in response.data
            assert user.auth_id == auth_id
            assert not user.last_updated

    def _assert_settings_update_password_POST_bad_request(self,
                                                          auth_id,
                                                          data,
                                                          expected_text):
        response = self._send_settings_update_password_POST_request(data)
        user = User.objects(auth_id=auth_id).first()
        assert response.status_code == 400
        assert expected_text.encode() in response.data
        assert not user.last_updated

    #
    # settings_delete_account_GET tests.
    #

    def test_settings_delete_account_GET_not_authenticated(self):
        self.logout()
        self._assert_settings_delete_account_GET_ok('Log In to Dawdle')

    def test_settings_delete_account_GET_authenticated(self):
        self._assert_settings_delete_account_GET_ok('Delete Account | Dawdle')

    def _assert_settings_delete_account_GET_ok(self, expected_text):
        response = self.client.get(
            url_for('user.settings_delete_account_GET'),
            follow_redirects=True,
        )
        assert response.status_code == 200
        assert expected_text.encode() in response.data

    #
    # settings_delete_account_POST tests.
    #

    def test_settings_delete_account_POST_not_authenticated(self):
        self.logout()
        data = self._get_mock_delete_account_data()
        response = self._send_settings_delete_account_POST_request(data)
        assert response.status_code == 200
        assert b'Log In to Dawdle' in response.data

    def test_settings_delete_account_POST_no_password(self):
        self.as_new_user()
        data = self._get_mock_delete_account_data()
        del data['password']
        self._assert_settings_delete_account_POST_bad_request(
            data,
            'Please enter your password',
        )

    def test_settings_delete_account_POST_incorrect_password(self):
        self.as_new_user()
        data = self._get_mock_delete_account_data(password='wrong')
        self._assert_settings_delete_account_POST_bad_request(
            data,
            'Incorrect password',
        )

    def test_settings_delete_account_POST_success(self):
        user, password = self.as_new_user()
        self.login(email=user.email, password=password)
        data = self._get_mock_delete_account_data(password=password)
        self._assert_settings_delete_account_POST_ok(user, data)

    def test_settings_delete_account_POST_with_boards(self):
        user, password = self.as_new_user()
        user.boards = self.create_boards(owner_id=user.id, max_boards=4)
        user.save()
        self.login(email=user.email, password=password)
        data = self._get_mock_delete_account_data(password=password)
        self._assert_settings_delete_account_POST_ok(user, data)

    @mock.patch('dawdle.components.user.utils.mail')
    def test_settings_delete_account_POST_error_sending_email(self, mail_mock):
        mail_mock.send.side_effect = RuntimeError('some error')
        user, password = self.as_new_user()
        data = self._get_mock_delete_account_data(password=password)
        self._assert_settings_delete_account_POST_ok(user, data)

    def _get_mock_delete_account_data(self, **kwargs):
        return {'password': kwargs.get('password', self.fake.password())}

    def _send_settings_delete_account_POST_request(self, data):
        return self.client.post(
            url_for('user.settings_delete_account_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_settings_delete_account_POST_ok(self, user, data):
        assert Board.objects(owner_id=user.id).count() == len(user.boards)
        response = self._send_settings_delete_account_POST_request(data)
        assert response.status_code == 200
        assert b'Your account has been deleted' in response.data
        assert Board.objects(owner_id=user.id).count() == 0

    def _assert_settings_delete_account_POST_bad_request(self,
                                                         data,
                                                         expected_text):
        response = self._send_settings_delete_account_POST_request(data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data
