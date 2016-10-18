<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row">
	<div class="col-xs-12 col-sm-3 col-md-3">
		<h3>Positions</h3>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 pull-right">
		<form class="form-inline pull-right" id='refinequery'>
			<div class="form-group">
				<select class="form-control" id="position_id" name="positionid"
					onChange='changePositionTo(this.value);'>
				</select>
			</div>
		</form>
	</div>
</div>
<div class="row content">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="x_panel">
			<div class="x_title">
				<h3 id="positionname">Position Name</h3>
			</div>
			<div class="x_content">

<!-- Begin Position Description and Stats -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>Position Description<i class="fa fa-briefcase pull-right"></i></h4>
						</div>
						<div class="x_content">
							<span id="positiondesc"></span>
							<hr>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Applicants</div>
								<div id="div_applicant_count" class="count">1234</div>
							</div>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Hires</div>
								<div id="div_hire_count" class="count">23</div>
							</div>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Hire Rate</div>
								<div id="div_hire_rate" class="count">35%</div>
							</div>
							<div class="col-xs-12 text-right">
								<hr>
								<span id='#last_updated' style="font-style:italic;">Model Last Updated: Oct 15, 2016</span>
							</div>
						</div>
					</div>
				</div>
<!-- End Position Description and Stats -->
<!-- Begin Stats by Profile Table -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Key Metrics by Grade<i class="fa fa-sliders pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
							<table class="table table-condensed table-hover">
								<thead id="gradeheader">
									<tr>
										<th>Grade</th>
										<th>Hire Rate</th>
										<th>Tenure</th>
									</tr>
								</thead>
								<tbody id="gradetable">
									<tr>
										<td><div class="btn-success text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-rocket" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr>
										<td><div class="btn-info text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-user-plus" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr style="vertical-align:middle;">
										<td><div class="btn-warning text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-warning" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i></div>
						   				</td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr>
										<td><div class="btn-danger text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-hand-stop-o" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
								</tbody>
								<tfoot id="gradefooter">
									<tr>
										<th>Totals</th>
										<th>##</th>
										<th>##</th>
									</tr>
								</tfoot>
							</table>
							</div>
						</div>
					</div>
				</div>
<!-- End Stats by Profile Table -->
<!-- Begin Position Core Factors Bar Chart -->
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Critical Factors<i class="fa fa-bar-chart pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
		<div class="col-xs-12 text-center"><h5>Scoring Average For Critical Factors</h5></div>
		<div id="factors_barchart" class="col-xs-12"><canvas id="criticalfactorschart"></canvas></div>
		<div class="col-xs-6 scaleleft">least important</div>
		<div class="col-xs-6 scaleright">most important</div>
							</div>
						</div>
					</div>
				</div>
<!-- End Position Core Factors Bar Chart -->
<!-- Begin Position Core Factor List -->
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Important Factors for Position<i class="fa fa-cogs pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<table class="table table-condensed table-hover">
								<thead>
									<tr>
										<th>Factor</th>
										<th>Description</th>
										<th>Type</th>
									</tr>
								</thead>
								<tbody id="corefactorlist"></tbody>
							</table>
						</div>
					</div>
				</div>
<!-- End Position Core Factor List -->
<!-- Begin Remnants to keep JS code working until fully tested -->
				<div class="hidden">
								<canvas id="positionTenure" style="width: 100%, height: auto;"></canvas>
								<canvas id="positionProfile" style="width: 100%, height: auto;"></canvas>
				</div>
<!-- End remnants to keep JS code working until fully tested -->
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

<script type="text/javascript">
	var cfBarChart = initCriticalFactorsChart();
	updatePositionsSelect(true); // this script gets all the positions from the server, then triggers the first "refresh"

	/// for now - we'll leave the raw code on this page, but it would be likely added to the "chage position" function
	var data = getStubDataForRoleBenchmark(); /// replace with REST call or pull from other var
	updatePositionModelDetails(data.role_benchmark);
	updateGradesTable(data.role_benchmark.role_grade);
	updateCriticalFactorsChart(data);
</script>

</html>