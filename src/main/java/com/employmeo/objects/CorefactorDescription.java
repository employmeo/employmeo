package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;


/**
 * The persistent class for the corefactor_descriptions database table.
 * 
 */
@Entity
@Table(name="corefactor_descriptions")
@NamedQuery(name="CorefactorDescription.findAll", query="SELECT c FROM CorefactorDescription c")
public class CorefactorDescription extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cfdesc_id")
	private Long cfdescId;

	@Column(name="cf_description")
	private String cfDescription;

	@Column(name="cf_high_end")
	private double cfHighEnd;

	// bi-directional many-to-one association to Corefactor
	@ManyToOne
	@JoinColumn(name = "cf_id",insertable=false,updatable=false)
	private Corefactor corefactor;
	
	@Column(name="cf_id")
	private Long cfId;

	@Column(name="cf_low_end")
	private double cfLowEnd;

	public CorefactorDescription() {
	}

	public Long getCfdescId() {
		return this.cfdescId;
	}

	public void setCfdescId(Long cfdescId) {
		this.cfdescId = cfdescId;
	}

	public String getCfDescription() {
		return this.cfDescription;
	}

	public void setCfDescription(String cfDescription) {
		this.cfDescription = cfDescription;
	}

	public double getCfHighEnd() {
		return this.cfHighEnd;
	}

	public void setCfHighEnd(double cfHighEnd) {
		this.cfHighEnd = cfHighEnd;
	}

	public Long getCfId() {
		return this.cfId;
	}

	public void setCfId(Long cfId) {
		this.cfId = cfId;
	}

	public double getCfLowEnd() {
		return this.cfLowEnd;
	}

	public void setCfLowEnd(double cfLowEnd) {
		this.cfLowEnd = cfLowEnd;
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return null;
	}

}