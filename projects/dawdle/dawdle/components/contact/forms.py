from flask_wtf import FlaskForm
from wtforms import StringField, TextAreaField
from wtforms.validators import DataRequired, Email, Length


class ContactForm(FlaskForm):

    email = StringField(
        'Email',
        validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    subject = StringField(
        'Subject',
        validators=[
            DataRequired(message='Please enter a subject'),
            Length(
                min=1,
                max=256,
                message='Subject must be between 1 and 256 characters',
            ),
        ],
        filters=[lambda s: ' '.join(s.split())],
    )

    message = TextAreaField(
        'Message',
        validators=[
            DataRequired(message='Please enter a message'),
            Length(
                min=1,
                max=1000,
                message='Message must be between 1 and 1000 characters',
            ),
        ],
        filters=[lambda s: s.strip()],
    )
