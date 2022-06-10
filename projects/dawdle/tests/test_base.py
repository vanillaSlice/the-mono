from faker import Faker
from flask import url_for

from dawdle import create_app
from dawdle.components.board.models import (Board, BoardType, BoardVisibility)
from dawdle.components.card.models import Card
from dawdle.components.column.models import Column

from dawdle.components.user.models import User


class TestBase:

    @classmethod
    def setup_class(cls):
        cls.fake = Faker()

        cls.app = create_app(testing=True)
        cls.app.app_context().push()
        cls.client = cls.app.test_client()

        cls.password = cls.fake.password()
        cls.user = cls.create_user(password=cls.password)
        cls.login()

    @classmethod
    def teardown_class(cls):
        cls.clear_db()

    @classmethod
    def create_user(cls, **kwargs):
        user = User()
        user.active = kwargs.get('active', True)
        user.email = kwargs.get('email', cls.fake.email())
        user.initials = kwargs.get(
            'initials',
            cls.fake.pystr(min_chars=1, max_chars=4).upper(),
        ).upper()
        user.name = kwargs.get('name', cls.fake.name())
        user.password = User.encrypt_password(
            kwargs.get('password', cls.fake.password()),
        )
        return user.save()

    @classmethod
    def create_boards(cls, **kwargs):
        owner_id = kwargs.get('owner_id', cls.user.id)
        min_boards = kwargs.get('min_boards', 1)
        max_boards = kwargs.get('max_boards', 4)
        num = cls.fake.pyint(min_boards, max_boards)
        boards = []
        for _ in range(num):
            boards.append(cls.create_board(owner_id=owner_id))
        return boards

    @classmethod
    def create_board(cls, **kwargs):
        board = Board()
        board.created_by = kwargs.get('created_by', cls.user.id)
        board.name = kwargs.get(
            'name',
            cls.fake.pystr(min_chars=1, max_chars=256),
        )
        board.owner_id = kwargs.get('owner_id', cls.user.id)
        board.type = kwargs.get('type', BoardType.PERSONAL.id)
        board.visibility = kwargs.get('visibility', BoardVisibility.PRIVATE.id)
        saved_board = board.save()

        user = User.objects(id=saved_board.owner_id).first()
        if user:
            user.boards.append(saved_board)
            user.save()

        return saved_board

    @classmethod
    def create_columns(cls, board, **kwargs):
        min_boards = kwargs.get('min_columns', 1)
        max_boards = kwargs.get('max_columns', 4)
        num = cls.fake.pyint(min_boards, max_boards)
        columns = []
        for _ in range(num):
            columns.append(cls.create_column(board))
        return columns

    @classmethod
    def create_column(cls, board, **kwargs):
        column = Column()
        column.board_id = board.id
        column.created_by = kwargs.get('created_by', cls.user.id)
        column.name = kwargs.get(
            'name',
            cls.fake.pystr(min_chars=1, max_chars=256),
        )
        saved_column = column.save()

        board.columns.append(saved_column)
        board.save()

        return saved_column

    @classmethod
    def create_cards(cls, column, **kwargs):
        min_cards = kwargs.get('min_cards', 1)
        max_cards = kwargs.get('max_cards', 4)
        num = cls.fake.pyint(min_cards, max_cards)
        cards = []
        for _ in range(num):
            cards.append(cls.create_card(column))
        return cards

    @classmethod
    def create_card(cls, column, **kwargs):
        card = Card()
        card.column_id = column.id
        card.created_by = kwargs.get('created_by', cls.user.id)
        card.name = kwargs.get(
            'name',
            cls.fake.pystr(min_chars=1, max_chars=256),
        )
        saved_card = card.save()

        column.cards.append(saved_card)
        column.save()

        return saved_card

    @classmethod
    def as_new_user(cls):
        password = cls.fake.password()
        user = cls.create_user(password=password)
        cls.login(email=user.email, password=password)
        return user, password

    @classmethod
    def login(cls, **kwargs):
        email = kwargs.get('email', cls.user.email)
        password = kwargs.get('password', cls.password)
        data = {'email': email, 'password': password}
        cls.client.post(url_for('auth.login_POST'), data=data)
        cls.logged_in = cls.user.email == email and cls.password == password

    @classmethod
    def logout(cls):
        cls.client.get(url_for('auth.logout_GET'))
        cls.logged_in = False

    @classmethod
    def clear_db(cls):
        Card.objects.delete()
        Column.objects.delete()
        Board.objects.delete()
        User.objects.delete()

    def setup_method(self):
        if not self.logged_in:
            self.login()
