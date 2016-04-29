//
// Global Variables and actions for the survey page(s)
//

var respondant;
var survey;
var questions;
var pagination;
var totalpages;
var responses;
var progress;
var urlParams; 

(window.onpopstate = function () {
    var match,
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
})();


//
// Post and get functions
//

function submitAnswer(form) {
    $.ajax({
           type: "POST",
           async: true,
           url: "/survey/response",
           data: $(form).serialize(), 
           success: function(data)
           {
              saveResponse(data);
           }
         });    
    mySwipe.next();  
}

function submitPlainAnswer(form, pagenum) {
    $.ajax({
           type: "POST",
           async: true,
           url: "/survey/response",
           data: $(form).serialize(), 
           success: function(data)
           {
              saveResponse(data);
              isPageComplete(pagenum);
           }
         });
}

function buildPlainSurveyWithRespondantId(respondantId) {
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/getsurvey",
        data: {
        	"respondant_id" : respondantId,
        	"noRedirect" : true        	
        },
        beforeSend: function() {
        	$('#wait').removeClass('hidden');
        },
        success: function(data)
        {
           assemblePlainSurvey(data);
        },
        complete: function() {
        	$('#wait').addClass('hidden');
        }
      });
}

function buildSurveyWithRespondantId(respondantId) {
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/getsurvey",
        data: {
        	"respondant_id" : respondantId,
        	"noRedirect" : true        	
        },
        beforeSend: function() {
        	$('#wait').removeClass('hidden');
        },
        success: function(data)
        {
           assembleVisualSurvey(data);
        },
        complete: function() {
        	$('#wait').addClass('hidden');
        }
      });
}

function getPlainSurveyForNewRespondant(form) {
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/order",
        data: $(form).serialize(),
        beforeSend: function() {
        	$('#wait').removeClass('hidden');
        },
        success: function(data)
        {
            console.log(data);
            assemblePlainSurvey(data);
        },
        complete: function() {
        	$('#wait').addClass('hidden');
        }
      });	
}

function getVisualSurveyForNewRespondant(form) {
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/order",
        data: $(form).serialize(),
        beforeSend: function() {
        	$('#wait').removeClass('hidden');
        },
        success: function(data)
        {
            console.log(data);
            assembleVisualSurvey(data);
        },
        complete: function() {
        	$('#wait').addClass('hidden');
        }
      });	
}

//
function createPlainNewRespondant(surveyId, accountId) {
  // code to create a form to fill out for a new survey respondant	
	var deck = document.getElementById('wrapper');
	$(deck).empty();
	var infopage = $('<div />', {});
	
	infopage.append(getHrDiv());
	infopage.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		}).html("<h3>Applicant Info</h3>"));
	infopage.append(getHrDiv());

	var form = $('<form />',{
		'class' : 'form'
	});
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'account_id',
		'value' : accountId
	}));
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'survey_id',
		'value' : surveyId		
	}));

	/* First Name */
	form.append($('<label />', {
		'for' : 'fname',
		'text' : 'First Name:'
	}));
	
	var row = $('<div />', {
		'class' : 'input-group has-feedback'
	});
	row.append($('<span />', {
		'class' : 'input-group-addon'}).html("<i class='fa fa-user fa-fw'></i>"));
	row.append($('<input />', {
		'class' : 'form-control',
		'type' : 'text',
		'name' : 'fname',
		'placeholder' : 'First Name',
		'required' : true				
	}));
	form.append(row);

	/* Last Name */
	form.append($('<label />', {
		'for' : 'lname',
		'text' : 'Last Name:'
	}));
    row = $('<div />', {
			'class' : 'input-group has-feedback'
		});
	row.append($('<span />', {
		'class' : 'input-group-addon'}).html("<i class='fa fa-user fa-fw'></i>"));
	row.append($('<input />', {
		'class' : 'form-control',
		'type' : 'text',
		'name' : 'lname',
		'placeholder' : 'Last Name',
		'required' : true				
	}));
	form.append(row);

	/* Email */	
	form.append($('<label />', {
		'for' : 'email',
		'text' : 'E-mail Address:'
	}));
	row = $('<div />', {
		'class' : 'input-group has-feedback'
	});
	row.append($('<span />', {
	'class' : 'input-group-addon'}).html("<i class='fa fa-envelope fa-fw'></i>"));
	row.append($('<input />', {
		'class' : 'form-control',
		'type' : 'email',
		'name' : 'email',
		'placeholder' : 'email',
		'required' : true		
	}));
	form.append(row);

	/* Home Address */
	form.append($('<label />', {
		'for' : 'address',
		'text' : 'Home Address:'
	}));
	row = $('<div />', {
		'class' : 'input-group has-feedback'
	});
	row.append($('<span />', {
	'class' : 'input-group-addon'}).html("<i class='fa fa-home fa-fw'></i>"));
	row.append($('<input />', {
		'class' : 'form-control',
		'type' : 'text',
		'name' : 'address',
		'id' : 'address',
		'required' : true				
	}));
	form.append(row);
	
	/* Button */
	form.append(getHrDiv());
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'lat',
		'id' : 'lat'
	}));
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'lng',
		'id' : 'lng'				
	}));
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'formatted_address',
		'id' : 'formatted_address'				
	}));
	form.append($('<input />', {
		'type' : 'hidden',
		'name' : 'country_short',
		'id' : 'country_short'				
	}));
	form.append($('<button />', {
		'type' : 'button',
		'class' : 'btn btn-primary',
		'onClick' : 'getPlainSurveyForNewRespondant(this.form);',
		'text' : 'Submit'
	}));

	infopage.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		}).append(form));
	infopage.appendTo(deck);
	
	prepSurvey();
	$('#address').geocomplete({details:'form'});
}

