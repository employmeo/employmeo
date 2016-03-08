package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the positions database table.
 * 
 */
@Entity
@Table(name="positions")
@NamedQuery(name="Position.findAll", query="SELECT p FROM Position p")
public class Position extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="position_id")
	private BigInteger positionId;

	@Column(name="position_name")
	private String positionName;

	@Column(name="position_target_hireratio")
	private BigDecimal positionTargetHireratio;

	@Column(name="position_target_tenure")
	private BigDecimal positionTargetTenure;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="position_account")
	private Account account;

	//bi-directional many-to-one association to Survey
	@OneToMany(mappedBy="position")
	private List<Survey> surveys;

	public Position() {
	}

	public BigInteger getPositionId() {
		return this.positionId;
	}

	public void setPositionId(BigInteger positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public BigDecimal getPositionTargetHireratio() {
		return this.positionTargetHireratio;
	}

	public void setPositionTargetHireratio(BigDecimal positionTargetHireratio) {
		this.positionTargetHireratio = positionTargetHireratio;
	}

	public BigDecimal getPositionTargetTenure() {
		return this.positionTargetTenure;
	}

	public void setPositionTargetTenure(BigDecimal positionTargetTenure) {
		this.positionTargetTenure = positionTargetTenure;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Survey> getSurveys() {
		return this.surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public Survey addSurvey(Survey survey) {
		getSurveys().add(survey);
		survey.setPosition(this);

		return survey;
	}

	public Survey removeSurvey(Survey survey) {
		getSurveys().remove(survey);
		survey.setPosition(null);

		return survey;
	}

	public static Position getPositionById(String lookupId) {
		
		return getPositionById(new BigInteger(lookupId));
		
	}
	
	public static Position getPositionById(BigInteger lookupId) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Position> q = em.createQuery("SELECT p FROM Position p WHERE p.positionId = :positionId", Position.class);
        q.setParameter("positionId", lookupId);
        Position position = null;
        try {
      	  position = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return position;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("position_id", this.positionId);
		json.put("position_name", this.positionName);
		json.put("position_target_hireratio", this.positionTargetHireratio);
		json.put("position_target_tenure", this.positionTargetTenure);
		
		if (this.account != null) json.put("position_account", this.account.getJSON());
		
		return json;
	}

}