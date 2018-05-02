#!/bin/bash

# Running this script as ec2-user
mkdir binaries

# Copy the morphline config file and the scripts that manage the index job
MORPHLINE_CONFIG=./ConfigFiles/morphline.conf
INDEX_JOB_SCRIPT=./ConfigFiles/run_index_job.sh
INDEX_TRIGGER_SCRIPT=./ConfigFiles/start_index_trigger.sh

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni.yaml
REMOTE_PATH=/home/ec2-user/binaries/
NODE_NAME=va6-ioni-ksmaster-1

ops $CLUSTER_CONFIG sync $MORPHLINE_CONFIG $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $INDEX_JOB_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $INDEX_TRIGGER_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Config done!"
