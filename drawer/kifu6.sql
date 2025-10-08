DROP table IF EXISTS pattern;

CREATE table pattern (
  pattern_id int NOT NULL AUTO_INCREMENT,
  pattern_name text,
  pattern_url text, 
  PRIMARY KEY (pattern_id)
);

DROP table IF EXISTS spot;

CREATE table spot (
  spot_id int NOT NULL AUTO_INCREMENT,
  spot_name text,
  pattern_id int,
  
  PRIMARY KEY (spot_id)
);

DROP table IF EXISTS class;

CREATE table class (
  class_id int NOT NULL AUTO_INCREMENT,
  class_name text,
  pattern_id int,
  
  PRIMARY KEY (class_id)
);

DROP table IF EXISTS relation;

CREATE table relation (
  relation_id int NOT NULL AUTO_INCREMENT,
  relation_name text,
  before_id text,
  after_id text, 
  pattern_id int,
  
  PRIMARY KEY (relation_id)
);

DROP table IF EXISTS attribute;

CREATE table attribute (
  attribute_id int NOT NULL AUTO_INCREMENT,
  attribute_name text,
  class_id int,
  pattern_id int,
  
  PRIMARY KEY (attribute_id)
);

DROP table IF EXISTS operation;

CREATE table operation (
  operation_id int NOT NULL AUTO_INCREMENT,
  operation_name text,
  class_id int,
  pattern_id int,
  
  PRIMARY KEY (operation_id)
);

DROP table IF EXISTS pattern_experiment_1;

CREATE table pattern_experiment_1 (
	pattern_id int NOT NULL AUTO_INCREMENT, 
	pattern_name text,
	pattern_url text,
	
	PRIMARY KEY (pattern_id)
);

DROP table IF EXISTS spot_experiment_1;

CREATE table spot_experiment_1 (
  spot_id int NOT NULL AUTO_INCREMENT,
  spot_name text,
  pattern_id int,
  
  PRIMARY KEY (spot_id)
);

	