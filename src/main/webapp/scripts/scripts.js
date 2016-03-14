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
    postToSurvey($(formname)); 
}
