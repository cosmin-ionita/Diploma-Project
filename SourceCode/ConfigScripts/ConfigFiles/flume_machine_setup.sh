#!/bin/bash

# I'm here on ec2-user home dir

# This is the main directory
mkdir workDir

# This will contain parser and generator jars as well as other useful directories
mkdir workDir/binaries
mkdir workDir/binaries/logGenerator
mkdir workDir/binaries/logGenerator/archiveWorkDir
mkdir workDir/binaries/logGenerator/rawLogFiles

mkdir workDir/binaries/logParser

# This directory is watched by flume
mkdir workDir/launchDirectory

# Add the flume user to ec2-user group
usermod -a -G ec2-user flume

# Set the appropriate permissions to give access to flume user to the launchDirectory
sudo chmod 770 /home/ec2-user
sudo chmod 775 ./workDir
sudo chmod 775 ./workDir/launchDirectory

sudo chown -R ec2-user:ec2-user ./workDir

mv run.sh clean.sh ./workDir/binaries/

echo "Config done!"
