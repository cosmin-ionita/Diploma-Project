#!/bin/bash

MORPHLINE_CONFIG=./ConfigFiles/morphline.conf
INDEX_JOB_SCRIPT=./ConfigFiles/run_index_job.sh

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni-qe-1.yaml
REMOTE_PATH=/home/ec2-user/
NODE_NAME=va6-ioni-qe-1-ksmaster-1

ops $CLUSTER_CONFIG sync $MORPHLINE_CONFIG $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $INDEX_JOB_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Config done!"
