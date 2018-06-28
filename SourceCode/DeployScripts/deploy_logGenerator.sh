#!/bin/bash

## This script deploys the log generator jar file to the cluster

LOG_GEN_PATH=../../CodeSamples/LogGenerator/Log_Generator/out/artifacts/Log_Generator_jar/Log_Generator.jar
CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni-qe-1.yaml
REMOTE_PATH=/home/ec2-user/workDir/binaries/logGenerator
NODE_NAME=va6-ioni-qe-1-ksmaster-3
echo "Deploy in progress..."

ops $CLUSTER_CONFIG sync $LOG_GEN_PATH $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Deploy done!"
