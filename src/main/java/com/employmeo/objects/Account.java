package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the accounts database table.
 * 
 */
@Entity
@Table(name="accounts")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account extends PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ACCOUNT_ID")
	private BigInteger accountId;

	@Column(name="ACCOUNT_CREATOR")
	private BigInteger accountCreator;

	@Column(name="ACCOUNT_CURRENCY")
	private String accountCurrency;

	@Column(name="ACCOUNT_NAME")
	private String accountName;

	@Column(name="ACCOUNT_STATUS")
	private int accountStatus;

	@Column(name="ACCOUNT_TIMEZONE")
	private String accountTimezone;

	@Column(name="ACCOUNT_TYPE")
	private int accountType;

	@Column(name="MODIFIED_DATE")
	private Timestamp modifiedDate;

	//bi-directional many-to-one association to Survey
	@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
	private List<Survey> surveys;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="account")
	private List<User> users;

	//bi-directional many-to-one association to Position
	@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
	private List<Position> positions;
	
	//bi-directional many-to-one association to Position
	@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
	private List<Location> locations;
	
	//bi-directional many-to-one association to Position	
	@OneToMany(mappedBy="account")
	//@OrderBy("respondant.respondantCreatedDate DESC")
	private List<Respondant> respondants;

	public Account() {
	}

	public BigInteger getAccountId() {
		return this.accountId;
	}

	public void setAccountId(BigInteger accountId) {
		this.accountId = accountId;
	}

	public BigInteger getAccountCreator() {
		return this.accountCreator;
	}

	public void setAccountCreator(BigInteger accountCreator) {
		this.accountCreator = accountCreator;
	}

	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountTimezone() {
		return this.accountTimezone;
	}

	public void setAccountTimezone(String accountTimezone) {
		this.accountTimezone = accountTimezone;
	}

	public int getAccountType() {
		return this.accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<Survey> getSurveys() {
		return this.surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public Survey addSurvey(Survey survey) {
		getSurveys().add(survey);
		survey.setAccount(this);

		return survey;
	}

	public Survey removeSurvey(Survey survey) {
		getSurveys().remove(survey);
		survey.setAccount(null);

		return survey;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setAccount(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setAccount(null);

		return user;
	}	
	
	public List<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public Position addPosition(Position position) {
		getPositions().add(position);
		position.setAccount(this);

		return position;
	}

	public Position removePosition(Position position) {
		getPositions().remove(position);
		position.setAccount(null);

		return position;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Location addLocation(Location location) {
		getLocations().add(location);
		location.setAccount(this);

		return location;
	}
	
	public Location removeLocation(Location location) {
		getLocations().remove(location);
		location.setAccount(null);

		return location;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("account_id", this.accountId);
		json.put("account_name", this.accountName);
		json.put("account_currency", this.accountCurrency);
		json.put("account_timezone", this.accountTimezone);
		json.put("account_status", this.accountStatus);
		json.put("account_type", this.accountType);
				
		return json;
	}
	
	public static Account getAccountById(String lookupId) {
		
		return getAccountById(new BigInteger(lookupId));
		
	}
	
	public static Account getAccountById(BigInteger lookupId) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();
		TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountId = :accountId", Account.class);
        q.setParameter("accountId", lookupId);
        Account account = null;
        try {
      	  account = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return account;
	}
	
	public List<Respondant> getRespondants() {
		return getRespondants(100);
	}

	public List<Respondant> getRespondants(int maxResults) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
		EntityManager em = emf.createEntityManager();		  
		TypedQuery<Respondant> q = em.createQuery("SELECT r FROM Respondant r WHERE r.respondantAccountId = :account_id ORDER BY r.respondantCreatedDate DESC", Respondant.class);
        q.setParameter("account_id", this.accountId);
        q.setMaxResults(maxResults);
        return q.getResultList();
	}

}