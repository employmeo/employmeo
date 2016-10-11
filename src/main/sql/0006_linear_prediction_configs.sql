-- linear regression model configurations

CREATE TABLE employmeo.linear_regression_config (
  config_id           bigserial NOT NULL PRIMARY KEY,
  model_id            bigint not null,
  position_id      bigint NOT NULL,
  corefactor_id    integer NOT NULL,
  coefficient      double precision,
  significance     double precision,
  active          boolean NOT NULL DEFAULT TRUE,
  created_date    timestamp WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,    
  CONSTRAINT fk_linear_regression_config_model_id
    FOREIGN KEY (model_id)
    REFERENCES employmeo.prediction_models(prediction_model_id),  
  CONSTRAINT fk_linear_regression_config_position_id
    FOREIGN KEY (position_id)
    REFERENCES employmeo.positions(position_id),    
  CONSTRAINT fk_linear_regression_config_corefactor_id
    FOREIGN KEY (corefactor_id)
    REFERENCES employmeo.corefactors(corefactor_id) 
) WITH (
    OIDS = FALSE
);


--//@UNDO

DROP table employmeo.linear_regression_config;
