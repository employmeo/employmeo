package com.employmeo.objects;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

/**
 * The persistent class for the corefactors database table.
 * 
 */
@Entity
@Table(name = "corefactors")
@NamedQuery(name = "Corefactor.findAll", query = "SELECT c FROM Corefactor c")
public class Corefactor extends PersistantObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private static List<Corefactor> corefactors = null;

	@Id
	@Column(name = "corefactor_id")
	private Integer corefactorId;

	@Column(name = "cf_high")
	private double cfHigh;

	@Column(name = "cf_high_description")
	private String cfHighDescription;

	@Column(name = "cf_low")
	private double cfLow;

	@Column(name = "cf_low_description")
	private String cfLowDescription;

	@Column(name = "cf_mean_score")
	private double cfMeanScore;

	@Column(name = "cf_measurements")
	private Long cfMeasurements;

	@Column(name = "cf_score_deviation")
	private double cfScoreDeviation;

	@Column(name = "cf_source")
	private String cfSource;

	@Column(name = "corefactor_description")
	private String corefactorDescription;

	@Column(name = "corefactor_name")
	private String corefactorName;

	@Column(name = "corefactor_foreign_id")
	private String corefactorForeignId;

	public Corefactor() {
	}

	public Integer getCorefactorId() {
		return this.corefactorId;
	}

	public void setCorefactorId(Integer corefactorId) {
		this.corefactorId = corefactorId;
	}

	public double getCfHigh() {
		return this.cfHigh;
	}

	public void setCfHigh(double cfHigh) {
		this.cfHigh = cfHigh;
	}

	public String getCfHighDescription() {
		return this.cfHighDescription;
	}

	public void setCfHighDescription(String cfHighDescription) {
		this.cfHighDescription = cfHighDescription;
	}

	public double getCfLow() {
		return this.cfLow;
	}

	public void setCfLow(double cfLow) {
		this.cfLow = cfLow;
	}

	public String getCfLowDescription() {
		return this.cfLowDescription;
	}

	public void setCfLowDescription(String cfLowDescription) {
		this.cfLowDescription = cfLowDescription;
	}

	public double getCfMeanScore() {
		return this.cfMeanScore;
	}

	public void setCfMeanScore(double cfMeanScore) {
		this.cfMeanScore = cfMeanScore;
	}

	public Long getCfMeasurements() {
		return this.cfMeasurements;
	}

	public void setCfMeasurements(Long cfMeasurements) {
		this.cfMeasurements = cfMeasurements;
	}

	public double getCfScoreDeviation() {
		return this.cfScoreDeviation;
	}

	public void setCfScoreDeviation(double cfScoreDeviation) {
		this.cfScoreDeviation = cfScoreDeviation;
	}

	public String getCfSource() {
		return this.cfSource;
	}

	public void setCfSource(String cfSource) {
		this.cfSource = cfSource;
	}

	public String getCorefactorDescription() {
		return this.corefactorDescription;
	}

	public void setCorefactorDescription(String corefactorDescription) {
		this.corefactorDescription = corefactorDescription;
	}

	public String getCorefactorName() {
		return this.corefactorName;
	}

	public void setCorefactorName(String corefactorName) {
		this.corefactorName = corefactorName;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("corefactor_name", this.corefactorName);
		json.put("corefactor_id", this.corefactorId);
		json.put("corefactor_description", this.corefactorDescription);
		json.put("corefactor_high", this.cfHigh);
		json.put("corefactor_low", this.cfLow);
		json.put("corefactor_mean_score", this.cfMeanScore);
		json.put("corefactor_score_deviation", this.cfScoreDeviation);
		json.put("corefactor_measurements", this.cfMeasurements);
		json.put("corefactor_source", this.cfSource);
		return json;
	}

	public static List<Corefactor> getAllCorefactors() {
		if (corefactors == null) {
			EntityManager em = DBUtil.getEntityManager();
			TypedQuery<Corefactor> q = em.createQuery("SELECT c FROM Corefactor c", Corefactor.class);
			try {
				corefactors = q.getResultList();
			} catch (NoResultException nre) {
			}
		}
		return corefactors;
	}

	public static Corefactor getCorefactorById(int lookupId) {

		EntityManager em = DBUtil.getEntityManager();
		return em.find(Corefactor.class, lookupId);
	}

	public static Corefactor getCorefactorByForeignId(String id) {
		Corefactor corefactor = null;
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Corefactor> q = em.createQuery("SELECT c FROM Corefactor c WHERE c.corefactorForeignId = :id", Corefactor.class);
		q.setParameter("id", id);
		corefactor =  q.getSingleResult();
		return corefactor;
	}

}