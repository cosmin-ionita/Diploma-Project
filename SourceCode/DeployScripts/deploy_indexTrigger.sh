#!/bin/bash

# This script deploys the log generator jar file to the cluster

CLIENT_PATH=../HadoopDriver/out/artifacts/HadoopDriver_jar/HadoopDriver.jar
#CLIENT_PATH=../HadoopDriver/target/HadoopDriver-1.0-SNAPSHOT.jar

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni-qe-1.yaml
REMOTE_PATH=/home/ec2-user/
NODE_NAME=va6-ioni-qe-1-ksmaster-1

echo "=== Deploy client in progress... ==="

ops $CLUSTER_CONFIG sync $CLIENT_PATH $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "=== Deploy client done! ==="


