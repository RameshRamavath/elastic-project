# Typical NoSQL database features

* **Key and Value**

      All NoSql databases have similar data model - Row key && value [contains attributes and values]

* **Indexed**
 
      Most of the NoSQL database are indexed on Row Key
  
* **Partitioned/Sharded**

      Data will be partitioned based on Row key. also called as sharding 
 
* **Replication**

      Multiple copies of data for fault toulerance

* **CAP Theorem**
      
      Consistency, Avaliability and Partition tolerance. One database can give any two of three features
      
* **Commit log**
             
      For restoring and recovery of data if something goes wrong with data base while doing some operations

       
* **Minor and Major compaction**

      Most Big Data tools work better if we have few larger files than too many small files
      
            
      ** When we do some Put in HBase - data won't be written immediately to HDFS, rather it keep data in memory and writes to Persisted files in some regular intervals - this process is called as Compaction
      
      Periodic merging of files within each partition to avoid having too many small files
      
* **tombstones**

      soft delete - HBase marks for deletion of record and in process of next Major compaction it actually deleted data from HDFS

* **Vacuum cleaning**

      Hard delete - actual delete of data from Data store under the process of major compaction
     
* **Consistency Level**

      Commit point or Ack to client 
      
      Zero level -- once insert/update/delete happened in memory we are good to consider it as completed transaction/commit - Data loss may occuer if data crashes before writing to Data store
      Master partition commit -- any operation success on Master replica is considered as complete
      complete consistency -- any operation should be written to replica copies also -- Performace may be less
      
* **CRUD Operations**

      create [insert]
      read
      update
      delete
      
* ****    

     