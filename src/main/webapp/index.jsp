<%@ include file="/WEB-INF/includes/inc_all.jsp"%>
<html>
<head>
<title>employmeo | "Dashboard"</title>
<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav hidden-xs">
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a href="#section1">Candidates</a></li>
						<li><a href="/completed_applications.jsp">-- Completed Applications</a></li>
						<li><a href="/invite_applicant.jsp">-- Invite Candidate</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
			</div>
			<div class="col-sm-9">
				<div class="col-sm-12 hidden-xs small">
				<div class="panel panel-body">
					<form class="form-inline pull-right" role="form" action="" id="refine_query">
						<div class="form-group">
							<label for="from_date">From:</label> <input type="date"
											class="form-control" id="from_date" name="from_date">
									</div>
									<div class="form-group">
										<label for="to_date">To:</label> <input type="date"
											class="form-control" id="to_date" name="to_date">
									</div>
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
							<button type="button" class="btn btn-default" onClick='updateDash()'><i class="fa fa-refresh"></i></button>
							<input type=hidden name="formname" value="updatedashboard"><input type="hidden" name="noRedirect" value=true>
						</form>
					</div>
				</div>
				<div class="col-sm-12 hidden-xs"><hr></div>
					<div class="col-xs-6 col-md-3">
						<div class="panel panel-default">
							<div class="panel-heading">
								<span class="text-left">Profiles<i class="fa fa-users pull-right"></i></span></div>
							<div class="panel-body text-center"><div>
								<div class="square btn-danger">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-hand-stop-o white"></i></div></div></div></div>
								<div class="rect btn-danger">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-left">Red Flag</div></div></div></div>
								<div class="square btn-warning">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-gear white"></i></div></div></div></div>
								<div class="rect btn-warning">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-left">Churner</div></div></div></div>
								<div class="square btn-info">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-user-plus white"></i></div></div></div></div>
								<div class="rect btn-info">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-left">Long Timer</div></div></div></div>
								<div class="square btn-success">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-rocket"></i></div></div></div></div>
								<div class="rect btn-success">
								  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-left">Rising Star</div></div></div></div>

								<div class="hidden"><h1 id="headerTurnover"></h1><canvas id="dashTurnover" width="100" height="100"></canvas></div>
									</div></div>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Applicants <i class="fa fa-users pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<canvas id="dashApplicants" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Interviewed <i class="fa fa-line-chart pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<canvas id="dashInterviews" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Hired <i class="fa fa-bar-chart pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<canvas id="dashHires" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<span class="text-left">Historical Performance <i
											class="fa fa-bar-chart pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<canvas id="dashHistory"
											style="width: 100%, height: auto;"></canvas>
									</div></div>
								</div>
							</div>
						</div>
						</div>
	</div>

	<script type="text/javascript">
	updateDateChoosers();
	updatePositionsSelect();
	updateLocationsSelect();
	updateSurveysSelect();

	updateDash();
	updateHistory();
	</script>

</body>
</html>