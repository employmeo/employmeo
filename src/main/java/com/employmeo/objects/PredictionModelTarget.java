package com.employmeo.objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

/**
 * The persistent class for the predictive_model database table.
 * 
 */
@Entity
@Table(name = "model_target_associations")
@NamedQuery(name = "PredictionModelTarget.findByTargetName", 
			query = "SELECT p FROM PredictionModelTarget p WHERE p.predictionTarget.name = :targetName and p.predictionTarget.active = TRUE")
@Data
public class PredictionModelTarget extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "model_target_association_id")
	private Integer modelTargetId;

	@ManyToOne
	@JoinColumn(name = "model_id")
	private PredictionModel model;

	@ManyToOne
	@JoinColumn(name = "prediction_target_id")
	private PredictionTarget predictionTarget;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	public PredictionModelTarget() {
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}