# Diploma-Project
This repository contains my Bachelor's CS degree project as well as it's timeline and incremental progress.

## Features and architecture

* [Features](Images/Specifications.png)
* [Architecture](Images/Architecture.png)

## Overall progress

### Week 1 (19.02.2018 -> 23.02.2018)

* :ballot_box_with_check: Define a specific set of use cases detailed in Images/Specifications.png file as a result of the discussion with Flavian, Cosmin R. and Dan T.
* :ballot_box_with_check: Research on the appropriate technologies (Apache Flume, Hadoop, Solr)
* :ballot_box_with_check: Create the project pre-architecture and establish it
* :ballot_box_with_check: Research on Apache Flume to see if it supports metadata extraction and different log formats
* :ballot_box_with_check: Create a prototype Solr project using Docker and SolrJ
* :ballot_box_with_check: Request access to AWS infrastructure

### Week 2 (26.02.2018 -> 02.03.2018)

* :ballot_box_with_check: Discuss with Ciprian D. (coordinator professor) to get approval on project architecture and features (last week progress)
* :ballot_box_with_check: Test Solr in manual configuration to understand the flow (results presented [here](Documentation/Solr/SolrSummary.md))
* :ballot_box_with_check: Research on different types of logs and think about a unique, structured data transfer object
* :ballot_box_with_check: Research on Solr Analyzers
* :ballot_box_with_check: Get access to AWS and Splunk
* :ballot_box_with_check: Research on Morphlines and MapReduceIndexerTool

### Week 3 (5.03.2018 -> 9.03.2018)

* :ballot_box_with_check: Discuss with Andrei F. to get access to the AWS infrastructure
* :ballot_box_with_check: Bootstrap the infrastructure and get access to Cloudera dashboard
* :ballot_box_with_check: Analyze the current infrastructure
* :ballot_box_with_check: Install Flume through Cloudera (need to make this persistent in the future)

### Week 4 (12.03.2018 -> 16.03.2018)

* Create a jar that accumulates logs and compress them (simulates the real world scenario)
* Research on a log parser (for log4j but not only)
* Deploy the jars on the cluster
* Configure Flume to send data to HDFS
* Analyze graphs and see how data flows
