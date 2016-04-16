<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
  <div class="row content">
    <div class="col-sm-12">
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
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>	
</html>