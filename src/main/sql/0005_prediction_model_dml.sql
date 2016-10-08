
-- definition data inserts 

-- prediction_models
INSERT into employmeo.prediction_models (prediction_model_id, model_name, version, model_type, description, active) 
VALUES (1,'initial_test', 1, 'linear', 'Hypothetical model for initial build out', TRUE); 
INSERT into employmeo.prediction_models (prediction_model_id, model_name, version, model_type, description, active) 
VALUES (2,'simple_linear', 1, 'linear', 'Simple linear regression model', TRUE); 

-- prediction_targets
INSERT INTO employmeo.prediction_targets ("prediction_target_id", "name", "label" )
values (1, 'hirability', 'Likelihood of getting hired');
INSERT INTO employmeo.prediction_targets ("prediction_target_id","name", "label" )
values (2, 'tenure_days', 'Tenure in number of days of serving role in said position');
INSERT INTO employmeo.prediction_targets ("prediction_target_id","name", "label" )
values (3, 'get_raise_in_6_months', 'Likelihood of getting a raise within 6 months in this position');

-- model_target_associations
INSERT INTO employmeo.model_target_associations ("model_target_association_id", "prediction_target_id", "model_id" )
values (1, 
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability'), 
	(select prediction_model_id from employmeo.prediction_models where model_name = 'simple_linear' and model_type = 'linear' and version = 1)
);

-- position_target_associations
INSERT INTO employmeo.position_target_associations ("position_target_association_id", "position_id" , "prediction_target_id")
values (1, 
	(select position_id from employmeo.positions where position_name = 'Manager' 
		and position_account = (select account_id from employmeo.accounts where account_name = 'Employmeo')),
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability')
);
INSERT INTO employmeo.position_target_associations ("position_target_association_id", "position_id" , "prediction_target_id")
values (2, 
	(select position_id from employmeo.positions where position_name = 'Cook' 
		and position_account = (select account_id from employmeo.accounts where account_name = 'Employmeo')),
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability')
);
INSERT INTO employmeo.position_target_associations ("position_target_association_id", "position_id" , "prediction_target_id")
values (3, 
	(select position_id from employmeo.positions where position_name = 'Crew' 
		and position_account = (select account_id from employmeo.accounts where account_name = 'Employmeo')),
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability')
);
INSERT INTO employmeo.position_target_associations ("position_target_association_id", "position_id" , "prediction_target_id")
values (4, 
	(select position_id from employmeo.positions where position_name = 'Clerk' 
		and position_account = (select account_id from employmeo.accounts where account_name = 'Employmeo')),
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability')
);
INSERT INTO employmeo.position_target_associations ("position_target_association_id", "position_id" , "prediction_target_id")
values (5, 
	(select position_id from employmeo.positions where position_name = 'Operations' 
		and position_account = (select account_id from employmeo.accounts where account_name = 'TCC')),
	(select prediction_target_id from employmeo.prediction_targets where name = 'hirability')
);


--//@UNDO

DELETE from employmeo.position_target_associations;
DELETE from employmeo.model_target_associations;
DELETE from employmeo.prediction_targets;
DELETE from employmeo.prediction_models;

