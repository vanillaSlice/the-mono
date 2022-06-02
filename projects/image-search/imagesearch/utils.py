"""
Exports reusable util functions.
"""

from flask import current_app as app
import requests

from imagesearch.models import SearchEntry

def search_images(terms, offset):
    """
    Performs an image search and returns the results.
    """

    payload = {'key': app.config['GOOGLE_API_KEY'],
               'cx': app.config['GOOGLE_CSE_ID'],
               'q': terms,
               'start': offset,
               'searchType': 'image'}
    response_json = requests.get('https://www.googleapis.com/customsearch/v1', params=payload).json()

    images = []

    if not 'items' in response_json:
        return images

    for item in response_json['items']:
        images.append({
            'url': item['link'],
            'snippet': item['snippet'],
            'thumbnail': item['image']['thumbnailLink'],
            'context': item['image']['contextLink']
        })

    return images

def save_search_to_database(terms):
    """
    Saves search to database.
    """

    SearchEntry(terms).save()

def get_latest_searches():
    """
    Returns latest performed searches.
    """

    latest_searches = SearchEntry.objects.order_by('-when').limit(10)
    latest_searches_as_json = []
    for search in latest_searches:
        latest_searches_as_json.append(search.to_json())
    return latest_searches_as_json
