<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row">
	<div class="col-xs-12 col-sm-3 col-md-3">
		<h3>Candidate Search</h3>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 pull-right">
		<form class="form-inline pull-right" id='refinequery'>
			<input type="hidden" name="respondant_status_low" value="1">
			<input type="hidden" name="respondant_status_high" value="9">
			<div class="form-group">
				<div id="reportrange" class="form-control"
					style="line-height: 1.42857143;">
					<i class="glyphicon glyphicon-calendar fa fa-calendar"></i> <span></span>
					<b class="caret"></b>
				</div>
				<input type=hidden name="fromdate" id='fromdate'> <input
					type=hidden name="todate" id='todate'>
			</div>
			<div class="form-group">
				<select class="form-control" id="location_id" name="location_id"
					onChange='updateRespondantsTable()'>
					<option value=-1>all locations</option>
				</select>
			</div>
			<div class="form-group">
				<select class="form-control" id="position_id" name="position_id"
					onChange='updateRespondantsTable()'>
					<option value=-1>all positions</option>
				</select>
			</div>
		</form>
	</div>
</div>
<div class="row content">
	<div class="col-sm-12">
		<div class="x_panel">
			<div class="waiting hidden" id="waitingmodal">
				<i class="fa fa-spinner fa-spin"></i>
			</div>
			<div class="x_title">
				<h4>Incomplete Assessments</h4>
			</div>
			<div class="x_content" id="respondant_list">
				<table id="respondants" class="table display table-condensed nowrap"
					width="100%"></table>
			</div>
		</div>
		<div id="applicantprofile" class="row content">
			<div class="col-xs-12 col-sm-12 col-md-6">
				<div class="x_panel">
					<div class="x_content">
						<div>
							<table class='table table-hover'>
								<thead>
									<tr>
										<td><h4>Core Factor</h4></td>
										<td><h4>Score</h4></td>
									</tr>
								</thead>
								<tbody id='assessmentresults'>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-6">
				<div class="x_panel">
					<div class="x_title">
						<h4>Similar Applicants</h4>
					</div>
					<div class="x_content">
						<div class="col-sm-6 col-md-9">
							<canvas id="positionTenure" style="width: 100%, height: auto;"></canvas>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		updatePositionsSelect(false);
		updateLocationsSelect(false);
		initializeDatePicker(updateRespondantsTable);
		initRespondantsTable();
	});
</script>
</html>