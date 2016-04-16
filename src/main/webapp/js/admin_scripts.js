//Useful Global Variables
var tenureChart;
var profileChart;
var respondantProfile;
var historyChart;

var redflagColor = "#d9534f";
var redflagOverlay = "rgba(217, 83, 79,0.3)";
var redflagHighlight = "#d43f3a";

var churnerColor = "#f0ad4e";
var churnerOverlay = "rgba(240, 173, 78, 0.3)";
var churnerHighlight = "#eea236";

var longtimerColor = "#5bc0de";
var longtimerOverlay = "rgba(91, 192, 222,0.3)";
var longtimerHighlight = "#46b8da";

var risingstarColor = "#5cb85c";
var risingstarOverlay = "rgba(92, 184, 92,0.3)";
var risingstarHighlight = "#4cae4c";

var applicantColor = "rgba(120,60,100,1)";
var applicantOverlay = "rgba(120,60,100,0.3)";
var applicantHighlight = "rgba(120,60,100,1)";


//new jquery plugin for datatables;
jQuery.fn.dataTableExt.oApi.fnProcessingIndicator = function ( oSettings, onoff )
{
	if ( onoff === undefined ) {
		onoff = true;
	}
	this.oApi._fnProcessingDisplay( oSettings, onoff );
};

//basic user / account functions (login/logout/etc)
function getUserFname() {
	var name = "user_fname=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1);
		if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
	}
	return "";
}

function timeZoneDetect(){
	return new Date().getTimezoneOffset()/-60;
}

// section for updating selectors
function updatePositionsSelect() {
	var url = "/mp";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: {
			"formname" : "getpositionlist",
			"noRedirect" : true
		},
		success: function(data)
		{
			var positions = JSON.parse(data);
			$.each(positions, function (index, value) {
				$('#position_id').append($('<option/>', { 
					value: this.position_id,
					text : this.position_name 
				}));
			});   
		}
	});
}

function updateLocationsSelect() {
	var url = "/mp";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: {
			"formname" : "getlocationlist",
			"noRedirect" : true
		},
		success: function(data)
		{
			var locations = JSON.parse(data);
			$.each(locations, function (index, value) {
				$('#location_id').append($('<option/>', { 
					value: this.location_id,
					text : this.location_name 
				}));
			});   
		}
	});
}

function updateSurveysSelect() {
	var url = "/mp";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: {
			"formname" : "getsurveylist",
			"noRedirect" : true
		},
		success: function(data)
		{
			var surveys = JSON.parse(data);
			$.each(surveys, function (index, value) {
				$('#survey_id').append($('<option/>', { 
					value: this.survey_id,
					text : this.survey_name 
				}));
			});   
		}
	});
}

function updateDateChoosers() {
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
	return;
}

function toggleLegend() {
	if ($('#expander').hasClass('fa-chevron-down')) {
		$('#expander').removeClass('fa-chevron-down');
		$('#expander').addClass('fa-chevron-up');	
		$('.rect').each(function() {
			$(this).removeClass('hidden');
		});
	} else {
		$('#expander').addClass('fa-chevron-down');
		$('#expander').removeClass('fa-chevron-up');		
		$('.rect').each(function() {
			$(this).addClass('hidden');
		});
	}
}

