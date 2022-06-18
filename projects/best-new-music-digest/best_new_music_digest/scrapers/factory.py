"""
Factory methods to get scrapers.
"""

from best_new_music_digest import settings
from best_new_music_digest.checkpoint import Checkpointer
from best_new_music_digest.scrapers import (pitchfork, sputnikmusic,
                                            the_needle_drop)


def get_scrapers():
    """
    Returns scrapers configured to run.
    """

    scrapers = []

    checkpointer = Checkpointer()

    if settings.PITCHFORK_ALBUMS:
        scrapers.append(pitchfork.AlbumScraper(checkpointer))

    if settings.PITCHFORK_TRACKS:
        scrapers.append(pitchfork.TrackScraper(checkpointer))

    if settings.SPUTNIKMUSIC_ALBUMS:
        scrapers.append(sputnikmusic.AlbumScraper(checkpointer))

    if settings.THE_NEEDLE_DROP_ALBUMS:
        scrapers.append(the_needle_drop.AlbumScraper(checkpointer))

    if settings.THE_NEEDLE_DROP_TRACKS:
        scrapers.append(the_needle_drop.TrackScraper(checkpointer))

    return scrapers
