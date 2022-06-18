# pylint: disable=broad-except

"""
Emails.
"""

from datetime import datetime

from sendgrid import SendGridAPIClient
from sendgrid.helpers.mail import Mail

from best_new_music_digest import settings


def send_email(digest, dad_joke=None, albums_playlist_url=None, tracks_playlist_url=None):
    """
    Sends out digest email.
    """

    if settings.ALWAYS_EMAIL:
        print("Always email is enabled")

    should_send = any(d["items"] or d["errors"] for d in digest) or settings.ALWAYS_EMAIL

    if not should_send:
        print("No items or errors to email about")
        return

    message = Mail(
        from_email=(settings.SENDER_EMAIL, settings.SENDER_NAME),
        to_emails=settings.RECIPIENT_EMAIL,
    )

    message.template_id = settings.SENDGRID_TEMPLATE_ID

    message.dynamic_template_data = {
        "date": datetime.utcnow().strftime("%d/%m/%Y"),
        "dad_joke": dad_joke,
        "digest": digest,
        "albums_playlist_url": albums_playlist_url,
        "tracks_playlist_url": tracks_playlist_url,
    }

    try:
        SendGridAPIClient(settings.SENDGRID_API_KEY).send(message)
    except Exception as exception:
        print("Failed to send email")
        print(exception)
