//
// Global Variables and actions for the survey page(s)
//
var workwithme;
var respondant;
var survey;
var questions;
var pagination;
var totalpages;
var responses;
var progress;
var urlParams;
var sections;

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
// Rest Service Calls
//

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

function buildPlainSurveyWithRespondantId(uuId) {
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/getsurvey",
        data: {
        	"respondant_uuid" : uuId       	
        },
        beforeSend: function() {
        	$('#wait').removeClass('hidden');
        },
        success: function(data)
        {
        	if (data.message != null) {
        		showAssessmentNotAvailable(data);
        	} else {
                assemblePlainSurvey(data);        		
        	}
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
            assemblePlainSurvey(data);
        },
        complete: function() {
        	$('#wait').addClass('hidden');
        }
      });	
}

function submitSurvey() {
	var redirect = 'http://employmeo.com';
	if (respondant.respondant_redirect_url != null) redirect = respondant.respondant_redirect_url;
    $.ajax({
        type: "POST",
        async: true,
        url: "/survey/submitassessment",
        data: {'respondant_id' : respondant.respondant_id},
        success: function(data)
        {
            window.location.assign(redirect);
        }
      });	
}

// Pagination Code
function nextPage() {	
	if(isPageComplete($('.carousel-inner div.active').index())) {
		$('#survey').carousel("next");
	} else {
		// TODO - some sort of user feedback to show page is incomplete
	}
}

function prevPage() {
	$('#survey').carousel("prev");
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

// Error Handling Functions
function showAssessmentNotAvailable(data) {
	  // code to create a form to fill out for a new survey respondant	
		var deck = document.getElementById('wrapper');
		$(deck).empty();
		totalpages = 1;
		var card = $('<div />', {
			'class' : 'item active'
		});		
		card.append(getHrDiv());
		card.append($('<div />', {
			'class' : 'col-xs-12 col-sm-12 col-md-12',
			}).append($('<h3 />', { 'class' : 'text-center', 'text' : data.message})));
		card.append(getHrDiv());
		card.appendTo(deck);
}

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
	
	$('#address').geocomplete({details:'form'});
}



//
// Survey page buidling functions
//

function assemblePlainSurvey(collection) {

	var deck = document.getElementById('wrapper');
	respondant = collection.respondant;
	survey = collection.survey;
	sections = survey.sections;
	questions = survey.questions.sort(function(a,b) {
		if (a.question_page == b.question_page) {
			return a.question_sequence < b.question_sequence ? -1:1;
		} else {
			return a.question_page < b.question_page ? -1:1;
		}});;
	
	pagination = new Array();
	$(deck).empty();
	
	// prepare survey into sections:
	var qlimit = 5; // questions per page
	totalpages = Math.ceil(questions.length / qlimit) + 1 + 1; // (preamble + questions total)

	var pagecount = 1;
	var card = getPreamble(pagecount, totalpages);
	card.appendTo(deck);
	pagecount++;

	
	$.each(sections, function(index, section) {
		section.questions = new Array();
		card = getInstructions(section, pagecount, totalpages);
		card.appendTo(deck);
		pagecount++;
		
		var qcount = 0;
		var qpp = 0;
		card = $('<div />', {'class' : 'questionpage item'});
		card.append(getHrDiv());
		pagination[pagecount] = new Array();
		
		for (var q=0;q<questions.length;q++) {
			var question = questions[q];
			if (question.question_page == section.section_number) {
					qcount++;
					if (qpp == qlimit) {
						card.append(getSurveyNav(pagecount, totalpages, 4));	
						card.append($('<div />', {
							'class' : 'col-xs-12 col-sm-12 col-md-12',
							'height' : '75px'}));
						card.appendTo(deck);
						pagecount++;
						pagination[pagecount] = new Array();
						qpp = 0;
						card = $('<div />', {'class' : 'questionpage item'});
						card.append(getHrDiv());				
					}
					var pageqs = pagination[pagecount];
					pageqs[qpp] = question;
					qpp++;
					var questionrow = $('<div />', {'class' : 'row'}).append($('<div />', {
						'class' : 'col-xs-1 col-sm-1 col-md-1 col-lg-1 text-right questiontext',
						'text'	: qcount + '.'
					}));
					var qcontent = $('<div />', {'class' : 'col-xs-11 col-sm-11 col-md-11 col-lg-11'});
					qcontent.append(getDisplayQuestion(question));
					qcontent.append(getPlainResponseForm(question, respondant, qcount, pagecount));
					questionrow.append(qcontent);		
					card.append(questionrow);
					card.append(getHrDiv());
			}
		}
	});
		
	card.append(getSurveyNav(pagecount, totalpages,3));	
	card.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		'height' : '75px'}));
	card.appendTo(deck);

	responses = new Array();
	
	if (collection.responses != null) {
		for (var i=0;i<collection.responses.length;i++) {
			saveResponse(collection.responses[i]);
			var radios =$('form[name=question_'+collection.responses[i].response_question_id+
    		'] :input[name=response_value][value=' + collection.responses[i].response_value + ']');
		    $(radios).prop('checked', true);
		}
		
		for (var i=1;i<=totalpages;i++) {
			isPageComplete(i);
		}
	}
}

