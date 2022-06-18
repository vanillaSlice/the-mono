# pylint: disable=bare-except, import-outside-toplevel, missing-class-docstring, missing-function-docstring, missing-module-docstring

import json
import os
import unittest
from importlib import reload
from unittest.mock import patch

import mongomock


class TestBase(unittest.TestCase):

    def setUp(self):
        self.__set_env_vars()

        from best_new_music_digest import settings
        self._settings = settings

        from best_new_music_digest.checkpoint import Checkpointer
        with patch("best_new_music_digest.checkpoint.MongoClient") as client:
            client.return_value = mongomock.MongoClient()
            self._checkpointer = Checkpointer()

    def tearDown(self):
        self.__set_env_vars()
        try:
            reload(self._settings)
        except:
            pass

    @staticmethod
    def _load_test_data(file_name):
        with open(os.path.join(os.path.dirname(__file__), "test_data", file_name)) as test_data:
            return test_data.read()

    @staticmethod
    def _load_json_test_data(file_name):
        return json.loads(TestBase._load_test_data(file_name))

    @staticmethod
    def _raise_exception():
        raise Exception()

    @staticmethod
    def __set_env_vars():
        os.environ["ALWAYS_EMAIL"] = "false"
        os.environ["CREATE_SPOTIFY_PLAYLISTS"] = "true"
        os.environ["DAD_JOKE"] = "true"
        os.environ["MONGODB_URI"] = "some-mongodb-uri"
        os.environ["PITCHFORK_ALBUMS"] = "true"
        os.environ["PITCHFORK_TRACKS"] = "true"
        os.environ["RECIPIENT_EMAIL"] = "some-recipient-email"
        os.environ["SENDER_EMAIL"] = "some-sender-email"
        os.environ["SENDER_NAME"] = "some-sender-name"
        os.environ["SENDGRID_API_KEY"] = "some-api-key"
        os.environ["SENDGRID_TEMPLATE_ID"] = "some-sendgrid-template-id"
        os.environ["SPOTIFY_CLIENT_ID"] = "some-spotify-client-id"
        os.environ["SPOTIFY_CLIENT_SECRET"] = "some-spotify-client-secret"
        os.environ["SPOTIFY_USERNAME"] = "some-spotify-username"
        os.environ["SPUTNIKMUSIC_ALBUMS"] = "true"
        os.environ["THE_NEEDLE_DROP_ALBUMS"] = "true"
        os.environ["THE_NEEDLE_DROP_TRACKS"] = "true"
        os.environ["YOUTUBE_API_KEY"] = "some-youtube-api-key"
