## HBase Architectural Components

*Three major components*

#### Master Server
  
   - HMaster is implementation of master server. It monitors all RegionServer instances in the cluster
   - HMaster is interface for all metadata changes - **Region assignment, DDL (create, delete tables) operations**
   
         Client ==={create delete tables}===> HMaster ==={interacts with}==> Region servers
   - The NameNode which is on Master Server maintains metadata information for all the physical data blocks that comprise the files 

   **What happens if master goes down??**
   
   - For HBase High Availability we may have more than 1 master in our cluster, so when run a multi master cluster, **all Masters compete to run the cluster** at startup
   - At **Runtime**
     
     * Because the **HBase client talks directly to the RegionServers**, the cluster can still function in a "steady state"
     * **hbase:meta** exists as an HBase table and is not resident in the Master. However, the Master controls critical functions such as RegionServer failover and completing region splits. So while the cluster can still run for a short time without the Master, the Master should be restarted as soon as possible

   - **HMasterInterface**
   
     * HBase master primarily does metadata oriented tasks
     * Table (createTable, modifyTable, removeTable, enable, disable)
     * ColumnFamily (addColumn, modifyColumn, removeColumn)
     * Region (move, assign, unassign)

#### RegionServer
