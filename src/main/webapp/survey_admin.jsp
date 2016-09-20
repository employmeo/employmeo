
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
  <div class="row content"> 
    <div class="col-sm-12">
      <div class="col-sm-12 col-md-12 col-lg-12">
        <div class="x_panel">
        	<div class="x_title"><h4>Export Survey</h4></div>
        	<div class="x_content hidden" id="surveyexported">
        		<h4>Generated Survey Definition</h4><hr>
        		<div class="col-xs-12 col-sm-12 col-md-12">
        		<p id="surveydefinition"></p>
        		<!-- 
        			<div class="form-group has-feedback">
        				<textarea class="form-control" rows="20" cols="80" id="surveydefinition" name="surveydefinition"></textarea>
        			</div>
        		-->
        		</div>
    			<button type="button" class="btn btn-block btn-default" onclick='resetExport();'>Export Again</button> 
        	</div>
        	<div class="x_content" id="exportsurveyform">
        	  <form id='exportsurvey' name="exportsurvey" class="form form-horizontal form-label-left input_mask">
			    <div class="col-xs-12 col-sm-12 col-md-12">
				    <label for="survey_id">Assessment:</label>
					<div class="form-group has-feedback">
						<select class="form-control" id="survey_id" name="survey_id"></select>
					</div>
					<button type="submit" class="btn btn-block btn-default"><!--  onclick='inviteApplicant(this.form);'-->
				    	Export Survey 
				    	<i id="spinner" class="fa fa-spinner fa-spin hidden"></i>
				    </button>   
				  </div>
				</form>
				</div>

        	</div>
        </div>
  	  </div>
    </div>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<script>
$(document).ready(function() {
	listSurveysSelect(false);
  $('#exportsurvey').submit(function(){
	  exportSurvey();
	  return false;});
});
</script>

</html>