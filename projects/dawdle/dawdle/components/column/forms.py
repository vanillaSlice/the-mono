from flask_wtf import FlaskForm
from wtforms import HiddenField, StringField
from wtforms.validators import DataRequired, Length


class CreateColumnForm(FlaskForm):

    create_column_path = HiddenField()

    name = StringField(
        'Name',
        validators=[
            DataRequired(message='Please enter a column name'),
            Length(
                min=1,
                max=256,
                message='Column name must be between 1 and 256 characters',
            ),
        ],
        filters=[lambda s: s.strip()],
    )


class DeleteColumnForm(FlaskForm):

    pass


class UpdateColumnForm(FlaskForm):

    name = StringField(
        'Name',
        validators=[
            DataRequired(message='Please enter a column name'),
            Length(
                min=1,
                max=256,
                message='Column name must be between 1 and 256 characters',
            ),
        ],
        filters=[lambda s: s.strip()],
    )
