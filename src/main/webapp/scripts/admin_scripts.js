// new jquery plugin for datatables;
jQuery.fn.dataTableExt.oApi.fnProcessingIndicator = function ( oSettings, onoff )
{
    if ( onoff === undefined ) {
        onoff = true;
    }
    this.oApi._fnProcessingDisplay( oSettings, onoff );
};


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
              response = data;
              $("#form_response").html(data);
           }
         });

    return response;
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

function initRespondantsTable() {
	  rTable = $('#respondants').DataTable( {
            //responsive: true,
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

function timeZoneDetect(){
    return new Date().getTimezoneOffset()/-60;
}

function updateDash() {   
    refreshDashCharts({
		dataApplicants: getApplicantData(),
		dataInterviews: getInterviewData(),
		dataHires: getHireData(),
		dataTurnover: getTurnoverData()
	}); //use stub code
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
    refreshPositionTenure(getPositionTenureData()); //use stub code	
    refreshPositionProfile(getApplicantProfileData()); //use stub code		
}

function updatePositionTenure(pos_id) {
    refreshPositionTenure(getPositionTenureData()); //use stub code	
}

function refreshPositionTenure(dataPositionTenure) {
	var positionTenure = $("#positionTenure").get(0).getContext("2d");
	new Chart(positionTenure).Bar(dataPositionTenure, { 
			showScale: true,
			scaleShowGridLines: false,
			responsive : true
		});	
}

function updatePositionProfile(pos_id) {
    refreshPositionProfile(getApplicantProfileData()); //use stub code	
}

function refreshPositionProfile(dataPositionTenure) {
	var positionProfile = $("#positionProfile").get(0).getContext("2d");
	new Chart(positionProfile).Radar(dataPositionTenure, { 
			showScale: true,
			responsive : true
		});	
}
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
              console.log(jResp);
              refreshRespondantProfile(jResp);
           }
         });    
 }

function refreshRespondantProfile(dataScores) {
	
	var scores = dataScores.scores;
	var respondant = dataScores.respondant;
	var labels = new Array();
	var data = new Array();
	var scorecount=0;
	
	for (var key in scores) {
		  if (scores.hasOwnProperty(key)) {
			  labels[scorecount] = key;
			  data[scorecount] = scores[key];
			  scorecount++;
		  }
	}
	
	var respondantData = {
			labels: labels,
			datasets: [
		        {
		            label: "Applicant",
		            fillColor: "rgba(220,220,220,0.3)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
		            data: data
		        },
		        {
			            label: "Red Flag",
			            fillColor: "rgba(191, 63, 63,0.0)",
			            strokeColor: "rgba(191, 63, 63,1)",
			            pointColor: "rgba(191, 63, 63,1)",
			            pointStrokeColor: "#BF3F3F",
			            pointHighlightFill: "#BF3F3F",
			            pointHighlightStroke: "rgba(191, 63, 63,1)",
			            data: [ 3, 2, 4, 4, 1]
		        },
		        {
			            label: "Churner",
			            fillColor: "rgba(191, 191, 63, 0.0)",
			            strokeColor: "rgba(191, 191, 63, 1)",
			            pointColor: "rgba(191, 191, 63, 1)",
			            pointStrokeColor: "#BFBF3F",
			            pointHighlightFill: "#BFBF3F",
			            pointHighlightStroke: "rgba(191, 191, 63, 1)",
			            data: [ 10, 8, 8, 7, 5]
		        },
		        {
			            label: "Long Timer",
			            fillColor: "rgba(63, 127, 191, 0.0)",
			            strokeColor: "rgba(63, 127, 191, 1)",
			            pointColor: "rgba(63, 127, 191, 1)",
			            pointStrokeColor: "#3F7FBF",
			            pointHighlightFill: "#3F7FBF",
			            pointHighlightStroke: "rgba(63, 127, 191, 1)",
			            data: [ 13, 14, 12, 10, 10]
		        },
		        {
			            label: "Rising Star",
			            fillColor: "rgba(61,191,63,0.0)",
			            strokeColor: "rgba(61,191,63,1)",
			            pointColor: "rgba(61,191,63,1)",
			            pointStrokeColor: "#3FBF3F",
			            pointHighlightFill: "#3FBF3F",
			            pointHighlightStroke: "rgba(61,191,63,1)",
			            data: [ 15, 15, 15, 15, 14]
		        }]
	};
		
	var respondantProfile = $("#respondantProfile").get(0).getContext("2d");
	new Chart(respondantProfile).Radar(respondantData, { 
			showScale: true,
			responsive : true
		});	
	
    refreshPositionTenure(getPositionTenureData()); //use stub code	
}

// Stub Functions to be removed
function getApplicantData(){
	return [
            {
                value: 100,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 300,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 250,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 20,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getInterviewData(){
	return [
            {
                value: 5,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 150,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 180,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 18,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getHireData(){
	return [
            {
                value: 2,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 75,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 125,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 15,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getTurnoverData() {
	return {
	    labels: ["Projection", "Target"],
	    datasets: [
	        {
	            label: "Months",
	            fillColor: "rgba(120,220,220,0.5)",
	            strokeColor: "rgba(120,220,220,0.8)",
	            highlightFill: "rgba(120,220,220,0.75)",
	            highlightStroke: "rgba(120,220,220,1)",
	            data: [8.8, 6]
	        }
	    ]
	};
}

function getHistoryData() {
	return {
	    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
	    datasets: [
	        {
	            label: "Hire Ratio",
	            fillColor: "rgba(120,220,220,0.5)",
	            strokeColor: "rgba(120,220,220,0.8)",
	            highlightFill: "rgba(120,220,220,0.75)",
	            highlightStroke: "rgba(120,220,220,1)",
	            data: [6.1,6.0,6.0,5.8,6.1,4.5,4.3,3.9,3.5,3.2,3.1,3.2]
	        },
	        {
	            label: "Projected Tenure",
	            fillColor: "rgba(120,120,220,0.5)",
	            strokeColor: "rgba(120,120,220,0.8)",
	            highlightFill: "rgba(120,120,220,0.75)",
	            highlightStroke: "rgba(120,120,220,1)",
	            data: [6.1,6.0,6.0,5.8,6.1,7.5,7.3,7.9,7.5,8.2,8.1,2.2]
	        }

	    ]
	};
}

function getPositionTenureData() {
	return {
	    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
	    datasets: [
	        {
	            label: "count",
	            fillColor: "rgba(220,220,220,0.2)",
	            strokeColor: "rgba(220,220,220,1)",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(220,220,220,1)",
	            data: [45,15,25,40,45,55,65,50,40,30,20,15,13,14,11,10,9]
	        }
	    ]
	};
}

function getApplicantProfileData() {
	return {
	    labels: ["Openness", "Conscientousness", "Confidence", "Sociability", "Compassion"],
	    datasets: [
	        {
	            label: "Applicant",
	            fillColor: "rgba(220,220,220,0.2)",
	            strokeColor: "rgba(220,220,220,1)",
	            pointColor: "rgba(220,220,220,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(220,220,220,1)",
	            data: [1, 3, 5, 4, 2]
	        },
	        {
	            label: "Average Score",
	            fillColor: "rgba(151,187,205,0.2)",
	            strokeColor: "rgba(151,187,205,1)",
	            pointColor: "rgba(151,187,205,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(151,187,205,1)",
	            data: [4, 3, 4, 2, 1]
	        }
	    ]
	};
}