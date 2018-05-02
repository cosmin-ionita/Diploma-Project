#!/bin/bash

echo "Removing generated archives..."
rm -rf logGenerator/archiveWorkDir/*

echo "Removing generated json files..."
rm -rf ../launchDirectory/*

echo "Killing LogParser process..."
sudo pkill -f 'java -jar logParser/Diploma-Project.jar --watch-directory=logGenerator/archiveWorkDir --destination-directory=../launchDirectory/'
