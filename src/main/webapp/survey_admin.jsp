
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
<div id="survey_admin_features">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#surveys">Surveys</a></li>
		<li><a data-toggle="tab" href="#corefactors">Corefactors</a></li>
	</ul>
	<div class="row tab-content">
	<!--  surveys tab begin -->
		<div id="surveys" class="tab-pane fade in active">
			<div class="row content">
				<div class="col-sm-12">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<div class="x_panel">
							<div class="x_title">
								<h4>Export Survey</h4>
							</div>
							<div class="x_content hidden" id="surveyexported">
								<h4>Generated Survey Definition</h4>
								<hr>
								<div class="col-xs-12 col-sm-12 col-md-12">
									<div class="form-group has-feedback">
										<textarea class="form-control" rows="10" cols="80"
											id="surveydefinition" name="surveydefinition"></textarea>
									</div>
								</div>
								<button type="button" class="btn btn-block btn-default"
									onclick='resetExport();'>Reset</button>
							</div>
							<div class="x_content" id="exportsurveyform">
								<form id='exportsurvey' name="exportsurvey"
									class="form form-horizontal form-label-left input_mask">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<label for="survey_id">Assessment:</label>
										<div class="form-group has-feedback">
											<select class="form-control" id="survey_id" name="survey_id"></select>
										</div>
										<button type="submit" class="btn btn-block btn-default">
											Export Survey <i id="spinner"
												class="fa fa-spinner fa-spin hidden"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row content">
				<div class="col-sm-12">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<div class="x_panel">
							<div class="x_title">
								<h4>Persist Survey</h4>
							</div>
							<div class="x_content hidden" id="surveypersisted">
								<h4>Operation Results</h4>
								<hr>
								<div class="col-xs-12 col-sm-12 col-md-12">
									<p id="persistenceresults"></p>
								</div>
								<button type="button" class="btn btn-block btn-default" onclick='resetPersistence();'>Reset</button>
							</div>
							<div class="x_content" id="persistsurveyform">
								<form id='persistsurvey' name="persistsurvey"
									class="form form-horizontal form-label-left input_mask">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<label for="survey_id">Survey Definition:</label>
										<div class="form-group has-feedback">
											<textarea class="form-control" id="inputsurveydefinition"
												name="inputsurveydefinition" rows="10" cols="80"></textarea>
										</div>
										<button type="submit" class="btn btn-block btn-default">
											Persist Survey <i id="spinner"
												class="fa fa-spinner fa-spin hidden"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!--  surveys tab end -->
		<!--  corefactors tab begin -->
		<div id="corefactors" class="tab-pane fade">

			<div class="row content">
				<div class="col-sm-12">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<div class="x_panel">
							<div class="x_title">
								<h4>Export Corefactors</h4>
							</div>
							<div class="x_content hidden" id="cfexported">
								<h4>Generated Corefactor Definitions</h4>
								<hr>
								<div class="col-xs-12 col-sm-12 col-md-12">
									<div class="form-group has-feedback">
										<textarea class="form-control" rows="10" cols="80"
											id="cfdefinition" name="cfdefinition"></textarea>
									</div>
								</div>
								<button type="button" class="btn btn-block btn-default" onclick='resetCfExport();'>Reset</button>
							</div>
							<div class="x_content" id="exportcfform">
								<button type="button" class="btn btn-block btn-default" onclick='exportCorefactors();'>
									Export Corefactors
									<i id="spinner" class="fa fa-spinner fa-spin hidden"></i>
								</button>								
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row content">
				<div class="col-sm-12">
					<div class="col-sm-12 col-md-12 col-lg-12">
						<div class="x_panel">
							<div class="x_title">
								<h4>Persist Corefactors</h4>
							</div>
							<div class="x_content hidden" id="cfpersisted">
								<h4>Operation Results</h4>
								<hr>
								<div class="col-xs-12 col-sm-12 col-md-12">
									<p id="cfpersistenceresults"></p>
								</div>
								<button type="button" class="btn btn-block btn-default" onclick='resetCfPersistence();'>Reset</button>
							</div>
							<div class="x_content" id="persistcfform">
								<form id='persistcf' name="persistcf"
									class="form form-horizontal form-label-left input_mask">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<label for="cf_id">Corefactor Definitions:</label>
										<div class="form-group has-feedback">
											<textarea class="form-control" id="inputcfdefinition" name="inputcfdefinition" rows="10" cols="80"></textarea>
										</div>
										<button type="submit" class="btn btn-block btn-default">Persist Corefactors 
											<i id="spinner" class="fa fa-spinner fa-spin hidden"></i>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

		
		</div>
	</div> <!-- tab content ends -->
</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<script>
$(document).ready(function() {
	listSurveysSelect(false);
  $('#exportsurvey').submit(function(){
	  exportSurvey();
	  return false;});
  $('#persistsurvey').submit(function(){
	  persistSurvey();
	  return false;});
/*  
  $('#exportcf').submit(function(){
	  exportCorefactors();
	  return false;});
*/	  
  $('#persistcf').submit(function(){
	  persistCorefactors();
	  return false;});  
});
</script>

</html>