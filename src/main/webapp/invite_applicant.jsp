
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>


  <div class="row content"> 
    <div class="col-sm-12">
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-primary">
        	<div class="panel-heading"><h4>Applicant Invitation</h4></div>
        	<div class="panel-body hidden" id="invitationsent">
        		<h4>Invitation Sent</h4>
    			<button type="button" class="btn btn-block btn-default" onclick='resetInvitation();'>Invite Another</button> 
        	</div>
        	<div class="panel-body" id="invitationform">
        	<form name="inviteapplicant" action="/mp" method="post" class="form">
			<div class="col-sm-12 col-md-4">
			<h4>Contact Info</h4><hr>
	<label for="fname">First Name:</label> 
	<div class="input-group has-success has-feedback">
	<input required class="form-control" id="fname" name="fname" placeholder="first" required>
	</div>
	<label for="lname">Last Name:</label>
	<div class="input-group has-success has-feedback">
	<input class="form-control" id="lname" name="lname" placeholder="last">
	</div>
	<label for="email">Email:</label>
	<div class="input-group">
	  <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
	  <input required class="form-control" type="email" name="email" required placeholder="email">
	  <span class="form-control-feedback" aria-hidden="true"><i class="fa fa-remove form- fa-fw"></i></span>
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
    <button type="button" class="btn btn-block btn-default" onclick='inviteApplicant(this.form);'>
    	Send Invitation 
    	<i id="spinner" class="fa fa-spinner fa-spin hidden"></i>
    	</button>   
  </div>
        	</form>
        	</div>
        </div>
  	  </div>
    </div>
  </div>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<script>
  updatePositionsSelect();
  updateLocationsSelect();
  updateSurveysSelect();
</script>

</html>