<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
  <div class="row content">
    <div class="col-sm-12">
 	<div class="row content">
      <div class="col-sm-12 col-md-12 col-lg-12">
   
        <div class="panel panel-primary">
        <div class="panel-heading">Upload Payroll</div>
        <div class="panel-body">
        	<div>
        		<input type="file" id="csvFile" name="csvFile">
			    <button class="btn-primary" type="button" onClick="uploadCSV();"><i class="fa fa-upload"></i></button>
			    <span>Drop files here to upload</span>
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