// Show Assessment Preamble
function getPreamble(pagecount, totalpages) {
	var preamble = $('<div />', {'class' : 'item active'});
	
	preamble.append(getHrDiv());
	preamble.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		}).html(survey.survey_preamble_text));
	preamble.append(getHrDiv());
	preamble.append(getSurveyNav(pagecount, totalpages, 1));	

	return preamble;
}

//Show Section Instructions
function getInstructions(section, pagecount, totalpages) {
	var instr = $('<div />', {'class' : 'item'});
	
	instr.append(getHrDiv());
	instr.append($('<div />', {
		'class' : 'col-xs-12 col-sm-12 col-md-12',
		}).html(section.section_instructions));
	instr.append(getHrDiv());
	instr.append(getSurveyNav(pagecount, totalpages, 2));	

	return instr;
}

// create the question / response form
function getDisplayQuestion(question) {
	var qtextdiv = $('<div />', {
		'class' : 'col-xs-12 col-sm-6 col-md-6 questiontext text-left',
		'text' : question.question_text
	});
	return qtextdiv;
}

function getPlainResponseForm(question, respondant, qcount, pagecount) {
	var ansblock = $('<div />', {'class' : 'col-xs-12 col-sm-6 col-md-6'});

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
	case 6: // yes-notsure-no
		var ansdiv = $('<div />', {
			'class' : 'row'
		});
		for (var ans=0;ans<question.answers.length;ans++) {
			var answer = question.answers[ans];
			var qrespdiv = $('<div />', {
				'style' : 'padding-right: 1px; padding-left: 1px;',
				'class' : 'col-xs-4 col-sm-4 col-md-4'
			});
			var radiobox = $('<input />', {
				'id'   : 'radiobox-' + question.question_id +"-"+ answer.answer_value,
				'type' : 'radio',
				'class' : 'radio-short',
				'name' : 'response_value',
				'onChange' : 'submitPlainAnswer(this.form,'+pagecount+')',
				'value' :  answer.answer_value
			});
			var radiolabel = $('<label />', {
				'for'   : 'radiobox-' + question.question_id +"-"+ answer.answer_value,
				'class' : 'radio-short',
				'text'  :  answer.answer_text.toUpperCase()
			});
		
			qrespdiv.append(radiobox);
			qrespdiv.append(radiolabel);

			ansdiv.append(qrespdiv);
		}
		form.append(ansdiv);
		break;
	case 7:
	case 9:
	case 10:
	case 11:
	case 12:
	case 13:
		// basic multichoice
		for (var ans=0;ans<question.answers.length;ans++) {
			var answer = question.answers[ans];
			var qrespdiv = $('<div />', {
				'class' : 'col-xs-12 col-sm-12 col-md-12'
			});
			var radiobox = $('<input />', {
				'id'   : 'radiobox-' + question.question_id +"-"+ answer.answer_value,
				'type' : 'radio',
				'class' : 'radio-select',
				'name' : 'response_value',
				'onChange' : 'submitPlainAnswer(this.form,'+pagecount+')',
				'value' :  answer.answer_value
			});
			var radiolabel = $('<label />', {
				'for'   : 'radiobox-' + question.question_id +"-"+ answer.answer_value,
				'class' : 'radio-select',
				'text'  :  answer.answer_text
			});
		
			qrespdiv.append(radiobox);
			qrespdiv.append(radiolabel);

			form.append(qrespdiv);
		}
		break;
	case 14: // Rank
		break;
	case 15: // Alike / Unlike
		break;
	case 8:
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
			'value' : 1
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
			'value' : 2
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
			'value' : 3
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
	ansblock.append(form);
	return ansblock;
}

function getHrDiv () {
	return $('<div />', {
		'class': 'col-xs-12 col-sm-12 col-md-12',
		'html': '<hr>'
	});
}

function getSurveyNav(pagecount, totalpages, pageType) {
	var navigation = $('<div />', {'class': 'col-xs-12 col-sm-12 col-md-12'});
	var leftnav = $('<div />', {'class': 'col-xs-4 col-sm-4 col-md-4 text-center'});
	var centernav = $('<div />', {
		'class': 'col-xs-4 col-sm-4 col-md-4 text-center',
		'text' : 'Page '+ pagecount + ' of ' + totalpages });
	var rightnav = $('<div />', {'class': 'col-xs-4 col-sm-4 col-md-4 text-center'});

	
	var nextbutton = $('<button />', {
		'id' : 'nextbtn-' + pagecount, 
		'class' : 'btn btn-primary',
		'text' : "Next >>",
		'onClick':'nextPage();' });
	
	switch (pageType) {
	case 1: //First Page
		$(nextbutton).text('I Agree');
		break;
	case 2: //Instruction Page
		$(nextbutton).text('Start Assessment');
		// nextbutton.attr('onClick','submitSurvey();'); - for timed sections?
		break;
	case 3: //Thank You Page
		$(nextbutton).text('Confirm Submission');
		nextbutton.attr('onClick','submitSurvey();');
		break;
	case 4: //Question Page
	default:		
		nextbutton.attr('disabled', true);
		leftnav.append($('<button />', {
			'id' : 'prevbtn-' + pagecount, 
			'class' : 'btn btn-primary',
			'text' : "<< Back",
			'onClick':'prevPage();'
		}));
		break;
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