// Section for inviting new applicants
function inviteApplicant(e) {
	// todo: validate form
	var url = "/mp";
	var response = "failed";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: $(e).serialize(),
		beforeSend: function(data) {
			$("#inviteapplicant :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			e.reset();
			$('#invitationform').addClass('hidden');
			$('#invitationsent').removeClass('hidden');
			response = JSON.parse(data);
		},
		complete: function(data) {
			$("#inviteapplicant :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});

	return response;
}

function resetInvitation() {
	$('#invitationsent').addClass('hidden');
	$('#invitationform').removeClass('hidden');	
}

//Section for search respondants / build respondants table
function initRespondantsTable() {
	var rTable = $('#respondants').DataTable( {
		// responsive: true,
		order: [[ 0, 'desc' ]],
		columns: [
		          { title: 'ID', data: 'respondant_id'},
		          { title: 'First Name', data: 'respondant_person_fname'},
		          { title: 'Last Name', data: 'respondant_person_lname'},
		          { title: 'Email', data: 'respondant_person_email'},
		          { title: 'View', data: null}
		          ],
		          columnDefs: [
		                       { responsivePriority: 2, targets: 1},
		                       { responsivePriority: 4, targets: 2},
		                       { responsivePriority: 6, targets: 3},
		                       { responsivePriority: 8, targets: 0},
		                       { targets: -1, data: null, defaultContent: "<button class='btn btn-default btn-xs'><i class=\"fa fa-search\"></i></button>"}
		                       ],
		                       bProcessing: true,
		                       language: {
		                    	   loadingRecords : "\<i class=\"fa fa-spinner fa-3x fa-spin\"\>\<\/i\>",
		                    	   processing : "\<i class=\"fa fa-spinner fa-3x fa-spin\"\>\<\/i\>"
		                       }
	});

	updateRespondantsTable();
}

function updateRespondantsTable() {

	var url = "/mp";
	var response = "failed";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: $('#refine_query').serialize(),
		beforeSend: function() {
			$("#spinner").addClass("fa-spin");
			rTable = $('#respondants').DataTable();
			rTable.clear();
			$('#respondants').dataTable().fnProcessingIndicator(true);
		},
		success: function(data)
		{
			response = JSON.parse(data);
			rTable = $('#respondants').DataTable();
			$('#respondants').dataTable().fnAddData(response);
			rTable.$('tr').click(function (){
				rTable.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
				var respondant = $('#respondants').dataTable().fnGetData(this);
//				window.location.assign('/respondant_score.html?&respondant_id='+respondant.respondant_id);
				showApplicantScoring(respondant);
			});
			rTable.on('click', 'button', function (){
				var respondant = rTable.row($(this).parents('tr')).data();
				window.location.assign('/respondant_score.html?&respondant_id='+respondant.respondant_id);
			});
		},
		complete: function() {
			$('#respondants').dataTable().fnProcessingIndicator(false);
		}
	});

	return response;
}


// Positions / roles pages functions.
function updatePositionsNav() {
	var url = "/mp";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: {
			"formname" : "getpositionlist",
			"noRedirect" : true
		},
		success: function(data)
		{
			var positions = JSON.parse(data);
			var pos_id;
			for (var i in positions) {
				var li = document.createElement("li");
				var a = document.createElement('a');
				var linkText = document.createTextNode(positions[i].position_name);
				a.appendChild(linkText);
				a.title = positions[i].position_name;
				a.href = "#";
				a.setAttribute("onClick", "changePositionTo("+positions[i].position_id+")");
				if (i==0) {
					li.setAttribute("class","active");
					pos_id = positions[i].position_id;
				}
				li.appendChild(a);
				li.setAttribute("pos_id", positions[i].position_id);
				$('#positions_nav').append(li);
			}
			updatePositionTenure(pos_id);
			updatePositionProfile(pos_id);
		}
	});
}

function changePositionTo(pos_id) {
	var items = $('#positions_nav li');

	items.each(function(li) {
		$(this).attr("class","");
		if (pos_id == $(this).attr("pos_id")) {
			$(this).attr("class", "active");
		}
	});

	updatePositionTenure(pos_id);
	updatePositionProfile(pos_id);
}


//Section for looking at / manipulating surveys
function updateSurveysNav() {
	var url = "/mp";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: {
			"formname" : "getsurveylist",
			"noRedirect" : true
		},
		success: function(data)
		{
			var surveys = JSON.parse(data);
			var survey_id;
			for (var i in surveys) {
				var li = document.createElement("li");
				var a = document.createElement('a');
				var linkText = document.createTextNode(surveys[i].survey_name);
				a.appendChild(linkText);
				a.title = surveys[i].survey_name;
				a.href = "#";
				a.setAttribute("onClick", "changeSurveyTo("+surveys[i].survey_id+")");
				if (i==0) {
					li.setAttribute("class","active");
					survey_id = surveys[i].survey_id;
				}
				li.appendChild(a);
				li.setAttribute("survey_id", surveys[i].survey_id);
				$(li).data('survey',surveys[i]);
				$('#surveys_nav').append(li);
			}
			updateSurveyForm(surveys[0]);
			initSurveyQuestionsTable(surveys[0]);
		}
	});
}

