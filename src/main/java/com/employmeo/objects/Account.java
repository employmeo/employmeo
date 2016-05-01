package com.employmeo.objects;

import java.io.Serializable;
import javax.persistence.*;

import org.json.JSONObject;

import com.employmeo.util.DBUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	@Column(name="account_id")
	private Long accountId;

	@Column(name="account_creator")
	private Long accountCreator;

	@Column(name="account_currency")
	private String accountCurrency;

	@Column(name="account_name")
	private String accountName;

	@Column(name="account_status")
	private int accountStatus;

	@Column(name="account_timezone")
	private String accountTimezone;

	@Column(name="account_type")
	private int accountType;

	@Column(name="modified_date")
	private Timestamp modifiedDate;

	@Column(name="account_ats_id")
	private String accountAtsId;

	@Column(name="account_default_location_id")
	private long accountDefaultLocationId;
	
	@Column(name="account_default_position_id")
	private long accountDefaultPositionId;
	
	@Column(name="account_default_asid")
	private long accountDefaultAsId;
	
	//bi-directional many-to-one association to Survey
	@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
	private List<AccountSurvey> accountSurveys;

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

	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountCreator() {
		return this.accountCreator;
	}

	public void setAccountCreator(Long accountCreator) {
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
		List<Survey> surveyset = new ArrayList<Survey>();
		for (int i=0;i<this.accountSurveys.size();i++) surveyset.add(accountSurveys.get(i).getSurvey());
		return surveyset;
	}
	
	public Survey getDefaultSurvey() {
		return AccountSurvey.getSurveyByASID(this.accountDefaultAsId);
	}

	/* At some point we'll need to create logic for setting one of the
	 * existing account surveys as default, and logic to put a new 
	 * survey into the account
	 * 
	public void setDefaultSurveyId(long surveyId) {
		AccountSurvey as = new AccountSurvey();		
		AccountSurvey.getSurveyByASID(this.accountDefaultAsId);
	}
	*/

	public void setDefaultLocation(long locationId) {
		this.accountDefaultLocationId = locationId;
	}
	
	public Location getDefaultLocation() {
		return Location.getLocationById(this.accountDefaultLocationId);
	}
	
	public void setDefaultPosition(long positionId) {
		this.accountDefaultPositionId = positionId;
	}

	public Position getDefaultPosition() {
		return Position.getPositionById(this.accountDefaultPositionId);
	}

	public String getAccountAtsId() {
		return this.accountAtsId;
	}

	public void setAccountAtsId(String atsId) {
		this.accountAtsId = atsId;
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
		
		return getAccountById(new Long(lookupId));
		
	}
	
	public static Account getAccountById(Long lookupId) {

		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.accountId = :accountId", Account.class);
        q.setParameter("accountId", lookupId);
        Account account = null;
        try {
      	  account = q.getSingleResult();
        } catch (NoResultException nre) {}
        
        return account;
	}
	
	public List<Respondant> getRespondants() {
		return getRespondants(Respondant.STATUS_SCORED, Respondant.STATUS_SCORED,100);
	}

	public List<Respondant> getRespondants(int maxResults) {
		return getRespondants(-1, 99, maxResults);
	}
	public List<Respondant> getRespondants(int status, int maxResults) {
		return getRespondants(status, status, maxResults);
	}

	public List<Respondant> getRespondants(int statusMin, int statusMax, int maxResults) {
		
		EntityManager em = DBUtil.getEntityManager();
		TypedQuery<Respondant> q = em.createQuery("SELECT r FROM Respondant r WHERE r.respondantAccountId = :account_id and r.respondantStatus >= :statusMin and r.respondantStatus <= :statusMax ORDER BY r.respondantCreatedDate DESC", Respondant.class);
        q.setParameter("account_id", this.accountId);
        q.setParameter("statusMin", statusMin);
        q.setParameter("statusMax", statusMax);
        q.setMaxResults(maxResults);
        return q.getResultList();
	}
	
}