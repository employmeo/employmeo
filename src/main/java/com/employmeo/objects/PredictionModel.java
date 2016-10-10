package com.employmeo.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONObject;

import com.employmeo.util.PredictionModelAlgorithm;

import lombok.Data;

/**
 * The persistent class for the predictive_model database table.
 * 
 */
@Entity
@Table(name = "prediction_models")
@NamedQuery(name = "PredictionModel.findAll", query = "SELECT p FROM PredictionModel p")
@Data
public class PredictionModel extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prediction_model_id")
	private Integer modelId;

	@Column(name = "model_name")
	private String name;

	@Column(name = "model_type")
	private String modelType;

	@Column(name = "version")
	private Integer version;

	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "prediction_target_id")
	private PredictionTarget predictionTarget;	
	
	@OneToMany(mappedBy = "predictionModel", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<PositionPredictionConfiguration> positionPredictionConfigs;
	
	@Column(name = "active")
	private Boolean active;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	public PredictionModel() {
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	public PredictionModelAlgorithm getAlgorithm() {
		return PredictionModelAlgorithm.builder()
				.modelName(this.getName())
				.modelType(this.getModelType())
				.predictionTarget(predictionTarget.getName())
				.modelVersion(this.getVersion())
				.build();
	}
}