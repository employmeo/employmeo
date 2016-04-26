package com.employmeo.objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

public abstract class PersistantObject {

	@PersistenceContext(unitName="employmeo")
	protected EntityManager em;

	public void persistMe() {
		em = DBUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.persist(this);
		txn.commit();
	}
	
	public void mergeMe() {
		em = DBUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.merge(this);
		txn.commit();
	}	
	public String getJSONString() {

		return getJSON().toString();
	}

	public abstract JSONObject getJSON();


}
