"""
Contains default and test config properties. To add local instance
config properties create a file 'instance/config.py' or export the
properties as environment variables (note that environment variables
will take precedence).
"""

class Default:
    """
    Default config properties.
    """

    DEBUG = False
    ENV = 'production'
    GOOGLE_API_KEY = None
    GOOGLE_CSE_ID = None
    MONGODB_DB = 'imagesearch'
    MONGODB_HOST = '127.0.0.1'
    MONGODB_PASSWORD = None
    MONGODB_PORT = 27017
    MONGODB_USERNAME = None
    SECRET_KEY = 'default secret key'
    SERVER_NAME = '127.0.0.1:5000'
    SESSION_COOKIE_DOMAIN = '127.0.0.1:5000'

class Test:
    """
    Test config properties.
    """

    DEBUG = True
    ENV = 'test'
    MONGODB_HOST = 'mongomock://localhost'