function changeSurveyTo(survey_id) {
	var items = $('#surveys_nav li');

	items.each(function(li) {
		$(this).attr("class","");
		if (survey_id == $(this).attr("survey_id")) {
			$(this).attr("class", "active");
			var survey = $(this).data('survey');
			updateSurveyForm(survey);
			updateSurveyQuestions(survey);
		}		
	});
}

function updateSurveyForm(survey) {
	$('#surveyid').val(survey.survey_id);
	$('#surveyname').val(survey.survey_name);
	$('#questiontotal').val(survey.questions.length);
	$('#surveytype').val(survey.survey_type);
	$('#surveystatus').val(survey.survey_status);
}

function initSurveyQuestionsTable(survey) {
	var qTable = $('#questions').DataTable( {
		// responsive: true,
		order: [[ 0, 'desc' ]],
		columns: [
		          { title: 'ID', data: 'question_id'},
		          { title: 'Question', data: 'question_text'},
		          { title: 'Type', data: 'question_type'},
		          { title: 'Corefactor', data: 'question_corefactor_id'},
		          { title: 'Image', data: 'question_display_id'}
		          ],
		          columnDefs: [
		                       { responsivePriority: 2, targets: 1},
		                       { responsivePriority: 4, targets: 2},
		                       { responsivePriority: 6, targets: 3},
		                       { responsivePriority: 8, targets: 0},
		                       { responsivePriority: 10, targets: 4},	    		
		                       ],
		                       bProcessing: true,
		                       language: {
		                    	   loadingRecords : "\<i class=\"fa fa-spinner fa-3x fa-spin\"\>\<\/i\>",
		                    	   processing : "\<i class=\"fa fa-spinner fa-3x fa-spin\"\>\<\/i\>"
		                       }
	});

	updateSurveyQuestions(survey);
}	

function updateSurveyQuestions(survey) {
	var qTable = $('#questions').DataTable();
	qTable.clear();
	$('#questions').dataTable().fnAddData(survey.questions);
	qTable.$('tr').click(function (){
		qTable.$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	});
	return
}

// Section for updating the dashboard
function updateDash() {   
	refreshDashCharts({
		dataApplicants: getApplicantData(),
		dataInterviews: getInterviewData(),
		dataHires: getHireData(),
		dataTurnover: getTurnoverData()
	}); // use stub code
}

function updateHistory() {
	var dashHistory = $("#dashHistory").get(0).getContext("2d");
	historyChart = new Chart(dashHistory, {
		type: 'bar', data: getHistoryData(),
		options: { 
			bar: {stacked: true},
			scales: { 
				xAxes: [{gridLines: {display: false}, stacked: true}],
				yAxes: [{gridLines: {display: true}, stacked: true}]
			},
			responsive: true,
			legend: { display: false }
		}
	});
	return;
}

