import time
from unittest import mock

from flask import url_for
from itsdangerous import TimedJSONWebSignatureSerializer, URLSafeSerializer

from dawdle.components.user.models import User
from tests.test_base import TestBase


class TestAuth(TestBase):

    #
    # sign_up_GET tests.
    #

    def test_sign_up_GET(self):
        response = self.client.get(url_for('auth.sign_up_GET'))
        assert response.status_code == 200
        assert b'Create a Dawdle Account' in response.data

    #
    # sign_up_POST tests.
    #

    def test_sign_up_POST_no_name(self):
        data = self._get_mock_sign_up_data()
        del data['name']
        self._assert_sign_up_POST_bad_request(data, 'Please enter a name')

    def test_sign_up_POST_name_equal_to_min(self):
        name = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_sign_up_data(name=name)
        self._assert_sign_up_POST_ok(data)

    def test_sign_up_POST_name_equal_to_max(self):
        name = self.fake.pystr(min_chars=50, max_chars=50)
        data = self._get_mock_sign_up_data(name=name)
        self._assert_sign_up_POST_ok(data)

    def test_sign_up_POST_name_greater_than_max(self):
        name = self.fake.pystr(min_chars=51, max_chars=51)
        data = self._get_mock_sign_up_data(name=name)
        self._assert_sign_up_POST_bad_request(
            data,
            'Your name must be between 1 and 50 characters',
        )

    def test_sign_up_POST_no_email(self):
        data = self._get_mock_sign_up_data()
        del data['email']
        self._assert_sign_up_POST_bad_request(data, 'Please enter an email')

    def test_sign_up_POST_invalid_email(self):
        email = self.fake.sentence()
        data = self._get_mock_sign_up_data(email=email)
        self._assert_sign_up_POST_bad_request(
            data,
            'Please enter a valid email',
        )

    def test_sign_up_POST_no_password(self):
        data = self._get_mock_sign_up_data()
        del data['password']
        self._assert_sign_up_POST_bad_request(data, 'Please enter a password')

    def test_sign_up_POST_password_less_than_min(self):
        password = self.fake.pystr(min_chars=7, max_chars=7)
        data = self._get_mock_sign_up_data(password=password)
        self._assert_sign_up_POST_bad_request(
            data,
            'Your password must be at least 8 characters',
        )

    def test_sign_up_POST_password_equal_to_min(self):
        password = self.fake.pystr(min_chars=8, max_chars=8)
        data = self._get_mock_sign_up_data(password=password)
        self._assert_sign_up_POST_ok(data)

    def test_sign_up_POST_account_already_exists(self):
        email = self.user.email
        data = self._get_mock_sign_up_data(email=email)
        self._assert_sign_up_POST_bad_request(
            data,
            'There is already an account with this email',
        )

    @mock.patch('dawdle.components.auth.utils.mail')
    def test_sign_up_POST_error_sending_email(self, mail_mock):
        mail_mock.send.side_effect = RuntimeError('some error')
        data = self._get_mock_sign_up_data()
        self._assert_sign_up_POST_ok(data, sent_email=False)

    def test_sign_up_POST_success(self):
        data = self._get_mock_sign_up_data()
        self._assert_sign_up_POST_ok(data)

    def _get_mock_sign_up_data(self, **kwargs):
        return {
            'email': kwargs.get('email', self.fake.email()),
            'name': kwargs.get('name', self.fake.name()),
            'password': kwargs.get('password', self.fake.password()),
        }

    def _send_sign_up_POST_request(self, data):
        return self.client.post(
            url_for('auth.sign_up_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_sign_up_POST_ok(self, data, sent_email=True):
        response = self._send_sign_up_POST_request(data)
        user = User.objects(email=data['email']).first()
        assert response.status_code == 200
        if sent_email:
            assert b'A verification email has been sent' in response.data
        else:
            assert b'Could not send a verification email' in response.data
        assert not user.is_active
        assert user.initials
        assert user.name == data['name']
        assert user.verify_password(data['password'])

    def _assert_sign_up_POST_bad_request(self, data, expected_text):
        response = self._send_sign_up_POST_request(data)
        user = User.objects(email=data.get('email')).first()
        assert response.status_code == 400
        assert expected_text.encode() in response.data
        assert not user or user == self.user

    #
    # verify_resend_GET tests.
    #

    def test_verify_resend_GET(self):
        response = self.client.get(url_for('auth.verify_resend_GET'))
        assert response.status_code == 200
        assert b'Verify Your Dawdle Account' in response.data

    def test_verify_resend_GET_with_email(self):
        email = self.fake.email()
        response = self.client.get(
            url_for('auth.verify_resend_GET', email=email),
        )
        assert response.status_code == 200
        assert b'Verify Your Dawdle Account' in response.data
        assert email.encode() in response.data

    #
    # verify_resend_POST tests.
    #

    def test_verify_resend_POST_no_email(self):
        data = self._get_mock_verify_resend_data()
        del data['email']
        self._assert_verify_resend_POST_bad_request(
            data,
            'Please enter an email',
        )

    def test_verify_resend_POST_email_in_request(self):
        user = self.create_user(active=False)
        data = self._get_mock_verify_resend_data()
        del data['email']
        self._assert_verify_resend_POST_ok(data, email=user.email)

    def test_verify_resend_POST_invalid_email(self):
        email = self.fake.sentence()
        data = self._get_mock_verify_resend_data(email=email)
        self._assert_verify_resend_POST_bad_request(
            data,
            'Please enter a valid email',
        )

    def test_verify_resend_POST_account_does_not_exist(self):
        user = self.create_user()
        user.delete()
        data = self._get_mock_verify_resend_data(email=user.email)
        self._assert_verify_resend_POST_bad_request(
            data,
            'There is no account with this email',
        )

    def test_verify_resend_POST_account_already_verified(self):
        data = self._get_mock_verify_resend_data(email=self.user.email)
        self._assert_verify_resend_POST_bad_request(
            data,
            'This account has already been verified',
        )

    @mock.patch('dawdle.components.auth.utils.mail')
    def test_verify_resend_POST_error_sending_email(self, mail_mock):
        mail_mock.send.side_effect = RuntimeError('some error')
        user = self.create_user(active=False)
        data = self._get_mock_verify_resend_data(email=user.email)
        self._assert_verify_resend_POST_internal_server_error(data)

    def test_verify_resend_POST_success(self):
        user = self.create_user(active=False)
        data = self._get_mock_verify_resend_data(email=user.email)
        self._assert_verify_resend_POST_ok(data)

    def _get_mock_verify_resend_data(self, **kwargs):
        return {'email': kwargs.get('email', self.fake.email())}

    def _send_verify_resend_POST_request(self, data, email=None):
        return self.client.post(
            url_for('auth.verify_resend_POST', email=email),
            data=data,
            follow_redirects=True,
        )

    def _assert_verify_resend_POST_ok(self, data, email=None):
        response = self._send_verify_resend_POST_request(data, email)
        assert response.status_code == 200
        assert b'A verification email has been sent' in response.data

    def _assert_verify_resend_POST_bad_request(self, data, expected_text):
        response = self._send_verify_resend_POST_request(data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    def _assert_verify_resend_POST_internal_server_error(self, data):
        response = self._send_verify_resend_POST_request(data)
        assert response.status_code == 500
        assert b'Could not send a verification email' in response.data

    #
    # verify_GET tests.
    #

    def test_verify_GET_invalid_token(self):
        user = self.create_user(active=False)
        token = 'invalid token'
        self._assert_verify_GET_not_found(token, user.email)

    def test_verify_GET_invalid_auth_id(self):
        user = self.create_user(active=False)
        token = self._get_verify_token('invalid auth id')
        self._assert_verify_GET_not_found(token, user.email)

    def test_verify_GET_account_does_not_exist(self):
        user = self.create_user(active=False)
        user.delete()
        token = self._get_verify_token(str(user.auth_id))
        self._assert_verify_GET_not_found(token, user.email)

    def test_verify_GET_bad_redirect_target(self):
        user = self.create_user(active=False)
        token = self._get_verify_token(str(user.auth_id))
        self._assert_verify_GET_bad_request(token, 'https://github.com/')

    def test_verify_GET_success(self):
        user = self.create_user(active=False)
        token = self._get_verify_token(str(user.auth_id))
        self._assert_verify_GET_ok(token, user.email)

    def _get_verify_token(self, auth_id):
        return URLSafeSerializer(self.app.secret_key).dumps(auth_id)

    def _send_verify_GET_request(self, token, redirect_target=None):
        return self.client.get(
            url_for('auth.verify_GET', token=token, next=redirect_target),
            follow_redirects=True,
        )

    def _assert_verify_GET_ok(self, token, email):
        response = self._send_verify_GET_request(token)
        user = User.objects(email=email).first()
        assert response.status_code == 200
        assert b'Personal Boards' in response.data
        assert user.is_active
        assert user.last_updated

    def _assert_verify_GET_not_found(self, token, email):
        response = self._send_verify_GET_request(token)
        user = User.objects(email=email).first()
        assert response.status_code == 404
        assert b'Not Found' in response.data
        assert not user or not user.is_active

    def _assert_verify_GET_bad_request(self, token, redirect_target):
        response = self._send_verify_GET_request(token, redirect_target)
        assert response.status_code == 400
        assert b'Bad Request' in response.data

    #
    # login_GET tests.
    #

    def test_login_GET(self):
        response = self.client.get(url_for('auth.login_GET'))
        assert response.status_code == 200
        assert b'Log In to Dawdle' in response.data

    #
    # login_POST tests.
    #

    def test_login_POST_no_email(self):
        data = self._get_mock_login_data()
        del data['email']
        self._assert_login_POST_bad_request(data, 'Please enter an email')

    def test_login_POST_invalid_email(self):
        email = self.fake.sentence()
        data = self._get_mock_login_data(email=email)
        self._assert_login_POST_bad_request(data, 'Please enter a valid email')

    def test_login_POST_no_password(self):
        data = self._get_mock_login_data()
        del data['password']
        self._assert_login_POST_bad_request(data, 'Please enter a password')

    def test_login_POST_account_does_not_exist(self):
        user = self.create_user(password='user password')
        user.delete()
        data = self._get_mock_login_data(
            email=user.email,
            password=user.password,
        )
        self._assert_login_POST_bad_request(data, 'Incorrect email')

    def test_login_POST_incorrect_password(self):
        password = 'incorrect password'
        data = self._get_mock_login_data(
            email=self.user.email,
            password=password,
        )
        self._assert_login_POST_bad_request(data, 'Incorrect password')

    def test_login_POST_inactive_account(self):
        password = 'user password'
        user = self.create_user(password=password, active=False)
        data = self._get_mock_login_data(email=user.email, password=password)
        self._assert_login_POST_bad_request(
            data,
            'Please verify your email before logging in',
        )

    def test_login_POST_bad_redirect_target(self):
        data = self._get_mock_login_data(
            email=self.user.email,
            password=self.password,
        )
        self._assert_login_POST_bad_request(
            data,
            'Bad Request',
            'https://github.com/',
        )

    def test_login_POST_success(self):
        data = self._get_mock_login_data(
            email=self.user.email,
            password=self.password,
        )
        self._assert_login_POST_ok(data)

    def _get_mock_login_data(self, **kwargs):
        return {
            'email': kwargs.get('email', self.fake.email()),
            'password': kwargs.get('password', self.fake.password()),
        }

    def _send_login_POST_request(self, data, redirect_target=None):
        return self.client.post(
            url_for('auth.login_POST', next=redirect_target),
            data=data,
            follow_redirects=True,
        )

    def _assert_login_POST_ok(self, data):
        response = self._send_login_POST_request(data)
        assert response.status_code == 200
        assert b'Personal Boards' in response.data

    def _assert_login_POST_bad_request(self,
                                       data,
                                       expected_text,
                                       redirect_target=None):
        response = self._send_login_POST_request(data, redirect_target)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    #
    # logout_GET tests.
    #

    def test_logout_GET_success(self):
        response = self.client.get(
            url_for('auth.logout_GET'),
            follow_redirects=True
        )
        assert response.status_code == 200
        assert b'You have been logged out' in response.data

    #
    # reset_password_request_GET tests.
    #

    def test_reset_password_request_GET_not_authenticated(self):
        self.logout()
        self._assert_reset_password_request_GET_ok()

    def test_reset_password_request_GET_authenticated(self):
        self._assert_reset_password_request_GET_ok()

    def _assert_reset_password_request_GET_ok(self):
        response = self.client.get(url_for('auth.reset_password_request_GET'))
        assert response.status_code == 200
        assert b'Reset Your Dawdle Password' in response.data
        assert self.logged_in == (self.user.email.encode() in response.data)

    #
    # reset_password_request_POST tests.
    #

    def test_reset_password_request_POST_no_email_not_authenticated(self):
        self.logout()
        data = self._get_mock_reset_password_request_data()
        del data['email']
        self._assert_reset_password_request_POST_bad_request(
            data,
            'Please enter an email',
        )

    def test_reset_password_request_POST_no_email_authenticated(self):
        data = self._get_mock_reset_password_request_data()
        del data['email']
        self._assert_reset_password_request_POST_ok(data)

    def test_reset_password_request_POST_invalid_email(self):
        email = self.fake.sentence()
        data = self._get_mock_reset_password_request_data(email=email)
        self._assert_reset_password_request_POST_bad_request(
            data,
            'Please enter a valid email',
        )

    def test_reset_password_request_POST_account_does_not_exist(self):
        user = self.create_user(active=False)
        user.delete()
        data = self._get_mock_reset_password_request_data(email=user.email)
        self._assert_reset_password_request_POST_bad_request(
            data,
            'There is no account with this email',
        )

    @mock.patch('dawdle.components.auth.utils.mail')
    def test_reset_password_request_POST_error_sending_email(self, mail_mock):
        mail_mock.send.side_effect = RuntimeError('some error')
        user = self.create_user(active=False)
        data = self._get_mock_reset_password_request_data(email=user.email)
        self._assert_reset_password_request_POST_internal_server_error(data)

    def test_reset_password_request_POST_success(self):
        user = self.create_user(active=False)
        data = self._get_mock_reset_password_request_data(email=user.email)
        self._assert_reset_password_request_POST_ok(data)

    def _get_mock_reset_password_request_data(self, **kwargs):
        return {'email': kwargs.get('email', self.fake.email())}

    def _assert_reset_password_request_POST_response(self, data, status_code):
        self.logout()
        response = self.client.post(
            url_for('auth.reset_password_request_POST'),
            data=data,
        )
        assert response.status_code == status_code

    def _send_reset_password_request_POST_request(self, data):
        return self.client.post(
            url_for('auth.reset_password_request_POST'),
            data=data,
            follow_redirects=True,
        )

    def _assert_reset_password_request_POST_ok(self, data):
        response = self._send_reset_password_request_POST_request(data)
        assert response.status_code == 200
        assert b'A password reset email has been sent' in response.data

    def _assert_reset_password_request_POST_bad_request(self,
                                                        data,
                                                        expected_text):
        response = self._send_reset_password_request_POST_request(data)
        assert response.status_code == 400
        assert expected_text.encode() in response.data

    def _assert_reset_password_request_POST_internal_server_error(self, data):
        response = self._send_reset_password_request_POST_request(data)
        assert response.status_code == 500
        assert b'Could not send a password reset email' in response.data

    #
    # reset_password_GET tests.
    #

    def test_reset_password_GET(self):
        token = self._get_reset_password_token(auth_id=str(self.user.auth_id))
        self._assert_reset_password_GET_ok(token)

    def test_reset_password_GET_invalid_token(self):
        token = 'invalid token'
        self._assert_reset_password_GET_not_found(token)

    def test_reset_password_GET_invalid_auth_id(self):
        token = self._get_reset_password_token('invalid auth id')
        self._assert_reset_password_GET_not_found(token)

    def test_reset_password_GET_expired_token(self):
        token = self._get_reset_password_token(
            auth_id=str(self.user.auth_id),
            expires_in=1,
        )
        time.sleep(2)
        self._assert_reset_password_GET_not_found(token)

    def test_reset_password_GET_account_does_not_exist(self):
        user = self.create_user(active=False)
        user.delete()
        token = self._get_reset_password_token(str(user.auth_id))
        self._assert_reset_password_GET_not_found(token)

    def _get_reset_password_token(self, auth_id, expires_in=3600):
        return TimedJSONWebSignatureSerializer(
            self.app.secret_key,
            expires_in,
        ).dumps(auth_id).decode('utf-8')

    def _send_reset_password_GET_request(self, token):
        return self.client.get(
            url_for('auth.reset_password_GET', token=token),
        )

    def _assert_reset_password_GET_ok(self, token):
        response = self._send_reset_password_GET_request(token)
        assert response.status_code == 200
        assert b'Reset Your Dawdle Password' in response.data

    def _assert_reset_password_GET_not_found(self, token):
        response = self._send_reset_password_GET_request(token)
        assert response.status_code == 404
        assert b'Not Found' in response.data

    #
    # reset_password_POST tests.
    #

    def test_reset_password_POST_invalid_token(self):
        data = self._get_mock_reset_password_data()
        token = 'invalid token'
        self._assert_reset_password_POST_not_found(token, data)

    def test_reset_password_POST_invalid_auth_id(self):
        data = self._get_mock_reset_password_data()
        token = self._get_reset_password_token('invalid auth id')
        self._assert_reset_password_POST_not_found(token, data)

    def test_reset_password_POST_expired_token(self):
        data = self._get_mock_reset_password_data()
        token = self._get_reset_password_token(
            auth_id=str(self.user.auth_id),
            expires_in=1,
        )
        time.sleep(2)
        self._assert_reset_password_POST_not_found(token, data)

    def test_reset_password_POST_account_does_not_exist(self):
        data = self._get_mock_reset_password_data()
        user = self.create_user(active=False)
        user.delete()
        token = self._get_reset_password_token(str(user.auth_id))
        self._assert_reset_password_POST_not_found(token, data)

    def test_reset_password_POST_no_password(self):
        user = self.create_user()
        data = self._get_mock_reset_password_data()
        del data['new_password']
        self._assert_reset_password_POST_bad_request(
            user.auth_id,
            data,
            'Please enter a new password',
        )

    def test_reset_password_POST_password_less_than_min(self):
        user = self.create_user()
        new_password = self.fake.pystr(min_chars=7, max_chars=7)
        data = self._get_mock_reset_password_data(
            new_password=new_password,
        )
        self._assert_reset_password_POST_bad_request(
            user.auth_id,
            data,
            'Your new password must be at least 8 characters',
        )

    def test_reset_password_POST_password_equal_to_min(self):
        user = self.create_user()
        new_password = self.fake.pystr(min_chars=8, max_chars=8)
        data = self._get_mock_reset_password_data(
            new_password=new_password,
        )
        self._assert_reset_password_POST_ok(user.id, user.auth_id, data)

    def test_reset_password_POST_success(self):
        user = self.create_user()
        new_password = self.fake.password()
        data = self._get_mock_reset_password_data(
            new_password=new_password,
        )
        self._assert_reset_password_POST_ok(user.id, user.auth_id, data)

    def _get_mock_reset_password_data(self, **kwargs):
        return {
            'new_password': kwargs.get('new_password', self.fake.password()),
        }

    def _send_reset_password_POST_request(self, data, token):
        return self.client.post(
            url_for('auth.reset_password_POST', token=token),
            data=data,
            follow_redirects=True,
        )

    def _assert_reset_password_POST_ok(self, user_id, auth_id, data):
        token = self._get_reset_password_token(auth_id=str(auth_id))
        response = self._send_reset_password_POST_request(data, token)
        user = User.objects(id=user_id).first()
        assert response.status_code == 200
        assert b'Your password has been reset' in response.data
        assert user.auth_id != auth_id
        assert user.last_updated
        assert user.verify_password(data['new_password'])

    def _assert_reset_password_POST_bad_request(self,
                                                auth_id,
                                                data,
                                                expected_text):
        token = self._get_reset_password_token(auth_id=str(auth_id))
        response = self._send_reset_password_POST_request(data, token)
        user = User.objects(auth_id=auth_id).first()
        assert response.status_code == 400
        assert expected_text.encode() in response.data
        assert not user.last_updated
        assert not user.verify_password(data.get('new_password'))

    def _assert_reset_password_POST_not_found(self, token, data):
        response = self._send_reset_password_POST_request(data, token)
        assert response.status_code == 404
        assert b'Not Found' in response.data
