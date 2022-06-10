from flask_wtf import FlaskForm
from wtforms import StringField
from wtforms.validators import DataRequired, Length


class CreateCardForm(FlaskForm):

    name = StringField(
        'Name',
        validators=[
            DataRequired(message='Please enter a card name'),
            Length(
                min=1,
                max=256,
                message='Card name must be between 1 and 256 characters',
            ),
        ],
        filters=[lambda s: s.strip()],
    )


class DeleteCardForm(FlaskForm):

    pass


class UpdateCardForm(FlaskForm):

    name = StringField(
        'Name',
        validators=[
            DataRequired(message='Please enter a card name'),
            Length(
                min=1,
                max=256,
                message='Card name must be between 1 and 256 characters',
            ),
        ],
        filters=[lambda s: s.strip()],
    )
