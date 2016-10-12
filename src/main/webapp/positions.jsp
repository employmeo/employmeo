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

<!-- Begin Position Description and Stats -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>Position Description<i class="fa fa-briefcase pull-right"></i></h4>
						</div>
						<div class="x_content">
							<span id="positiondesc"></span>
							<hr>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Applicants</div>
								<div id="div_applicant_count" class="count">1234</div>
							</div>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Hires</div>
								<div id="div_hire_count" class="count">23</div>
							</div>
							<div class="col-xs-12 col-sm-4 tile_stats_count text-center">
								<div class="count_top">Hire Rate</div>
								<div id="div_hire_rate" class="count">35%</div>
							</div>
							<div class="col-xs-12 text-right">
								<hr>
								<span id='#last_updated' style="font-style:italic;">Model Last Updated: Oct 15, 2016</span>
							</div>
						</div>
					</div>
				</div>
<!-- End Position Description and Stats -->
<!-- Begin Stats by Profile Table -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Key Metrics by Grade<i class="fa fa-sliders pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
							<table class="table table-condensed table-hover">
								<thead id="gradeheader">
									<tr>
										<th>Grade</th>
										<th>Hire Rate</th>
										<th>Tenure</th>
									</tr>
								</thead>
								<tbody id="gradetable">
									<tr>
										<td><div class="btn-success text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-rocket" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr>
										<td><div class="btn-info text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-user-plus" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr style="vertical-align:middle;">
										<td><div class="btn-warning text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-warning" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i></div>
						   				</td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
									<tr>
										<td><div class="btn-danger text-center" style="height: 30px; width: 30px;vertical-align:middle;">
						   					<i class="fa fa-hand-stop-o" style="margin: auto; float: none;font-size:17px;line-height:28px;"></i>
						   				</div></td>
						   				<td>15</td>
						   				<td>15</td>
						   			</tr>
								</tbody>
								<tfoot id="gradefooter">
									<tr>
										<th>Totals</th>
										<th>##</th>
										<th>##</th>
									</tr>
								</tfoot>
							</table>
							</div>
						</div>
					</div>
				</div>
<!-- End Stats by Profile Table -->
<!-- Begin Position Core Factors Bar Chart -->
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Critical Factors<i class="fa fa-bar-chart pull-right"></i>
							</h4>
						</div>
						<div class="x_content">
							<div>
		<div class="col-xs-12 text-center"><h5>Scoring Average For Critical Factors</h5></div>
		<div id="factors_barchart" class="col-xs-12"><canvas id="criticalfactorschart"></canvas></div>
		<div class="col-xs-6 scaleleft">least important</div>
		<div class="col-xs-6 scaleright">most important</div>
							</div>
						</div>
					</div>
				</div>
<!-- End Position Core Factors Bar Chart -->
<!-- Begin Position Core Factor List -->
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="x_panel">
						<div class="x_title">
							<h4>
								Important Factors for Position<i class="fa fa-cogs pull-right"></i>
							</h4>
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
<!-- End Position Core Factor List -->
<!-- Begin Remnants to keep JS code working until fully tested -->
				<div class="hidden">
								<canvas id="positionTenure" style="width: 100%, height: auto;"></canvas>
								<canvas id="positionProfile" style="width: 100%, height: auto;"></canvas>
				</div>
<!-- End remnants to keep JS code working until fully tested -->
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp"%>

