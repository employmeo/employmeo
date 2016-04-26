package com.employmeo.objects;

import java.io.Serializable;

import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;
import com.employmeo.util.SecurityUtil;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long userId;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	@Column(name="USER_AVATAR_URL")
	private String userAvatarUrl;

	@Column(name="USER_EMAIL")
	private String userEmail;

	@Column(name="USER_FNAME")
	private String userFname;

	@Column(name="USER_LNAME")
	private String userLname;

	@Column(name="USER_LOCALE")
	private String userLocale;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_PASSWORD")
	private String userPassword;

	@Column(name="USER_STATUS")
	private int userStatus;

	@Column(name="USER_TYPE")
	private int userType;

	//bi-directional many-to-one association to Survey
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private List<Survey> surveys;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ACCOUNT_ID")
	private Account account;

	public User() {
	}
	
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getUserAvatarUrl() {
		return this.userAvatarUrl;
	}

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserFname() {
		return this.userFname;
	}

	public void setUserFname(String userFname) {
		this.userFname = userFname;
	}

	public String getUserLname() {
		return this.userLname;
	}

	public void setUserLname(String userLname) {
		this.userLname = userLname;
	}

	public String getUserLocale() {
		return this.userLocale;
	}

	public void setUserLocale(String userLocale) {
		this.userLocale = userLocale;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public List<Survey> getSurveys() {
		return this.surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public Survey addSurvey(Survey survey) {
		getSurveys().add(survey);
		survey.setUser(this);

		return survey;
	}

	public Survey removeSurvey(Survey survey) {
		getSurveys().remove(survey);
		survey.setUser(null);

		return survey;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public static User login(String email, String password) {
		  String hashword = SecurityUtil.hashPassword(password);
		  return loginHashword(email, hashword);       
	}
	
	public static User loginHashword(String email, String hashword) {

		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.userEmail = :email AND u.userPassword = :password", User.class);
        q.setParameter("email", email);
        q.setParameter("password", hashword);
        User user = null;
        try {
      	  user = q.getSingleResult();
        } catch (NoResultException nre) {
          user = new User();
          user.setUserEmail(email);
        }
        
        return user;
	}
	
	public static User lookupByEmail(String email) {

		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.userEmail = :email", User.class);
        q.setParameter("email", email);
        User user = null;
        try {
      	  user = q.getSingleResult();
        } catch (NoResultException nre) {
          user = new User();
          user.setUserEmail(email);
        }
        
        return user;
	}

	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("user_id", this.userId);
		json.put("user_email", this.userEmail);
		json.put("user_fname", this.userFname);
		json.put("user_lname", this.userLname);
		json.put("user_status", this.userStatus);
		json.put("user_type", this.userType);
		json.put("user_locale", this.userLocale);
		
		if (this.account != null) json.put("user_account", this.account.getJSON());
		json.put("user_avatar_url", this.userAvatarUrl);
		
		return json;
	}

}