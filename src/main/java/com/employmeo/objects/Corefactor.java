package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;


/**
 * The persistent class for the corefactors database table.
 * 
 */
@Entity
@Table(name="corefactors")
@NamedQuery(name="Corefactor.findAll", query="SELECT c FROM Corefactor c")
public class Corefactor extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="corefactor_id")
	private Integer corefactorId;

	@Column(name="corefactor_description")
	private String corefactorDescription;

	@Column(name="corefactor_name")
	private String corefactorName;

	public Corefactor() {
	}

	public Integer getCorefactorId() {
		return this.corefactorId;
	}

	public void setCorefactorId(Integer corefactorId) {
		this.corefactorId = corefactorId;
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
		return json;
	}

}