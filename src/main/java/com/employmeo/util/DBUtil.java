package com.employmeo.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


@SuppressWarnings({ "rawtypes", "unchecked" })
@PersistenceContext(unitName="employmeo")
public class DBUtil {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {

		Map properties = new HashMap();

		// Get database connection details from ENV VARIABLES
		String dbuser = System.getenv("DB_USERNAME"); 
		String dbpass = System.getenv("DB_PASSWORD"); 
		String dburl = System.getenv("DB_URL")+ "?currentSchema=employmeo&sslmode=require";
		properties.put("javax.persistence.jdbc.user", dbuser);
		properties.put("javax.persistence.jdbc.password", dbpass );
		properties.put("javax.persistence.jdbc.url", dburl);

		emf = Persistence.createEntityManagerFactory("employmeo", properties);
	}

	public static EntityManager getEntityManager() {
		if (em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}

	
}
