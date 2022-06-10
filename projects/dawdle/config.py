class Default:

    CONTACT_EMAIL = None
    DEBUG = False
    ENV = 'production'
    MAIL_DEFAULT_SENDER = None
    MAIL_PASSWORD = None
    MAIL_PORT = 587
    MAIL_SERVER = 'smtp.gmail.com'
    MAIL_SUPPRESS_SEND = False
    MAIL_USE_SSL = False
    MAIL_USE_TLS = True
    MAIL_USERNAME = None
    MONGODB_DB = 'dawdle'
    MONGODB_HOST = '127.0.0.1'
    MONGODB_PASSWORD = None
    MONGODB_PORT = 27017
    MONGODB_USERNAME = None
    SECRET_KEY = 'default secret key'
    SERVER_NAME = '127.0.0.1:5000'
    SESSION_COOKIE_DOMAIN = '127.0.0.1:5000'
    WTF_CSRF_ENABLED = True


class Test:

    CONTACT_EMAIL = None
    DEBUG = True
    ENV = 'test'
    MAIL_DEFAULT_SENDER = 'dawdle'
    MAIL_PASSWORD = None
    MAIL_PORT = 587
    MAIL_SERVER = 'smtp.gmail.com'
    MAIL_SUPPRESS_SEND = True
    MAIL_USE_SSL = False
    MAIL_USE_TLS = True
    MAIL_USERNAME = None
    MONGODB_DB = 'dawdle'
    MONGODB_HOST = 'mongomock://localhost'
    MONGODB_PASSWORD = None
    MONGODB_PORT = 27017
    MONGODB_USERNAME = None
    SECRET_KEY = 'default secret key'
    SERVER_NAME = '127.0.0.1:5000'
    SESSION_COOKIE_DOMAIN = '127.0.0.1:5000'
    WTF_CSRF_ENABLED = False