function refreshDashCharts(response) {

	// Build Applicants Widget
	var dashApplicants = $("#dashApplicants").get(0).getContext("2d");
	var applicantTotal = 0;
	$.each(response.dataApplicants.datasets[0].data,function() { applicantTotal += this; });
	var appDoughnutChart = new Chart(dashApplicants, {
		   type: 'doughnut',
		   data: response.dataApplicants,
		   options: {
			   cutoutPercentage : 65,
			   responsive : true,
			   legend: { display: false },
			   tooltips: {enabled: false},
			   animation: { onComplete: function() {
				   var canvasWidthvar = $('#dashApplicants').width();
				   var canvasHeight = $('#dashApplicants').height();
				   var fontsize = (canvasHeight/70).toFixed(2);
				   dashApplicants.font=fontsize +"em Comfortaa bold";
				   dashApplicants.textBaseline="middle"; 
				   var textWidth = dashApplicants.measureText(applicantTotal).width;
				   var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
				   dashApplicants.fillText(applicantTotal, txtPosx, canvasHeight/2);				   
			   }}}});
	
	// Build Interviews Widget
	var dashInterviews = $("#dashInterviews").get(0).getContext("2d");
	var interviewTotal = 0;
	$.each(response.dataInterviews.datasets[0].data,function() { interviewTotal += this; });
	var intDoughnutChart= new Chart(dashInterviews, {
		type: 'doughnut', 
		data: response.dataInterviews,
		options: {
			   cutoutPercentage : 65,
			   responsive : true,
			   legend: { display: false },
			   tooltips: {enabled: false},
			   animation: { onComplete: function() {
				   var canvasWidthvar = $('#dashInterviews').width();
				   var canvasHeight = $('#dashInterviews').height();
				   var fontsize = (canvasHeight/70).toFixed(2);
				   dashInterviews.font=fontsize +"em Comfortaa bold";
				   dashInterviews.textBaseline="middle"; 
				   var textWidth = dashInterviews.measureText(interviewTotal).width;
				   var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
				   dashInterviews.fillText(interviewTotal, txtPosx, canvasHeight/2);				   
			   }}}});

	// Build Hires Widget
	var dashHires = $("#dashHires").get(0).getContext("2d");
	var hireTotal = 0;
	$.each(response.dataHires.datasets[0].data,function() {  hireTotal += this; });
	new Chart(dashHires, {
		type: 'doughnut', 
		data: response.dataHires, 
		options: {
		   cutoutPercentage : 65,
		   responsive : true,
		   legend: { display: false },
		   tooltips: {enabled: false},
		   animation: { onComplete: function() {
			   var canvasWidthvar = $('#dashHires').width();
			   var canvasHeight = $('#dashHires').height();
			   var fontsize = (canvasHeight/70).toFixed(2);
			   dashHires.font=fontsize +"em Comfortaa bold";
			   dashHires.textBaseline="middle"; 
			   var textWidth = dashHires.measureText(hireTotal).width;
			   var txtPosx = Math.round((canvasWidthvar - textWidth)/2);
			   dashHires.fillText(hireTotal, txtPosx, canvasHeight/2);				   
		   }}}});


	// Build Turnover Widget
	var dashTurnover = $("#dashTurnover").get(0).getContext("2d");
	var turnProjection = 0;
	$.each(response.dataTurnover,function() { turnProjection += this.value; });
	new Chart(dashTurnover, {type: 'bar', data: response.dataTurnover, options: { showScale:false, responsive : true, legend: { display: false } }});

}

function showApplicantScoring(applicantData) {
	refreshPositionTenure(getPositionTenureData()); // use stub code
	refreshPositionProfile(getApplicantProfileData()); // use stub code
}

function updatePositionTenure(pos_id) {
	refreshPositionTenure(getPositionTenureData()); // use stub code
}

function refreshPositionTenure(dataPositionTenure) {
	var positionTenure = $("#positionTenure").get(0).getContext("2d");
	if (tenureChart != null) tenureChart.destroy();
	tenureChart = new Chart(positionTenure, {type: 'bar', data: dataPositionTenure, options: { 
		showScale: true,
		scaleShowGridLines: false,
		responsive : true,
		legend: { display: false }
	}});	
}

function updatePositionProfile(pos_id) {
	refreshPositionProfile(getApplicantProfileData()); // use stub code
}

function refreshPositionProfile(dataPositionProfile) {
	var positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	profileChart = new Chart(positionProfile, {type: 'radar', data: dataPositionProfile, options: { 
		showScale: true,
		responsive : true,
		defaultFontSize: 16,
		legend: { display: false }
	}});	
}


// Payroll tools section

function uploadPayroll(e) {
	$('#csvFile').parse({
		config : {
			header: true,
			dynamicTyping: true,
			complete: function(results, file) {
				$('#payroll').DataTable( {
					data: results.data,
					columns : [
					           { title: 'Employee ID', data: 'employee'},
					           { title: 'Raise Rate', data: 'RaiseRate'},
					           { title: 'Total Hours', data: 'Total Hours' },
					           { title: 'Tenure', data: 'Tenure' },
					           { title: 'Monthly Hours', data: 'Monthly Hours' }
					           ]

				});

				console.log("Parsing complete:", results, file);
			}
		},
		before : function(file, inputElem){},
		error: function(err, file, inputElem, reason){},
		complete : {}

	})
}


// Respondant scoring section
function processRespondant(respondantId) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/mp",
		data: {
			"respondant_id" : respondantId,
			"formname" : "processrespondant",
			"noRedirect" : true        	
		},
		success: function(data)
		{
			var jResp = JSON.parse(data);
			jResp.position = getPositionDetails(jResp.scores);
			refreshRespondantProfile(jResp);
			createProfileSquares(jResp);
		}
	});    

}

