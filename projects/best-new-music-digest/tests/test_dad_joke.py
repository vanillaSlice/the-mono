# pylint: disable=import-outside-toplevel, missing-class-docstring, missing-function-docstring, missing-module-docstring

import requests_mock

from tests import helpers


class TestDadJoke(helpers.TestBase):

    def setUp(self):
        super().setUp()

        from best_new_music_digest import dad_joke
        self.__dad_joke = dad_joke

    def test_get_dad_joke(self):
        response = {"joke": "some-joke"}

        with requests_mock.Mocker() as req_mock:
            req_mock.get("https://icanhazdadjoke.com/",
                         headers={"Accept": "application/json"},
                         json=response)
            joke = self.__dad_joke.get_dad_joke()

        assert joke == "some-joke"

    def test_get_dad_joke_error(self):
        with requests_mock.Mocker() as req_mock:
            req_mock.get("https://icanhazdadjoke.com/",
                         headers={"Accept": "application/json"},
                         status_code=404)
            joke = self.__dad_joke.get_dad_joke()

        assert joke == "It would seem that I've run out of dad jokes. I hope you're happy now 😞."

    def test_get_dad_joke_not_enabled(self):
        self._settings.DAD_JOKE = False

        assert not self.__dad_joke.get_dad_joke()
