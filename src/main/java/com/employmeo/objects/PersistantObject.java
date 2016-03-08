package com.employmeo.objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.json.JSONObject;

public abstract class PersistantObject {
	
	public void persistMe() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.persist(this);
		txn.commit();
	}
	
	public void mergeMe() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		em.merge(this);
		txn.commit();
	}	
	public String getJSONString() {
		JSONObject json = getJSON();
		return json.toString();
	}

	public abstract JSONObject getJSON();


}
