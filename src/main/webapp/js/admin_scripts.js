//Useful Global Variables
var tenureChart;
var profileChart;
var respondantProfile;
var positionProfile;
var historyChart;
var dashApplicants;
var dashHires;

var respondant;
var surveyList;
var positionList;
var locationList;
var qTable;
var detailedScores;

//basic user / account functions (login/logout/etc)
function login() {
	$.ajax({
		type: "POST",
		async: true,
		data : $('#loginform').serialize(),
		url: "/admin/login",
		xhrFields: {
			withCredentials: true
		},
		beforeSend : function() {
			$("#wait").removeClass('hidden');			
			$('#loginresponse').text('');
		},
		success: function(data) {
			var startPage = $('#toPage').val();
			if (startPage != null) window.location.assign(startPage);
		},
		statusCode: {
		      401: function(){
					$('#loginresponse').text('Login failed');
					$("#wait").addClass('hidden');
		      }
		},
		error: function(data) {
			$('#loginresponse').text('Login failed');
			$("#wait").addClass('hidden');
		}		
	});	
}

function logout() {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/logout",
		xhrFields: {
			withCredentials: true
		},
		success: function(data) {
			window.location.assign('/login.jsp');
		}
	});	
}

function forgotPass() {
	$.ajax({
		type: "POST",
		async: true,
		data : $('#forgotpassform').serialize(),
		url: "/admin/forgotpassword",
		success: function(data) {
			// disable forms
			$('#forgotpassform :submit').text('Request Sent');
			$('#forgotpassform :input').prop('disabled', true);
			$('#emailtoyou').text('An password reset request has been submitted. Please check your email for instructions to reset your password.');	
		},
		error: function(data) {
			console.log(data);			
		}
	});	
}

function resetPassword() {
	$.ajax({
		type: "POST",
		url: "/admin/changepass",
		async: true,
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    dataType: 'json',
		data : JSON.stringify({
			'email' : email,
			'hash' : hash,
			'newpass' : $('input[name=newpass]').val()
		}),
		success: function(data) {
			if (data.user_fname != null) {
				// drop a cookie
				document.cookie = "user_fname=" + data.user_fname;
				window.location.assign('/index.jsp');
			} else {
				$('#errormsg').text('Unable to change your password. Please request another password reset.');
			}
		},
		error: function(data) {
			console.log(data);			
		}
	});	
}

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

//section for updating selectors
function updatePositionsSelect(detail) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getpositions",
		success: function(data)
		{
			positionList = data;
			$.each(data, function (index, value) {
				$('#position_id').append($('<option/>', { 
					value: this.position_id,
					text : this.position_name 
				}));
			});
			if (detail) changePositionTo($('#position_id').val());
		}
	});
}

function updateLocationsSelect(detail) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getlocations",
		success: function(data)
		{
			locationList = data;
			$.each(data, function (index, value) {
				$('#location_id').append($('<option/>', { 
					value: this.location_id,
					text : this.location_name 
				}));
			});
//			if (detail) changeLocationTo($('#location_id').val());
		}
	});
}

function updateSurveysSelect(detail) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getassessments",
		success: function(data)
		{
			surveyList = data;
			$.each(data, function (index, value) {
				$('#asid').append($('<option />', { 
					value: this.survey_asid,
					text : this.survey_name
				}));
			});
			if (detail) changeSurveyTo($('#asid').val());
		}
	});
}

function listSurveysSelect(detail) {
	$.ajax({
		type: "GET",
		async: true,
		url: "/survey/list",
		success: function(data)
		{
			surveyList = data;
			$.each(data, function (index, value) {
				$('#survey_id').append($('<option />', { 
					value: this.survey_id,
					text : this.survey_name
				}));
			});
			if (detail) changeSurveyTo($('#survey_id').val());
		}
	});
}

