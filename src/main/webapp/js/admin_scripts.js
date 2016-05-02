//Useful Global Variables
var tenureChart;
var profileChart;
var respondantProfile;
var historyChart;

var respondant;
var surveyList;
var positionList;
var locationList;
var qTable;

//basic user / account functions (login/logout/etc)
function login() {
	var redirectTo = $('#topage').value;
	$.ajax({
		type: "POST",
		async: true,
		data : $('#loginform').serialize(),
		url: "/admin/login",
		xhrFields: {
            withCredentials: true
        },
		success: function(data) {
			window.location.assign(redirectTo);
		},
		error: function(data) {
			console.log(data);			
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
			console.log(data);
			window.location.assign('/login.jsp');
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


// section for updating selectors
function updatePositionsSelect() {
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
			if ($('#position_id').val() != -1) changePositionTo($('#position_id').val());
		}
	});
}

function updateLocationsSelect() {
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
//			if ($('#location_id').val() != -1) changeLocationTo($('#location_id').val());
		}
	});
}

function updateSurveysSelect() {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/getassessments",
		success: function(data)
		{
			surveyList = data;
			$.each(data, function (index, value) {
				$('#survey_id').append($('<option />', { 
					value: this.survey_id,
					text : this.survey_name
				}));
			});
			if ($('#survey_id').val() != -1) {
				changeSurveyTo($('#survey_id').val());
			}
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


// Section for inviting new applicants
function inviteApplicant(e) {
	$.ajax({
		type: "POST",
		async: true,
		url: "/admin/inviteapplicant",
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
		},
		complete: function(data) {
			$("#inviteapplicant :input").prop('readonly', false);
			$("#spinner").addClass('hidden');
		}
	});
}

function resetInvitation() {
	$('#invitationsent').addClass('hidden');
	$('#invitationform').removeClass('hidden');	
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
		},
		complete: function() {
			$("#waitingmodal").addClass("hidden");
		}
	});
}

//Section for looking at / manipulating surveys
function changeSurveyTo(survey_id) {
	$(surveyList).each(function(li) {
		if (survey_id == this.survey_id) {
			updateSurveyFields(this);
			updateSurveyQuestions(this);
		}		
	});
}

function updateSurveyFields(survey) {
	$('#surveyname').text(survey.survey_name);
	$('#questiontotal').text(survey.questions.length);
}

function initSurveyQuestionsTable(survey) {
	qTable = $('#questions').DataTable( {
		responsive: true,
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
	// Build Applicants Widget
	var dashApplicants = $("#dashApplicants").get(0).getContext("2d");
	var appDoughnutChart = new Chart(dashApplicants, {
		   type: 'doughnut',
		   data: dataApplicants,
		   options: {
			   cutoutPercentage : 35,
			   responsive : true,
			   legend: { display: false }
		   }});
}

function refreshDashHires(dataHires) {
	// Build Hires Widget
	var dashHires = $("#dashHires").get(0).getContext("2d");
	new Chart(dashHires, {
		type: 'doughnut', 
		data: dataHires, 
		options: {
		   cutoutPercentage : 35,
		   responsive : true,
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
	var mydata = new Array();
	var mylabels = new Array();
	var scores = applicantData.scores;
	var i=0;
	for (var key in scores) {
		if (scores.hasOwnProperty(key)) {
			mylabels[i] = key;
			mydata[i] = applicantData.scores[key];
			i++;
		}
	}

	refreshPositionProfile({
		labels: mylabels,
		datasets: [
		        {
		            label: applicantData.respondant_person_fname + ' ' + applicantData.respondant_person_lname,
		            backgroundColor: applicantOverlay,
		            borderColor: applicantColor,
		            pointBackgroundColor: applicantHighlight,
		            pointBorderColor: applicantColor,
		            pointHoverBackgroundColor: applicantColor,
		            pointHoverBorderColor: applicantHighlight,
		            data: mydata
		        }
		    ]
		});

	refreshPositionTenure(getPositionTenureData()); // use stub code
}

function changePositionTo(pos_id) {

	updatePositionTenure(pos_id);
	updatePositionProfile(pos_id);
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


// Respondant scoring section
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
			data.position = getPositionDetails(data.scores);
			refreshRespondantProfile(data);
			createProfileSquares(data);
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
					$('.square').removeClass("selected");
					$(this).addClass("selected");
					
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
	respondant = dataScores.respondant;
	$('#candidatename').text(respondant.respondant_person_fname + ' ' + respondant.respondant_person_lname);
	$('#candidateemail').text(respondant.respondant_person_email);
	$('#candidateaddress').text(respondant.respondant_person_address);
	$('#candidateposition').text(respondant.respondant_position_name);
	$('#candidatelocation').text(respondant.respondant_location_name);
	$('#candidateicon').html('<i class="fa ' + respondant.respondant_profile_icon +'"></i>');
	$('#candidateicon').addClass(respondant.respondant_profile_class);

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
