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
* :ballot_box_with_check: Tried to fix the QE cluster with Dragos C. and Vlad C.
* :ballot_box_with_check: Built the presentation for Scientific Communication Session 2018
* :ballot_box_with_check: Built a document with the initial work on this project

### Week 12 (14.05.2018 -> 18.05.2018)

* :ballot_box_with_check: Added CommonsCLI support in the Client project
* :ballot_box_with_check: Added a custom control in the desktop UI for the search fields
* :ballot_box_with_check: Developed the SolrAPI class on the client
* :ballot_box_with_check: Created custom classes for each command

### Week 13 (21.05.2018 -> 25.05.2018)

* :ballot_box_with_check: Created the SpringBoot REST API on HadoopDriver project (index trigger)
* :ballot_box_with_check: Fixed some packet collisions that were leading to a corrupt fat JAR
* :ballot_box_with_check: Developed a HadoopRestAPI Client on the client project
* :ballot_box_with_check: Tested the API to make sure that index-now and index-interval commands work as expected

### Week 14 (28.05.2018 -> 01.06.2018)

* :ballot_box_with_check: Created a command executor model on the client
* :ballot_box_with_check: Developed the export command in both CLI and GUI
* :ballot_box_with_check: Implemented a merge logic between the time-interval and date-interval commands
* :ballot_box_with_check: Started to work on the official documentation

### Week 15 (04.05.2018 -> 08.06.2018)

* :ballot_box_with_check: Added S3 download functionality on parser
* :ballot_box_with_check: Added SQS receive logic on parser
* :ballot_box_with_check: Implemented the processing logic for each archive using an ExecutorService
* :ballot_box_with_check: Created a test S3 bucket and tested the developed workflow
* :ballot_box_with_check: Created a DataGenerator multithreaded project that generates archives and loads them in S3

### Week 16 (11.05.2018 -> 15.06.2018)

* :ballot_box_with_check: Finalized the client side and tested it manually
* :ballot_box_with_check: Implemented the Job Scheduler on the Hadoop Driver (using a timer controllable by the client)
* :ballot_box_with_check: Developed a logic to detect when the indexing job is finished
* :ballot_box_with_check: Diagnosed a log4j deadlock (Call Appenders) caused by a missing log4j.properties file
* :ballot_box_with_check: Accelerated the work on the documentation
* :ballot_box_with_check: Worked on the presentation for the KeyStone team

### Week 18 (18.05.2018 -> 22.06.2018)

* :ballot_box_with_check: Presented the project to the keystone team
* :ballot_box_with_check: Worked on the documentation
* :ballot_box_with_check: Loaded 34.7 GB of data into the system and tested the entire data flow
* :ballot_box_with_check: Worked on the Faculty formalities regarding the diploma exam

### Week 19 (25.05.2018 -> 29.06.2018)

* :ballot_box_with_check: Finalized and delivered the documentation
* :ballot_box_with_check: Loaded 120 GB of data and tested the entire workflow
* :ballot_box_with_check: Created the official presentation for the next week
