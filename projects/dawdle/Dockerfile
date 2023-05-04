FROM python:3.11.3-alpine
WORKDIR /opt/app
COPY ./requirements.txt ./requirements.txt
RUN pip install -r requirements.txt
RUN rm ./requirements.txt
COPY ./dawdle ./dawdle
COPY ./config.py ./config.py
COPY ./run.py ./run.py
CMD gunicorn --workers=4 run:app
