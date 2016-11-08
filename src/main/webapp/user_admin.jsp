<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
<div class="row">
	<div class="col-xs-12 col-sm-3 col-md-3">
		<h3>Manage My Settings</h3>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 pull-right"><h3><i class="fa fa-gear pull-right"></i></h3></div>
</div>
<div class="row content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="x_panel">
			<div class="x_title">
				<h3 id="assessmentname">My Profile</h3>
			</div>
			<div class="x_content" style="max-width:600px;">
			<form id='updateuser' action="javascript:udpateUser();">
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
 				  <div class="row-content text-center">
 				  <div><hr></div>
				    <button type="submit" class="btn btn-default">Save</button>
 				  </div>
 				  
 			</form>
	</div>
	</div>
	</div>
</div>
<div class="row content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="x_panel">
			<div class="x_title">
				<h3 id="assessmentname">Change Password</h3>
			</div>
			<div class="x_content">
	</div>
	</div>
	</div>
</div>

<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
<script type="text/javascript">
$(document).ready(function() {

});
</script>
</html>