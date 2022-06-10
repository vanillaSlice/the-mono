from flask_login import current_user
from flask_wtf import FlaskForm
from wtforms import StringField
from wtforms.validators import DataRequired, Email, Length
from wtforms.widgets import PasswordInput

from dawdle.components.user.models import User


class UpdateAccountDetailsForm(FlaskForm):

    name = StringField(
        'Name',
        validators=[
            DataRequired(message='Please enter a name'),
            Length(
                min=1,
                max=50,
                message='Your name must be between 1 and 50 characters',
            ),
        ],
        filters=[lambda s: ' '.join(s.split())],
    )

    initials = StringField(
        'Initials',
        validators=[
            DataRequired(message='Please enter initials'),
            Length(
                min=1,
                max=4,
                message='Your initials must be between 1 and 4 characters',
            ),
        ],
        filters=[lambda s: ''.join(s.split()).upper()],
    )

    def update_needed(self):
        return self.name.data != current_user.name or \
            self.initials.data != current_user.initials


class UpdateEmailForm(FlaskForm):

    email = StringField(
        'Email',
        validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    password = StringField(
        'Password Confirmation',
        validators=[
            DataRequired(message='Please enter your password'),
        ],
        widget=PasswordInput(hide_value=False),
    )

    def validate_on_submit(self):
        if not super().validate_on_submit():
            return False

        if not current_user.verify_password(self.password.data):
            self.password.errors.append('Incorrect password')
            return False

        existing_user = User.objects(email=self.email.data).first()

        if current_user.email != self.email.data and existing_user:
            self.email.errors.append(
                'There is already an account with this email',
            )
            return False

        return True

    def update_needed(self):
        return self.email.data != current_user.email


class UpdatePasswordForm(FlaskForm):

    current_password = StringField(
        'Current Password',
        validators=[
            DataRequired(message='Please enter your current password'),
        ],
        widget=PasswordInput(hide_value=False),
    )

    new_password = StringField(
        'New Password',
        validators=[
            DataRequired(message='Please enter a new password'),
            Length(
                min=8,
                message='Your new password must be at least 8 characters',
            ),
        ],
        widget=PasswordInput(hide_value=False),
    )

    def validate_on_submit(self):
        if not super().validate_on_submit():
            return False

        if not current_user.verify_password(self.current_password.data):
            self.current_password.errors.append('Incorrect current password')
            return False

        return True

    def update_needed(self):
        return not current_user.verify_password(self.new_password.data)


class DeleteUserForm(FlaskForm):

    password = StringField(
        'Password Confirmation',
        validators=[
            DataRequired(message='Please enter your password'),
        ],
        widget=PasswordInput(hide_value=False),
    )

    def validate_on_submit(self):
        if not super().validate_on_submit():
            return False

        if not current_user.verify_password(self.password.data):
            self.password.errors.append('Incorrect password')
            return False

        return True