function createVisualNewRespondant(surveyId, accountId) {
	  // code to create a form to fill out for a new survey respondant	
}

//
// Survey page buidling functions
//

function assembleNewRespondantPage(collection) {
var jResp = JSON.parse(data);
console.log(jResp);
}

function assemblePlainSurvey(collection) {

	var deck = document.getElementById('wrapper');
	respondant = collection.respondant;
	survey = collection.survey;
	questions = survey.questions;
	pagination = new Array();
	$(deck).empty();

	var pagecount = 1;
	var qlimit = 5; // questions per page
	totalpages = Math.ceil(questions.length / qlimit) + 1;
	
	var card = $('<div />', {});
	
	card.append(getHrDiv());
	card.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		}).html(getSurveyDisclaimer(survey)));
	card.append(getHrDiv());
	card.append(getSurveyNav(pagecount, totalpages));	
	card.appendTo(deck);
	pagecount++;
	
	var qcount = 0;
	var qpp = 0;
	card = $('<div />', {
		'class' : 'questionpage'
	});
	card.append(getHrDiv());
	pagination[pagecount] = new Array();
	$.each(questions, function(index, question) {
		qcount++;
		if (qpp == qlimit) {
			card.append(getSurveyNav(pagecount, totalpages));	
			card.append($('<div />', {
				'class' : 'col-xs-12 col-sm-12 col-md-12',
				'height' : '75px'}));
			card.appendTo(deck);
			pagecount++;
			pagination[pagecount] = new Array();
			qpp = 0;
			card = $('<div />', {
				'class' : 'questionpage'
			});
			card.append(getHrDiv());				
		}
		var pageqs = pagination[pagecount];
		pageqs[qpp] = question;
		qpp++;
		 var questionrow = $('<div />', {
			 'class' : 'row'
		 }).append(getPlainResponseForm(question, respondant, qcount, pagecount));
		 var widerow = $('<div />', {
			 'class' : 'col-xs-12 col-sm-12 col-md-12'
		 }).append(questionrow);
		 card.append(widerow);
		 card.append(getHrDiv());
	});
	
	card.append(getSurveyNav(pagecount, totalpages));	
	card.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		'height' : '75px'}));
	card.appendTo(deck);

	prepSurvey();
	responses = new Array();
}

