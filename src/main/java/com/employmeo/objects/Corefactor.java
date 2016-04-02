package com.employmeo.objects;

import java.io.Serializable;
import java.util.List;

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
    private static List<Corefactor> corefactors = null;

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

	public static List<Corefactor> getAllCorefactors() {

		if (corefactors == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
			EntityManager em = emf.createEntityManager();
			TypedQuery<Corefactor> q = em.createQuery("SELECT c FROM Corefactor c", Corefactor.class);
			try {
				corefactors = q.getResultList();
			} catch (NoResultException nre) {}
		}
		
		return corefactors;
	}

	public static Corefactor getCorefactorById(int lookupId) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Corefactor> q = em.createQuery("SELECT c FROM Corefactor c WHERE c.corefactorId = :corefactorId", Corefactor.class);
        q.setParameter("corefactorId", lookupId);
        Corefactor corefactor = null;
        try {
      	  corefactor = q.getSingleResult();
        } catch (NoResultException nre) {}
        
		return corefactor;
	}

}