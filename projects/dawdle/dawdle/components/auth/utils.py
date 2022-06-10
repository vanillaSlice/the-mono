from bson.objectid import ObjectId
from flask import current_app, flash, render_template
from flask_mail import Message
from itsdangerous import (BadSignature,
                          TimedJSONWebSignatureSerializer,
                          URLSafeSerializer)

from dawdle.extensions.mail import mail
from dawdle.utils import get_mail_sender, to_ObjectId


def serialize_verification_token(user):
    return URLSafeSerializer(current_app.secret_key).dumps(str(user.auth_id))


def deserialize_verification_token(token):
    try:
        auth_id = URLSafeSerializer(current_app.secret_key).loads(token)
        return to_ObjectId(auth_id)
    except BadSignature:
        return ObjectId()


def send_verification_email(user, redirect_target=None):
    try:
        recipients = [user.email]
        msg = Message('Verification',
                      sender=get_mail_sender(),
                      recipients=recipients)
        msg.html = render_template(
            'auth/verify-email.html',
            user=user,
            token=serialize_verification_token(user),
            redirect_target=redirect_target,
        )
        mail.send(msg)
        flash(
            'A verification email has been sent to {}. Please verify your '
            'email before logging in to Dawdle.'.format(user.email),
            'info',
        )
        return True
    except Exception:
        flash(
            'Could not send a verification email to {}. Please try again.'
            .format(user.email),
            'danger',
        )
        return False


def serialize_password_reset_token(user):
    return TimedJSONWebSignatureSerializer(
        current_app.secret_key,
        expires_in=600,
    ).dumps(str(user.auth_id)).decode('utf-8')


def deserialize_password_reset_token(token):
    try:
        auth_id = TimedJSONWebSignatureSerializer(
            current_app.secret_key,
            expires_in=600,
        ).loads(token)
        return to_ObjectId(auth_id)
    except BadSignature:
        return ObjectId()


def send_password_reset_email(user):
    try:
        recipients = [user.email]
        msg = Message('Password Reset',
                      sender=get_mail_sender(),
                      recipients=recipients)
        msg.html = render_template(
            'auth/reset-password-email.html',
            user=user,
            token=serialize_password_reset_token(user),
        )
        mail.send(msg)
        flash(
            'A password reset email has been sent to {}. This will expire in '
            '10 minutes.'.format(user.email),
            'info',
        )
        return True
    except Exception:
        flash(
            'Could not send a password reset email to {}. Please try again.'
            .format(user.email),
            'danger',
        )
        return False
