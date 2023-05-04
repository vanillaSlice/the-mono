# Image Search

[![Build Status](https://img.shields.io/github/actions/workflow/status/vanillaSlice/the-mono/image-search.yml?branch=main)](https://github.com/vanillaSlice/the-mono/actions?query=workflow%3AImage-Search+branch%3Amain)
[![Coverage Status](https://img.shields.io/codecov/c/gh/vanillaSlice/the-mono/main?flag=ImageSearch)](https://codecov.io/gh/vanillaSlice/the-mono/tree/main/projects/image-search)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

A simple image search API built with [Flask](http://flask.pocoo.org/) and [MongoDB](https://www.mongodb.com/).
A deployed version can be viewed [here](https://imagesearch.mikelowe.xyz/).

## Getting Started

* [With Docker](#with-docker)
* [Without Docker](#without-docker)

### With Docker

#### Prerequisites

* [Docker](https://www.docker.com/)

#### Running

From your terminal/command prompt run:

```
docker-compose up
```

Then point your browser to [http://127.0.0.1:5000/](http://127.0.0.1:5000/).

### Without Docker


#### Installing Requirements

1. (Optional) Install [virtualenv](https://pypi.org/project/virtualenv/) and
[virtualenvwrapper](https://virtualenvwrapper.readthedocs.io/en/latest/) and create a new environment.
2. Run `pip install -r requirements.txt`.

#### Setting up MongoDB

You can either:

* Install MongoDB locally by going [here](https://www.mongodb.com/download-center#community).

or:

* Create a database in the cloud using [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).

#### Setting up Google Custom Search Engine

The app uses a Google Custom Search Engine to perform searches. Go [here](https://www.google.com/cse/) to set one
up.

#### Configuration

The following properties can be configured:

| Name                    | Purpose                                                          | Default              |
| ----------------------- | ---------------------------------------------------------------- | -------------------- |
| `DEBUG`                 | If debug mode is enabled.                                        | `False`              |
| `ENV`                   | Environment the app is running in.                               | `production`         |
| `GOOGLE_API_KEY`        | The Google API Key.                                              | `None`               |
| `GOOGLE_CSE_ID`         | The Google CSE ID.                                               | `None`               |
| `MONGODB_DB`            | The MongoDB database name.                                       | `imagesearch`        |
| `MONGODB_HOST`          | The MongoDB host name.                                           | `127.0.0.1`          |
| `MONGODB_PASSWORD`      | The MongoDB password.                                            | `None`               |
| `MONGODB_PORT`          | The MongoDB port.                                                | `27017`              |
| `MONGODB_USERNAME`      | The MongoDB username.                                            | `None`               |
| `SECRET_KEY`            | A secret key used for security.                                  | `default secret key` |
| `SERVER_NAME`           | The host and port of the server.                                 | `127.0.0.1:5000`     |
| `SESSION_COOKIE_DOMAIN` | The domain match rule that the session cookie will be valid for. | `127.0.0.1:5000`     |

To change these properties you can export them as environment variables or create a file `instance/config.py` (note
that environment variables take precedence).

URI style connections are also supported for connecting to MongoDB, just supply the URI as `MONGODB_HOST` (note that
URI properties will take precedence).

#### Running

From your terminal/command prompt run:

```
./run.py
```

Then point your browser to [http://127.0.0.1:5000/](http://127.0.0.1:5000/).

## Technology Used

For those of you that are interested, the technology used in this project includes:

* [Python](https://www.python.org/)
* [Flask](http://flask.pocoo.org/) (Microframework)
* [MongoDB](https://www.mongodb.com/) and
[Flask-MongoEngine](http://docs.mongoengine.org/projects/flask-mongoengine/en/latest/) (Database)
* [Swagger](https://swagger.io/) (API documentation)
* [pytest](https://docs.pytest.org/en/latest/), [Mongomock](https://github.com/mongomock/mongomock)
and [requests-mock](https://requests-mock.readthedocs.io/en/latest/) (Testing)
* [Docker](https://www.docker.com/)

## Useful Links

Resources useful for the completion of this project:

* [Google Custom Search](https://developers.google.com/custom-search/) (the search engine used for image searching)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
