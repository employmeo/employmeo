<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row">
	<div class="col-xs-12 col-sm-3 col-md-3">
		<h3>Assessments</h3>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 pull-right">
		<form class="form-inline pull-right" id='refinequery'>
			<div class="form-group">
				<select class="form-control" id="asid" name="asid"
					onChange='changeSurveyTo(this.value);'>
				</select>
			</div>
		</form>
	</div>
</div>
<div class="row content">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="x_panel">
			<div class="x_title">
				<h3 id="assessmentname"></h3>
			</div>
			<div class="x_content">
	<div class="col-sm-12 col-md-6 col-lg-6">
	<div class="x_panel">
			<div class="x_title">
				<h4>Assessment Details</h4>
			</div>
			<div class="x_content">
			<span id="assessmentdesc"></span>
			Total Questions: <span id="questiontotal"></span>
			</div>
	</div>
	
	</div>
	<div class="col-sm-6 col-md-3 col-lg-3">
	<div class="x_panel">
			<div class="x_title">
				<h4>Completion Rate</h4>
			</div>
			<div class="x_content text-center">
            	<span class="chart" id="completionguage" data-percent="0">
                	<span style="line-height:100px;font-size:30px;" class="percent"></span>
				</span>
			</div>
	</div>
	</div>
	<div class="col-sm-6 col-md-3 col-lg-3">
	<div class="x_panel">
			<div class="x_title">
				<h4>Time to Complete</h4>
			</div>
			<div class="x_content text-center"><span id="assessmenttime" style="font-size:5vw;"></span>
			</div>
	</div>
	</div>

	<div class="col-sm-12 col-md-12 col-lg-12">
	<div class="x_panel">
	<div class="x_title">
	<h4>Survey Questions</h4>
	</div>
	<div class="x_content">
						<table id="questions"
							class="table table-hover display compact responsive nowrap">
						</table>
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
	$('#completionguage').easyPieChart({
    	easing: 'easeOutBounce',
    	lineWidth: '10',
    	barColor: '#75BCDD',
    	scaleColor: false,
    	size: $('#completionguage').width(),
    	onStep: function(from, to, percent) { $(this.el).find('.percent').text(Math.round(percent));}
  	});
	updateSurveysSelect(true);
});
</script>

</html>