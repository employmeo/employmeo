<%@ include file="/WEB-INF/includes/inc_all.jsp" %>

<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Take Survey")%></title>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, width=device-width"/>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<link href='https://fonts.googleapis.com/css?family=Comfortaa' rel='stylesheet' type='text/css'>
<link rel='stylesheet' href='/scripts/style.css' type='text/css' media='all' />
<link rel="shortcut icon" href="/images/favico.gif">
<link rel="icon" type="image/gif" href="/images/favico.gif">
<script src="//code.jquery.com/jquery-1.12.1.min.js"></script>
<script src='/scripts/swipe.js'></script>
<script src='/scripts/scripts.js'></script>
</head>
<body>
<header></header>
<div id="maincontent" class="container-fluid" style="margin:0px;padding:0px;">
    <div id='mySwipe' style='max-width:900px;margin:0 auto' class='swipe'>
      <div class='swipe-wrap'>

<%
String surveyID = request.getParameter("survey_id");
String respondantID = request.getParameter("respondant_id");
Survey survey = null;
Respondant respondant = null;

if (respondantID != null) {
	respondant = Respondant.getRespondantById(respondantID);
	survey = respondant.getSurvey();
} else if (surveyID != null) {
	survey = Survey.getSurveyById(surveyID);
	Person person = new Person();
	person.setPersonFname(RandomizerUtil.randomFname());
	person.setPersonLname(RandomizerUtil.randomLname());
	person.setPersonEmail(RandomizerUtil.randomEmail(person));
	person.persistMe();
	respondant = new Respondant();
	respondant.setSurvey(survey);
	respondant.setRespondantAccountId(survey.getAccount().getAccountId());
	respondant.setPerson(person);
	respondant.persistMe();
}

