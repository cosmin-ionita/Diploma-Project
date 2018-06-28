#!/bin/bash

SCHEMA=./ConfigFiles/schema.xml
CREATE_SOLR_CORE=./ConfigFiles/create_solr_core.sh
DELETE_SOLR_CORE=./ConfigFiles/delete_solr_core.sh

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni-qe-1.yaml
REMOTE_PATH=/home/ec2-user/
NODE_NAME=va6-ioni-qe-1-kssolr-1

ops $CLUSTER_CONFIG sync $SCHEMA $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $CREATE_SOLR_CORE $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $DELETE_SOLR_CORE $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Config done!"
