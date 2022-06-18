# pylint: disable=missing-class-docstring, missing-function-docstring, missing-module-docstring

import requests_mock

from best_new_music_digest.scrapers import pitchfork
from tests import helpers


class TestAlbumScraper(helpers.TestBase):

    def setUp(self):
        super().setUp()

        self.__scraper = pitchfork.AlbumScraper(self._checkpointer)

    def test_scrape_without_checkpoint(self):
        self.__test_scrape("pitchfork_albums_output_without_checkpoint.json")

    def test_scrape_with_checkpoint(self):
        self._checkpointer.save_checkpoint(
            "Pitchfork Albums",
            "https://www.pitchfork.com/reviews/albums/" \
            "perfume-genius-set-my-heart-on-fire-immediately/"
        )

        self.__test_scrape("pitchfork_albums_output_with_checkpoint.json")

    def test_scrape_up_to_date(self):
        self._checkpointer.save_checkpoint(
            "Pitchfork Albums",
            "https://www.pitchfork.com/reviews/albums/run-the-jewels-rtj4/"
        )

        self.__test_scrape("pitchfork_albums_output_up_to_date.json")

    def test_scrape_saves_checkpoint(self):
        self.__test_scrape("pitchfork_albums_output_without_checkpoint.json")
        self.__test_scrape("pitchfork_albums_output_up_to_date.json")

    def __test_scrape(self, output):
        test_data = self._load_test_data("pitchfork_albums_input.html")

        with requests_mock.Mocker() as req_mock:
            req_mock.get("https://www.pitchfork.com/reviews/best/albums/", text=test_data)
            items = self.__scraper.scrape()

        assert items == self._load_json_test_data(output)

class TestTrackScraper(helpers.TestBase):

    def setUp(self):
        super().setUp()

        self.__scraper = pitchfork.TrackScraper(self._checkpointer)

    def test_scrape_without_checkpoint(self):
        self.__test_scrape("pitchfork_tracks_output_without_checkpoint.json")

    def test_scrape_with_checkpoint(self):
        self._checkpointer.save_checkpoint(
            "Pitchfork Tracks",
            "https://www.pitchfork.com/reviews/tracks/" \
            "megan-thee-stallion-savage-remix-ft-beyonce/"
        )

        self.__test_scrape("pitchfork_tracks_output_with_checkpoint.json")

    def test_scrape_up_to_date(self):
        self._checkpointer.save_checkpoint(
            "Pitchfork Tracks",
            "https://www.pitchfork.com/reviews/tracks/medhane-im-deadass/"
        )

        self.__test_scrape("pitchfork_tracks_output_up_to_date.json")

    def test_scrape_saves_checkpoint(self):
        self.__test_scrape("pitchfork_tracks_output_without_checkpoint.json")
        self.__test_scrape("pitchfork_tracks_output_up_to_date.json")

    def __test_scrape(self, output):
        test_data = self._load_test_data("pitchfork_tracks_input.html")

        with requests_mock.Mocker() as req_mock:
            req_mock.get("https://www.pitchfork.com/reviews/best/tracks/", text=test_data)
            items = self.__scraper.scrape()

        assert items == self._load_json_test_data(output)
