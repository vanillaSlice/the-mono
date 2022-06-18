# pylint: disable=invalid-name, too-few-public-methods

"""
sputnikmusic scrapers.
"""

import requests
from bs4 import BeautifulSoup

from best_new_music_digest.scrapers.base import Scraper


class AlbumScraper(Scraper):
    """
    sputnikmusic album scraper.
    """

    __BASE_URL = "https://www.sputnikmusic.com"
    __SCRAPE_URL = f"{__BASE_URL}/bestnewmusic"

    def __init__(self, checkpointer):
        super().__init__(checkpointer, "Sputnikmusic Albums", self.__SCRAPE_URL, "albums")

    def _get_items(self):
        items = []

        response = requests.get(self.__SCRAPE_URL)
        soup = BeautifulSoup(response.text, "html.parser")

        checkpoint = self._get_checkpoint()

        for td in soup.find_all("td", "bestnewmusic"):
            a = td.find("a")
            link = f"{self.__BASE_URL}{a.get('href')}"

            if link == checkpoint:
                break

            self._save_checkpoint(link)

            item = {
                "artist": a.find("strong").contents[0],
                "title": a.find_all("font")[1].contents[1],
                "link": link,
            }

            items.append(item)

        return items