<script type="text/javascript">
	var cfBarChart = initCriticalFactorsChart();
	updatePositionsSelect(true); // this script gets all the positions from the server, then triggers the first "refresh"

	/// for now - we'll leave the raw code on this page, but it would be likely added to the "chage position" function
	var data = getStubDataForRoleBenchmark(); /// replace with REST call or pull from other var
	updatePositionModelDetails(data.role_benchmark);
	updateGradesTable(data.role_benchmark.role_grade);
	updateCriticalFactorsChart(data);

	
	function updatePositionModelDetails(role_benchmark) {
		document.querySelector('#div_applicant_count').innerHTML = role_benchmark.applicant_count;
		document.querySelector('#div_hire_count').innerHTML = role_benchmark.hire_count;
		document.querySelector('#div_hire_rate').innerHTML = Math.round((role_benchmark.hire_count/role_benchmark.applicant_count)*100)+'%';		
	}
	
	function updateGradesTable(arr1) {
		$('#gradetable').empty();
		$('#gradefooter').empty();
		
		var frag = document.createDocumentFragment();
		// measure variables
		var avg0 = 0;
		var avg1 = 0;
				
		for (var i = 0, len = Object.keys(arr1).length; i < len; i++) {
			//summary variables
			avg0 += parseFloat(arr1[i].v0);
			avg1 += parseFloat(arr1[i].v1);
			
			var tr0 = document.createElement("tr");
			var td0 = document.createElement("td");
			var divClass;
			var iconClass;
			
			switch (arr1[i].grade){
				case "A":
					divClass='btn-success';
					iconClass='fa-rocket';
					break;
				case "B":
					divClass='btn-info';
					iconClass='fa-user-plus';
					break;
				case "C":
					divClass='btn-warning';
					iconClass='fa-warning';
					break;
				case "D":
					divClass='btn-danger';
					iconClass='fa-hand-stop-o';
					break;
			}	
			$(td0).append(getProfileBadge(divClass, iconClass));
			tr0.appendChild(td0);		
			
			var td1 = document.createElement("td");
			td1.className="text-right";
			td1.innerHTML = arr1[i].v0;
			
			var td2 = document.createElement("td");
			td2.className="text-right";
			td2.innerHTML = (arr1[i].v1*100).toPrecision(2)+'%';
			
			tr0.appendChild(td1);
			tr0.appendChild(td2);
			frag.appendChild(tr0);	
			
			var el = document.querySelector('#gradetable');
			el.appendChild(frag);
		}
		
		var tr0 = document.createElement("tr");
		var td0 = document.createElement("th");
		td0.innerHTML = "Average";
		var td1 = document.createElement("th");
		td1.className="text-right";
		td1.innerHTML = (avg0/Object.keys(arr1).length).toFixed(1);
		var td2 = document.createElement("th");
		td2.className="text-right";
		td2.innerHTML = (avg1*100/Object.keys(arr1).length).toFixed(1)+'%';
		
		tr0.appendChild(td0);
		tr0.appendChild(td1);
		tr0.appendChild(td2);	
		
		var el = document.querySelector('#gradefooter');
		el.appendChild(tr0);
	}

	
	function getProfileBadge(divClass,iconClass) {
		var div = $('<div />', {'class':'profilesquare'}).addClass(divClass);
		var icon = $('<i />', {'class':'fa'}).addClass(iconClass);
		$(div).append(icon);
		return div;
	}	

  	function initCriticalFactorsChart() {
	  	var ctx = document.querySelector("#criticalfactorschart").getContext("2d");
  		var barChartConfig = {
  	  	    type: "bar",
  	  	    data: {
    	  	  	  labels: ["loading..."],
      	  	  	  
      	  	  	  datasets: [{
      	  	  		label: "Applicant",
      	  	        backgroundColor: 'rgba(150, 150, 150, 0.8)',
      	  	  	    data: []
      	  	  	  },
      	  	  	{
      	  	  		label: "Employee",
      	  	  		backgroundColor: 'rgba(0, 150, 0, 0.8)',
      	  	    	data: []
      	  	    	  }
      	  	  	  ]
      	  	  	},
	  	  	    options: {
	  	  	        scales: {
	  	  	            xAxes: [{
	  	  	                stacked: false
	  	  	                ,gridLines: {display:false}
	  	  	            	,display: true
	  	  	            }],
	  	  	            yAxes: [{
	  	  	                stacked: false
	  	  	                ,gridLines: {display:false}
	  	  	            	,display: false
	  	  	            }]
	  	  	        ,showScale: false
	  	  	        },
	  	  	    animation: {
	  	    	  	duration: 500,
	  	    	  	onComplete: function () {
	  	    	  	    // render the value of the chart above the bar
	  	    	  	    var ctx = this.chart.ctx;
	  	    	  	    ctx.font = Chart.helpers.fontString(Chart.defaults.global.defaultFontSize, 'normal', Chart.defaults.global.defaultFontFamily);
	  	    	  	    ctx.fillStyle = this.chart.config.options.defaultFontColor;
	  	    	  	    ctx.textAlign = 'center';
	  	    	  	    ctx.textBaseline = 'bottom';
	  	    	  	    this.data.datasets.forEach(function (dataset) {
	  	    	  	        for (var i = 0; i < dataset.data.length; i++) {
	  	    	  	            var model = dataset.metaData[i]._model;
	  	    	  	            ctx.fillText(dataset.data[i], model.x, model.y - 0);
	  	    	  	        }
	  	    	  	    });
	  	    	  	}}    
	  	  	    }
	  	  	};
  		return new Chart(ctx, barChartConfig);

  	}
  	
  	function updateCriticalFactorsChart(data) {
 
		var arr1 = data.role_benchmark.cf;
		var arr2 = data.person.cf;
		var chartLabels = [];
		var chartData0 = []; 
		var chartData1 = [];
		
		for (var i = 0, len = Object.keys(arr1).length; i < len; i++) {
			chartLabels.push(arr1[i].cf_name);
			chartData0.push(arr1[i].value);
		}
		for (var i = 0, len = Object.keys(arr2).length; i < len; i++) {
			chartData1.push(arr2[i].value);
		}
		
		cfBarChart.config.data.labels = chartLabels;
//		barChartConfig.data.datasets[0].data = chartData0;
//		barChartConfig.data.datasets[1].data = chartData1;
		
  		cfBarChart.config.data.datasets[0].data = [dData(), dData(), dData(), dData(), dData(), dData(), dData()];
  		cfBarChart.config.data.datasets[1].data = [dData(), dData(), dData(), dData(), dData(), dData(), dData()];
  		cfBarChart.update();
  	}
	
	// Stub Code:
  	function dData () {
  	  return Math.round(Math.random() * 10) + 1
  	};

	function getStubDataForRoleBenchmark() {
		
		var person = {
			first_name : 'John',
			cf : {
				0:{"cf_name":"Work Ethic","value":15},
				1:{"cf_name":"Perseverence","value":20},
				2:{"cf_name":"Prior Experience","value":30},
				3:{"cf_name":"Referral","value":40},
				4:{"cf_name":"Commute","value":45},
				5:{"cf_name":"Job History","value":45},
				6:{"cf_name":"Personal Relationship","value":45}
			}
		};
		var role_benchmark = {
			role_name : 'Crew',
			role_description : 'Crew is an entry level position. Required basic work skills and ability to read / speak English.',
			applicant_count : '1300',
			hire_count : '300',
			role_grade : {
				0:{"grade":"A","n0":"tenure","v0":"9.3","n1":"wage_increase","v1":".034"},
				1:{"grade":"B","n0":"tenure","v0":"8.2","n1":"wage_increase","v1":".021"},
				2:{"grade":"C","n0":"tenure","v0":"5.7","n1":"wage_increase","v1":".014"},
				3:{"grade":"D","n0":"tenure","v0":"2.4","n1":"wage_increase","v1":".020"}
			},
			cf : {0:{"cf_name":"Work Ethic","value":15},1:{"cf_name":"Perseverence","value":20},2:{"cf_name":"Prior Experience","value":30},3:{"cf_name":"Referral","value":40},4:{"cf_name":"Commute","value":45},5:{"cf_name":"Job History","value":45},6:{"cf_name":"Personal Relationship","value":45}},
			date : 'Oct 14, 2016'
		};
		return {'person' : person, 'role_benchmark' : role_benchmark };
	}

</script>

</html>