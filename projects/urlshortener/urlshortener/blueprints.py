"""
Exports URL Shortener app blueprints.
"""

import re

from flask import (abort,
                   Blueprint,
                   current_app,
                   jsonify,
                   redirect,
                   render_template,
                   request)
from hashids import Hashids
from mongoengine.errors import ValidationError
from pysafebrowsing import SafeBrowsing

from urlshortener.models import URLEntry

home = Blueprint('home', __name__, url_prefix='/')


@home.route('/')
def index():
    """
    Index route.
    """

    return render_template('home.html')


@home.route('/new/<path:path>')
def new_url(path):
    """
    Saves a new URL to the database and returns the short URL.
    """

    request_path = '{}/new/'.format(request.url_root)
    request_url_index_start = \
        request.url.find(request_path) + len(request_path)
    request_url = request.url[request_url_index_start:].strip()
    request_url_without_prefixes = \
        re.sub(r'^(http(s)?://)?(www.)?', '', request_url)

    server_name = current_app.config.get('SERVER_NAME')

    if request_url_without_prefixes.startswith(server_name):
        return jsonify({'error': 'That is already a shortened link'}), 400

    if __is_malicious_link(request_url):
        return jsonify({'error': 'Trying to shorten a malicious link'}), 400

    url_entry = URLEntry.objects(_id=request_url).first()

    if not url_entry:
        try:
            url_entry = URLEntry(_id=request_url).save()
        except ValidationError:
            return jsonify({'error': 'Invalid URL'}), 400

    if current_app.config.get('SSL'):
        app_url = request.host_url.replace('http://', 'https://')
    else:
        app_url = request.host_url

    hashids = Hashids(current_app.config['SECRET_KEY'])
    encoded_sequence = hashids.encode(url_entry.sequence)

    return jsonify({
        'original_url': request_url,
        'short_url': app_url + encoded_sequence
    }), 200


@home.route('/<hashed_id>')
def go_to_url(hashed_id):
    """
    Redirects to URL in database with the given hashed ID.
    """

    hashids = Hashids(current_app.config['SECRET_KEY'])
    decoded_sequence_tuple = hashids.decode(hashed_id)

    if not decoded_sequence_tuple:
        return abort(404)

    decoded_sequence = decoded_sequence_tuple[0]
    url_entry = URLEntry.objects(sequence=decoded_sequence).first()

    if not url_entry:
        return abort(404)

    if __is_malicious_link(url_entry.get_url()):
        URLEntry.objects(sequence=decoded_sequence).delete()
        return abort(404)

    return redirect(url_entry.get_url())


def __is_malicious_link(url):
    safe_browsing_api_key = current_app.config.get('SAFE_BROWSING_API_KEY')

    if not safe_browsing_api_key:
        return False

    safe_browsing = SafeBrowsing(safe_browsing_api_key)
    response = safe_browsing.lookup_urls([url])[url]

    return response['malicious']
