
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

CREATE TABLE employmeo.prediction_targets (
  prediction_target_id  bigserial NOT NULL,
  "name"                varchar(50) NOT NULL,
  label                 varchar(100) NOT NULL,
  description           varchar(500),
  active                boolean NOT NULL DEFAULT true,
  created_date          timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  /* Keys */
  CONSTRAINT prediction_targets_pkey
    PRIMARY KEY (prediction_target_id), 
  CONSTRAINT uc_prediction_targets_name
    UNIQUE ("name")
) WITH (
    OIDS = FALSE
  );
  
CREATE TABLE IF NOT EXISTS  employmeo.position_target_associations (
  position_target_association_id  bigserial NOT NULL PRIMARY KEY,
  position_id bigint not null,
  prediction_target_id bigint not null,
  target_threshold numeric(10) DEFAULT NULL::numeric,
  active          boolean NOT NULL DEFAULT TRUE,
  created_date    timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  /* Foreign keys */
  CONSTRAINT fk_position_target_associations_target_id
    FOREIGN KEY (prediction_target_id)
    REFERENCES employmeo.prediction_targets(prediction_target_id),
  CONSTRAINT fk_position_target_associations_position_id
    FOREIGN KEY (position_id)
    REFERENCES employmeo.positions(position_id)
) WITH (
    OIDS = FALSE
);    

CREATE TABLE IF NOT EXISTS  employmeo.model_target_associations (
  model_target_association_id  bigserial NOT NULL PRIMARY KEY,
  prediction_target_id bigint not null,
  model_id bigint not null,
  active          boolean NOT NULL DEFAULT TRUE,
  created_date    timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  /* Foreign keys */
  CONSTRAINT fk_model_target_associations_model_id
    FOREIGN KEY (model_id)
    REFERENCES employmeo.prediction_models(prediction_model_id),
  CONSTRAINT fk_model_target_associations_target_id
    FOREIGN KEY (prediction_target_id)
    REFERENCES employmeo.prediction_targets(prediction_target_id)
) WITH (
    OIDS = FALSE
);  
 
--//@UNDO

DROP table  employmeo.model_target_associations;
DROP table  employmeo.position_target_associations;
DROP table  employmeo.prediction_targets;
DROP table  employmeo.model_configuration;
DROP table  employmeo.prediction_models;


