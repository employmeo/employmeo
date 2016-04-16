<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
		<div class="row content">
			<div class="col-sm-12">
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
							<table id="respondants" class="table table-hover display compact responsive nowrap" cellspacing="0" width="100%"></table>
<div id="applicantprofile" class="row content">
		<div class="col-xs-12 hidden-xs"><hr></div>
            <div class="col-sm-12 col-md-6">
			<div class="panel panel-info">
					<div class="panel-heading">
						<span class="text-left">Applicant Profile<i class="fa fa-line-chart pull-right"></i></span>
					</div>
					<div class="panel-body text-center">
						<div><canvas id="positionProfile" style="width: 100%, height: auto;"></canvas></div>
					</div>
				</div>
				</div>
			<div class="col-sm-12 col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						<span class="text-left">Similar Applicants<i class="fa fa-line-chart pull-right"></i></span>
					</div>
					<div class="panel-body text-center">
						<div class="col-sm-6 col-md-9">
							<canvas id="positionTenure" style="width: 100%, height: auto;"></canvas>
						</div>
					</div>
				</div>				
</div>
</div>									</div>
								</div>
							</div>
				</div>
	<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
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