from flask_login import LoginManager

from dawdle.components.user.models import User
from dawdle.utils import to_ObjectId

login_manager = LoginManager()

login_manager.login_view = 'auth.login_GET'
login_manager.login_message_category = 'info'


@login_manager.user_loader
def load_user(user_id):
    return User.objects(auth_id=to_ObjectId(user_id)).first()
