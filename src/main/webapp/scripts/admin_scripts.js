//Useful Global Variables
var tenureChart;
var profileChart;

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
			console.log("Fetch complete:", positions);
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
			console.log("Fetch complete:", locations);
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
			console.log("Fetch complete:", surveys);
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
		          { title: 'Completed At', data: 'respondant_created_date', 'type' : 'date'}
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
				showApplicantScoring(respondant);
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
			console.log("Fetch complete:", positions);
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
		console.log($(this).attr('pos_id'));
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
	new Chart(dashHistory).Bar(getHistoryData(), { scaleShowLine:false, responsive: true });
}

function refreshDashCharts(response) {

	// Build Applicants Widget
	var dashApplicants = $("#dashApplicants").get(0).getContext("2d");
	var applicantTotal = 0;
	$.each(response.dataApplicants,function() { applicantTotal += parseInt(this.value,10); });
	$("#headerApplicants").text(applicantTotal);
	new Chart(dashApplicants).Doughnut(response.dataApplicants, { percentageInnerCutout : 50, responsive : true });

	// Build Interviews Widget
	var dashInterviews = $("#dashInterviews").get(0).getContext("2d");
	var interviewTotal = 0;
	$.each(response.dataInterviews,function() { interviewTotal += parseInt(this.value,10); });
	$("#headerInterviews").text(interviewTotal);
	new Chart(dashInterviews).Doughnut(response.dataInterviews, { percentageInnerCutout : 50, responsive : true });

	// Build Hires Widget
	var dashHires = $("#dashHires").get(0).getContext("2d");
	var hireTotal = 0;
	$.each(response.dataHires,function() { hireTotal += parseInt(this.value,10); });
	$("#headerHires").text(hireTotal);
	new Chart(dashHires).Doughnut(response.dataHires, { percentageInnerCutout : 50, responsive : true });

	// Build Turnover Widget
	var dashTurnover = $("#dashTurnover").get(0).getContext("2d");
	var turnProjection = 0;
	$.each(response.dataTurnover,function() { turnProjection += this.value; });
	$("#headerTurnover").text(turnProjection);
	new Chart(dashTurnover).Bar(response.dataTurnover, { showScale:false, responsive : true });

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
	tenureChart = new Chart(positionTenure).Bar(dataPositionTenure, { 
		showScale: true,
		scaleShowGridLines: false,
		responsive : true
	});	
}

function updatePositionProfile(pos_id) {
	refreshPositionProfile(getApplicantProfileData()); // use stub code
}

function refreshPositionProfile(dataPositionTenure) {
	var positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	profileChart = new Chart(positionProfile).Radar(dataPositionTenure, { 
		showScale: true,
		responsive : true
	});	
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
			jResp.position = getPositionDetails();
			jResp.scores = getRandomScores();
			refreshRespondantProfile(jResp);
			createProfileSquares(getRespondantScore());
		}
	});    

}

function createProfileSquares(dataScores) {

	var profiles = dataScores.position.position_profiles;
	$('#profilesquares').empty();
	for (var i in profiles) {
		var square = $('<div />', {
			'title': profiles[i].profile_name,
			'class': profiles[i].profile_class,
		}).append($('<div />', {'class': 'probability','text': '65%'}));
		$(square).data('profile', profiles[i]);
		$(square).data('index', i);
		var profileData = new Array();
		var index = 0;
		for (var key in dataScores.position.position_corefactors) {
			profileData[index] = profiles[i].profile_scores[dataScores.position.position_corefactors[key]];
			index++;
		}
		$(square).on('touchstart click', function(e) {
			console.log(e, this);
			e.preventDefault();
			if ($(this).hasClass('selected')) return;
			$('.square').removeClass("selected");
			$(this).addClass("selected");
			var profile = $(this).data('profile');
			var profileTenureData = profile.profile_tenure_data;
			profileTenureData.datasets[0].label = "Applicants";
			profileTenureData.datasets[0].fillColor = profile.profile_color;
			profileTenureData.datasets[0].strokeColor = profile.profile_color;
			profileTenureData.datasets[0].pointHighlightFill = profile.profile_highlight;
			profileTenureData.datasets[0].pointHighlightStroke = profile.profile_highlight;		
			refreshPositionTenure(profileTenureData);

			profileChart.datasets[0].fillColor = 'rgba(0,0,0,0)';
			profileChart.datasets[1].fillColor = 'rgba(0,0,0,0)';
			profileChart.datasets[2].fillColor = 'rgba(0,0,0,0)';
			profileChart.datasets[3].fillColor = 'rgba(0,0,0,0)';
			profileChart.datasets[$(this).data('index')].fillColor = profile.profile_highlight;
			profileChart.update();
		}
		);

		$('#profilesquares').append(square);
	}
}

function refreshRespondantProfile(dataScores) {

	var respondantProfile = $("#respondantProfile").get(0).getContext("2d");	
	var scores = dataScores.scores;
	var respondant = dataScores.respondant;
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
				fillColor: profile.profile_highlight,
				strokeColor: profile.profile_highlight,
				pointColor: profile.profile_color,
				pointStrokeColor: profile.profile_color,
				pointHighlightFill: profile.profile_highlight,
				pointHighlightStroke: profile.profile_color,
				data: profileData
		};
		profiles++;

	}
	dataset[profiles] = {
			label: respondant.respondant_person_fname,
			fillColor: "rgba(120,60,100,0.3)",
			strokeColor: "rgba(120,60,100,1)",
			pointColor: "rgba(120,60,100,1)",
			pointStrokeColor: "rgba(120,60,100,1)",
			pointHighlightFill: "rgba(120,60,100,1)",
			pointHighlightStroke: "rgba(120,60,100,1)",
			data: data
	};

	var respondantData = {
			labels: position.position_corefactors,
			datasets: dataset	};

	profileChart = new Chart(respondantProfile).Radar(respondantData, { 
		showScale: true,
		responsive : true,
		datasetStrokeWidth : 2,
	});	

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
