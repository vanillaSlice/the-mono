from unittest import mock

from flask import url_for

from tests.test_base import TestBase


class TestContact(TestBase):

    #
    # index_GET tests.
    #

    def test_index_GET_not_authenticated(self):
        self.logout()
        self._assert_index_GET_ok()

    def test_index_GET_authenticated(self):
        self._assert_index_GET_ok()

    def _assert_index_GET_ok(self):
        response = self.client.get(url_for('contact.index_GET'))
        assert response.status_code == 200
        assert b'Contact Us' in response.data
        assert self.logged_in == (self.user.email.encode() in response.data)

    #
    # index_POST tests.
    #

    def test_index_POST_no_email_not_authenticated(self):
        self.logout()
        data = self._get_mock_contact_data()
        del data['email']
        self._assert_index_POST_bad_request(data, 'Please enter an email')

    def test_index_POST_no_email_authenticated(self):
        data = self._get_mock_contact_data()
        del data['email']
        self._assert_index_POST_ok(data)

    def test_index_POST_invalid_email(self):
        email = self.fake.sentence()
        data = self._get_mock_contact_data(email=email)
        self._assert_index_POST_bad_request(
            data,
            'Please enter a valid email',
        )

    def test_index_POST_no_subject(self):
        data = self._get_mock_contact_data()
        del data['subject']
        self._assert_index_POST_bad_request(data, 'Please enter a subject')

    def test_index_POST_subject_equal_to_min(self):
        subject = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_contact_data(subject=subject)
        self._assert_index_POST_ok(data)

    def test_index_POST_subject_equal_to_max(self):
        subject = self.fake.pystr(min_chars=256, max_chars=256)
        data = self._get_mock_contact_data(subject=subject)
        self._assert_index_POST_ok(data)

    def test_index_POST_subject_greater_than_max(self):
        subject = self.fake.pystr(min_chars=257, max_chars=257)
        data = self._get_mock_contact_data(subject=subject)
        self._assert_index_POST_bad_request(
            data,
            'Subject must be between 1 and 256 characters',
        )

    def test_index_POST_no_message(self):
        data = self._get_mock_contact_data()
        del data['message']
        self._assert_index_POST_bad_request(data, 'Please enter a message')

    def test_index_POST_message_equal_to_min(self):
        message = self.fake.pystr(min_chars=1, max_chars=1)
        data = self._get_mock_contact_data(message=message)
        self._assert_index_POST_ok(data)

    def test_index_POST_message_equal_to_max(self):
        message = self.fake.pystr(min_chars=1000, max_chars=1000)
        data = self._get_mock_contact_data(message=message)
        self._assert_index_POST_ok(data)

    def test_index_POST_message_greater_than_max(self):
        message = self.fake.pystr(min_chars=1001, max_chars=1001)
        data = self._get_mock_contact_data(message=message)
        self._assert_index_POST_bad_request(
            data,
            'Message must be between 1 and 1000 characters',
        )

    @mock.patch('dawdle.components.contact.utils.mail')
    def test_index_POST_error_sending_email(self, mail_mock):
        mail_mock.send.side_effect = RuntimeError('some error')
        data = self._get_mock_contact_data()
        self._assert_index_POST_internal_server_error(data)

    def test_index_POST_success(self):
        data = self._get_mock_contact_data()
        self._assert_index_POST_ok(data)

    def _get_mock_contact_data(self, **kwargs):
        return {
            'email': kwargs.get('email', self.fake.email()),
            'subject': kwargs.get('subject', self.fake.sentence()),
            'message': kwargs.get('message', self.fake.sentences()),
        }

    def _assert_index_POST_response(self, data, status_code, expected_text):
        response = self.client.post(
            url_for('contact.index_POST'),
            data=data,
            follow_redirects=True,
        )
        assert response.status_code == status_code
        assert expected_text.encode() in response.data

    def _assert_index_POST_ok(self, data):
        self._assert_index_POST_response(
            data,
            200,
            'We have received your message',
        )

    def _assert_index_POST_bad_request(self, data, expected_text):
        self._assert_index_POST_response(data, 400, expected_text)

    def _assert_index_POST_internal_server_error(self, data):
        self._assert_index_POST_response(data, 500, 'Could not send message')
