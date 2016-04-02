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
					<li class="active"><a href="#section1">Dashboard</a></li>
					<li><a href="/candidates.jsp">Candidates</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
				<br>
			</div>
			<div class="col-sm-9">
				<div class="row content">
					<div class="col-sm-12">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4>Dashboard</h4>
						</div>

						<div class="panel-body">
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
									<button type="button" class="btn btn-default" onClick='updateDash()'><i
											class="fa fa-refresh"></i></button>
									<input type=hidden name="formname" value="updatedashboard"><input type="hidden" name="noRedirect" value=true>
								</form>
							</div>
							<div class="col-xs-12 hidden-xs">
								<hr>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Applicants <i
											class="fa fa-users pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<h1 id="headerApplicants"></h1>
										<canvas id="dashApplicants" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Interviewed <i
											class="fa fa-line-chart pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<h1 id="headerInterviews"></h1>
										<canvas id="dashInterviews" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="text-left">Hired <i
											class="fa fa-bar-chart pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<h1 id="headerHires"></h1>
										<canvas id="dashHires" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
								<div class="panel panel-success">
									<div class="panel-heading">
										<span class="text-left">Projected Turnover <i
											class="fa fa-refresh pull-right"></i></span>
									</div>
									<div class="panel-body text-center"><div>
										<h1 id="headerTurnover"></h1>
										<canvas id="dashTurnover" width="100" height="100"></canvas>
									</div></div>
								</div>
							</div>
						</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-md-12 col-lg-12">
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

	updateDash();
	updateHistory();
	</script>

</body>
</html>