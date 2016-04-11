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
					<li><a href="/index.jsp">Candidates</a></li>
					<li><a href="/positions.jsp">Positions</a></li>
					<li><a href="/surveys.jsp">Assessments</a></li>
					<li><a href="/data_admin.jsp">Administration</a></li>
				</ul>
    </div>
			<div class="col-sm-9">
				<div class="row content">
					<ul class="nav nav-tabs" id="surveys_nav"></ul>
					<br>
				</div>
				<div class="row content">
					<div class="col-sm-12 col-md-12 col-lg-12">

						<div class="panel panel-primary">
							<div class="panel-heading">Surveys</div>
							<div class="panel-body">
							<form id="updatesurveyform" class="form">
							    Survey ID: <input class="form-control" id="surveyid" name="survey_id" disabled>
							    Survey Name: <input class="form-control" id="surveyname" name="survey_name" disabled>
							    Survey Question Count: <input class="form-control" id="questiontotal" disabled>
							    Survey Type: <input class="form-control" id="surveytype" name="survey_type" disabled>
							    Survey Status: <input class="form-control" id="surveystatus" name="survey_status" disabled>
							</form>

							</div>
						</div>
						<div class="row content">
							<div class="col-sm-12 col-md-12 col-lg-12">

								<div class="panel panel-primary">
									<div class="panel-heading">Survey Questions</div>
									<div class="panel-body">
										<table id="questions" class="table table-hover display compact responsive nowrap" cellspacing="0" width="100%">
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
		updateSurveysNav();
	</script>
</html>