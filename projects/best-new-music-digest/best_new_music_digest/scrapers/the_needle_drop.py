# pylint: disable=too-few-public-methods

"""
The Needle Drop scrapers.
"""

import re

import requests

from best_new_music_digest import settings
from best_new_music_digest.scrapers.base import Scraper


class AlbumScraper(Scraper):
    """
    The Needle Drop album scraper.
    """

    __BASE_URL = "https://www.youtube.com"

    def __init__(self, checkpointer):
        super().__init__(checkpointer,
                         "The Needle Drop Albums",
                         f"{self.__BASE_URL}/user/theneedledrop",
                         "albums")

    def _get_items(self):
        items = []

        response = requests.get("https://www.googleapis.com/youtube/v3/playlistItems?" \
                                "part=snippet&playlistId=PLP4CSgl7K7oo93I49tQa0TLB8qY3u7xuO&" \
                                f"key={settings.YOUTUBE_API_KEY}")

        checkpoint = self._get_checkpoint()

        for response_item in response.json()["items"]:
            snippet = response_item["snippet"]
            link = f"{self.__BASE_URL}/watch?v={snippet['resourceId']['videoId']}"

            if link == checkpoint:
                break

            self._save_checkpoint(link)

            video_title = snippet["title"].split(" - ")

            item = {
                "artist": video_title[0],
                "title": video_title[1].replace("ALBUM REVIEW", ""),
                "link": link,
            }

            items.append(item)

        return items

class TrackScraper(Scraper):
    """
    The Needle Drop track scraper.
    """

    __BASE_URL = "https://www.youtube.com"

    def __init__(self, checkpointer):
        super().__init__(checkpointer,
                         "The Needle Drop Tracks",
                         f"{self.__BASE_URL}/user/theneedledrop",
                         "tracks")
        self._pattern = re.compile(r"!!!BEST TRACKS THIS WEEK!!!(.*?)\.\.\.meh\.\.\.", re.DOTALL)

    def _get_items(self):
        items = []

        response = requests.get("https://www.googleapis.com/youtube/v3/playlistItems?" \
                                "part=snippet&playlistId=PLP4CSgl7K7or84AAhr7zlLNpghEnKWu2c&" \
                                f"key={settings.YOUTUBE_API_KEY}")

        checkpoint = self._get_checkpoint()

        for response_item in response.json()["items"]:
            snippet = response_item["snippet"]

            if not snippet["title"].startswith("Weekly Track Roundup"):
                continue

            link = f"{self.__BASE_URL}/watch?v={snippet['resourceId']['videoId']}"

            if link == checkpoint:
                break

            self._save_checkpoint(link)

            for line in self._pattern.findall(snippet["description"])[0].split("\n"):
                if " - " not in line:
                    continue

                video_title = line.split(" - ")

                item = {
                    "artist": video_title[0],
                    "title": video_title[1],
                    "link": link,
                }

                items.append(item)

        return items
