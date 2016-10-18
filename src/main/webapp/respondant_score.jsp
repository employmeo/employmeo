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
				<div class="x_title">
					<div style='display:inline-block;margin-left:5px;'><h3 id='candidatename'></h3></div>
					<div class="profilebadge pull-left" id='candidateicon'>
						<i class="fa fa-spinner fa-spin"></i>
					</div>
				</div>
				<div class="x_content">
				<h2>Applicant Details <i class="fa fa-chevron-down pull-right" onclick="expander('candidatedetails');"></i>
				</h2>
					<div class='row'>
						<table class="table table-hover hidden" id='candidatedetails'>
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
					<div>
						<span id='assessmentdate' class='pull-right'>Date</span>
						<h4 id='assessmentname'>Assessment</h4>
					</div>
					<div>
						<table class='table table-hover'>
							<tbody id='assessmentresults'>
							</tbody>
						</table>
						<hr>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8 col-sm-8 col-xs-12">
			<div class='x_panel'>
				<div class="x_title">
					<h3>Composite Score: <span class="pull-right" id='compositescore'></span></h3>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div id='fulltextdesc'>
					</div>
					<hr>
					<div class='container fluid text-center' id='predictions'>
					    <div class='row text-center'><h4 id='probheader'></h4></div>
						<div id='pred1' class="col-md-4 col-sm-4 col-xs-12">
						  <h5 id='prob1header'></h5>
			            	<span class="chart" id="pred1guage" data-percent="0">
			                	<span style="line-height:100px;font-size:30px;" class="percent"></span>
							</span>
						</div>
						<div id='pred2' class="col-md-4 col-sm-4 col-xs-12">
						  <h5 id='prob2header'></h5>
			            	<span class="chart" id="pred2guage" data-percent="0">
			                	<span style="line-height:100px;font-size:30px;" class="percent"></span>
							</span>
						</div>
						<div id='pred3' class="col-md-4 col-sm-4 col-xs-12">
						  <h5 id='prob3header'></h5>
			            	<span class="chart" id="pred3guage" data-percent="0">
			                	<span style="line-height:100px;font-size:30px;" class="percent"></span>
							</span>
						</div>
					</div>
					<div class='hidden'>
						<canvas id="respondantProfile"></canvas>
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
		getPredictions(respondantId);
	}
	if (respondantUuid != null) {
		getPredictionsUuid(respondantUuid);
	}		
	function presentPredictions(dataScores) {
		$('#candidatename').text(respondant.respondant_person_fname + ' ' + respondant.respondant_person_lname);
		$('#candidateemail').text(respondant.respondant_person_email);
		$('#candidateaddress').text(respondant.respondant_person_address);
		$('#candidateposition').text(respondant.respondant_position_name);
		$('#candidatelocation').text(respondant.respondant_location_name);
		$('#assessmentname').text(respondant.respondant_survey_name);
		$('#assessmentdate').text(respondant.respondant_created_date);
		$('#fulltextdesc').text('Lots and lots of text goes here, until typing is done');
		renderAssessmentScore(dataScores.scores);
		$('#candidateicon').html('<i class="fa ' + respondant.respondant_profile_icon +'"></i>');
		$('#candidateicon').addClass(respondant.respondant_profile_class);
		$('#compositescore').text(Math.round(respondant.respondant_composite_score));

		var header = $('<h4 />',{'text': 'Probability that ' + respondant.respondant_person_fname + ' ...'});
		$('#predictions').empty();
		$('#predictions').append($('<div />',{'class':'row text-center'}).append(header));
	    var pred1 = {
	    		'prediction_id' : 1,
	    		'header' : 'Gets Hired',
	    		'probability' : .72,
	    		'barcolor' : '#75BCDD'
	    };
	    var pred2 = {
	    		'prediction_id' : 2,
	    		'header' : 'Gets Hired',
	    		'probability' : .72,
	    		'barcolor' : '#75BCDD'
	    };
	    var pred3 = {
	    		'prediction_id' : 3,
	    		'header' : 'Gets Hired',
	    		'probability' : .72,
	    		'barcolor' : '#75BCDD'
	    };
	    
	    addPrediction(pred1);
	    addPrediction(pred2);
	    addPrediction(pred3);
	    
	}
	
	function addPrediction(prediction) {

		var preddiv = $('<div />', { 'class' : 'col-md-4 col-sm-4 col-xs-12 text-center'});
        preddiv.append($('<h5 />',{'text' : prediction.header} ));
        
        var spanid = 'prediction_' + prediction.prediction_id;
        var spanChart = $('<span />', {
        	'class' : 'chart',
        	'id' : spanid,
        	'data-percent' : 0
        }).append($('<span />', {
        	'class' : 'percent',
        	'style' : 'line-height:100px;font-size:30px;'
        }));

		preddiv.append(spanChart);
		$('#predictions').append(preddiv);
				
		$('#'+spanid).easyPieChart({
	    	easing: 'easeOutBounce',
	    	lineWidth: '10',
	    	barColor: prediction.barcolor,
	    	scaleColor: false,
	    	size: $('#'+spanid).width(),
	    	onStep: function(from, to, percent) { $(this.el).find('.percent').text(Math.round(percent));}
	  	});
		$('#'+spanid).data('easyPieChart').update(100*prediction.probability);
		
	}
	
</script>

</html>