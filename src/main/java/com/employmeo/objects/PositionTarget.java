package com.employmeo.objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

/**
 * The persistent class for the predictive_model database table.
 * 
 */
@Entity
@Table(name = "position_target_associations")
@Data
public class PositionTarget extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_target_association_id")
	private Integer positionTargetId;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "prediction_target_id")
	private PredictionTarget predictionTarget;

	@Column(name = "target_threshold")
	private BigDecimal targetThreshold;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	public PositionTarget() {
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}