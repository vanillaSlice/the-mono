from flask import render_template
from flask_mail import Message

from dawdle.extensions.mail import mail
from dawdle.utils import get_mail_sender


def send_delete_account_email(user):
    try:
        recipients = [user.email]
        msg = Message('Account Deleted',
                      sender=get_mail_sender(),
                      recipients=recipients)
        msg.html = render_template(
            'user/settings-delete-account-email.html',
            user=user,
        )
        mail.send(msg)
        return True
    except Exception:
        return False
