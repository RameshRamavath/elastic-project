# transaction-search-elastic

### Running Elasticsearch and Kibana on local using docker compose

    * Use /src/main/resources/docker-compose.yml
    * Create volume
    * Run - docker-compose up -d
    
 
### Creating and updating the Mapping

* Dynamic mapping - with explicit properties
* Types of mapping 

   * dynamic - true - new fields will be added to index mapping and indexed in Elastic and available for search
   * dynamic - false - New fields will be stored in Elastic but not indexed and hence not available for search 
   * dynamic - strict - Throws exception on unknown fields in payload
   
* Create mapping POST request:
   
   * Schema of requestBody - all fields must be available in payload
   
       private String indexName;

       private int numberOfShards;
       
       private int numberOfReplicas;

       private boolean dynamic;

       private Map<String, Object> properties;
   
   * Sample create index with mapping
   
       POST - http://localhost:8080/search/v1/index
       
            {
                "properties": {
                    "receiver_firstname":    { "type": "text", "index":true },
                    "receiver_lastname":  { "type": "text" , "index":false},
                    "sender_firstnamedecrypted":   { "type": "text" ,"index":true },
                    "sender_lastnamedecrypted":   { "type": "text" ,"index":true },
                    "sender_emailaddressdecrypted":   { "type": "keyword" ,"index":true },
                    "sender_customernumber":   { "type": "double" ,"index":true },
                    "sender_billing_phonenumberdecrypted":   { "type": "double" ,"index":true },
                    "transaction_timestamp":   { "type": "date" ,"index":true }
              },
                    "numberOfShards": 2,
                    "numberOfReplicas": 2,
                    "indexName": "transaction_test",
                    "dynamic" : false
              }

* GET mapping for a index

   URL - http://localhost:8080/search/v1/mapping/transaction_test
   
   * Response:
   
           {
               "dynamic": "false",
               "properties": {
                   "receiver_firstname": {
                       "type": "text"
                   },
                   "sender_firstnamedecrypted": {
                       "type": "text"
                   },
                   "sender_emailaddressdecrypted": {
                       "type": "keyword"
                   },
                   "transaction_timestamp": {
                       "type": "date"
                   },
                   "sender_lastnamedecrypted": {
                       "type": "text"
                   },
                   "receiver_lastname": {
                       "index": false,
                       "type": "text"
                   },
                   "sender_billing_phonenumberdecrypted": {
                       "type": "double"
                   },
                   "sender_customernumber": {
                       "type": "double"
                   }
               }
           }
       
      

* Update the schema - PUT Call

   * Schema of requestBody
   
       private String indexName;

       private Map<String, Object> properties;
   
   * Sample create index with mapping
       
           PUT - http://localhost:8080/search/v1/index
           
               {
                     "properties": {
                           "transaction_ipaddress": {
                                 "type": "keyword",
                                 "index": true
                           }
                     },
                     "indexName": "transaction_test"
               }
           
        
            Test  - http://localhost:8080/search/v1/mapping/transaction_test
            
            {
                "dynamic": "false",
                "properties": {
                    "receiver_firstname": {
                        "type": "text"
                    },
                    "sender_firstnamedecrypted": {
                        "type": "text"
                    },
                    "sender_emailaddressdecrypted": {
                        "type": "keyword"
                    },
                    "transaction_timestamp": {
                        "type": "date"
                    },
                    "sender_lastnamedecrypted": {
                        "type": "text"
                    },
                    "receiver_lastname": {
                        "index": false,
                        "type": "text"
                    },
                    "sender_billing_phonenumberdecrypted": {
                        "type": "double"
                    },
                    "sender_customernumber": {
                        "type": "double"
                    },
                    "transaction_ipaddress": {   --- newly adde field
                        "type": "keyword"
                    }
                }
            }

### Document Search

* Using query_string 

  template - https://www.elastic.co/guide/en/elasticsearch/reference/7.8/query-dsl-query-string-query.html
  
* RequestBody for POST call

   * Payload
   
           @NotEmpty(message = "Please provide valid query...!")
           private String query;
           @NotEmpty (message = "Please provide exiting collection name...!")
           private String collection;
           private String [] includeFields;
           private String [] excludeFields;

   * sample query
   
           {
           	"collection" : "transactionv2",
           	"query" : "sender_firstnamedecrypted:BROU",
           	"includeFields": [],
           	"excludeFields": []
           }
           
           {
           	"collection" : "transactionv2",
           	"query" : "sender_firstnamedecrypted:BROU",
           	"includeFields": ["sender_firstnamedecrypted","transaction_csntransactionid"],
           	"excludeFields": []
           }
           
           {
           	"collection" : "transactionv2",
           	"query" : "sender_firstnamedecrypted:BROU AND sender_emailaddressdecrypted:SERGEDAHI@GMAIL.COM",
           	"includeFields": ["sender_firstnamedecrypted","transaction_csntransactionid"],
           	"excludeFields": []
           }
           
           {
           	"collection" : "transactionv2",
           	"query" : "sender_firstnamedecrypted:BROU AND sender_emailaddressdecrypted:SERGEDAHI@GMAIL.COM"
           }
           
           ** Range query
           
           {
           	"collection" : "transactionv2",
           	"query" : "sender_customernumber:406017555 AND sender_emailaddressdecrypted:SERGEDAHI@GMAIL.COM AND sender_firstnamedecrypted:IGOR AND sender_lastnamedecrypted:DAHI AND sender_billing_phonenumberdecrypted:611191538 AND receiver_firstname:AKASSI AND receiver_lastname:KOUASSI AND transaction_timestamp:[2020-06-10 TO 2020-06-30]"
           }
          
   
   
### Focus on date columns 

* use this link - https://www.elastic.co/guide/en/elasticsearch/reference/current/date.html
