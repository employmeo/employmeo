<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="">
	<div class="page-title">
		<div class="title_left">
			<h3>Candidate Results</h3>
		</div>
		<div class="title_right">
			<div
				class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
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
		<div class="col-md-4 col-sm-4 col-xs-12">
			<div class="x_panel">
				<div class="x_content">
				<div class='row'>
					<h3 id='candidatename'></h3>
				</div></div>
				<div class="x_content">
					<div class='row'>
						<table class="table table-hover" id='candidatedetails'>
							<tbody>
								<tr title="email address">
									<td><span><i class="fa fa-envelope"></i></span></td>
									<td><span id='candidateemail'></span></td>
								</tr>
								<tr title="home address">
									<td><span><i class="fa fa-home"></i></span></td>
									<td><span id='candidateaddress'></span></td>
								</tr>
								<tr title="position applied to">
									<td><span><i class="fa fa-briefcase"></i></span></td>
									<td><span id='candidateposition'></span></td>
								</tr>
								<tr title="location applied to">
									<td><span><i class="fa fa-map-marker"></i></span></td>
									<td><span id='candidatelocation'></span></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-12">
			<div class='x_panel'>
				<div class="x_title">
					<h3>Assessment Score</h3>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div>
						<span id='assessmentdate' class='pull-right'>Date</span>
						<h4 id='assessmentname'>Assessment</h4>
						<span id='showall' class='pull-right'><a href="#" onclick="showAllDetails()">Click <i class="fa fa-plus-square-o"></i> to show detailed explanations</a></span>
						<span id='hideall' class='hidden pull-right'><a href="#" onclick="hideAllDetails()">Click <i class="fa fa-minus-square-o"></i> to hide detailed explanations</a></span>
					</div>
					<div>
						<table class='table table-hover'>
							<tbody id='assessmentresults'>
							</tbody>
						</table>
						<hr>
					</div>
					<div class='hidden'>
						<canvas id="respondantProfile"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class='row'>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class='x_panel' id='analyticspanel'>
				<div class='x_title'>
					<div class="profilebadge pull-right" id='candidateicon'>
						<i class="fa fa-spinner fa-spin"></i>
					</div>
					<h3>Predictive Analytics</h3>
					<div class="clearfix"></div>
				</div>
				<div class='x_content'>
					<div class="col-md-4 col-sm-4 col-xs-12">
						<div class='row content' style='background-color: #EFEFEF;'>
						<form id='corefactorsused' class='form-horizontal form-label-left'>
						</form>
						</div>
					</div>
					<div class="col-md-8 col-sm-8 col-xs-12">
						<div><span id='projectedtenure' class='pull-right'>11.6 Months</span>
						<h4>Tenure Projection</h4></div>
						<div>Percent By Month<hr></div>	
						<div>
							<canvas id="positionTenure"></canvas>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
<script>
	var urlParams;
	(window.onpopstate = function() {
		var match, pl = /\+/g, // Regex for replacing addition symbol with a space
		search = /([^&=]+)=?([^&]*)/g, decode = function(s) {
			return decodeURIComponent(s.replace(pl, " "));
		}, query = window.location.search.substring(1);

		urlParams = {};
		while (match = search.exec(query))
			urlParams[decode(match[1])] = decode(match[2]);
	})();
	var respondantId = urlParams.respondant_id;
	var respondantUuid = urlParams.respondant_uuid;
	if (respondantId != null) {
		getScore(respondantId);
	}
	if (respondantUuid != null) {
		getScoreUuid(respondantUuid);
	}
</script>

</html>