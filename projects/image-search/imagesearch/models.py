"""
Exports Image Search app data models.
"""

from datetime import datetime

from mongoengine import DateTimeField, Document, StringField

class SearchEntry(Document):
    """
    Search entry used in database.
    """

    terms = StringField()
    when = DateTimeField(default=datetime.utcnow)

    meta = {'collection': 'searches'}

    def to_json(self, *args, **kwargs):
        return {
            'terms': self.terms,
            'when': self.when.isoformat()
        }
