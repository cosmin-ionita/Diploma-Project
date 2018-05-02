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

# Copy the run and clean scripts (neccessary for development stage)

RUN_SCRIPT=./ConfigFiles/run.sh
CLEAN_SCRIPT=./ConfigFiles/clean.sh

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni.yaml
REMOTE_PATH=/home/ec2-user/workDir/binaries/
NODE_NAME=va6-ioni-ksmaster-3

ops $CLUSTER_CONFIG sync $RUN_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $CLEAN_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Config done!"
