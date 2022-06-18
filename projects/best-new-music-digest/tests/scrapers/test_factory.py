# pylint: disable=import-outside-toplevel, missing-class-docstring, missing-function-docstring, missing-module-docstring

from tests import helpers


class TestFactory(helpers.TestBase):

    def setUp(self):
        super().setUp()

        self._settings.PITCHFORK_ALBUMS = False
        self._settings.PITCHFORK_TRACKS = False
        self._settings.SPUTNIKMUSIC_ALBUMS = False
        self._settings.THE_NEEDLE_DROP_ALBUMS = False
        self._settings.THE_NEEDLE_DROP_TRACKS = False

        from best_new_music_digest.scrapers import factory
        self.__factory = factory

    def test_get_scrapers_pitchfork_albums(self):
        self._settings.PITCHFORK_ALBUMS = True
        self.__test_get_scrapers(["Pitchfork Albums"])

    def test_get_scrapers_pitchfork_tracks(self):
        self._settings.PITCHFORK_TRACKS = True
        self.__test_get_scrapers(["Pitchfork Tracks"])

    def test_get_scrapers_sputnikmusic_albums(self):
        self._settings.SPUTNIKMUSIC_ALBUMS = True
        self.__test_get_scrapers(["Sputnikmusic Albums"])

    def test_get_scrapers_the_needle_drop_albums(self):
        self._settings.THE_NEEDLE_DROP_ALBUMS = True
        self.__test_get_scrapers(["The Needle Drop Albums"])

    def test_get_scrapers_the_needle_drop_tracks(self):
        self._settings.THE_NEEDLE_DROP_TRACKS = True
        self.__test_get_scrapers(["The Needle Drop Tracks"])

    def test_get_scrapers_all_scrapers(self):
        self._settings.PITCHFORK_ALBUMS = True
        self._settings.PITCHFORK_TRACKS = True
        self._settings.SPUTNIKMUSIC_ALBUMS = True
        self._settings.THE_NEEDLE_DROP_ALBUMS = True
        self._settings.THE_NEEDLE_DROP_TRACKS = True

        self.__test_get_scrapers([
            "Pitchfork Albums",
            "Pitchfork Tracks",
            "Sputnikmusic Albums",
            "The Needle Drop Albums",
            "The Needle Drop Tracks",
        ])

    def __test_get_scrapers(self, expected_scrapers):
        actual_scrapers = self.__factory.get_scrapers()

        assert len(actual_scrapers) == len(expected_scrapers)

        for scraper in actual_scrapers:
            assert scraper.get_title() in expected_scrapers