function createProfileSquares(dataScores) {

	var profiles = dataScores.position.position_profiles;
	var max = 0;
	for (var i in profiles) {
		var pname = profiles[i].profile_name;
		$('#profilesquares > .' + profiles[i].profile_class).each(function() {
			$(this).data('index', i);
			var profileData = new Array();
			$(this).data('profile', profiles[i]);							
			var index = 0;
			for (var key in dataScores.position.position_corefactors) {
				profileData[index] = profiles[i].profile_scores[dataScores.position.position_corefactors[key]];
				index++;
			}
			if ($(this).hasClass('square')) {
				$(this).find('.squaretext').text(profiles[i].profile_probability+'%');
			}
			$(this).on('touchstart click', function(e) {
				e.preventDefault();
					var profile = $(this).data('profile');
					$('.square').addClass("unselected");
					$('.rect').addClass("unselected");
					$('.'+profile.profile_class).removeClass("unselected");
					
					var profileTenureData = profile.profile_tenure_data;
					profileTenureData.datasets[0].label = profile.profile_name;
					profileTenureData.datasets[0].backgroundColor = profile.profile_color;
					profileTenureData.datasets[0].borderColor = profile.profile_color;
					profileTenureData.datasets[0].hoverBackgroundColor = profile.profile_overlay;
					profileTenureData.datasets[0].hoverBorderColor = profile.profile_highlight;		
					refreshPositionTenure(profileTenureData);

					profileChart.config.data.datasets[0].hidden = true;
					profileChart.config.data.datasets[1].hidden = true;
					profileChart.config.data.datasets[2].hidden = true;
					profileChart.config.data.datasets[3].hidden = true;
					profileChart.config.data.datasets[$(this).data('index')].hidden = false;
					profileChart.update();
			});
			if(profiles[i].profile_probability > max) {
				max = profiles[i].profile_probability;
				$(this).click();
			}
			
		});
	}
}

function refreshRespondantProfile(dataScores) {
	respondantProfile = $("#respondantProfile").get(0).getContext("2d");	
	var scores = dataScores.scores;
	var respondant = dataScores.respondant;
	$('#applicantname').text(respondant.respondant_person_fname + ' ' + respondant.respondant_person_lname);
	var position = dataScores.position;
	var data = new Array();
	var index = 0;
	for (var key in position.position_corefactors) {
		data[index] = scores[position.position_corefactors[key]];
		index++;
	}

	var dataset = new Array();

	var profiles = 0;
	for (var num in position.position_profiles) {
		var profile = position.position_profiles[num];
		var profileData = new Array();
		index = 0;
		for (var key in position.position_corefactors) {
			profileData[index] = profile.profile_scores[position.position_corefactors[key]];
			index++;
		}
		dataset[profiles] = {	        
				label: profile.profile_name,
				backgroundColor: profile.profile_overlay,
				borderColor: profile.profile_highlight,
				hoverBackgroundColor: profile.profile_color,
				hoverBorderColor: profile.profile_color,
				pointBacgroundColor: profile.profile_highlight,
				pointBorderColor: profile.profile_color,
				data: profileData
		};
		profiles++;

	}
	dataset[profiles] = {
			label: respondant.respondant_person_fname,
			backgroundColor: applicantOverlay,
			borderColor: applicantHighlight,
			hoverBackgroundColor: applicantColor,
			hoverBorderColor: applicantColor,
			pointBacgroundColor: applicantColor,
			pointBorderColor: applicantColor,
			data: data
	};

	var respondantData = {
			labels: position.position_corefactors,
			datasets: dataset	};

	profileChart = new Chart(respondantProfile, { type : 'radar', data: respondantData, options: { 
		responsive : true,
		legend: { display: false },
		scale: { 
			gridLines: {display: false},
			scaleLabel: {display: true, fontFamily: "'Comfortaa'", fontSize: 16}, 
			ticks: {beginAtZero: true, suggestedMax: 5}}
	}});	
}

//Generic, always useful post form to action
function postToAction(e) {
	var url = "/mp";
	var response = "failed";
	$.ajax({
		type: "POST",
		async: true,
		url: url,
		data: $(e).serialize(), 
		success: function(data)
		{
			response = JSON.parse(data);
			console.log(response);
			$("#form_response").html(data);
		}
	});

	return response;
}
