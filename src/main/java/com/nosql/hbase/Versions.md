## Version in HBase

*A {row, column, version} tuple exactly specifies a cell in HBase*

While **rows and column keys** are expressed as **bytes**, the **version** is specified using a **long integer**

    java.util.Date.getTime() or 
    System.currentTimeMillis() is stored as version
    
The HBase version dimension is stored in decreasing order, so that when reading from a store file, the most recent values are found first

#### Specifying the Number of Versions to Store

   Can be mentioned at the time of creation or can alter the table to mention how many version of each column needs to stored
   
   default version number - 1
   
     alter ‘t1′, NAME => ‘f1′, VERSIONS => 5  --  keep a maximum of 5 versions of all columns in column family f1
     
 ### Versions and HBase Operations
 
   **Get/Scan**
   
   By default we will getting the latest value for each column in a column family
   
   The default behavior can be modified in two ways
   
     to return more than one version, see Get.setMaxVersions()
     
     to return versions other than the latest, see Get.setTimeRange()
     
   https://hbase.apache.org/book.html#_get_scan
   
   **Put**
   
     Doing a put always creates a new version of a cell, at a certain timestamp. By default the system uses the server’s currentTimeMillis, but you can specify the version (= the long integer) yourself, on a per-column level. This means you could assign a time in the past or the future, or use the long value for non-time purposes.
     
     To overwrite an existing value, do a put at exactly the same row, column, and version as that of the cell you want to overwrite.
     
     
   **Delete**
   
     