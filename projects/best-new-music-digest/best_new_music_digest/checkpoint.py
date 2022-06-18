"""
Checkpointing.
"""

from pymongo import MongoClient

from best_new_music_digest import settings


class Checkpointer:
    """
    Saves and loads checkpoints.
    """

    def __init__(self):
        self.__checkpoints = MongoClient(settings.MONGODB_URI)["best-new-music-digest"].checkpoints

    def get_checkpoint(self, name):
        """
        Returns the checkpoint for a given name (None if it doesn't already exist).
        """

        checkpoint = self.__checkpoints.find_one({"name": name})
        return checkpoint["link"] if checkpoint else None

    def save_checkpoint(self, name, link):
        """
        Saves the checkpoint.
        """

        self.__checkpoints.find_one_and_update(
            {"name": name},
            {"$set": {"link": link}},
            upsert=True,
        )
