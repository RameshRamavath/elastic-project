##     HBase and Schema Design

#### Table Schema Rules Of Thumb

*In HBase tables --> multiple regions and these regions are served by Region Servers*

*Regions are vertically divided by **column families** into “**Stores**” and Stores are saved as files on HDFS*

**Focus areas**

 - Row key
 - Column family size [number of columns]
 - number of total column families
 - Region servers & size of data/ data served by each region server

  
  