function assembleVisualSurvey(collection) {
	var deck = document.getElementById('wrapper');
	respondant = collection.respondant;
	survey = collection.survey;
	questions = survey.questions;

	var card = $('<div />', {});
	var qpanel = $('<div />', {
		 'class' : "qpanel qpanel-default",
		 'style' : "background-image:url('/images/background-1.jpg');"
	});
	var header = $('<div />', {
		 'class' : "qpanel-header text-center",
	}).append($('<h4/>', {	'html': 'Welcome' }));
	var footer = $('<div />', {
		 'class' : "qpanel-footer text-center",
	}).append($('<h4/>', {	'text': 'Swipe Left to Begin  ' }).append($('<i/>',{
		'class': "fa fa-play-circle-o", 
		'onClick':'mySwipe.next();'
		})
	));
	qpanel.append(header);
	qpanel.append(footer);
	card.append(qpanel);
	card.appendTo(deck);
	
	$.each(questions, function(index, question) {
		 card = $('<div />', {});
		 qpanel = $('<div />', {
			 'class' : "qpanel qpanel-default",
			 'style' : "background-image:url('/images/question-"+question.question_display_id+".jpg');"
		 });
		 header = $('<div />', {
			 'class' : "qpanel-header text-center",
		 }).append($('<h4/>', {	'text': question.question_text }));
		 footer = $('<div />', {
			 'class' : "qpanel-footer text-center",
		 }).append(getResponseForm(question, respondant));
		 qpanel.append(header);
		 qpanel.append(footer);
		 card.append(qpanel);
		 card.appendTo(deck);
	});
	
	card = $('<div />', {});
	qpanel = $('<div />', {
		 'class' : "qpanel qpanel-default",
		 'style' : "background-image:url('/images/background-1.jpg');"
	});
	header = $('<div />', {
		 'class' : "qpanel-header text-center",
	}).append($('<h4/>', {	'text': 'Thank You' }));
	footer = $('<div />', {
		 'class' : "qpanel-footer text-center",
	}).append($('<button/>', {
		'class' : 'button btn-block',
		'style' : 'font-family: Comfortaa;font-size: 24px;',
		'type': 'button',
		'text': 'Click to Submit',
		'onClick':'window.location.assign(\"'+'/respondant_score.jsp?&respondant_id='+respondant.respondant_id+'\");'
		}));
	
	qpanel.append(header);
	qpanel.append(footer);
	card.append(qpanel);
	card.appendTo(deck);

	prepSurvey();
	responses = new Array();
}

function prepSurvey() {
	var elem = document.getElementById('survey');
	window.mySwipe = Swipe(elem, {
		callback: function() {
			
		}	
	});
}

function getResponseForm(question, respondant) {
	var form =  $('<form />', {
		 'name' : 'question_'+question.question_id,
		 'action' : "/response"
	 });
	form.append($('<input/>', {
		name : 'response_id',
		type : 'hidden',
		id : 'qr'+question.question_id,
		value : ''
	}));
	form.append($('<input/>', {
		name : 'response_respondant_id',
		type : 'hidden',
		value : respondant.respondant_id
	}));
	form.append($('<input/>', {
		name : 'response_question_id',
		type : 'hidden',
		value : question.question_id
	}));
	
	switch (question.question_type) {
	case 1:
		break;
	case 2:
		var thumbs = $('<div />', {
			'class' : 'thumbs'
		});
		thumbs.append($('<input/>', {
			'class': 'thumbs-up',
			'id': "thumbs-up-" + question.question_id,
			'type': "radio",
			'name': "response_value",
			'onclick': 'submitAnswer(this.form)',
			'value': 11
		}));
		thumbs.append($('<label/>', {
			'class': 'thumbs-up',
			'for': "thumbs-up-" + question.question_id,
			'text': 'Me'			
		}));
		thumbs.append($('<input/>', {
			'class': 'thumbs-down',
			'id': "thumbs-down-" + question.question_id,
			'type': "radio",
			'name': "response_value",
			'onclick': 'submitAnswer(this.form)',
			'value': 1
		}));
		thumbs.append($('<label/>', {
			'class': 'thumbs-down',
			'for': "thumbs-down-" + question.question_id,
			'text': 'Not Me'
		}));
		form.append(thumbs);
		break;
	case 4:
		form.append($('<div />', {
			'class' : 'stars text-center',
			'style' : 'font-size: 18px;',
			'text' : 'Rate on a scale of 1-5'
		}));
		var stars = $('<div />', {
			'class' : 'stars'
		});

		for (var i = 1; i <=11; i+= 2) {
			stars.append($('<input/>',{
				'class' : 'star star-' + i,
				'id' : 'star-' + i + '-' + question.question_id,
				'type': 'radio',
				'name': "response_value",
				'onclick': 'submitAnswer(this.form)',
				'value': i
			}));
			stars.append($('<label/>',{
				'class' : 'star star-' + i,
				'for' : 'star-' + i + '-' + question.question_id				
			}));
		}
		form.append(stars);
		break;
	case 5:
		form.append($('<div />', {
			'class' : 'likert text-center',
			'style' : 'font-size: 18px;',
			'text' : 'Disagree | Neutral | Agree  '
		}));
		var likert = $('<div />', {
			'class' : 'likert'
		});

		for (var i = 1; i <=11; i+= 2) {
			likert.append($('<input/>',{
				'class' : 'likert likert-' + i,
				'id' : 'likert-' + i + '-' + question.question_id,
				'type': 'radio',
				'name': "response_value",
				'onclick': 'submitAnswer(this.form)',
				'value': i
			}));
			likert.append($('<label/>',{
				'class' : 'likert likert-' + i,
				'for' : 'likert-' + i + '-' + question.question_id				
			}));
		}		
		form.append(likert);
		break;
	case 4:
		break;
	case 6:
		break;
	}	
	return form;
}


