# pylint: disable=import-outside-toplevel, missing-class-docstring, missing-function-docstring, missing-module-docstring

from unittest.mock import patch

import requests_mock

from tests import helpers


class TestApp(helpers.TestBase):

    def setUp(self):
        super().setUp()

        from best_new_music_digest import app
        self.__app = app

    @patch("best_new_music_digest.app.send_email")
    @patch("best_new_music_digest.app.create_playlists")
    @patch("best_new_music_digest.scrapers.factory.Checkpointer")
    def test_run(self, checkpointer, create_playlists, send_email):
        checkpointer.return_value = self._checkpointer

        create_playlists.return_value = "some-albums-playlist-url", "some-tracks-playlist-url"

        with requests_mock.Mocker() as req_mock:
            req_mock.get("https://www.pitchfork.com/reviews/best/albums/",
                         text=self._load_test_data("pitchfork_albums_input.html"))

            req_mock.get("https://www.pitchfork.com/reviews/best/tracks/",
                         text=self._load_test_data("pitchfork_tracks_input.html"))

            req_mock.get("https://www.sputnikmusic.com/bestnewmusic",
                         text=self._load_test_data("sputnikmusic_albums_input.html"))

            req_mock.get("https://www.googleapis.com/youtube/v3/playlistItems?" \
                         "part=snippet&playlistId=PLP4CSgl7K7oo93I49tQa0TLB8qY3u7xuO&" \
                         f"key={self._settings.YOUTUBE_API_KEY}",
                         text=self._load_test_data("the_needle_drop_albums_input.json"))

            req_mock.get("https://www.googleapis.com/youtube/v3/playlistItems?" \
                         "part=snippet&playlistId=PLP4CSgl7K7or84AAhr7zlLNpghEnKWu2c&" \
                         f"key={self._settings.YOUTUBE_API_KEY}",
                         text=self._load_test_data("the_needle_drop_tracks_input.json"))

            req_mock.get("https://icanhazdadjoke.com/",
                         headers={"Accept": "application/json"},
                         json={"joke": "some dad joke"})

            self.__app.run()

        digest = [
            self._load_json_test_data("pitchfork_albums_output_without_checkpoint.json"),
            self._load_json_test_data("pitchfork_tracks_output_without_checkpoint.json"),
            self._load_json_test_data("sputnikmusic_albums_output_without_checkpoint.json"),
            self._load_json_test_data("the_needle_drop_albums_output_without_checkpoint.json"),
            self._load_json_test_data("the_needle_drop_tracks_output_without_checkpoint.json"),
        ]

        create_playlists.assert_called_with(digest)

        send_email.assert_called_with(
            digest,
            "some dad joke",
            "some-albums-playlist-url",
            "some-tracks-playlist-url",
        )
