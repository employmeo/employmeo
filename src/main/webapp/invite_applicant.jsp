<%@ page import="javax.persistence.*" %>
<%@ page import="com.employmeo.objects.*" %>
<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | Invite Applicant</title>
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
						<li><a href="/completed_applications.jsp">-- Completed Applications</a></li>
						<li><a href="/incomplete_applications.jsp">-- In-Process Applications</a></li>
						<li class="active"><a href="#">-- Invite Candidate</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
    </div>  
    <div class="col-sm-9">
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-primary">
        	<div class="panel-heading"><h4>Applicant Invitation</h4></div>
        	<div class="panel-body">
        	<form name="inviteapplicant" action="/mp" method="post" class="form">
			<div class="col-sm-12 col-md-4">
			<h4>Contact Info</h4><hr>
    <label for="fname">First Name:</label> <input required class="form-control" id="fname" name="fname" placeholder="first" required>
    <label for="lname">Last Name:</label> <input class="form-control" id="lname" name="lname" placeholder="last">
	<label for="email">Email:</label>
	<div class="input-group margin-bottom-sm">
	  <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
	  <input required class="form-control" type="email" name="email" required placeholder="email">
	</div>
  </div>
<div class="col-sm-12 col-md-4">
			<h4>Address</h4><hr>
    <label for="street1">Street:</label>
    <input class="form-control" id="street1" name="street1">
    <input class="form-control" id="street2" name="street2">
    <label for="city">City:</label>
    <input class="form-control" id="city" name="city">
    <label for="state">State:</label>
    <input class="form-control" id="state" name="state">
    <label for="zip">Zip:</label>
    <input class="form-control" id="zip" name="zip">
  </div>
			<div class="col-sm-12 col-md-4">
				<h4>Application Details</h4><hr>
    <label for="location">Location:</label>
    <select class="form-control" id="location_id"></select>
        <label for="position">Position:</label>
    <select class="form-control" id="position_id">
    </select>
        <label for="position">Survey:</label>
    <select class="form-control" id="survey_id" name="survey_id">
    </select>
  </div>

  <div class="col-sm-12 col-lg-12">
	<hr>
    <input type="hidden" id="formname" name="formname" value="inviteapplicant">
    <input type="hidden" name="noRedirect" value="true">
    <button type="button" class="btn btn-block btn-default" onclick='postToAction(this.form)'>Send Invitation</button>   
  </div>
        	</form>
        	</div>
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