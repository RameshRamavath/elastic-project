# HBase Data model

**Flexible schema - no need to mention or give column names while creating HBase table**

*In application developer can maintain logic for adding columns to specific table on the fly*


#### Namespace

 - A namespace is a **logical grouping of tables** analogous to a **database** in relation database systems
 - **Use**:
   - **Quota Management** - Restrict the amount of resources (i.e. regions, tables) a namespace can consume.
   - **Namespace Security Administration** - Provide another level of security administration for tenants.
   - **Region server groups** - A namespace/table can be pinned onto a subset of RegionServers thus guaranteeing a coarse level of isolation
 
 
     #Create a namespace
     create_namespace 'my_ns'
 
   
#### Table

  Tables are declared up front at schema definition time
  
     Row -> Key {Column Family {group of columns}}
     
     Row           : Rows are lexicographically sorted with the lowest order appearing first in a table
     Column Family : Columns in Apache HBase are grouped into column families. All column members of a column family have the same prefix
                     courses:history and 
                     courses:math
                     
                     courses - one column family
                     history & math - Columns
                     
     column qualifier: A new column qualifier (column_family:column_qualifier) can be added to an existing column family at any time.
     Cell          : A {row, column, version} tuple exactly specifies a cell in HBase        
  
#### 