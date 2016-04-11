<%@ page import="javax.persistence.*" %>
<%@ page import="com.employmeo.objects.*" %>
<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Testing Page")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav hidden-xs">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="/index.jsp">Dashboard</a></li>
					<li><a href="/candidates.jsp">Candidates</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
    </div>  
    <div class="col-sm-9">
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-primary">
        	<div class="panel-heading"><h4>Test Bed 1</h4></div>
        	<div class="panel-body">
        	<form name="inviteapplicant" action="/mp" method="post" class="form">
			   <div class="form-group form-inline">
    <label for="fname">Name:</label>
    <input class="form-control" id="fname" name="fname" placeholder="first" required>
    <input class="form-control" id="lname" name="lname" placeholder="last">
	<label for="fname">Email:</label>
	<i class="fa fa-envelope-o fa-fw"></i>
	<input class="form-control" type="email" name="email" required placeholder="email">
  </div>
  <div class="form-group form-inline">
									<div class="form-group">
										<select	class="form-control" id="location_id" name="location_id">
											<option>all locations</option>
										</select>
									</div>
									<div class="form-group">
										<select class="form-control" id="position_id" name="position_id">
											<option>all positions</option>
										</select>
									</div>
									<div class="form-group">
										 <select class="form-control" id="survey_id" name="survey_id">
											<option>all surveys</option>
										 </select>
									</div>
  </div>
  <div class="form-group form-inline">

  </div>
  <div class="form-group form-inline">
    <label for="position">Action:</label>
    <select class="form-control" id="formname" name="formname">    
        <option value="testaction">TestAction</option>
        <option value="inviteapplicant">Invite</option>
		<option value="login" >Login</option>
		<option value="logout">Logout</option>
		<option value="getsurveylist">Get Survey List</option>
		<option value="getlocationlist">Get Location List</option>
		<option value="getpositionlist">Get Position List</option>
		<option value="getfullsurvey">Get Full Survey</option>
		<option value="getrespondants">Get Applicants</option>
		<option value="forgotpassword">Forgot Password</option>
		<option value="resetpassword">Reset Password</option>
		<option value="changepassword">Change Password</option>
		<option value="contactus">Contact Us</option>
		<option value="settimezone">Set Timezone</option>
    </select>
    	<input type="hidden" name="noRedirect" value="true">
	<input type="hidden" name="survey_id" value="2">
	<input type="hidden" name="account_id" value="0">
    <button type="button" class="btn btn-default" onclick='postToAction(this.form)'>Submit</button>   
  </div>
        	</form>
        	</div>
        </div>
  	  </div>
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-primary">
        	<div class="panel-heading"><h4>Test Bed 2</h4></div>
        	<div class="panel-body" id="form_response">
        	  <%=fRes%>
        	</div>
        </div>
  	  </div>
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-primary">
        	<div class="panel-heading"><h4>Test Bed 3</h4></div>
        	<div class="panel-body"><%
        	EntityManagerFactory emf = Persistence.createEntityManagerFactory("employmeo");
			EntityManager em = emf.createEntityManager();
			TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a", Account.class);
			List<Account> accountList = q.getResultList();
	          for (Account jpaAccount : accountList) {
	               out.println(jpaAccount.getAccountId());
	          }
        	%></div>
        </div>
  	  </div>
    </div>
  </div>
</div>

<script>
  updatePositionsSelect();
  updateLocationsSelect();
  updateSurveysSelect();
</script>

</html>