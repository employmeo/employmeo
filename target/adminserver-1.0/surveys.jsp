<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Home Page")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav hidden-xs">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="/index.jsp">Dashboard</a></li>
					<li><a href="/candidates.jsp">Candidates</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
    </div>
    <div class="col-sm-9">
      <div class="well">
             <p style="font-size:32px"><%=LocaleHelper.getMessage(locale, "Survey Questions")%></p>
<%
String surveyID = request.getParameter("survey_id");
Survey survey = null;
if (surveyID != null) {
	survey = Survey.getSurveyById(surveyID);
%>
<table class="table table-bordered table-hover">
<tr><th>ID</th><th>Name</th><th>Type</th><th>Status</th></tr>
<%
	Question question = null;
	List<SurveyQuestion> questions = survey.getSurveyQuestions();
	for (int j=0;j<questions.size();j++){
		question = questions.get(j).getQuestion();
			//System.out.println(j + ". " + question.getJSONString());
%>
				<tr><td colspan="2"><%=question.getQuestionId() %>. <%=question.getQuestionText() %></td><td><%=question.getQuestionDescription() %></td><td><%=question.getQuestionType() %></td></tr>
<%			
		List<Answer> answers = question.getAnswers();
		for (int k=0;k<answers.size();k++) {
			Answer answer = answers.get(k);
%>
				<tr class="clickable-row small"><td></td><td><i class="fa fa-square-o"></i> <%=answer.getAnswerText() %></td><td><%=answer.getAnswerDescription() %></td><td><%=answer.getAnswerValue() %></td></tr>
<%			
			}
%>
<%
		}
%>
</table>
<%
}
%>
      </div>
  </div>
</div>
</div>
</html>