package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	private Long positionId;

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


	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="position",fetch=FetchType.EAGER)
	private List<PredictiveModel> pmFactors;
	
	public Position() {
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
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

	public static Position getPositionById(String lookupId) {
		
		return getPositionById(new Long(lookupId));
		
	}
	
	public static Position getPositionById(Long lookupId) {

		EntityManager em = DBUtil.getEntityManager();		  
		TypedQuery<Position> q = em.createQuery("SELECT p FROM Position p WHERE p.positionId = :positionId", Position.class);
        q.setParameter("positionId", lookupId);
        Position position = null;
        try {
      	  position = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return position;
	}

	public List<PredictiveModel> getPmFactors() {
		return this.pmFactors;
	}
	
	public List<Corefactor> getCorefactors() {
		List<Corefactor> corefactors = new ArrayList<Corefactor>();
		for (int i=0;i<this.pmFactors.size();i++) {
			corefactors.add(pmFactors.get(i).getCorefactor());
		}
		return corefactors;
	}
	
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("position_id", this.positionId);
		json.put("position_name", this.positionName);
		json.put("position_target_hireratio", this.positionTargetHireratio);
		json.put("position_target_tenure", this.positionTargetTenure);
		
		if (this.account != null) json.put("position_account", this.account.getJSON());
		List<Corefactor> corefactors = getCorefactors();
		if (!corefactors.isEmpty()) {
			for (int i=0;i<corefactors.size();i++) {
				json.accumulate("position_corefactors", corefactors.get(i).getCorefactorName());
			}
			json.accumulate("position_profiles", PositionProfile.getProfileA(this));
			json.accumulate("position_profiles", PositionProfile.getProfileB(this));
			json.accumulate("position_profiles", PositionProfile.getProfileC(this));
			json.accumulate("position_profiles", PositionProfile.getProfileD(this));			
		}
		return json;
	}

}