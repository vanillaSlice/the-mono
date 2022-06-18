"""
Best New Music Digest App.
"""

from best_new_music_digest.dad_joke import get_dad_joke
from best_new_music_digest.email import send_email
from best_new_music_digest.playlist import create_playlists
from best_new_music_digest.scrapers import factory


def run():
    """
    Run the app.
    """

    scrapers = factory.get_scrapers()
    digest = [scraper.scrape() for scraper in scrapers]
    dad_joke = get_dad_joke()
    albums_playlist_url, tracks_playlist_url = create_playlists(digest)
    send_email(digest, dad_joke, albums_playlist_url, tracks_playlist_url)
