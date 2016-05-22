package com.employmeo.objects;

import java.io.Serializable;
import java.security.Principal;

import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

/**
 * The persistent class for the partners database table.
 * 
 */
@Entity
@Table(name = "partners")
@NamedQuery(name = "Partner.findAll", query = "SELECT p FROM Partner p")
public class Partner extends PersistantObject implements Serializable, Principal {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "partner_id")
	private Integer partnerId;

	@Column(name = "partner_login")
	private String partnerLogin;

	@Column(name = "partner_name")
	private String partnerName;

	@Column(name = "partner_password")
	private String partnerPassword;

	public Partner() {
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerLogin() {
		return this.partnerLogin;
	}

	public void setPartnerLogin(String partnerLogin) {
		this.partnerLogin = partnerLogin;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerPassword() {
		return this.partnerPassword;
	}

	public void setPartnerPassword(String partnerPassword) {
		this.partnerPassword = partnerPassword;
	}

	public static Partner loginPartner(String login, String password) {
		// TODO Auto-generated method stub
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Partner> q = em.createQuery(
				"SELECT p from Partner p WHERE p.partnerLogin = :login AND p.partnerPassword = :password",
				Partner.class);
		q.setParameter("login", login);
		q.setParameter("password", password);
		Partner partner = null;

		try {
			partner = q.getSingleResult();
		} catch (NoResultException nre) {
			// Return null partner
		}

		return partner;
	}

	@Override
	public JSONObject getJSON() {
		return new JSONObject().put("partner_name", this.getPartnerName());
	}

	@Override
	public String getName() {
		return this.getPartnerName();
	}
}