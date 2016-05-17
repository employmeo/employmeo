package com.employmeo.objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

public abstract class PersistantObject {

	public void persistMe() {
		EntityManager em = DBUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.persist(this);
		txn.commit();
	}
	
	public void mergeMe() {
		EntityManager em = DBUtil.getEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.merge(this);
		txn.commit();
	}	

	public void refreshMe() {
		EntityManager em = DBUtil.getEntityManager();
		em.refresh(this);
	}
	
	public String getJSONString() {

		return getJSON().toString();
	}

	public abstract JSONObject getJSON();


}
