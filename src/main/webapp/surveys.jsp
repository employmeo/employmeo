<%@ include file="/WEB-INF/includes/inc_head.jsp" %>

  <div class="row content">
			<div class="col-sm-12">
				<div class="row content">
					<ul class="nav nav-tabs" id="surveys_nav"></ul>
					<br>
				</div>
				<div class="row content">
					<div class="col-sm-12 col-md-12 col-lg-12">

						<div class="panel panel-primary">
							<div class="panel-heading">Surveys</div>
							<div class="panel-body">
							<form id="updatesurveyform" class="form">
							    Survey ID: <input class="form-control" id="surveyid" name="survey_id" disabled>
							    Survey Name: <input class="form-control" id="surveyname" name="survey_name" disabled>
							    Survey Question Count: <input class="form-control" id="questiontotal" disabled>
							    Survey Type: <input class="form-control" id="surveytype" name="survey_type" disabled>
							    Survey Status: <input class="form-control" id="surveystatus" name="survey_status" disabled>
							</form>

							</div>
						</div>
						<div class="row content">
							<div class="col-sm-12 col-md-12 col-lg-12">

								<div class="panel panel-primary">
									<div class="panel-heading">Survey Questions</div>
									<div class="panel-body">
										<table id="questions" class="table table-hover display compact responsive nowrap">
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
	<script type="text/javascript">
		updateSurveysNav();
	</script>
</html>