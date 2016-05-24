package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

/**
 * The persistent class for the account_surveys database table.
 * 
 */
@Entity
@Table(name = "account_surveys")
@NamedQuery(name = "AccountSurvey.findAll", query = "SELECT a FROM AccountSurvey a")
public class AccountSurvey extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "as_id")
	private Integer asId;

	@ManyToOne
	@JoinColumn(name = "as_account_id", insertable = false, updatable = false)
	private Account account;

	@Column(name = "as_account_id")
	private Long asAccountId;

	@Column(name = "as_display_name")
	private String asDisplayName;

	@Column(name = "as_price")
	private Double asPrice;

	@Column(name = "as_preamble_text")
	private String asPreambleText;

	@Column(name = "as_thankyou_text")
	private String asThankyouText;

	@Column(name = "as_redirect_page")
	private String asRedirectPage;

	@Column(name = "as_status")
	private Integer asStatus;

	@ManyToOne
	@JoinColumn(name = "as_survey_id", insertable = false, updatable = false)
	private Survey survey;

	@Column(name = "as_survey_id")
	private Long asSurveyId;

	public AccountSurvey() {
	}

	public Integer getAsId() {
		return this.asId;
	}

	public void setAsId(Integer asId) {
		this.asId = asId;
	}

	public Long getAsAccountId() {
		return this.asAccountId;
	}

	public void setAsAccountId(Long asAccountId) {
		this.asAccountId = asAccountId;
	}

	public String getAsPreambleText() {
		return this.asPreambleText;
	}

	public void setAsPreambleText(String asPreambleText) {
		this.asPreambleText = asPreambleText;
	}

	public String getAsThankyouText() {
		return this.asThankyouText;
	}

	public void setAsThankyouText(String asThankyouText) {
		this.asThankyouText = asThankyouText;
	}

	public String getAsRedirectPage() {
		return this.asRedirectPage;
	}

	public void setAsRedirectPage(String asRedirectPage) {
		this.asRedirectPage = asRedirectPage;
	}

	public Integer getAsStatus() {
		return this.asStatus;
	}

	public void setAsStatus(Integer asStatus) {
		this.asStatus = asStatus;
	}

	public Long getAsSurveyId() {
		return this.asSurveyId;
	}

	public Survey getSurvey() {
		if (this.survey == null)
			this.survey = Survey.getSurveyById(getAsSurveyId());
		return this.survey;
	}

	public void setAsSurveyId(Long asSurveyId) {
		this.asSurveyId = asSurveyId;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject aSurvey = this.survey.getJSON();
		aSurvey.put("survey_preamble_text", this.asPreambleText);
		aSurvey.put("survey_thankyou_text", this.asThankyouText);
		aSurvey.put("survey_redirect_page", this.asRedirectPage);
		aSurvey.put("survey_asid", this.asId);
		aSurvey.put("survey_name", this.asDisplayName);
		aSurvey.put("survey_price", this.asPrice);
		
		return aSurvey;
	}

	public static Survey getSurveyByASID(long accountDefaultAsId) {
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<AccountSurvey> q = em.createQuery("SELECT a FROM AccountSurvey a WHERE a.asId = :asId",
				AccountSurvey.class);
		q.setParameter("asId", accountDefaultAsId);
		Survey aSurvey = null;
		try {
			aSurvey = q.getSingleResult().getSurvey();
		} catch (NoResultException nre) {
		}

		return aSurvey;
	}

}