function getPlainResponseForm(question, respondant, qcount, pagecount) {
	var form =  $('<form />', {
		 'name' : 'question_'+question.question_id,
		 'action' : "/response"
	 });
	form.append($('<input/>', {
		name : 'response_id',
		type : 'hidden',
		id : 'qr'+question.question_id,
		value : ''
	}));
	form.append($('<input/>', {
		name : 'response_respondant_id',
		type : 'hidden',
		value : respondant.respondant_id
	}));
	form.append($('<input/>', {
		name : 'response_question_id',
		type : 'hidden',
		value : question.question_id
	}));
	var qtextdiv = $('<div />', {
		'class' : 'col-xs-12 col-sm-8 col-md-8'
	});
	var questionlist = $('<ol />', {
		'start' : qcount,
		'class' : 'questiontext'
	}).append($('<li />', {
		'text' : question.question_text
	}));
	
	qtextdiv.append(questionlist);
	form.append(qtextdiv);
	
	switch (question.question_type) {
	default:
		var qrespdiv = $('<div />', {
			'class' : 'col-xs-12 col-sm-4 col-md-4'
		});
		var leftdiv = $('<div />', {
			'class' : 'col-xs-4 yesnoleft'
		});
		var yesbox = $('<input />', {
			'class': 'yesbox',
			'id'   : 'yesbox-' + question.question_id,
			'type' : 'radio',
			'name' : 'response_value',
			'onChange' : 'submitPlainAnswer(this.form,'+pagecount+')',
			'value' : 11
		});
		var yesboxlabel = $('<label />', {
			'class' : 'yesbox',
			'for'   : 'yesbox-' + question.question_id
		});
		yesboxlabel.append($('<i />', {
			'class' : 'fa fa-check-circle'			
		}));
		yesboxlabel.append($('<br/>'));
		yesboxlabel.append($('<span />', {
			'style' : 'font-size:10px;',
			'text'   : 'Yes'
		}));
		
		leftdiv.append(yesbox);
		leftdiv.append(yesboxlabel);

		var centerdiv = $('<div />', {
			'class' : 'col-xs-4 yesnocenter'
		});
		var sometimesbox = $('<input />', {
			'class': 'sometimesbox',
			'id'   : 'sometimesbox-' + question.question_id,
			'type' : 'radio',
			'name' : 'response_value',
			'onChange' : 'submitPlainAnswer(this.form,'+pagecount+')',
			'value' : 6
		});
		var sometimesboxlabel = $('<label />', {
			'class' : 'sometimesbox',
			'for'   : 'sometimesbox-' + question.question_id
		});
		sometimesboxlabel.append($('<i />', {
			'class' : 'fa fa-question-circle'			
		}));
		sometimesboxlabel.append($('<br/>'));
		sometimesboxlabel.append($('<span />', {
			'style' : 'font-size:10px;',
			'text'   : 'Sometimes'
		}));
		
		centerdiv.append(sometimesbox);
		centerdiv.append(sometimesboxlabel);

		var rightdiv = $('<div />', {
			'class' : 'col-xs-4 yesnoright'
		});
		var nobox = $('<input />', {
			'class': 'nobox',
			'id'   : 'nobox-' + question.question_id,
			'type' : 'radio',
			'name' : 'response_value',
			'onChange' : 'submitPlainAnswer(this.form,'+pagecount+')',
			'value' : 1
		});
		var noboxlabel = $('<label />', {
			'class' : 'nobox',
			'for'   : 'nobox-' + question.question_id
		});
		noboxlabel.append($('<i />', {
			'class' : 'fa fa-times-circle'			
		}));
		noboxlabel.append($('<br/>'));
		noboxlabel.append($('<span />', {
			'style' : 'font-size:10px;',
			'text'   : 'No'
		}));
		
		rightdiv.append(nobox);
		rightdiv.append(noboxlabel);	

		qrespdiv.append(leftdiv);
		qrespdiv.append(centerdiv);
		qrespdiv.append(rightdiv);
		form.append(qrespdiv);
		break;
	}
	return form;
}

