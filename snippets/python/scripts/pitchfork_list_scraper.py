#!/usr/bin/env python3

import argparse
import csv
import os
import re
import time

from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.action_chains import ActionChains


URLS = {
    '1960s': {
        'ALBUMS': 'https://pitchfork.com/features/lists-and-guides/the-200-best-albums-of-the-1960s/',
        'TRACKS': 'https://pitchfork.com/features/lists-and-guides/6405-the-200-greatest-songs-of-the-1960s/',
    },
}


class PitchforkListScraper:

    @classmethod
    def run(cls, list_url):
        chrome_options = Options()
        chrome_options.add_argument('--headless')

        driver = webdriver.Chrome(chrome_options=chrome_options)
        driver.get(list_url)
        driver.find_element_by_class_name('fts-arrow--next').click()

        list_items = cls._get_list_items(driver)

        driver.close()

        return list_items

    @classmethod
    def _get_list_items(cls, driver):
        list_items = []

        keep_scraping = True

        while keep_scraping:
            if cls._is_list_style_one(driver):
                list_items.extend(cls._parse_list_style_one(driver))
            elif cls._is_list_style_two(driver):
                list_items.extend(cls._parse_list_style_two(driver))

            keep_scraping = cls._go_to_next_page(driver)

        return list_items

    @classmethod
    def _is_list_style_one(cls, driver):
        return len(driver.find_elements_by_class_name('list-blurb')) > 0

    @classmethod
    def _parse_list_style_one(cls, driver):
        list_items = []

        elems = driver.find_elements_by_class_name('list-blurb')

        for elem in elems:
            rank = elem.find_element_by_class_name('rank').text
            artist_links = elem.find_element_by_class_name('list-blurb__artists')
            artist = artist_links.find_element_by_tag_name('li').text
            title = elem.find_element_by_class_name('list-blurb__work-title').text
            try:
                year = elem.find_element_by_class_name('list-blurb__year').text
            except WebDriverException:
                label_text = elem.find_element_by_class_name('labels-list__item').text
                year = re.sub('[()]', '', label_text.split(' ')[-1])
            picture = elem.find_element_by_tag_name('img').get_attribute('src')

            list_items.append(ListItem(rank=rank, artist=artist, title=title, year=year, picture=picture))

        return list_items

    @classmethod
    def _is_list_style_two(cls, driver):
        return len(driver.find_element_by_class_name('contents')) > 0

    @classmethod
    def _parse_list_style_two(cls, driver):
        list_items = []

        content = driver.find_element_by_class_name('contents')
        elems = content.find_elements_by_tag_name('strong')

        for elem in elems:
            text = elem.text.split('\n')
            rank = ''
            artist = text[0]
            title = ''
            if len(text) > 1:
                title = text[1]
            year = ''
            picture = ''

            list_items.append(ListItem(rank=rank, artist=artist, title=title, year=year, picture=picture))

        return list_items

    # TODO do this better i.e. without sleep
    @classmethod
    def _go_to_next_page(cls, driver):
        try:
            next_button = driver.find_element_by_css_selector('.fts-arrow--next.fts-arrow--active')
            actions = ActionChains(driver)
            actions.move_to_element(next_button)
            actions.click(next_button)
            actions.perform()
            time.sleep(5)
            return True
        except WebDriverException:
            return False


class ListItem:

    def __init__(self, rank="", artist="", title="", year="", picture=""):
        self.rank = rank
        self.artist = artist
        self.title = title
        self.year = year
        self.picture = picture


def write_list_items_to_csv_file(list_items, output_file):
    output_dir = os.path.dirname(output_file)

    if output_dir:
        os.makedirs(output_dir, exist_ok=True)

    with open(output_file, 'w', newline='') as csv_file:
        writer = csv.writer(csv_file, delimiter=',', escapechar='\\', quotechar='"', quoting=csv.QUOTE_ALL)
        for list_item in list_items:
            writer.writerow([list_item.rank, list_item.artist, list_item.title, list_item.year, list_item.picture])


def write_list_items_to_console(list_items):
    for list_item in list_items:
        print('{}. {} - {} ({})'.format(list_item.rank, list_item.artist, list_item.title, list_item.year))


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Scrapes Pitchfork lists.')
    parser.add_argument('-y', '--year', type=str, help='year')
    parser.add_argument('-t', '--type', type=str, help='type')
    parser.add_argument('-o', '--output', type=str, help='path to output CSV file')
    args = parser.parse_args()

    year = args.year
    typ = args.type
    output_file = args.output

    list_url = URLS[year][typ]

    print('Scraping {} {} 🎶'.format(year, typ.lower()))
    list_items = PitchforkListScraper.run(list_url)
    print('Found {} list items'.format(len(list_items)))

    if output_file:
        print('Writing to {}'.format(output_file))
        write_list_items_to_csv_file(list_items, output_file)
    else:
        write_list_items_to_console(list_items)