function initializeDatePicker(callback) {

	var cb = function(start, end, label) {
		$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		$('#fromdate').val(start.format('YYYY-MM-DD'));
		$('#todate').val(end.format('YYYY-MM-DD'));
		callback();
	}

	var optionSet1 = {
			startDate: moment().subtract(29, 'days'),
			endDate: moment(),
			minDate: '01/01/2012',
			maxDate: moment().format('MM/DD/YYYY'),
			dateLimit: {
				days: 365
			},
			showDropdowns: true,
			showWeekNumbers: true,
			timePicker: false,
			timePickerIncrement: 1,
			timePicker12Hour: true,
			ranges: {
				'This Month': [moment().startOf('month'), moment().endOf('month')],
				'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
				'Last 30 Days': [moment().subtract(29, 'days'), moment()],
				'Last 90 Days': [moment().subtract(89, 'days'), moment()],
				'Last 180 Days': [moment().subtract(179, 'days'), moment()]
			},
			opens: 'left',
			buttonClasses: ['btn btn-default'],
			applyClass: 'btn-small btn-primary',
			cancelClass: 'btn-small',
			format: 'MM/DD/YYYY',
			separator: ' to ',
			locale: {
				applyLabel: 'Submit',
				cancelLabel: 'Clear',
				fromLabel: 'From',
				toLabel: 'To',
				customRangeLabel: 'Custom',
				daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
				monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
				firstDay: 1
			}
	};
	$('#reportrange span').html(moment().subtract(89, 'days').format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
	$('#fromdate').val(moment().subtract(89, 'days').format('YYYY-MM-DD'));
	$('#todate').val(moment().format('YYYY-MM-DD'));

	$('#reportrange').daterangepicker(optionSet1, cb);

	return;
}


//Section for inviting new applicants
function inviteApplicant() {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/inviteapplicant",
		data: $('#inviteapplicant').serialize(),
		beforeSend: function(data) {
			$("#inviteapplicant :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			$('#inviteapplicant').trigger('reset');
			$('#invitationform').addClass('hidden');
			$('#invitationsent').removeClass('hidden');
		},
		complete: function(data) {
			$("#inviteapplicant :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
	return false; // so as not to trigger actual action.
}

function resetInvitation() {
	$('#invitationsent').addClass('hidden');
	$('#invitationform').removeClass('hidden');	
}

function exportSurvey() {
	$.ajax({
		type: "GET",
		async: true,
		url: "/survey/definition",
		data: $('#exportsurvey').serialize(),
		beforeSend: function(data) {
			$("#exportsurvey :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			$('#exportsurvey').trigger('reset');
			$('#exportsurveyform').addClass('hidden');
			$('#surveyexported').removeClass('hidden');
			
			$('#surveydefinition').text(JSON.stringify(data));		
		},
		complete: function(data) {
			$("#exportsurvey :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
	return false; // so as not to trigger actual action.
}

function resetExport() {
	$('#surveyexported').addClass('hidden');
	$('#exportsurveyform').removeClass('hidden');
	$('#surveydefinition').text('');
}

function resetPersistence() {
	$('#surveypersisted').addClass('hidden');
	$('#persistsurveyform').removeClass('hidden');
	$('#persistenceresults').text('');
}

function persistSurvey() {
	$.ajax({
		type: "POST",
		async: true,
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    dataType: 'json',		
		url: "/survey/definition",
		data: $('#inputsurveydefinition').val(),
		beforeSend: function(data) {
			$("#persistsurvey :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			$('#persistsurvey').trigger('reset');
			$('#persistsurveyform').addClass('hidden');
			$('#surveypersisted').removeClass('hidden');
			console.log(data);
			if(data.message != null) {
				$('#persistenceresults').text(data.message);
			}
		},	
		complete: function(data) {
			$("#persistsurvey :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
	return false; // so as not to trigger actual action.
}

// Corefactor migrations

function exportCorefactors() {
	$.ajax({
		type: "GET",
		async: true,
		url: "/survey/corefactor",
		//data: $('#exportsurvey').serialize(),
		beforeSend: function(data) {
			$("#exportcf :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			//$('#exportcf').trigger('reset');
			$('#exportcfform').addClass('hidden');
			$('#cfexported').removeClass('hidden');
			
			$('#cfdefinition').text(JSON.stringify(data));		
		},
		complete: function(data) {
			$("#exportcf :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
	return false; // so as not to trigger actual action.
}

function resetCfExport() {
	$('#cfexported').addClass('hidden');
	$('#exportcfform').removeClass('hidden');
	$('#cfdefinition').text('');
}

function resetCfPersistence() {
	$('#cfpersisted').addClass('hidden');
	$('#persistcfform').removeClass('hidden');
	$('#cfpersistenceresults').text('');
}

function persistCorefactors() {
	$.ajax({
		type: "POST",
		async: true,
		headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
	    dataType: 'json',		
		url: "/survey/corefactor",
		data: $('#inputcfdefinition').val(),
		beforeSend: function(data) {
			$("#persistcf :input").prop('readonly', true);
			$("#spinner").removeClass('hidden');
		},
		success: function(data)
		{
			$('#persistcf').trigger('reset');
			$('#persistcfform').addClass('hidden');
			$('#cfpersisted').removeClass('hidden');
			console.log(data);
			if(data.message != null) {
				$('#cfpersistenceresults').text(data.message);
			}
		},	
		complete: function(data) {
			$("#persistcf :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
	return false; // so as not to trigger actual action.
}

//Section for search respondants / build respondants table
function initRespondantsTable() {
	var rTable = $('#respondants').DataTable( {
		responsive: true,
		order: [[ 0, 'desc' ]],
		columns: [
		          { className: 'text-center', responsivePriority: 1, title: 'Score', 
		        	  data: 'respondant_profile_icon', 
		        	  render : function ( data, type, row ) {
		        		  return '<div class="profilemini ' + row.respondant_profile_class +
		        		  '"><i class="fa '+ data + '"></i></div>';
		        	  }
		          },
		          { responsivePriority: 2, className: 'text-left', title: 'First Name', data: 'respondant_person_fname'},
		          { responsivePriority: 3, className: 'text-left', title: 'Last Name', data: 'respondant_person_lname'},
		          { responsivePriority: 6, className: 'text-left', title: 'Email', data: 'respondant_person_email'},
		          { responsivePriority: 7, className: 'text-left', title: 'Position', data: 'respondant_position_name'},
		          { responsivePriority: 8, className: 'text-left', title: 'Location', data: 'respondant_location_name'}
		          ]
	});
	$.fn.dataTable.ext.errMode = 'none';
	updateRespondantsTable();
}

function updateRespondantsTable() {

	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getrespondants",
		data: $('#refinequery').serialize(),
		beforeSend: function() {
			$("#waitingmodal").removeClass("hidden");
			rTable = $('#respondants').DataTable();
			rTable.clear();
		},
		success: function(data)
		{
			rTable = $('#respondants').DataTable();
			if (data.length > 0) {
				$('#respondants').dataTable().fnAddData(data);
				rTable.$('tr').click(function (){
					rTable.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
					var respondant = $('#respondants').dataTable().fnGetData(this);
					showApplicantScoring(respondant);
				});
				rTable.on('click', 'i', function (){
					var respondant = rTable.row($(this).parents('tr')).data();
					window.location.assign('/respondant_score.jsp?&respondant_id='+respondant.respondant_id);
				});
			}
		},
		complete: function() {
			$("#waitingmodal").addClass("hidden");
		}
	});
}

//Section for looking at / manipulating surveys
function changeSurveyTo(asid) {
	$(surveyList).each(function(li) {
		if (asid == this.survey_asid) {
			updateSurveyFields(this);
			updateSurveyQuestions(this);
		}		
	});
}

function updateSurveyFields(survey) {
	console.log(survey);
	$('#assessmentname').text(survey.survey_name);
	$('#assessmenttime').text(msToTime(survey.survey_completion_time));
	$('#assessmentdesc').html(survey.survey_description);
	$('#completionguage').data('easyPieChart').update(100*survey.survey_completion_pct);  
	$('#questiontotal').text(survey.questions.length);
	function msToTime(s) {
		  var ms = s % 1000;
		  s = (s - ms) / 1000;
		  var secs = s % 60;
		  s = (s - secs) / 60;
		  var mins = s % 60;
		  return + mins + ':' + (secs<10 ? '0':'') + secs;
	}	
}

function initSurveyQuestionsTable() {
	qTable = $('#questions').DataTable( {
		responsive: true,
		order: [[0, 'asc'],[ 1, 'asc' ]],
		columns: [{ title: 'Sec', data: 'question_page'},
		          { title: '#', data: 'question_sequence'},
		          { title: 'Question', data: 'question_text'}],
		          columnDefs: [{ responsivePriority: 2, targets: 2},
		                       { responsivePriority: 4, targets: 1},
		                       { responsivePriority: 6, targets: 0}]
	});
}	

function updateSurveyQuestions(survey) {
	if (qTable == null) initSurveyQuestionsTable();
	qTable.clear();
	$('#questions').dataTable().fnAddData(survey.questions);
	qTable.$('tr').click(function (){
		qTable.$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
	});
	return
}

function updateDash() {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/updatedash",
		data: $('#refinequery').serialize(),
		success: function(data)
		{
			$('#invitecount').html(data.totalinvited);
			$('#completedcount').html(data.totalcompleted);
			$('#scoredcount').html(data.totalscored);	
			$('#hiredcount').html(data.totalhired);

			refreshDashApplicants(data.applicantData);
			refreshDashHires(data.hireData);
			refreshProgressBars(data.applicantData, data.hireData);
			updateHistory(getHistoryData());
		}
	});

}


function refreshDashApplicants(dataApplicants) {
	if (dashApplicants != null) dashApplicants.destroy();
	// Build Applicants Widget
	dashApplicants = new Chart($("#dashApplicants").get(0).getContext("2d"), {
		type: 'doughnut',
		data: dataApplicants,
		options: {
			cutoutPercentage : 35,
			responsive : true,
			legend: { display: false }
		}});
}

function refreshDashHires(dataHires) {
	if (dashHires != null) {
		dashHires.destroy();
		dashHires = null;
	}

	// Build Hires Widget
	dashHires = new Chart($("#dashHires").get(0).getContext("2d"), {
		type: 'doughnut', 
		data: dataHires, 
		options: {
			cutoutPercentage : 35,
			responsive : true,
			animation : { onProgress : function (chart){
				var ctx = chart.chartInstance.chart.ctx;
				var total = 0;
				ctx.fillText(total, ctx.width/2 - 20, ctx.width/2, 200);

			}},	
			legend: { display: false }
	}});
}

function refreshProgressBars(dataApplicants, dataHires) {
	var rate;

	rate = Math.round(100*dataHires.datasets[0].data[1] / dataApplicants.datasets[0].data[1]);
	$('#risingstarbar').attr('aria-valuenow',rate);
	$('#risingstarbar').attr('style','width:'+rate+'%;');
	$('#risingstarrate').html(rate + '%');

	rate = Math.round(100*dataHires.datasets[0].data[2] / dataApplicants.datasets[0].data[2]);
	$('#longtimerbar').attr('aria-valuenow',rate);
	$('#longtimerbar').attr('style','width:'+rate+'%;');
	$('#longtimerrate').html(rate + '%');

	rate = Math.round(100*dataHires.datasets[0].data[3] / dataApplicants.datasets[0].data[3]);
	$('#churnerbar').attr('aria-valuenow',rate);
	$('#churnerbar').attr('style','width:'+rate+'%;');
	$('#churnerrate').html(rate + '%');

	rate = Math.round(100*dataHires.datasets[0].data[4] / dataApplicants.datasets[0].data[4]);
	$('#redflagbar').attr('aria-valuenow',rate);
	$('#redflagbar').attr('style','width:'+rate+'%;');
	$('#redflagrate').html(rate + '%');

}

function updateHistory(historyData) {
	var dashHistory = $("#dashHistory").get(0).getContext("2d");
	historyChart = new Chart(dashHistory, {
		type: 'bar', data: historyData,
		options: { 
			bar: {stacked: true},
			scales: { 
				xAxes: [{
					gridLines: {color : "rgba(0, 0, 0, 0)"},
					stacked: true,
					categoryPercentage: 0.5
				}],
				yAxes: [{gridLines: {display: true}, scaleLabel: {fontSize: '18px'}, stacked: true}]
			},
			responsive: true,
			legend: { display: false }
		}
	});
	return;
}

function showApplicantScoring(applicantData) {
	renderAssessmentScore(applicantData.scores);
	refreshPositionTenure(getPositionTenureData()); // use stub code
}

function changePositionTo(pos_id) {
	$(positionList).each(function() {
		if (pos_id == this.position_id) {
			updatePositionDetails(this);
			updatePositionTenure(this);
			updatePositionProfile(this);
		}		
	});
}

function updatePositionDetails(position) {
	$('#positionname').text(position.position_name);
	$('#positiondesc').text(position.position_description);
	position.position_corefactors.sort(function(a,b) {
		return a.corefactor_display_group.localeCompare(b.corefactor_display_group);
	});
	
	$('#corefactorlist').empty();
	$(position.position_corefactors).each(function () {
		var row = $('<tr/>');
		row.append($('<td />',{ text : this.corefactor_name }));
		row.append($('<td />',{ text : this.corefactor_description }));
		row.append($('<td />',{ text : this.corefactor_display_group }));		
		$('#corefactorlist').append(row);
	});
}

function updatePositionTenure(position) {
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

function updatePositionProfile(position) {
	var dataPositionProfile = getApplicantProfileData(); // use stub code
	positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	var dataPosProfile = {
			datasets : [{
				backgroundColor : position.position_profiles[0].profile_overlay,
				borderColor : position.position_profiles[0].profile_color,
				label : position.position_profiles[0].profile_name,
				pointBackgroundColor : position.position_profiles[0].profile_color,
				pointBorderColor : position.position_profiles[0].profile_color,
				pointHoverBackgroundColor : position.position_profiles[0].profile_highlight,
				pointHoverBorderColor : position.position_profiles[0].profile_highlight,
				data : []
			}],
			labels : []};
		
	$(position.position_corefactors).each(function (i) {
		dataPosProfile.labels[i] = this.corefactor_name;
		dataPosProfile.datasets[0].data[i] = this.pm_score_a;
	});
	

	profileChart = new Chart(positionProfile, {type: 'radar', data: dataPosProfile, options: { 
		scale: {
                ticks: {
                    beginAtZero: true
                },
                pointLabels : {
                	fontSize : 16
                }
        },
		legend: { display: false }
	}});	
}

function refreshPositionProfile(dataPositionProfile) {
	positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	profileChart = new Chart(positionProfile, {type: 'radar', data: dataPositionProfile, options: { 
		showScale: true,
		responsive : true,
		defaultFontSize: 16,
		legend: { display: false }
	}});	
}


//Payroll tools section
function uploadPayroll(e) {
	$('#csvFile').parse({
		config : {
			header: true,
			dynamicTyping: true,
			complete: function(results, file) {
				$('#payroll').DataTable( {
					responsive: true,
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


//Respondant scoring section
function getScore(respondantId) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getscore",
		data: {
			"respondant_id" : respondantId   	
		},
		success: function(data)
		{
			respondant = data.respondant;
			presentRespondantScores(data);
		}
	});    
}

//Respondant scoring section
function getScoreUuid(respondantUuid) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getscore",
		data: {
			"respondant_uuid" : respondantUuid   	
		},
		success: function(data)
		{
			respondant = data.respondant;
			presentRespondantScores(data);
		}
	});    
}

function presentRespondantScores(dataScores) {
	$('#candidatename').text(respondant.respondant_person_fname + ' ' + respondant.respondant_person_lname);
	$('#candidateemail').text(respondant.respondant_person_email);
	$('#candidateaddress').text(respondant.respondant_person_address);
	$('#candidateposition').text(respondant.respondant_position_name);
	$('#candidatelocation').text(respondant.respondant_location_name);
	$('#assessmentname').text(respondant.respondant_survey_name);
	$('#assessmentdate').text(respondant.respondant_created_date);

	detailedScores = dataScores.detailed_scores;
	renderDetailedAssessmentScore();
	renderPredictionElements(dataScores.scores);
	renderPredictionChart(dataScores.scores);
}

function showAllDetails() {
	for (i in detailedScores) {
		var score = detailedScores[i];
		showDetail(score.corefactor_id);
	}
	$('#hideall').removeClass('hidden');
	$('#showall').addClass('hidden');
}

function hideAllDetails() {
	for (i in detailedScores) {
		var score = detailedScores[i];
		hideDetail(score.corefactor_id);
	}	
	$('#showall').removeClass('hidden');
	$('#hideall').addClass('hidden');
}

function showDetail(cfid) {
	$('#cfmessage_' + cfid).removeClass('hidden');	
	$('#expander_' + cfid).attr('onclick', 'hideDetail('+cfid+')');
	$('#expander_' + cfid).removeClass('fa-plus-square-o');
	$('#expander_' + cfid).addClass('fa-minus-square-o');
}


function hideDetail(cfid) {
	$('#cfmessage_' + cfid).addClass('hidden');
	$('#expander_' + cfid).attr('onclick', 'showDetail('+cfid+')');
	$('#expander_' + cfid).removeClass('fa-minus-square-o');
	$('#expander_' + cfid).addClass('fa-plus-square-o');
}

function renderDetailedAssessmentScore() {
	$('#assessmentresults').empty();
	detailedScores.sort(function(a, b) {
	    return a.corefactor_display_group.localeCompare(b.corefactor_display_group);
	});
	var displaygroup = "";
	
	for (var i in detailedScores) {
		var score = detailedScores[i];
		if (displaygroup != score.corefactor_display_group) {
			displaygroup = score.corefactor_display_group;
			var grouprow = $('<tr />');
			grouprow.append($('<th />', {'style':'text-align:center;'}).append($('<h4 />',{text:displaygroup})));
			$('#assessmentresults').append(grouprow);
		}
		var row = $('<tr />', {
			'title' : score.corefactor_description});
		var namediv = $('<div />', {
			'class' : 'col-xs-10 col-sm-8 col-md-6 col-lg-6 text-left',
			title: score.corefactor_description});
		var expander = $('<h5 />');
		expander.append($('<strong />', { text : score.corefactor_name + ' '}));
		expander.append($('<i />', {
			'onclick' : "showDetail(" + score.corefactor_id + ")",
			'class' : 'fa fa-plus-square-o',
			'id' : 'expander_' + score.corefactor_id
		}));
		namediv.append(expander);
		var scorediv = $('<div />', {
			'class' : 'col-xs-2 col-sm-4 col-md-6 col-lg-6 text-right', 
			html : '<h5><strong>' + score.cf_score + "/" + score.corefactor_high + '</strong></h5>'});
		var lowdesc = $('<div />', {
			'class' : 'hidden-xs col-sm-3 col-md-2 col-lg-2 text-left', 
			html : '<h6><em>' +score.corefactor_low_desc + '</em></h6>'});
		var highdesc = $('<div />', {
			'class' : 'hidden-xs col-sm-3 col-md-2 col-lg-2 text-right', 
			html : '<h6><em>' +score.corefactor_high_desc + '</em></h6>'});
		var progress = $('<div />', {'class' : 'progress col-xs-12 col-sm-6 col-md-8 col-lg-8'
				}).append($('<div />', {
			'class': 'progress-bar progress-bar-success progress-bar-striped',
			'role': 'progressbar',
			'aria-valuenow' : score.cf_score,
			'aria-valuemin' : "1",
			'aria-valuemax' : "11",
			'style' : 'width: ' + (100*score.cf_score/score.corefactor_high) + '%;'
		}));

		var tablecell = $('<td />');
		tablecell.append(namediv);
		tablecell.append(scorediv);
		tablecell.append(lowdesc);
		tablecell.append(progress);
		tablecell.append(highdesc);
		row.append(tablecell);
		$('#assessmentresults').append(row);
		var messageRow = $('<tr />',{
			'id' : 'cfmessage_' + score.corefactor_id,
			'class' : 'hidden'
		}).append($('<td />',{
			'bgcolor' : '#F7F7F7',
			'border-top' : 'none',
			'text' : prepPersonalMessage(score.cf_description)
			}));
		$('#assessmentresults').append(messageRow);
		

	}
	
}

function prepPersonalMessage (message) {
	var pm = message;
	pm = pm.replace(new RegExp("\\[FNAME\\]","g"),respondant.respondant_person_fname);
	pm = pm.replace(new RegExp("\\[LNAME\\]","g"),respondant.respondant_person_lname);

	pm = pm.replace(new RegExp("\\[CHESHE\\]","g"),"This candidate");
	pm = pm.replace(new RegExp("\\[LHESHE\\]","g"),"this candidate");
	pm = pm.replace(new RegExp("\\[CHIMHER\\]","g"),"Him or her");
	pm = pm.replace(new RegExp("\\[LHIMHER\\]","g"),"him or her");
	pm = pm.replace(new RegExp("\\[HIMHER\\]","g"),"him or her");
	pm = pm.replace(new RegExp("\\[CHISHER\\]","g"),"His or her");
	pm = pm.replace(new RegExp("\\[LHISHER\\]","g"),"his or her");
	pm = pm.replace(new RegExp("\\[HIMSELFHERSELF\\]","g"),"him or herself");

	return pm;
}

function renderAssessmentScore(scores) {

	$('#assessmentresults').empty();
	for (var key in scores) {
		var row = $('<tr />');
		row.append($('<th />', {'style':'width:100px;', 'text': key }))
		var progress = $('<div />', {'class' : 'progress'}).append($('<div />', {
			'class': 'progress-bar progress-bar-success progress-bar-striped',
			'role': 'progressbar',
			'aria-valuenow' : scores[key],
			'aria-valuemin' : "1",
			'aria-valuemax' : "11",
			'style' : 'width: ' + scores[key]/0.11 + '%;',
			'text' : scores[key]
		})); 
		row.append($('<td />').append(progress));
		$('#assessmentresults').append(row);
	}

}

function renderPredictionElements(scores) {
	$('#corefactorsused').empty();
	var title = $('<div />', {
		'class' : 'form-group'
	});
	title.append($('<h4 />', {
		'class' : 'text-center',
		'text' : 'Corefactors'
	}));
	$('#corefactorsused').append(title);
	for (var key in scores) {
		var group = $('<div />', {
			'class' : 'form-group'
		});

		group.append($('<label />', {
			'class' : 'control-label col-md-6 col-sm-6 col-xs-6',
			'text' : key
		}));

		group.append($('<div />',{
			'class':'col-md-6 col-sm-6 col-xs-6'
		}).append($('<input />',{
			'class':'form-control text-right',
			'disabled' : true,
			'value' : scores[key],
			'type' : 'text'
		})));

		$('#corefactorsused').append(group);
	}
}

function renderPredictionChart(scores) {
	$('#candidateicon').html('<i class="fa ' + respondant.respondant_profile_icon +'"></i>');
	$('#candidateicon').addClass(respondant.respondant_profile_class);

	refreshPositionTenure(getPositionTenureData()); // use stub code
}


function lookupLastTenCandidates() {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getlastten",
		data: $('#refinequery').serialize(),
		success: function(respondants)
		{
			$('#recentcandidates').empty();
			for (var i = 0; i < respondants.length; i++ ) {
				var li = $('<li />', { 'class' : 'media event' });

				var div = $('<div />', {
					'class' : respondants[i].respondant_profile_class + ' profilebadge' 
				}).append($('<i />', {'class' : "fa " + respondants[i].respondant_profile_icon }));

				var ico = $('<a />', {
					'class' : "pull-left",
					'href' : '/respondant_score.jsp?&respondant_id=' + respondants[i].respondant_id
				}).append(div);

				var badge = $('<div />', { 'class' : 'media-body' });
				$('<a />', {
					'class' : 'title',
					'href' : '/respondant_score.jsp?&respondant_id=' + respondants[i].respondant_id,
					'text' : respondants[i].respondant_person_fname + ' ' + respondants[i].respondant_person_lname
				}).appendTo(badge);
				$('<p />', {
					'text' : respondants[i].respondant_position_name
				}).appendTo(badge);
				$('<p />', {
					'html' : '\<small\>' + respondants[i].respondant_location_name + '\<\/small\>'
				}).appendTo(badge);

				li.append(ico);
				li.append(badge);
				$('#recentcandidates').append(li);
			}}});
}