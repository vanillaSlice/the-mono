# pylint: disable=missing-class-docstring, missing-function-docstring, missing-module-docstring

from tests import helpers


class TestCheckpointer(helpers.TestBase):

    def test_get_checkpoint_new(self):
        assert not self._checkpointer.get_checkpoint("checkpoint-1")

    def test_get_checkpoint_old(self):
        self._checkpointer.save_checkpoint("checkpoint-2", "some-link")
        assert self._checkpointer.get_checkpoint("checkpoint-2") == "some-link"
