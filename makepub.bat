@echo off
echo Input your txt book location
set /p txt_file=
java -jar txt2md.jar %txt_file%
echo convert to markdown finished.
pandoc --epub-stylesheet=stylesheet.css %txt_file%.md -o %txt_file%.epub
set over=
pause