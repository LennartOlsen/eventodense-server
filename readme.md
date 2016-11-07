SQL :
    
 ```
 CREATE TABLE points (
     id character varying(255) NOT NULL,
     lat double precision,
     lng double precision,
     "timestamp" bigint,
     accuracy double precision,
     altitude double precision,
     eventid character varying(255),
     deviceid character varying(255)
 );
 
 ALTER TABLE ONLY points
     ADD CONSTRAINT points_pkey PRIMARY KEY (id);
 ```