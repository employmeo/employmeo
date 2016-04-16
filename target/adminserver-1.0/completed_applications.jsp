<%@ include file="/WEB-INF/includes/inc_all.jsp"%>
<html>
<head>
<title>employmeo | Current Applications</title>
<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav hidden-xs">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="/index.jsp">Dashboard</a></li>
					<li><a href="/candidates.jsp">Candidates</a></li>
						<li class="active"><a href="#">-- Completed Applications</a></li>
						<li><a href="/incomplete_applications.jsp">-- In-Process Applications</a></li>
						<li><a href="/invite_applicant.jsp">-- Invite Candidate</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
			</div>
			<div class="col-sm-9">
								<div class="panel panel-primary">
									<div class="panel-heading">Applicants
									</div>
									<div class="panel-body" id="respondant_list">
																<div class="col-sm-12 hidden-xs small">
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
									<button id="applicantrefresh" class="btn btn-default" type="button" onClick="updateRespondantsTable();"><i class="fa fa-refresh" id='spinner'></i></button>
									<input type=hidden name="formname" value="getrespondants"><input type="hidden" name="noRedirect" value=true>
								</form>
							</div>
							<div class="col-xs-12 hidden-xs">
								<hr>
							</div>
										<table id="respondants" class="table table-hover table-condensed"></table>
									
									</div>
								</div>
							</div>
				</div>
			</div>
			<script type="text/javascript">
			var now = new Date();
			var from = new Date();
			from.setTime(now.getTime()-1000*60*60*24*90);
			var fromDay = ("0" + from.getDate()).slice(-2);
			var fromMonth = ("0" + (from.getMonth() + 1)).slice(-2);
			var fromDate = from.getFullYear()+"-"+(fromMonth)+"-"+(fromDay) ;
			$("#from_date").val(fromDate);
			
			var day = ("0" + now.getDate()).slice(-2);
			var month = ("0" + (now.getMonth() + 1)).slice(-2);
			var toDate = now.getFullYear()+"-"+(month)+"-"+(day) ;
			$("#to_date").val(toDate);
			updatePositionsSelect();
			updateLocationsSelect();
			updateSurveysSelect();
			
			initRespondantsTable();
			</script>
</html>