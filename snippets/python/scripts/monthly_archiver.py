#!/usr/bin/env python3

import argparse
from datetime import datetime
from os import listdir, path, remove, walk
import shutil
import zipfile

parser = argparse.ArgumentParser(description='Archive files from previous month.')
parser.add_argument('input_dir', type=str, help='path to input directory')
parser.add_argument('output_dir', type=str, help='path to output directory')
parser.add_argument('-d', '--delete', action='store_true', help='whether to delete the original files')
args = parser.parse_args()

input_dir = path.realpath(args.input_dir)
output_dir = path.realpath(args.output_dir)
delete_original_files = args.delete

now = datetime.now()
archive_year = now.year
archive_month = now.month - 1

if archive_month == 0:
  archive_year -= 1
  archive_month = 12

output_file_path = path.join(output_dir, '{}-{}-archive.zip'.format(archive_year, archive_month))

input_files = listdir(input_dir)

for input_file in input_files:
  if input_file.endswith('-archive.zip'):
    continue

  input_file = path.join(input_dir, input_file)

  last_modified = datetime.fromtimestamp(path.getmtime(input_file))
  last_modified_month = last_modified.month
  last_modified_year = last_modified.year

  if archive_year != last_modified_year or archive_month != last_modified_month:
    continue

  output_file = zipfile.ZipFile(output_file_path, 'a', zipfile.ZIP_DEFLATED)

  is_file = path.isfile(input_file)

  if is_file:
    output_file.write(input_file, path.relpath(input_file, input_dir))
  else:
    for dirname, subdirs, files in walk(input_file):
      for filename in files:
        output_file.write(path.join(dirname, filename), path.relpath(path.join(dirname, filename), input_dir))

  output_file.close()

  if delete_original_files:
    if is_file:
      remove(input_file)
    else:
      shutil.rmtree(input_file)
