$(document).ready(function () {
    $('#maincontent').css('marginTop', $('#header').outerHeight(true)+ 2 );
    $('#maincontent').css('marginBottom', $('#footer').outerHeight(true)+ 2 );
});

function expand(div) {
	var pane = document.getElementById(div);
	pane.className='collapsable';
	pane.onclick='';
}

function collapse(div) {
	var pane = document.getElementById(div);
	pane.onclick=function(){reset(div);};
	pane.className='expandable';
}

function reset(div) {
	var pane = document.getElementById(div);
	pane.onclick=function(){expand(div);};	
}

function postToSurvey(e) {
    var url = "/response";
    var response = "{}";
    var jResp = JSON.parse(response);
    $.ajax({
           type: "POST",
           async: true,
           url: url,
           data: $(e).serialize(), 
           success: function(data)
           {
              response = data;
              jResp = JSON.parse(response);
              var field = '#qr' + jResp.response_question_id;
              $(field).val(jResp.response_id);
              mySwipe.next();  
           }
         });
    
    
    return response;
}

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
           }
         });


    return response;
}


function submitAnswer(formname) {
    //var balance = parseInt($(zone).html()) + parseInt(points);
    //$('#award_amount').val(points);
    //$('#award_kid_id').val(kid);    
    postToSurvey($(formname)); 
    //$(zone).html(balance);
}

function awardTask(kid, task, points, date) {
    var url = "/mp";
    var zone = "#dropzone_" + kid;
    var image = "#img_" + kid + "_" + task + "_" + date;
    var balance = parseInt($(zone).html()) + parseInt(points);
    $('#award_amount').val(points);
    $('#award_task_id').val(task);
    $('#award_kid_id').val(kid);
    $('#award_date').val(date);
    $(image).attr('src','/images/wait.gif');
    $(image).attr('onclick','');
    $.ajax({
           type: "POST",
           async: true,
           url: url,
           data: $('#awardme').serialize(), 
           success: function()
           {
              $(zone).html(balance);
              $('#award_task_id').val('');
              $(image).attr('src','/images/checked.jpg');
              $(image).click(function(){clearAward(kid, task, points, date);});
           },
           error: function(data) {
        	  $(image).attr('src','/images/unchecked.jpg');
        	  $('#award_task_id').val('');
        	  alert("An error occurred: " + data);
           }
    });
    return true;
}

function timeZoneDetect(){
    return new Date().getTimezoneOffset()/-60;
}