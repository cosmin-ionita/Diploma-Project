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

* :ballot_box_with_check: Analyze all keystone logs to see their format
* :ballot_box_with_check: Create a simulator that takes a large log file, splits it into multiple files and archives those files
* :ballot_box_with_check: Create a parser that decompresses each archive and converts each log event into a structured JSON model

### Week 5 (19.03.2018 -> 23.03.2018)

* :ballot_box_with_check: Test the parser along with the simulator
* :ballot_box_with_check: Modify the parser to support high speed archive ingestion
* :ballot_box_with_check: Add support for multiline logs (ex. stacktraces)
* :ballot_box_with_check: Add parser functions that support only a subset of log formats (ongoing work)

### Week 6 (26.03.2018 -> 30.03.2018)

* :ballot_box_with_check: Research on Grok parser as a final parsing solution
* :ballot_box_with_check: Created a test project to illustrate the functionality of Grok
* :ballot_box_with_check: Started to analyze Flume configuration
* :ballot_box_with_check: Modified Flume config file to send data to a specific HDFS directory

### Week 7 (02.04.2018 -> 06.04.2018)

* :ballot_box_with_check: Changed Flume config to send an entire blob (file) to HDFS, with an established max size
* :ballot_box_with_check: Tested the LogGenerator along with the LogParser and with Flume
* :ballot_box_with_check: Modified both projects accordingly to pass the functionality test
* :ballot_box_with_check: Research on the MapReduceIndexerTool job

### Week 8 (09.04.2018 -> 13.04.2018)

* :ballot_box_with_check: Research on the Morphline concept along with the MapReduceIndexerTool
* :ballot_box_with_check: Created a Morphline config file that matches the project needs
* :ballot_box_with_check: Created a script that starts the indexing job
* :ballot_box_with_check: Debugged a strange error on MapReduceIndexerTool using [StackOverflow](https://stackoverflow.com/questions/49672282/mapreduceindexertool-output-dir-error-cannot-write-parent-of-file)
* :ballot_box_with_check: Managed to run the indexing job in --dry-run mode (without Solr loading)

### Week 9 (16.04.2018 -> 20.04.2018)

* :ballot_box_with_check: Modified the Morphline config file to load the index into Solr
* :ballot_box_with_check: Created a script that generates a Solr Config file
* :ballot_box_with_check: Created a script that creates a Solr Core based on a generated configuration
* :ballot_box_with_check: Adapted the default Solr schema to match the serialized JSON model of a log event
* :ballot_box_with_check: Ran the entire flow and checked the index correctness on small files with a few models

### Week 10 (23.04.2018 -> 27.04.2018)

* :ballot_box_with_check: Implemented the Grok engine in the project parser
* :ballot_box_with_check: Created a mockup client project
* :ballot_box_with_check: Implemented TarGz decompressor
* :ballot_box_with_check: Implemented Zip decompressor
* :ballot_box_with_check: Created unit tests (using JUnit) for all decompressors
* :ballot_box_with_check: Presented the current progress to the coordinator professor
* :ballot_box_with_check: Discussed with Cosmin R. about triggering the index job using SQS

### Week 11 (30.04.2018 -> 4.05.2018)

* :ballot_box_with_check: Created a set of config scripts that will prepare the newly created infrastructure
* :ballot_box_with_check: Created the daemon that will run on HDFS machine and trigger the index job (IndexTrigger)
* :ballot_box_with_check: Started building of a Desktop app UI using Java Swing

### Week 12 (07.05.2018 -> 11.05.2018)

* :ballot_box_with_check: Replaced Swing UI with a Java FX one (because it's more flexible)
