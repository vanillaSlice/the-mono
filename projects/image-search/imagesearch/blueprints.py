"""
Exports Image Search app blueprints.
"""

from flask import Blueprint, jsonify, redirect

from imagesearch.utils import get_latest_searches, save_search_to_database, search_images

home = Blueprint('home', __name__, url_prefix='/')

@home.route('/')
def index():
    """
    Index route redirects to Swagger UI.
    """

    return redirect('/ui')

@home.route('/search/<terms>')
def search(terms, offset=1):
    """
    Search route performs the image search and returns results.
    """

    images = search_images(terms, offset)
    save_search_to_database(terms)
    return jsonify(images)

@home.route('/latest')
def latest():
    """
    Latest route returns latest performed searches.
    """

    return jsonify(get_latest_searches())
