from flask_wtf import FlaskForm
from wtforms import BooleanField, StringField
from wtforms.validators import DataRequired, Email, Length
from wtforms.widgets import PasswordInput

from dawdle.components.user.models import User


class SignUpForm(FlaskForm):

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
        filters=[lambda s: ' '.join(s.split()) if s else None],
    )

    email = StringField(
        'Email',
        validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    password = StringField(
        'Password',
        validators=[
            DataRequired(message='Please enter a password'),
            Length(
                min=8,
                message='Your password must be at least 8 characters',
            ),
        ],
        widget=PasswordInput(hide_value=False),
    )

    def validate_on_submit(self, extra_validators=None):
        if not super().validate_on_submit(extra_validators):
            return False

        if User.objects(email=self.email.data).first():
            self.email.errors.append(
                'There is already an account with this email',
            )
            return False

        return True


class VerifyResendForm(FlaskForm):

    email = StringField(
        'Email',
        validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.user = None

    def validate_on_submit(self, extra_validators=None):
        if not super().validate_on_submit(extra_validators):
            return False

        self.user = User.objects(email=self.email.data).first()

        if not self.user:
            self.email.errors.append('There is no account with this email')
            return False

        if self.user.is_active:
            self.email.errors.append('This account has already been verified')
            return False

        return True


class LoginForm(FlaskForm):

    email = StringField(
        'Email',
        validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    password = StringField(
        'Password',
        validators=[
            DataRequired(message='Please enter a password'),
        ],
        widget=PasswordInput(hide_value=False),
    )

    remember_me = BooleanField('Remember Me')

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.user = None

    def validate_on_submit(self, extra_validators=None):
        if not super().validate_on_submit(extra_validators):
            return False

        self.user = User.objects(email=self.email.data).first()

        if not self.user or not self.user.verify_password(self.password.data):
            self.email.errors.append('Incorrect email')
            self.password.errors.append('Incorrect password')
            return False

        if not self.user.is_active:
            self.email.errors.append(
                'Please verify your email before logging in',
            )
            return False

        return True


class ResetPasswordRequestForm(FlaskForm):

    email = StringField(
        'Email', validators=[
            DataRequired(message='Please enter an email'),
            Email(message='Please enter a valid email'),
        ],
    )

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.user = None

    def validate_on_submit(self, extra_validators=None):
        if not super().validate_on_submit(extra_validators):
            return False

        self.user = User.objects(email=self.email.data).first()

        if not self.user:
            self.email.errors.append('There is no account with this email')
            return False

        return True


class ResetPasswordForm(FlaskForm):

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
