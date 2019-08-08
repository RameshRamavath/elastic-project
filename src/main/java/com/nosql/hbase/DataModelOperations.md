##  Operations in HBase

*The four primary data model operations are **Get, Put, Scan, and Delete.** Operations are applied via Table instances*

**Get**

  to get specific row from table. it will executed via Table.get
  
    default Result get(Get get)
              throws IOException  --- > Extracts certain cells from a given row.
              
    default Result[] get(List<Get> gets)
              throws IOException  ----> Extracts specified cells from the given rows, as a batch.
              
**Put**

  Put either add new rows to table [if already not exists] or can update rows [if exists]
  
    default void put(Put put)
              throws IOException   -- > Puts some data in the table.
              
    default void put(List<Put> puts)
              throws IOException   ----> Batch puts the specified data into the table.
              
              
    use Table.batch operation for bulk load
    
    default void batch(List<? extends Row> actions,
                       Object[] results)
                throws IOException, InterruptedException
                
                actions - list of Get, Put, Delete, Increment, Append, RowMutations.
                results - Empty Object[], same size as actions. Provides access to partial results, in case an exception is thrown
                
**Scans**

  *Scan allow iteration over multiple rows for specified attributes*  -- https://hbase.apache.org/book.html#scan
  
  Assume that a table is populated with rows with keys "row1", "row2", "row3", and then another set of rows with the keys "abc1", "abc2", and "abc3". 
  Then using a Scan instance we can get the rows beginning with "row"
  
**Delete**

  Delete removes a row from a table.
  
  HBase does not modify data in place, and so deletes are handled by creating new markers called **tombstones**. These tombstones, along with the dead values, are cleaned up on **major compactions**
  
  