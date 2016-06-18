<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
<div class="row">
	<div class="col-xs-12 col-sm-3 col-md-3">
		<h3>Positions</h3>
	</div>
	<div class="col-md-9 col-sm-9 col-xs-12 pull-right">
		<form class="form-inline pull-right" id='refinequery'>
			<div class="form-group">
				<select class="form-control" id="position_id" name="positionid"
					onChange='changePositionTo(this.value);'>
				</select>
			</div>
		</form>
	</div>
</div>
<div class="row content">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="x_panel">
			<div class="x_title">
				<h3 id="positionname">Position Name</h3>
			</div>
			<div class="x_content">
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>Position Description<i class="fa fa-briefcase pull-right"></i></h4>
						</div>
						<div class="x_content">
							<span id="positiondesc"></span>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>Important Factors for Position<i class="fa fa-cogs pull-right"></i></h4>
						</div>
						<div class="x_content">
							<table class="table table-condensed table-hover">
								<thead>
									<tr>
										<th>Factor</th>
										<th>Description</th>
										<th>Type</th>
									</tr>
								</thead>
								<tbody id="corefactorlist"></tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Position Profile<i class="fa fa-sliders pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
								<canvas id="positionProfile" style="width: 100%, height: auto;"></canvas>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Position Turnover<i class="fa fa-bar-chart pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
								<canvas id="positionTenure" style="width: 100%, height: auto;"></canvas>
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
	updatePositionsSelect(true);
</script>
</html>