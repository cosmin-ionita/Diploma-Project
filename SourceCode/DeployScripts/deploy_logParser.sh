#!/bin/bash

## This script deploys the log parser jar file to the cluster

LOG_GEN_PATH=../LogParser/out/artifacts/Diploma_Project_jar/Diploma-Project.jar
CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni.yaml
REMOTE_PATH=/home/ec2-user/workDir/binaries/logParser
NODE_NAME=va6-ioni-ksmaster-3

echo "Deploy in progress..."

ops $CLUSTER_CONFIG sync $LOG_GEN_PATH $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Deploy done!"
