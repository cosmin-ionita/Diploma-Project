[Date: 26.03.2018]

## Apache Solr brief description

[Terminal workout, manual configuration]

* Solr has one control script: **/bin/solr** that can be used to manage it
* A _core_ or a _collection_ is basically a unqiue index.
* [Idea] You can use cores to maintain a clear distinction between a searchable index
and a updating index (batch mode vs near real-time mode).
* You can create cores with the command **/bin/solr create_core -c <core_name>**
* You can add files to be indexed with the following command **/bin/post -c <core_name> <path_to_files>**
* The **/bin/post** binary can be used to interact with Solr in many ways (use Schema API, etc.)
* There are 2 configuration files that can be modified to match your data: solrConfig.xml and schema.xml
* Initially, those files are configured to detect the schema automatically (by type guessing [Consider this]).
