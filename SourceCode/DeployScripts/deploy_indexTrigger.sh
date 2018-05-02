#!/bin/bash

# This script deploys the log generator jar file to the cluster

CLIENT_PATH=../IndexTrigger/out/artifacts/IndexTrigger_jar/IndexTrigger.jar

CLIENT_CONFIGS_PATH=../IndexTrigger/configs/
CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni.yaml
REMOTE_PATH=/home/ec2-user/binaries/
NODE_NAME=va6-ioni-ksmaster-1

if [ "$#" -ne 1 ]; then
	echo " === Deploying only the client jar ==="
else
	if [ "$1" = "include_configs" ]; then

		echo "=== Deploy client configuration in progress... ==="

        	ops $CLUSTER_CONFIG sync $CLIENT_CONFIGS_PATH $NODE_NAME:$REMOTE_PATH -l ec2-user

	        echo "=== Client configuration deployment done! ==="
	fi
fi

echo "=== Deploy client in progress... ==="

ops $CLUSTER_CONFIG sync $CLIENT_PATH $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "=== Deploy client done! ==="


