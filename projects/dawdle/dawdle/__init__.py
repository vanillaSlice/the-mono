import os

from flask import abort, Flask, render_template

version = 'v0.1.0'


def create_app(testing=False):
    app = Flask(__name__, instance_relative_config=True)

    _attach_context_processors(app)
    _load_config(app, testing)
    _init_extensions(app)
    _register_blueprints(app)
    _attach_error_handlers(app)
    _disable_strict_trailing_slashes(app)

    return app


def _attach_context_processors(app):
    @app.context_processor
    def _inject_version():
        return {'version': version}


def _load_config(app, testing):
    app.config.from_object('config.Default')

    app.config.from_pyfile('config.py', silent=True)

    def load_env_var(key):
        return os.environ.get(key, app.config.get(key))

    def load_env_var_bool(key):
        return str(load_env_var(key)).lower() == 'true'

    def load_env_var_int(key):
        return int(load_env_var(key))

    app.config.update({
        'CONTACT_EMAIL': load_env_var('CONTACT_EMAIL'),
        'DEBUG': load_env_var_bool('DEBUG'),
        'ENV': load_env_var('ENV'),
        'MAIL_DEFAULT_SENDER': load_env_var('MAIL_DEFAULT_SENDER'),
        'MAIL_PASSWORD': load_env_var('MAIL_PASSWORD'),
        'MAIL_PORT': load_env_var_int('MAIL_PORT'),
        'MAIL_SERVER': load_env_var('MAIL_SERVER'),
        'MAIL_SUPPRESS_SEND': load_env_var_bool('MAIL_SUPPRESS_SEND'),
        'MAIL_USE_SSL': load_env_var_bool('MAIL_USE_SSL'),
        'MAIL_USE_TLS': load_env_var_bool('MAIL_USE_TLS'),
        'MAIL_USERNAME': load_env_var('MAIL_USERNAME'),
        'MONGODB_DB': load_env_var('MONGODB_DB'),
        'MONGODB_HOST': load_env_var('MONGODB_HOST'),
        'MONGODB_PASSWORD': load_env_var('MONGODB_PASSWORD'),
        'MONGODB_PORT': load_env_var_int('MONGODB_PORT'),
        'MONGODB_USERNAME': load_env_var('MONGODB_USERNAME'),
        'SECRET_KEY': load_env_var('SECRET_KEY'),
        'SERVER_NAME': load_env_var('SERVER_NAME'),
        'SESSION_COOKIE_DOMAIN': load_env_var('SESSION_COOKIE_DOMAIN'),
        'WTF_CSRF_ENABLED': load_env_var_bool('WTF_CSRF_ENABLED'),
    })

    app.config.update({'TESTING': testing})

    if testing:
        app.config.from_object('config.Test')


def _init_extensions(app):
    from dawdle.extensions.assets import assets
    assets.init_app(app)

    from dawdle.extensions.login import login_manager
    login_manager.init_app(app)

    from dawdle.extensions.mail import mail
    mail.init_app(app)

    from dawdle.extensions.mongoengine import mongoengine
    mongoengine.init_app(app)

    from dawdle.extensions.wtf import csrf
    csrf.init_app(app)


def _register_blueprints(app):
    from dawdle.components.auth.blueprints import auth_bp
    app.register_blueprint(auth_bp)

    from dawdle.components.board.blueprints import board_bp
    app.register_blueprint(board_bp)

    from dawdle.components.card.blueprints import card_bp
    app.register_blueprint(card_bp)

    from dawdle.components.column.blueprints import column_bp
    app.register_blueprint(column_bp)

    from dawdle.components.contact.blueprints import contact_bp
    app.register_blueprint(contact_bp)

    from dawdle.components.home.blueprints import home_bp
    app.register_blueprint(home_bp)

    from dawdle.components.user.blueprints import user_bp
    app.register_blueprint(user_bp)


def _attach_error_handlers(app):
    @app.errorhandler(Exception)
    def _handle_exception(_):
        abort(500)

    @app.errorhandler(400)
    @app.errorhandler(403)
    @app.errorhandler(404)
    @app.errorhandler(500)
    def _handle_error(error):
        return render_template(
            f'error/{error.code}.html',
            error=error,
        ), error.code


def _disable_strict_trailing_slashes(app):
    app.url_map.strict_slashes = False
