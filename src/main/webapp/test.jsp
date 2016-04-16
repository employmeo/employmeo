<%@ include file="/WEB-INF/includes/inc_head.jsp" %>

                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Testing Page</h3>
                        </div>

                        <div class="title_right">
                            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search for...">
                                    <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Go!</button>
                        </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
     <div class="row">
       <div class="col-sm-12 col-md-12 col-lg-12">
         <div class="x_panel">
		   <div class="x_title">
             <h2>Form Inputs</h2>
             <ul class="nav navbar-right panel_toolbox">
               <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a></li>
               <li><a class="close-link"><i class="fa fa-close"></i></a></li>
             </ul>
             <div class="clearfix"></div>
           </div>
           <div class="x_content">
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
        <div class="x_panel">
        	<div class="x_title"><h4>Form Response</h4></div>
        	<div class="x_content" id="form_response">
        	</div>
        </div>
  	  </div>
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="x_panel">
        	<div class="x_title"><h4>Test Bed 3</h4></div>
        	<div class="x_content"></div>
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