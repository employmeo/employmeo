<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-12">
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
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
	<script type="text/javascript">
		updatePositionsNav();
	</script>
</html>