function getHrDiv () {
	return $('<div />', {
		'class': 'col-xs-12 col-sm-12 col-md-12',
		'html': '<hr>'
	});
}

function getSurveyNav(pagecount, totalpages) {
	var navigation = $('<div />', {
		'class': 'col-xs-12 col-sm-12 col-md-12',		
	});
	var leftnav = $('<div />', {
		'class': 'col-xs-4 col-sm-4 col-md-4 text-center',		
	});
	if (pagecount > 1) {
		leftnav.append($('<button />', {
			'id' : 'prevbtn-' + pagecount, 
			'class' : 'btn btn-primary',
			'text' : "<< Back",
			'onClick':'mySwipe.prev();'
		}));
	}
	var centernav = $('<div />', {
		'class': 'col-xs-4 col-sm-4 col-md-4 text-center',
		'text' : 'Page '+ pagecount + ' of ' + totalpages
	});
	var rightnav = $('<div />', {
		'class': 'col-xs-4 col-sm-4 col-md-4 text-center',		
	});
	var nextbutton = $('<button />', {
		'id' : 'nextbtn-' + pagecount, 
		'class' : 'btn btn-primary',
		'text' : "Next >>",
		'onClick':'mySwipe.next();'
	});
	if (pagecount == totalpages) {
		nextbutton.attr('disabled', true);
		$(nextbutton).text('Finished');
		nextbutton.attr('onClick','mySwipe.next();');
	} else if (pagecount > 1) {
		nextbutton.attr('disabled', true);
	}
	rightnav.append(nextbutton);
	
	navigation.append(leftnav);
	navigation.append(centernav);
	navigation.append(rightnav);
	
	return navigation;
}


function isPageComplete(pagenum) {
	var qlist = pagination[pagenum];
	var complete = true;
	for (var key in qlist ) {
		if (responses[qlist[key].question_id] == null) complete = false;
	}
	if (complete) {
		var button = '#nextbtn-' + pagenum;
		$(button).attr('disabled', false);
	}
	return complete;
}

function isSurveyComplete() {
	var complete = true;
	for (var key in questions ) {
		if (responses[questions[key].question_id] == null) complete = false;
	}
	return complete;
}

function saveResponse(response) {
	responses[response.response_question_id] = response;
    var field = '#qr' + response.response_question_id;
    $(field).val(response.response_id);
    var totalresponses = 0;
    for (var i in responses) {
    	totalresponses ++;
    }
    progress = 100* totalresponses / questions.length;
    $('.progress-bar').attr('style','width:'+progress+'%;');
    $('.progress-bar').attr('aria-valuenow',progress);
    return;
}