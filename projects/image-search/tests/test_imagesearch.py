import pytest
import requests_mock

from imagesearch import create_app
from imagesearch.models import SearchEntry

@pytest.fixture
def client():
    yield create_app(testing=True).test_client()

    # make sure we clear the database when we're done
    SearchEntry.objects.delete()

def test_home_redirects_to_swagger_ui(client):
    res = client.get('/')
    assert res.status_code == 302

def test_search_returns_search_results_and_saves_search_to_database(client):
    with requests_mock.Mocker() as req:
        # mock the image search request
        images = {
            'items': [{
                'link': 'link for image 1',
                'snippet': 'snippet for image 1',
                'image': {
                    'thumbnailLink': 'thumbnail for image 1',
                    'contextLink': 'context for image 1'
                }
            }, {
                'link': 'link for image 2',
                'snippet': 'snippet for image 2',
                'image': {
                    'thumbnailLink': 'thumbnail for image 2',
                    'contextLink': 'context for image 2'
                }
            }]
        }
        req.get('https://www.googleapis.com/customsearch/v1', json=images)

        # perform search
        res = client.get('/search/nicolas cage')

        # verify we get search results and save the search terms to the database
        assert res.status_code == 200
        assert SearchEntry.objects().first().terms == 'nicolas cage'
        assert len(res.json) == 2
        assert res.json[0]['url'] == 'link for image 1'
        assert res.json[0]['snippet'] == 'snippet for image 1'
        assert res.json[0]['thumbnail'] == 'thumbnail for image 1'
        assert res.json[0]['context'] == 'context for image 1'
        assert res.json[1]['url'] == 'link for image 2'
        assert res.json[1]['snippet'] == 'snippet for image 2'
        assert res.json[1]['thumbnail'] == 'thumbnail for image 2'
        assert res.json[1]['context'] == 'context for image 2'

def test_search_empty_response_from_api_returns_empty_list(client):
    with requests_mock.Mocker() as req:
        # mock the image search request
        req.get('https://www.googleapis.com/customsearch/v1', json={})

        # perform search
        res = client.get('/search/nicolas cage')

        # verify we get an empty list back
        assert res.status_code == 200
        assert not res.json

def test_search_invalid_offset_returns_400(client):
    res = client.get('/search/nicolas cage?offset=invalid')
    assert res.status_code == 400

def test_latest_returns_latest_search_results(client):
    with requests_mock.Mocker() as req:
        # mock the image search request
        req.get('https://www.googleapis.com/customsearch/v1', json={'items': []})

        # perform some searches
        client.get('/search/krispy kreme')
        client.get('/search/nicolas cage')
        client.get('/search/yeezy')

        # call /latest endpoint
        res = client.get('/latest')

        # verify we get latest search terms back
        assert res.status_code == 200
        assert len(res.json) == 3
        assert res.json[0]['terms'] == 'yeezy'
        assert res.json[1]['terms'] == 'nicolas cage'
        assert res.json[2]['terms'] == 'krispy kreme'

def test_invalid_endpoint_returns_404(client):
    res = client.get('/invalid')
    assert res.status_code == 404
