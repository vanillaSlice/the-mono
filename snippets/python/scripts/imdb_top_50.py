#!/usr/bin/env python3

import argparse

from bs4 import BeautifulSoup
import urllib3

urllib3.disable_warnings()

parser = argparse.ArgumentParser(description='Prints out the IMDB top 50 movies for a given year.')
parser.add_argument('year', type=int)

args = parser.parse_args()

year = args.year

url = 'https://www.imdb.com/search/title?release_date={},{}&title_type=feature'.format(year, year)

html_doc = urllib3.PoolManager().request('GET', url).data
soup = BeautifulSoup(html_doc, 'html.parser')

print('Most Popular Feature Films Released in {}'.format(year))

movies = soup.findAll('div', attrs={'class': 'lister-item mode-advanced'})
for i, div_item in enumerate(movies):
  div = div_item.find('div', attrs={'class': 'lister-item-content'})
  header = div.findChildren('h3', attrs={'class': 'lister-item-header'})
  movie = (header[0].findChildren('a'))[0].contents[0].encode('utf-8').decode('ascii', 'ignore')
  print('{}. {}'.format(i + 1, movie))
