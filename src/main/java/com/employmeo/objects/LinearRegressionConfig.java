package com.employmeo.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

/**
 * The persistent class for the predictive_model database table.
 * 
 */
@Entity
@Table(name = "linear_regression_config")
@NamedQueries({
	@NamedQuery(name = "LinearRegressionConfig.findByModelIdAndPositionId", 
				query = "SELECT lrc FROM LinearRegressionConfig lrc WHERE lrc.modelId = :modelId AND lrc.positionId = :positionId"),
	@NamedQuery(name = "LinearRegressionConfig.findDefaultByModelId", 
				query = "SELECT lrc FROM LinearRegressionConfig lrc WHERE lrc.modelId = :modelId AND lrc.positionId IS NULL")
})

@Data
public class LinearRegressionConfig extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Long configId;
	
	@Column(name = "model_id")
	private Long modelId;	

	@Column(name = "corefactor_id")
	private Integer corefactorId;

	@Column(name = "position_id")
	private Long positionId;

	@Column(name = "coefficient")
	private Double coefficient;
	
	@Column(name = "significance")
	private Double significance;

	public LinearRegressionConfig() {
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}