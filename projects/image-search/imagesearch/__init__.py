"""
Exports a function to create an instance of the Image Search app.
"""

import os

from connexion import FlaskApp
from flask import Flask, render_template
from flask_mongoengine import MongoEngine
from swagger_ui_bundle import swagger_ui_3_path

mongoengine = MongoEngine()

def create_app(testing=False):
    """
    Creates an instance of the Image Search app.
    """

    # create connexion app
    connexion_options = {'swagger_path': swagger_ui_3_path}
    connexion_app = FlaskApp(__name__, specification_dir='./swagger', options=connexion_options)
    connexion_app.add_api('swagger.yml')

    # get a reference to underlying flask app
    app = connexion_app.app

    # do this because we want to allow instance configs
    app.config.root_path = app.instance_path

    # load default config
    app.config.from_object('config.Default')

    # load instance config (if present)
    app.config.from_pyfile('config.py', silent=True)

    # load test config (if testing)
    if testing:
        app.config.from_object('config.Test')

    app.config.update({'TESTING': testing})

    # load environment variables (if present)
    app.config.update({
        'DEBUG': os.environ.get('DEBUG', str(app.config.get('DEBUG'))).lower() == 'true',
        'ENV': os.environ.get('ENV', app.config.get('ENV')),
        'GOOGLE_API_KEY': os.environ.get('GOOGLE_API_KEY', app.config.get('GOOGLE_API_KEY')),
        'GOOGLE_CSE_ID': os.environ.get('GOOGLE_CSE_ID', app.config.get('GOOGLE_CSE_ID')),
        'MONGODB_DB': os.environ.get('MONGODB_DB', app.config.get('MONGODB_DB')),
        'MONGODB_HOST': os.environ.get('MONGODB_HOST', app.config.get('MONGODB_HOST')),
        'MONGODB_PASSWORD': os.environ.get('MONGODB_PASSWORD', app.config.get('MONGODB_PASSWORD')),
        'MONGODB_PORT': int(os.environ.get('MONGODB_PORT', app.config.get('MONGODB_PORT'))),
        'MONGODB_USERNAME': os.environ.get('MONGODB_USERNAME', app.config.get('MONGODB_USERNAME')),
        'SECRET_KEY': os.environ.get('SECRET_KEY', app.config.get('SECRET_KEY')),
        'SERVER_NAME': os.environ.get('SERVER_NAME', app.config.get('SERVER_NAME')),
        'SESSION_COOKIE_DOMAIN':
            os.environ.get('SESSION_COOKIE_DOMAIN', app.config.get('SESSION_COOKIE_DOMAIN'))
    })

    # init extensions
    mongoengine.init_app(app)

    # disable strict trailing slashes e.g. so /latest and /latest/ both resolve to same endpoint
    app.url_map.strict_slashes = False

    # register blueprints
    from imagesearch.blueprints import home
    app.register_blueprint(home)

    # disable caching when debugging
    if app.debug:
        @app.after_request
        def after_request(response):
            response.headers['Cache-Control'] = 'no-cache, no-store, must-revalidate'
            response.headers['Expires'] = 0
            response.headers['Pragma'] = 'no-cache'
            return response

    return app
