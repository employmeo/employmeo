
CREATE TABLE IF NOT EXISTS  employmeo.prediction_models (
  prediction_model_id  bigserial NOT NULL PRIMARY KEY,
  model_name    varchar(50) NOT NULL,
  version          integer NOT NULL DEFAULT 1,
  model_type 	varchar(50) NOT NULL,
  description    varchar(500) NOT NULL,
  active          boolean NOT NULL DEFAULT TRUE,
  created_date    timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
) WITH (
    OIDS = FALSE
);
  
CREATE TABLE IF NOT EXISTS  employmeo.model_configuration (
  model_configuration_id  bigserial NOT NULL PRIMARY KEY,
  model_id bigint not null,
  config_key    varchar(50) NOT NULL,
  config_value    varchar(500) NOT NULL,
  active          boolean NOT NULL DEFAULT TRUE,
  created_date    timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  /* Foreign keys */
  CONSTRAINT fk_model_configuration_model_id
    FOREIGN KEY (model_id)
    REFERENCES employmeo.prediction_models(prediction_model_id)  
) WITH (
    OIDS = FALSE
);  


-- definition data inserts 
INSERT into employmeo.prediction_models (model_name, version, model_type, description, active) 
VALUES ('initial_test_model', 1, 'linear', 'Hypothetical model for initial build out', TRUE); 
INSERT into employmeo.prediction_models (model_name, version, model_type, description, active) 
VALUES ('simple_linear', 1, 'linear', 'Simple linear model', TRUE); 

--//@UNDO

DELETE from employmeo.prediction_models;

DROP table  employmeo.model_configuration;
DROP table  employmeo.prediction_models;
