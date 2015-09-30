#!/bin/bash

## convert to markdown
txt2md.sh $1

pandoc --epub-stylesheet=/Users/wuzhong/softs/bin/stylesheet.css $1.md -o $1.epub
