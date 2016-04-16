<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row top_tiles">
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-users"></i>
				</div>
				<div class="count">300</div>
				<h3>Invited</h3>
			</div>
		</div>
	</div>
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-check-square-o"></i>
				</div>
				<div class="count">250</div>
				<h3>Completed</h3>
			</div>
		</div>
	</div>
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-bar-chart"></i>
				</div>
				<div class="count">250</div>
				<h3>Scored</h3>
			</div>
		</div>
	</div>
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-user-plus"></i>
				</div>
				<div class="count">100</div>
				<h3>Hired</h3>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="dashboard_graph">
			<div class="row x_title">
				<h3>
					Applicant Mix <i class="fa fa-bar-chart pull-right"></i>
				</h3>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div>
					<canvas id="dashHistory" style="width: 100%, height: auto;"></canvas>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-12">
				<div class="col-md-12 col-sm-12 col-xs-6">
					<div class="x_title">
						<h4>Legend</h4>
					</div>
					<div class="x_content">
						<table class="tile_info">
							<tbody>
								<tr>
									<td>Rising Star</td>
									<td><div class="btn-success text-center"
											style="height: 30px; width: 30px;">
											<i class="fa fa-rocket" style="margin: auto; float: none;"></i>
										</div></td>
								</tr>
								<tr>
									<td>Long Timer</td>
									<td><div class="btn-info text-center"
											style="height: 30px; width: 30px;">
											<i class="fa fa-user-plus" style="margin: auto; float: none;"></i>
										</div></td>
								</tr>
								<tr>
									<td>Churner</td>
									<td><div class="btn-warning text-center"
											style="height: 30px; width: 30px;">
											<i class="fa fa-gear" style="margin: auto; float: none;"></i>
										</div></td>
								</tr>
								<tr>
									<td>Red Flag</td>
									<td><div class="btn-danger text-center"
											style="height: 30px; width: 30px;">
											<i class="fa fa-hand-stop-o"
												style="margin: auto; float: none;"></i>
										</div></td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-6">
					<form class="form" role="form" action="" id="refine_query">
						<div class="x_title">
							<h4>Filter</h4>
						</div>
						<div class="x_content">
							<div class="form-group">
								<div id="reportrange"
									style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
									<i class="glyphicon glyphicon-calendar fa fa-calendar"></i> <span>March
										18, 2016 - April 16, 2016</span> <b class="caret"></b>
								</div>
							</div>
							<div class="form-group">
								<select class="form-control" id="location_id" name="location_id"
									onChange='updateDash()'>
									<option>all locations</option>
								</select>
							</div>
							<div class="form-group">
								<select class="form-control" id="position_id" name="position_id"
									onChange='updateDash()'>
									<option>all positions</option>
								</select>
							</div>
						</div>
						<input type=hidden name="formname" value="updatedashboard"><input
							type="hidden" name="noRedirect" value=true>
					</form>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<br>
<div class="row">
	<div class="col-xs-6 col-sm-3">
		<div class="x_panel">
			<div class="x_title">
				<h4>
					Applicants <i class="fa fa-users pull-right"></i>
				</h4>
			</div>
			<div class="x_content">
				<div>
					<canvas id="dashApplicants" width="100" height="100"></canvas>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-6 col-sm-3 hidden">
		<div class="x_panel">
			<div class="x_title">
				<h3>
					Interviewed <i class="fa fa-line-chart pull-right"></i>
				</h3>
			</div>
			<div class="x_content">
				<div>
					<canvas id="dashInterviews" width="100" height="100"></canvas>
				</div>
				<div>
					<canvas id="dashTurnover" width="100" height="100"></canvas>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-6 col-sm-3">
		<div class="x_panel">
			<div class="x_title">
				<h4>
					Hired <i class="fa fa-bar-chart pull-right"></i>
				</h4>
			</div>
			<div class="x_content">
				<div>
					<canvas id="dashHires" width="100" height="100"></canvas>
				</div>
			</div>
		</div>
	</div>
	<div class="col-xs-12 col-sm-6">
		<div class="x_panel">
			<div class="x_title">
				<h4>
					Hire Rate<i class="fa fa-bar-chart pull-right"></i>
				</h4>
			</div>
			<div class="x_content">
				<div class="widget_summary">
                	<div class="w_left w_10">
                	    <div class="btn-success text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   <i class="fa fa-rocket" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						</div>
						</div>
                    <div class="clearfix"></div>
                    <div class="w_left w_20"><h4>Rising Star</h4></div>
                    <div class="clearfix"></div>
                     <div class="w_center w_55">
                      	<div class="progress" style="height: 30px;">
                           	<div class="progress-bar  progress-bar-success" role="progressbar" style="width:73%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
                               	<span class="sr-only">73% Hired</span>
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span>73%</span></div>
                    <div class="clearfix"></div>
                </div>
				<div class="widget_summary">
                	<div class="w_left w_10">
                	    <div class="btn-info text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   <i class="fa fa-user-plus" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						</div>
						</div>
                    <div class="clearfix"></div>
                    <div class="w_left w_20"><h4>Long Timer</h4></div>
                    <div class="clearfix"></div>
                     <div class="w_center w_55">
                      	<div class="progress" style="height: 30px;">
                           	<div class="progress-bar  progress-bar-info" role="progressbar" style="width:85%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
                               	<span class="sr-only">85% Hired</span>
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span>85%</span></div>
                    <div class="clearfix"></div>
                </div>
                <div class="widget_summary">
                	<div class="w_left w_10">
                	    <div class="btn-warning text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   <i class="fa fa-gear" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						</div>
						</div>
                    <div class="clearfix"></div>
                    <div class="w_left w_20"><h4>Churner</h4></div>
                    <div class="clearfix"></div>
                     <div class="w_center w_55">
                      	<div class="progress" style="height: 30px;">
                           	<div class="progress-bar  progress-bar-warning" role="progressbar" style="width:35%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
                               	<span class="sr-only">35% Hired</span>
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span>35%</span></div>
                    <div class="clearfix"></div>
                </div>
                <div class="widget_summary">
                	<div class="w_left w_10">
                	    <div class="btn-danger text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   <i class="fa fa-hand-stop-o" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						</div>
						</div>
                    <div class="clearfix"></div>
                    <div class="w_left w_20"><h4>Red Flag</h4></div>
                    <div class="clearfix"></div>
                     <div class="w_center w_55">
                      	<div class="progress" style="height: 30px;">
                           	<div class="progress-bar  progress-bar-danger" role="progressbar" style="width:3%" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">
                               	<span class="sr-only">3% Hired</span>
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span>3%</span></div>
                    <div class="clearfix"></div>
                </div>
            </div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

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