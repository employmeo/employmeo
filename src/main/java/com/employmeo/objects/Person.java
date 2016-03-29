package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import java.util.List;


/**
 * The persistent class for the persons database table.
 * 
 */
@Entity
@Table(name="persons")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="person_id")
	private Integer personId;

	@Column(name="person_city")
	private String personCity;

	@Column(name="person_email")
	private String personEmail;

	@Column(name="person_fname")
	private String personFname;

	@Column(name="person_lname")
	private String personLname;

	@Column(name="person_ssn")
	private String personSsn;

	@Column(name="person_state")
	private String personState;

	@Column(name="person_street1")
	private String personStreet1;

	@Column(name="person_street2")
	private String personStreet2;

	@Column(name="person_zip")
	private String personZip;
	
	@Column(name="person_lat")
	private double personLat;
	
	@Column(name="person_long")
	private double personLong;

	//bi-directional many-to-one association to Respondant
	@OneToMany(mappedBy="person")
	private List<Respondant> respondants;

	public Person() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonCity() {
		return this.personCity;
	}

	public void setPersonCity(String personCity) {
		this.personCity = personCity;
	}

	public String getPersonEmail() {
		return this.personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getPersonFname() {
		return this.personFname;
	}

	public void setPersonFname(String personFname) {
		this.personFname = personFname;
	}

	public String getPersonLname() {
		return this.personLname;
	}

	public void setPersonLname(String personLname) {
		this.personLname = personLname;
	}

	public String getPersonSsn() {
		return this.personSsn;
	}

	public void setPersonSsn(String personSsn) {
		this.personSsn = personSsn;
	}

	public String getPersonState() {
		return this.personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getPersonStreet1() {
		return this.personStreet1;
	}

	public void setPersonStreet1(String personStreet1) {
		this.personStreet1 = personStreet1;
	}

	public String getPersonStreet2() {
		return this.personStreet2;
	}

	public void setPersonStreet2(String personStreet2) {
		this.personStreet2 = personStreet2;
	}

	public String getPersonZip() {
		return this.personZip;
	}

	public void setPersonZip(String personZip) {
		this.personZip = personZip;
	}

	public double getPersonLat() {
		return this.personLat;
	}

	public void setPersonLat(double personLat) {
		this.personLat = personLat;
	}

	public double getPersonLong() {
		return this.personLong;
	}

	public void setPersonLong(double personLong) {
		this.personLong = personLong;
	}

	public List<Respondant> getRespondants() {
		return this.respondants;
	}

	public void setRespondants(List<Respondant> respondants) {
		this.respondants = respondants;
	}

	public Respondant addRespondant(Respondant respondant) {
		getRespondants().add(respondant);
		respondant.setPerson(this);

		return respondant;
	}

	public Respondant removeRespondant(Respondant respondant) {
		getRespondants().remove(respondant);
		respondant.setPerson(null);

		return respondant;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("person_id", this.personId);
		json.put("person_email", this.personEmail);
		json.put("person_fname", this.personFname);
		json.put("person_lname", this.personLname);
		json.put("person_street1", this.personStreet1);
		json.put("person_street2", this.personStreet2);
		json.put("person_city", this.personCity);
		json.put("person_state", this.personState);
		json.put("person_zip", this.personZip);
		
		return json;
	}

}