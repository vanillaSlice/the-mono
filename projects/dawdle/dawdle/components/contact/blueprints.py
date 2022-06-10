from flask import Blueprint, redirect, render_template, request, url_for
from flask_login import current_user

from dawdle.components.contact.forms import ContactForm
from dawdle.components.contact.utils import send_contact_emails

contact_bp = Blueprint('contact', __name__, url_prefix='/contact')


@contact_bp.route('/')
def index_GET():
    return render_template(
        'contact/index.html',
        form=ContactForm(request.form, obj=current_user),
    )


@contact_bp.route('/', methods=['POST'])
def index_POST():
    form = ContactForm(request.form, obj=current_user)

    if not form.validate_on_submit():
        return render_template('contact/index.html', form=form), 400

    sent_email = send_contact_emails(
        email=form.email.data,
        subject=form.subject.data,
        message=form.message.data,
    )

    if not sent_email:
        return render_template('contact/index.html', form=form), 500

    return redirect(url_for('contact.index_GET'))
