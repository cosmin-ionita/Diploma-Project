#!/bin/bash

CORE_NAME=TestCore

# This loads the SOLR_ZK_ENSEMBLE env var
source /etc/solr/conf/solr-env.sh

solrctl --zk $SOLR_ZK_ENSEMBLE instancedir --generate $HOME/solr_configs

cp schema.xml ./solr_configs/conf/

solrctl --zk $SOLR_ZK_ENSEMBLE instancedir --create $CORE_NAME $HOME/solr_configs

solrctl --zk $SOLR_ZK_ENSEMBLE collection --create $CORE_NAME -r 2