if ((survey != null) && (respondant != null)){
	Question question = null;
	List<SurveyQuestion> questions = survey.getSurveyQuestions();
		for (int j=0;j<questions.size();j++){
			question = questions.get(j).getQuestion();
			int type = question.getQuestionType();
%>
<div>

            <div class="qpanel qpanel-default" style="background-image:url('/images/question-<%=question.getQuestionDisplayId() %>.jpg');">
				<div class="qpanel-header text-center">
					<h4><%=question.getQuestionText() %></h4>
				</div>
                <div class="qpanel-body">
                    <a href="#" class="zoom">
                        <!-- img src="/images/question-<%=question.getQuestionId() %>.jpg"-->
                    </a>
                </div>
           
                <div class="qpanel-footer">  
					<form name="question_<%=question.getQuestionId()%>" action="/response">
					<input name="response_id" type="hidden" id="qr<%=question.getQuestionId()%>" value="">
					<input type="hidden" name="response_respondant_id" value=<%=respondant.getRespondantId()%>>
					<input type="hidden" name="response_question_id" value=<%=question.getQuestionId()%>>
<%
if (type==1) {
%>

<div class="checkboxes">
<%			
			List<Answer> answers = question.getAnswers();
			for (int k=0;k<answers.size();k++) {
				Answer answer = answers.get(k);
%>
	<input class="check-square" id="check-square-<%=answer.getAnswerValue() %>-<%=question.getQuestionId()%>" type="checkbox" name="response_value" value=<%=answer.getAnswerValue() %>>
	<label class="check-square" for="check-square-<%=answer.getAnswerValue() %>-<%=question.getQuestionId()%>"><%=answer.getAnswerText() %></label><br>
<%
			}
%>
	<a class="check-submit" href="#" onclick='submitAnswer(this.form)'>Next</a>		
</div>
<%
} else if (type==2) {
%>
					<div class="thumbs">
						<input class="thumbs-up" id="thumbs-up-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=1>
						<label class="thumbs-up" for="thumbs-up-<%=question.getQuestionId()%>">Me</label>
						<input class="thumbs-down" id="thumbs-down-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=2>
						<label class="thumbs-down" for="thumbs-down-<%=question.getQuestionId()%>">Not Me</label>
					</div>
<%
} else if (type==3) {
%>
					<div class="schedules">
						<table class="table"><tbody>
						<tr><td>S</td><td>M</td><td>T</td><td>W</td><td>T</td><td>F</td><td>S</td></tr>
						<tr>
							<td><input class="check-morning" id="sun-morn-<%=question.getQuestionId()%>" type="checkbox" name="sun-morn">
	          				<label class="check-morning" for="sun-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="mon-morn-<%=question.getQuestionId()%>" type="checkbox" name="mon-morn">
	          				<label class="check-morning" for="mon-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="tue-morn-<%=question.getQuestionId()%>" type="checkbox" name="tue-morn">
	          				<label class="check-morning" for="tue-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="wed-morn-<%=question.getQuestionId()%>" type="checkbox" name="wed-morn">
	          				<label class="check-morning" for="wed-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="thu-morn-<%=question.getQuestionId()%>" type="checkbox" name="thu-morn">
	          				<label class="check-morning" for="thu-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="fri-morn-<%=question.getQuestionId()%>" type="checkbox" name="fri-morn">
	          				<label class="check-morning" for="fri-morn-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-morning" id="sat-morn-<%=question.getQuestionId()%>" type="checkbox" name="sat-morn">
	          				<label class="check-morning" for="sat-morn-<%=question.getQuestionId()%>"></label></td>
						</tr>
						<tr>
							<td><input class="check-afternoon" id="sun-aft-<%=question.getQuestionId()%>" type="checkbox" name="sun-aft">
	          				<label class="check-afternoon" for="sun-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="mon-aft-<%=question.getQuestionId()%>" type="checkbox" name="mon-aft">
	          				<label class="check-afternoon" for="mon-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="tue-aft-<%=question.getQuestionId()%>" type="checkbox" name="tue-aft">
	          				<label class="check-afternoon" for="tue-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="wed-aft-<%=question.getQuestionId()%>" type="checkbox" name="wed-aft">
	          				<label class="check-afternoon" for="wed-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="thu-aft-<%=question.getQuestionId()%>" type="checkbox" name="thu-aft">
	          				<label class="check-afternoon" for="thu-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="fri-aft-<%=question.getQuestionId()%>" type="checkbox" name="fri-aft">
	          				<label class="check-afternoon" for="fri-aft-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-afternoon" id="sat-aft-<%=question.getQuestionId()%>" type="checkbox" name="sat-aft">
	          				<label class="check-afternoon" for="sat-aft-<%=question.getQuestionId()%>"></label></td>
						</tr>
						<tr>
							<td><input class="check-evening" id="sun-eve-<%=question.getQuestionId()%>" type="checkbox" name="sun-eve">
	          				<label class="check-evening" for="sun-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="mon-eve-<%=question.getQuestionId()%>" type="checkbox" name="mon-eve">
	          				<label class="check-evening" for="mon-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="tue-eve-<%=question.getQuestionId()%>" type="checkbox" name="tue-eve">
	          				<label class="check-evening" for="tue-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="wed-eve-<%=question.getQuestionId()%>" type="checkbox" name="wed-eve">
	          				<label class="check-evening" for="wed-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="thu-eve-<%=question.getQuestionId()%>" type="checkbox" name="thu-eve">
	          				<label class="check-evening" for="thu-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="fri-eve-<%=question.getQuestionId()%>" type="checkbox" name="fri-eve">
	          				<label class="check-evening" for="fri-eve-<%=question.getQuestionId()%>"></label></td>
							<td><input class="check-evening" id="sat-eve-<%=question.getQuestionId()%>" type="checkbox" name="sat-eve">
	          				<label class="check-evening" for="sat-eve-<%=question.getQuestionId()%>"></label></td>
						</tr>
						<tr>
							<td colspan="7"><button type="button" onclick='submitAnswer(this.form);return false'>Next</button></td>
						</tbody></table>
					</div><%
} else if (type==4) {
%>
					<div class="stars text-center" style="font-size: 18px;">Rate on a Scale of 1-5</div>
					<div class="stars">
    	  				<input class="star star-1" id="star-1-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=1>
          				<label class="star star-1" for="star-1-<%=question.getQuestionId()%>"></label>
    	  				<input class="star star-2" id="star-2-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=2>
          				<label class="star star-2" for="star-2-<%=question.getQuestionId()%>"></label>
    	  				<input class="star star-3" id="star-3-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=3>
          				<label class="star star-3" for="star-3-<%=question.getQuestionId()%>"></label>
    	  				<input class="star star-4" id="star-4-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=4>
          				<label class="star star-4" for="star-4-<%=question.getQuestionId()%>"></label>
    	  				<input class="star star-5" id="star-5-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=5>
          				<label class="star star-5" for="star-5-<%=question.getQuestionId()%>"></label>	
					</div>
<%
} else if (type==5) {
%>
					<div class="likert text-center" style="font-size: 18px;">Disagree | Neutral | Agree &nbsp;</div>
					<div class="likert">
    	  				<input class="likert likert-1" id="likert-1-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=1>
          				<label class="likert likert-1" for="likert-1-<%=question.getQuestionId()%>"></label>
    	  				<input class="likert likert-2" id="likert-2-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=2>
          				<label class="likert likert-2" for="likert-2-<%=question.getQuestionId()%>"></label>
    	  				<input class="likert likert-3" id="likert-3-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=3>
          				<label class="likert likert-3" for="likert-3-<%=question.getQuestionId()%>"></label>
    	  				<input class="likert likert-4" id="likert-4-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=4>
          				<label class="likert likert-4" for="likert-4-<%=question.getQuestionId()%>"></label>
    	  				<input class="likert likert-5" id="likert-5-<%=question.getQuestionId()%>" type="radio" name="response_value" onclick='submitAnswer(this.form)' value=5>
          				<label class="likert likert-5" for="likert-5-<%=question.getQuestionId()%>"></label>	
					</div>

<%
} else {
%>
<div class="radios">
<%			
		List<Answer> answers = question.getAnswers();
		for (int k=0;k<answers.size();k++) {
			Answer answer = answers.get(k);
%>
	<input class="radio-select" id="radio-<%=question.getQuestionId()%>-<%=answer.getAnswerValue() %>" type="checkbox" name="response_value" onclick='submitAnswer(this.form)' value=<%=answer.getAnswerValue() %>>
	<label class="radio-select" for="radio-<%=question.getQuestionId()%>-<%=answer.getAnswerValue() %>"><%=answer.getAnswerText() %></label><br>
<%
			}
%>
</div>

<%
}
%>
					</form>
                </div>
           </div>
</div>
<%
		}
%>
		<div>
            <div class="qpanel qpanel-default" style="background-image:url('/images/background-1.jpg');">
				<div class="qpanel-header text-center">
					<h4>Thank You</h4>
				</div>
                <div class="qpanel-body">
                    <a href="#" class="zoom">
                    </a>
                </div>           
                <div class="qpanel-footer text-center">
                    <button type="button">Finish</button>
				</div>
			</div>
		</div>
<%
}
%>

	  </div>
	</div>
</div>
<script>
var elem = document.getElementById('mySwipe');
window.mySwipe = Swipe(elem, {
    callback: function() {
    }	
});
</script>

</html>