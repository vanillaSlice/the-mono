from datetime import datetime

from bson.objectid import ObjectId
from flask import (abort,
                   Blueprint,
                   flash,
                   redirect,
                   render_template,
                   request,
                   url_for)
from flask_login import current_user, login_user, logout_user

from dawdle.components.auth.forms import (LoginForm,
                                          ResetPasswordForm,
                                          ResetPasswordRequestForm,
                                          SignUpForm,
                                          VerifyResendForm)
from dawdle.components.user.models import User
from dawdle.components.auth.utils import (deserialize_password_reset_token,
                                          deserialize_verification_token,
                                          send_password_reset_email,
                                          send_verification_email)
from dawdle.utils import is_safe_url

auth_bp = Blueprint('auth', __name__, url_prefix='/auth')


@auth_bp.route('/sign-up')
def sign_up_GET():
    return render_template('auth/sign-up.html', form=SignUpForm(request.form))


@auth_bp.route('/sign-up', methods=['POST'])
def sign_up_POST():
    form = SignUpForm(request.form)

    if not form.validate_on_submit():
        return render_template('auth/sign-up.html', form=form), 400

    user = User()
    form.populate_obj(user)
    user.initials = User.create_initials(user.name)
    user.password = User.encrypt_password(form.password.data)
    user.save()

    send_verification_email(user)

    return redirect(url_for('auth.verify_resend_GET', email=form.email.data))


@auth_bp.route('/verify/resend')
def verify_resend_GET():
    return render_template(
        'auth/verify-resend.html',
        form=VerifyResendForm(request.form, email=request.args.get('email')),
    )


@auth_bp.route('/verify/resend', methods=['POST'])
def verify_resend_POST():
    form = VerifyResendForm(request.form, email=request.args.get('email'))

    if not form.validate_on_submit():
        return render_template('auth/verify-resend.html', form=form), 400

    sent_email = send_verification_email(form.user, request.args.get('next'))
    status_code = 200 if sent_email else 500

    return render_template('auth/verify-resend.html', form=form), status_code


@auth_bp.route('/verify/<token>')
def verify_GET(token):
    auth_id = deserialize_verification_token(token)
    user = User.objects(auth_id=auth_id).first()

    if not user:
        abort(404)

    user.active = True
    user.auth_id = ObjectId()
    user.last_updated = datetime.utcnow()
    user.save()

    login_user(user)

    flash('Your email address has been verified.', 'success')

    next_target = request.args.get('next')

    if not is_safe_url(next_target):
        abort(400)

    return redirect(next_target or url_for('user.boards_GET'))


@auth_bp.route('/login')
def login_GET():
    return render_template('auth/login.html', form=LoginForm(request.form))


@auth_bp.route('/login', methods=['POST'])
def login_POST():
    form = LoginForm(request.form)

    if not form.validate_on_submit():
        return render_template('auth/login.html', form=form), 400

    login_user(form.user, remember=form.remember_me.data)

    next_target = request.args.get('next')

    if not is_safe_url(next_target):
        abort(400)

    return redirect(next_target or url_for('user.boards_GET'))


@auth_bp.route('/logout')
def logout_GET():
    logout_user()

    flash('You have been logged out.', 'info')

    return redirect(url_for('home.index_GET'))


@auth_bp.route('/reset-password')
def reset_password_request_GET():
    return render_template(
        'auth/reset-password-request.html',
        form=ResetPasswordRequestForm(request.form, obj=current_user),
    )


@auth_bp.route('/reset-password', methods=['POST'])
def reset_password_request_POST():
    form = ResetPasswordRequestForm(request.form, obj=current_user)

    if not form.validate_on_submit():
        return render_template(
            'auth/reset-password-request.html',
            form=form,
        ), 400

    sent_email = send_password_reset_email(form.user)
    status_code = 200 if sent_email else 500

    return render_template(
        'auth/reset-password-request.html',
        form=form,
    ), status_code


@auth_bp.route('/reset-password/<token>')
def reset_password_GET(token):
    auth_id = deserialize_password_reset_token(token)
    user = User.objects(auth_id=auth_id).first()

    if not user:
        abort(404)

    return render_template(
        'auth/reset-password.html',
        form=ResetPasswordForm(request.form),
    )


@auth_bp.route('/reset-password/<token>', methods=['POST'])
def reset_password_POST(token):
    auth_id = deserialize_password_reset_token(token)
    user = User.objects(auth_id=auth_id).first()

    if not user:
        abort(404)

    form = ResetPasswordForm(request.form)

    if not form.validate_on_submit():
        return render_template('auth/reset-password.html', form=form), 400

    user.password = User.encrypt_password(form.new_password.data)
    user.auth_id = ObjectId()
    user.last_updated = datetime.utcnow()
    user.save()

    flash('Your password has been reset.', 'success')

    login_user(user)

    return redirect(url_for('user.boards_GET'))
