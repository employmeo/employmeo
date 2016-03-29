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
					<li><a href="/positions.jsp">Job Definitions</a></li>
					<li class="active"><a href="#">Current Applications</a></li>
					<li><a href="/analytics.jsp">Analytics</a></li>
					<li><a href="/data_admin.jsp">Data Administration</a></li>
				</ul>
				<br>
			</div>
			<div class="col-sm-9">
				<div class="row content">
							<div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">

								<div class="panel panel-primary">
									<div class="panel-heading">Applicants
										<button id="applicantrefresh" class="btn-primary pull-right" type="button" onClick="updateRespondantsTable();"><i class="fa fa-refresh"></i></button>
									</div>
									<div class="panel-body" id="respondant_list">
										<table id="respondants" class="table table-hover table-condensed"></table>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
									<div class="panel panel-info">
										<div class="panel-heading">
											<span class="text-left">Position Profile<i
												class="fa fa-line-chart pull-right"></i></span>
										</div>
										<div class="panel-body text-center">
											<div>
												<canvas id="positionProfile"
													style="width: 100%, height: auto;"></canvas>
											</div>
										</div>
									</div>
																		<div class="panel panel-info">
										<div class="panel-heading">
											<span class="text-left">Position Turnover<i
												class="fa fa-bar-chart pull-right"></i></span>
										</div>
										<div class="panel-body text-center">
											<div>
												<canvas id="positionTenure"
													style="width: 100%, height: auto;"></canvas>
											</div>
										</div>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				updatePositionProfile();
				initRespondantsTable()
			</script>

			<%@ include file="/WEB-INF/includes/inc_footer.jsp"%>
</html>