# pylint: disable=import-outside-toplevel, missing-class-docstring, missing-function-docstring, missing-module-docstring, too-many-arguments

from unittest.mock import patch

from freezegun import freeze_time

from tests import helpers


@freeze_time("2020-01-01")
class TestEmail(helpers.TestBase):

    def setUp(self):
        super().setUp()

        from best_new_music_digest import email
        self.__email = email

    @patch("best_new_music_digest.email.SendGridAPIClient.send")
    def test_send_email_no_digest_no_errors(self, send):
        digest = [
            {
                "items": [],
                "errors": False,
            },
            {
                "items": [],
                "errors": False,
            },
        ]

        self.__test_send(send, digest, expect_called=False)

    @patch("best_new_music_digest.email.SendGridAPIClient.send")
    def test_send_email_no_digest_no_errors_always_send(self, send):
        self._settings.ALWAYS_EMAIL = True

        digest = [
            {
                "items": [],
                "errors": False,
            },
            {
                "items": [],
                "errors": False,
            },
        ]

        self.__test_send(send, digest)

    @patch("best_new_music_digest.email.SendGridAPIClient.send")
    def test_send_email_no_digest_with_errors(self, send):
        digest = [
            {
                "items": [],
                "errors": False,
            },
            {
                "items": [],
                "errors": True,
            },
        ]

        self.__test_send(send, digest)

    @patch("best_new_music_digest.email.SendGridAPIClient.send")
    def test_send_email_digest(self, send):
        digest = [
            self._load_json_test_data("the_needle_drop_albums_output_with_checkpoint.json"),
            self._load_json_test_data("the_needle_drop_tracks_output_with_checkpoint.json"),
        ]

        self.__test_send(send, digest)

    @patch("best_new_music_digest.email.SendGridAPIClient.send")
    def test_send_email_error(self, send):
        send.side_effect = self._raise_exception

        digest = [
            self._load_json_test_data("the_needle_drop_albums_output_with_checkpoint.json"),
            self._load_json_test_data("the_needle_drop_tracks_output_with_checkpoint.json"),
        ]

        self.__email.send_email(digest)

    def __test_send(self,
                    send,
                    digest,
                    dad_joke="some-dad-joke",
                    albums_playlist_url="some-albums-playlist-url",
                    tracks_playlist_url="some-tracks-playlist-url",
                    expect_called=True):
        self.__email.send_email(digest, dad_joke, albums_playlist_url, tracks_playlist_url)

        if not expect_called:
            send.assert_not_called()
            return

        message = send.call_args[0][0]

        assert message.from_email.name == self._settings.SENDER_NAME
        assert message.from_email.email == self._settings.SENDER_EMAIL
        assert message.personalizations[0].tos[0]["name"] == self._settings.RECIPIENT_EMAIL
        assert message.template_id.template_id == self._settings.SENDGRID_TEMPLATE_ID

        expected_template_data = {
            "date": "01/01/2020",
            "dad_joke": dad_joke,
            "digest": digest,
            "albums_playlist_url": albums_playlist_url,
            "tracks_playlist_url": tracks_playlist_url,
        }

        assert message.personalizations[0].dynamic_template_data == expected_template_data
