<%@ include file="/WEB-INF/includes/inc_all.jsp"%>
<html>
<head>
<title>employmeo | Positions</title>
<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav hidden-xs">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="/index.jsp">Dashboard</a></li>
					<li><a href="/candidates.jsp">Candidates</a></li>
					<li class="active"><a href="#">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
			</div>
			<div class="col-sm-9">
				<div class="row content">
					<ul class="nav nav-tabs" id="positions_nav"></ul>
					<br>
				</div>
				<div class="row content">
					<div class="col-sm-12 col-md-12 col-lg-12">

						<div class="panel panel-primary">
							<div class="panel-heading">Position Analysis</div>
							<div class="panel-body">
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
									<div class="panel panel-info">
										<div class="panel-heading">
											<span class="text-left">Position Profile<i
												class="fa fa-line-chart pull-right"></i></span>
										</div>
										<div class="panel-body text-center">
											<div>
												<canvas id="positionProfile"
													style="width: 100%, height: auto;"></canvas>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
									<div class="panel panel-info">
										<div class="panel-heading">
											<span class="text-left">Position Turnover<i
												class="fa fa-bar-chart pull-right"></i></span>
										</div>
										<div class="panel-body text-center">
											<div>
												<canvas id="positionTenure"
													style="width: 100%, height: auto;"></canvas>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
						<div class="row content">
							<div class="col-sm-12 col-md-12 col-lg-12">

								<div class="panel panel-primary">
									<div class="panel-heading">Application Questions</div>
									<div class="panel-body">
										<table class="table table-bordered table-hover">
											<tr>
												<th>Question</th>
												<th>Type</th>
												<th><i class="fa fa-search"></i></th>
											</tr>
											<%
												Survey survey = account.getSurveys().get(0);
												Question question = null;
												List<SurveyQuestion> questions = survey.getSurveyQuestions();
												for (int j = 0; j < questions.size(); j++) {
													question = questions.get(j).getQuestion();
											%>
											<tr>
												<td><%=question.getQuestionId()%>. <%=question.getQuestionText()%></td>
												<td><%=question.getQuestionType()%></td>
												<td><a
													href='/edit_question.jsp?question_id=<%=question.getQuestionId()%>'><i
														class="fa fa-pencil"></i></a></td>
											</tr>
											<%
												}
											%>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		updatePositionsNav();
	</script>
</html>