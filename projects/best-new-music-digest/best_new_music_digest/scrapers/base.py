# pylint: disable=broad-except, no-self-use, too-few-public-methods

"""
Base scrapers.
"""


class Scraper:
    """
    Base scraper.
    """

    def __init__(self, checkpointer, title, link, scraper_type):
        self.__checkpointer = checkpointer
        self.__title = title
        self.__link = link
        self.__type = scraper_type
        self.__saved_checkpoint = False

    def scrape(self):
        """
        Scrapes music information.
        """

        print(f"Running {self.__title} scraper")

        errors = False

        try:
            items = self._get_items()
            self.__sanitise_items(items)
        except Exception as exception:
            print("Failed to run successfully")
            print(exception)
            items = []
            errors = True

        print(f"Found {len(items)} new {self.__type}")

        return {
            "title": self.__title,
            "link": self.__link,
            "items": items,
            "errors": errors,
            "type": self.__type,
        }

    @staticmethod
    def __sanitise_items(items):
        def remove_extra_whitespace(string):
            return " ".join(string.split())

        for item in items:
            item["artist"] = remove_extra_whitespace(item["artist"])
            item["title"] = remove_extra_whitespace(item["title"])
            item["link"] = remove_extra_whitespace(item["link"])

    def _get_items(self):
        return []

    def _get_checkpoint(self):
        return self.__checkpointer.get_checkpoint(self.__title)

    def _save_checkpoint(self, link):
        if not self.__saved_checkpoint:
            self.__checkpointer.save_checkpoint(self.__title, link)
            self.__saved_checkpoint = True

    def get_title(self):
        """
        Return scraper title.
        """

        return self.__title
