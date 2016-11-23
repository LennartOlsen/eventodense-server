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
 
```
CREATE TABLE "public"."events" (
    "id" varchar(255) NOT NULL,
    "lat" double precision,
    "lng" double precision,
    "start" bigint,
    "end" bigint,
    "name" text,
    "description" text,
    "imageId" varchar(255),
    "radius" bigint,
    "color", text,
    PRIMARY KEY ("id")
);
``` 