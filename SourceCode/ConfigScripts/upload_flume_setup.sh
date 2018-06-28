FLUME_SETUP_SCRIPT=./ConfigFiles/flume_machine_setup.sh
RUN_SCRIPT=./ConfigFiles/run.sh
CLEAN_SCRIPT=./ConfigFiles/clean.sh

CLUSTER_CONFIG=../../../../cns/clusters/kscluster-ioni-qe-1.yaml
REMOTE_PATH=/home/ec2-user/
NODE_NAME=va6-ioni-qe-1-ksmaster-3

ops $CLUSTER_CONFIG sync $FLUME_SETUP_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $RUN_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user
ops $CLUSTER_CONFIG sync $CLEAN_SCRIPT $NODE_NAME:$REMOTE_PATH -l ec2-user

echo "Done!"
