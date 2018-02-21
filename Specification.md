# Feature list

This document contains the list of features that the project will support. This list will
be updated along the way.

|                        Tool Features                       |
|------------------------------------------------------------|
|   Category    |       Name        |      Description       |
|------------------------------------------------------------|
|  System Wide Features |  Compression formats | It should support an extensible interface for compression formats - zip, gzip, tar, etc. |
|                       |  Cleanup procedure   | Old data should be recycled (compressed on a cheaper storage or deleted)                 |
|                       |  Data retention      | The user should be able to configure data storage time (permanent, temporary - configurable by user) |
|                       |  Batch mode          | The tool can index files and perform map reduce jobs on a configurable period of time. When the user runs the search, it will get the results based on the last indexed files. |
|                       |  Near real time mode | The tool can be run by the user in grep like way (waiting for the results).              |
