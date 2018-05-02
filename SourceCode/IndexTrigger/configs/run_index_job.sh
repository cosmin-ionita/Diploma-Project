#!/bin/bash

SOLR_CORE=TestCore

sudo hadoop 															\
	--config /etc/hadoop/conf.cloudera.YARN/ 										\
	jar /opt/cloudera/parcels/CDH/lib/solr/contrib/mr/search-mr-*-job.jar org.apache.solr.hadoop.MapReduceIndexerTool     	\
	--morphline-file /home/ec2-user/workdir/morphline.conf									\
	--output-dir hdfs://va6-ioni-ksmaster-1:8020/dir						      			\
	--go-live														\
	--verbose														\
	--zk-host va6-ioni-ksmaster-1:2181/solr											\
	--collection $SOLR_CORE													\
	--input-list /home/ec2-user/workdir/input.txt										\
