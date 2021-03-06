
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
  <div class="row content"> 
    <div class="col-sm-12">
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="x_panel">
        	<div class="x_title"><h4>Applicant Invitation</h4></div>
        	<div class="x_content hidden" id="invitationsent">
        		<h4>Invitation Sent</h4>
    			<button type="button" class="btn btn-block btn-default" onclick='resetInvitation();'>Invite Another</button> 
        	</div>
        	<div class="x_content" id="invitationform">
        	  <form id='inviteapplicant' name="inviteapplicant" class="form form-horizontal form-label-left input_mask">
			    <div class="col-xs-12 col-sm-12 col-md-6">
				  <h4>Contact Info</h4><hr>
				  <label for="fname">First Name:</label> 
				  <div class="input-group has-feedback">
				    <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
			        <input type="text" class="form-control" id="fname" name="fname" placeholder="First Name" required>
				  </div>
				  <label for="lname">Last Name:</label>
				  <div class="input-group has-feedback">
				    <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
				    <input class="form-control" id="lname" name="lname" placeholder="Last Name">
				  </div>
				  <label for="email">Email:</label>
				  <div class="input-group has-feedback">
				    <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
				    <input required class="form-control" type="email" name="email" required placeholder="email" required>
 				  </div>
				    <label for="street1">Address:</label>
				    <div class="form-group has-feedback">
				    <input required class="form-control" id="address" name="address">
				    </div>
				    <input type='hidden' id='lat' name='lat'>
				    <input type='hidden' id='lng' name='lng'>
				    <input type='hidden' id='formatted_address' name='formatted_address'>
				    <input type='hidden' id='country_short' name='country_short'>
				</div>
			    <div class="col-xs-12 col-sm-12 col-md-6">
					<h4>Application Details</h4><hr>
				    <label for="asid">Assessment:</label>
					<div class="form-group has-feedback">
						<select class="form-control" id="asid" name="asid"></select>
					</div>
				    <label for="location">Location:</label>
					<div class="form-group has-feedback">
				    	<select class="form-control" id="location_id" name="location_id"></select>
					</div>
				    <label for="position">Position:</label>
					<div class="form-group has-feedback">
				    	<select class="form-control" id="position_id" name="position_id"></select>
					</div>
                    <div class="form-group">
                        <input type="checkbox" id="notifyme" name="notifyme" value="true" checked>
                        <label for="notifyme">Notify me upon scoring</label>
                    </div>
				</div>
				 <div class="col-sm-12 col-lg-12">
					<hr>
				    <button type="submit" class="btn btn-block btn-default"><!--  onclick='inviteApplicant(this.form);'-->
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
$(document).ready(function() {
  updatePositionsSelect(false);
  updateLocationsSelect(false);
  updateSurveysSelect(false);
  $('#address').geocomplete({details:'form'});
  $('#inviteapplicant').submit(function(){
	  inviteApplicant();
	  return false;});
});
</script>

</html>