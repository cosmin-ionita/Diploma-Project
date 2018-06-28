#!/bin/bash

CORE_NAME="TestCore"

rm -rf ./solr_configs

solrctl instancedir --delete $CORE_NAME
solrctl collection --delete $CORE_NAME
