[Date: 26.03.2018]

## Apache Solr brief description

### [Terminal workout - manual configuration]

* Solr has one control script: **/bin/solr** that can be used to manage it
* A _core_ or a _collection_ is basically a unqiue index.
* [Idea] You can use cores to maintain a clear distinction between a searchable index
and a updating index (batch mode vs near real-time mode).
* You can create cores with the command **/bin/solr create_core -c <core_name>**
* You can add files to be indexed with the following command **/bin/post -c <core_name> <path_to_files>**
* The **/bin/post** binary can be used to interact with Solr in many ways (use Schema API, etc.)
* There are 2 configuration files that can be modified to match your data: solrConfig.xml and schema.xml
* Initially, those files are configured to detect the schema automatically (by type guessing [Consider this]).
* Make sure that java -version is 1.8 - (1.6.1) and not (1.5.1)

### [Solr on top of Hadoop]

* Solr can be instructed to store it's index in Hadoop but not to index automatically data that appeared in HDFS
* We need to index files that have been added to HDFS (our usecase)
* Apparently this behavior is not built-in in Solr. See [1] and [2]
* We need to create a logic for this





[1] https://hortonworks.com/hadoop-tutorial/searching-data-solr/

[2] https://github.com/lucidworks/hadoop-solr
