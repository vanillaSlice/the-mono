# pylint: disable=invalid-name, too-few-public-methods

"""
Pitchfork scrapers.
"""

import requests
from bs4 import BeautifulSoup

from best_new_music_digest.scrapers.base import Scraper


class AlbumScraper(Scraper):
    """
    Pitchfork album scraper.
    """

    __BASE_URL = "https://www.pitchfork.com"
    __SCRAPE_URL = f"{__BASE_URL}/reviews/best/albums/"

    def __init__(self, checkpointer):
        super().__init__(checkpointer, "Pitchfork Albums", self.__SCRAPE_URL, "albums")

    def _get_items(self):
        items = []

        response = requests.get(self.__SCRAPE_URL)
        soup = BeautifulSoup(response.text, "html.parser")

        checkpoint = self._get_checkpoint()

        for div in soup.find_all("div", "review"):
            link = f"{self.__BASE_URL}{div.find('a').get('href')}"

            if link == checkpoint:
                break

            self._save_checkpoint(link)

            item = {
                "artist": " & ".join([li.contents[0] for li in div.find("ul").find_all("li")]),
                "title": div.find("h2").contents[0],
                "link": link,
            }

            items.append(item)

        return items

class TrackScraper(Scraper):
    """
    Pitchfork track scraper.
    """

    __BASE_URL = "https://www.pitchfork.com"
    __SCRAPE_URL = f"{__BASE_URL}/reviews/best/tracks/"

    def __init__(self, checkpointer):
        super().__init__(checkpointer, "Pitchfork Tracks", self.__SCRAPE_URL, "tracks")

    def _get_items(self):
        items = []

        response = requests.get(self.__SCRAPE_URL)
        soup = BeautifulSoup(response.text, "html.parser")

        checkpoint = self._get_checkpoint()

        details = soup.find("div", "track-details")
        link = f"{self.__BASE_URL}{details.find('a').get('href')}"

        if link == checkpoint:
            return []

        self._save_checkpoint(link)

        item = {
            "artist": " & ".join([li.contents[0] for li in details.find("ul").find_all("li")]),
            "title": self.__normalise_title(details.find("h2").contents[0]),
            "link": link,
        }

        items.append(item)

        for details in soup.find_all("a", "track-collection-item__track-link"):
            link = f"{self.__BASE_URL}{details.get('href')}"

            if link == checkpoint:
                break

            items.append({
                "artist": " & ".join([li.contents[0] for li in details.find("ul").find_all("li")]),
                "title": self.__normalise_title(details.find("h2").contents[0]),
                "link": link,
            })

        return items

    @staticmethod
    def __normalise_title(title):
        return title.replace("“", " ", 1).replace("”", " ", 1).replace("’", "'")
