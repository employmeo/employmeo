<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row">
                        <div class="col-xs-12 col-sm-3 col-md-3">
                            <h3>Dashboard</h3>
                        </div>
                            <div class="col-md-9 col-sm-9 col-xs-12 pull-right">
						<form class="form-inline pull-right" id='refinequery'>
								<div class="form-group">
									<div id="reportrange" class="form-control" style="line-height: 1.42857143;">
									<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
									<span></span> <b class="caret"></b>
									</div>
								    <input type=hidden name="fromdate" id='fromdate'>
								    <input type=hidden name="todate" id='todate'>
								</div>
								<div class="form-group">
									<select class="form-control" id="location_id" name="location_id" onChange='updateDash()'>
										<option value=-1>all locations</option>
									</select>
								</div>
                            	<div class="form-group">
									<select class="form-control" id="position_id" name="position_id" onChange='updateDash()'>
										<option value=-1>all positions</option>
									</select>
								</div>
						</form>
							</div>
						</div>

<div class="clearfix"></div>

<div class="row top_tiles">
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-users"></i>
				</div>
				<div class="count" id='invitecount'>0</div>
				<h3>Invited</h3>
			</div>
		</div>
	</div>
	<div class="animated flipInY col-md-3 col-sm-6 col-xs-12 tile_stats_count">
		<div class="tile-stats">
			<div class="right">
				<div class="icon">
					<i class="fa fa-check-square-o">0</i>
				</div>
				<div class="count" id='completedcount'>0</div>
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
				<div class="count" id='scoredcount'>0</div>
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
				<div class="count" id='hiredcount'>0</div>
				<h3>Hired</h3>
			</div>
		</div>
	</div>
</div>

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
                           	<div class="progress-bar  progress-bar-success" role="progressbar" id='risingstarbar' style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span id='risingstarrate'>%</span></div>
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
                           	<div class="progress-bar  progress-bar-info" id='longtimerbar' role="progressbar" style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span id='longtimerrate'>%</span></div>
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
                           	<div class="progress-bar  progress-bar-warning" id='churnerbar' role="progressbar" style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span id='churnerrate'>%</span></div>
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
                           	<div class="progress-bar  progress-bar-danger" id='redflagbar' role="progressbar" style="width:0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                            </div>
                        </div>
                    </div>
                    <div class="w_right w_20"><span id='redflagrate'>%</span></div>
                    <div class="clearfix"></div>
                </div>
            </div>
		</div>
	</div>
</div>



<div class="row">
	<div class="col-md-9 col-sm-9 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h4>
					Hiring Mix <i class="fa fa-bar-chart pull-right"></i>
				</h4>
			</div>
			<div class="x_content">
				<div>
					<canvas id="dashHistory" style="width: 100%, height: auto;"></canvas>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-3 col-sm-3 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h4>Recent Candidates<i class="fa fa-refresh pull-right" onclick="lookupLastTenCandidates();"></i></h4>
			</div>
			<div class="x_content">
              <ul class="list-unstyled top_profiles scroll-view" tabindex="5001" style="overflow-y:scroll;outline: none;" id="recentcandidates"></ul>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

<script type="text/javascript">
    $(document).ready(function() {
    	initializeDatePicker(updateDash);
		updatePositionsSelect(false);
		updateLocationsSelect(false);
		updateDash();
		lookupLastTenCandidates();
    });
</script>

</body>
</html>