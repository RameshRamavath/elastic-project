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
 
**In HBase, data is stored in tables, which have rows and columns, but it's better to visualize it as a **multi-dimensional map****
  
#### Table

  *An HBase table consists of multiple rows*
  
  **ROW** in HBase consists of a row key and one or more columns with values associated with them and rows re sorted by the row key - hence **the design of the row key is very important**

  **Column** in HBase consists of a column family and a column qualifier, which are delimited by a : (colon) character.  
  
  **Column Family**
  
     Column families physically colocate a set of columns and their values, often for performance reasons. Each column family has a set of storage properties, such as whether its values should be cached in memory, how its data is compressed or its row keys are encoded, and others
  
  **Column Qualifier**
  
     A column qualifier is added to a column family to provide the index for a given piece of data. 
     Given a column family content, a column qualifier might be content:html, and another might be content:pdf. 
     Though column families are fixed at table creation, column qualifiers are mutable and may differ greatly between rows.
  
  **Cell**
  
     A cell is a combination of row, column family, and column qualifier, and contains a value and a timestamp, which represents the value’s version.
  
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
  
#### Conceptual View

  In HBase for each PUT operation one timestamp will be maintained along with the columns we have added.
  
  https://hbase.apache.org/book.html#conceptual.view
  
  in this view it looks very sparse, but in data store [physically] it won't store any NULL values
  
#### Physical view

  Physically the data is stored by column family. A new column qualifier (column_family:column_qualifier) can be added to an existing column family at any time.
  
  https://hbase.apache.org/book.html#physical.view
  
  
  
  
