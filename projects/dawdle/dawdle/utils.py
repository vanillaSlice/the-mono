from functools import update_wrapper, wraps
from urllib.parse import urljoin, urlparse

from bson.errors import InvalidId
from bson.objectid import ObjectId
from flask import flash, current_app, make_response, request


def get_mail_sender():
    return ('Dawdle', current_app.config['MAIL_DEFAULT_SENDER'])


def to_ObjectId(value):
    try:
        return ObjectId(value)
    except InvalidId:
        return ObjectId()


def is_safe_url(target):
    ref_url = urlparse(request.host_url)
    test_url = urlparse(urljoin(request.host_url, target))
    return test_url.scheme in ('http', 'https') and \
        ref_url.netloc == test_url.netloc


def safely_delete_documents(documents):
    for document in documents:
        safely_delete_document(document)


def safely_delete_document(document):
    try:
        document.delete()
    except Exception:
        pass


def no_cache(view):
    @wraps(view)
    def decorated_function(*args, **kwargs):
        response = make_response(view(*args, **kwargs))
        headers = response.headers
        headers['Cache-Control'] = 'no-cache, no-store, must-revalidate'
        headers['Expires'] = '0'
        headers['Pragma'] = 'no-cache'
        return response
    return update_wrapper(decorated_function, view)


def flash_params_message():
    message = request.args.get('message')
    category = request.args.get('category')

    if message:
        flash(message, category)
