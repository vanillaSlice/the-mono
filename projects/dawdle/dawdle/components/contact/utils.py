from flask import current_app, flash, render_template
from flask_mail import Message

from dawdle.extensions.mail import mail
from dawdle.utils import get_mail_sender


def send_contact_emails(email, subject, message):
    try:
        # send email to us
        recipients = [current_app.config['CONTACT_EMAIL']]
        msg = Message(subject, sender=get_mail_sender(), recipients=recipients)
        msg.body = 'From: {}\n\n{}'.format(email, message)
        mail.send(msg)

        # send email to user saying we've received their message
        recipients = [email]
        msg = Message(subject,
                      sender=get_mail_sender(),
                      recipients=recipients)
        msg.html = render_template('contact/email.html', message=message)
        mail.send(msg)

        flash(
            'We have received your message. '
            'Somebody will get back to you shortly.',
            'success',
        )

        return True
    except Exception:
        flash('Could not send message. Please try again.', 'danger')
        return False
