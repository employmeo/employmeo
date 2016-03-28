<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>employmeo | Data Administration</title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav hidden-xs">
      <ul class="nav nav-pills nav-stacked">
        <li><a href="/index.jsp">Dashboard</a></li>
        <li><a href="/positions.jsp">Job Definitions</a></li>
        <li><a href="/applications.jsp">Current Applications</a></li>
        <li><a href="/analytics.jsp">Analytics</a></li>
        <li class="active"><a href="#">Data Administration</a></li>
      </ul><br>
    </div>  
    <div class="col-sm-9">
 	<div class="row content">
      <div class="col-sm-12 col-md-12 col-lg-12">
   
        <div class="panel panel-primary">
        <div class="panel-heading">Upload Payroll</div>
        <div class="panel-body">
        	<div>
				<form class="form"><div class="input-group form-inline">
                	<input type="file" placeholder="Select CSV" id="csvFile" name="csvFile" class="filestyle" data-size="sm" data-buttonName="btn-default">
			    	<button class="btn-primary" type="button" onClick="uploadPayroll(this);"><i class="fa fa-upload"></i></button>
				</div></form>
			</div>
			<div><hr></div>
			<div>
				<table id="payroll" class="table table-hover table-condensed"></table>
			</div>
      </div>
</div></div>
</div>
</div>
  </div>
</div>	
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>