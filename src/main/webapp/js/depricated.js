function updatePositionTenure(position) {
	refreshPositionTenure(getPositionTenureData()); // use stub code
}

function refreshPositionTenure(dataPositionTenure) {
	var positionTenure = $("#positionTenure").get(0).getContext("2d");
	if (tenureChart != null) tenureChart.destroy();
	tenureChart = new Chart(positionTenure, {type: 'bar', data: dataPositionTenure, options: { 
		showScale: true,
		scaleShowGridLines: false,
		responsive : true,
		legend: { display: false }
	}});	
}

function updatePositionProfile(position) {
	var dataPositionProfile = getApplicantProfileData(); // use stub code
	positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	var dataPosProfile = {
			datasets : [{
				backgroundColor : position.position_profiles[0].profile_overlay,
				borderColor : position.position_profiles[0].profile_color,
				label : position.position_profiles[0].profile_name,
				pointBackgroundColor : position.position_profiles[0].profile_color,
				pointBorderColor : position.position_profiles[0].profile_color,
				pointHoverBackgroundColor : position.position_profiles[0].profile_highlight,
				pointHoverBorderColor : position.position_profiles[0].profile_highlight,
				data : []
			}],
			labels : []};
		
	$(position.position_corefactors).each(function (i) {
		dataPosProfile.labels[i] = this.corefactor_name;
		dataPosProfile.datasets[0].data[i] = this.pm_score_a;
	});
	

	profileChart = new Chart(positionProfile, {type: 'radar', data: dataPosProfile, options: { 
		scale: {
                ticks: {
                    beginAtZero: true
                },
                pointLabels : {
                	fontSize : 16
                }
        },
		legend: { display: false }
	}});	
}

function refreshPositionProfile(dataPositionProfile) {
	positionProfile = $("#positionProfile").get(0).getContext("2d");
	if (profileChart != null) profileChart.destroy();
	profileChart = new Chart(positionProfile, {type: 'radar', data: dataPositionProfile, options: { 
		showScale: true,
		responsive : true,
		defaultFontSize: 16,
		legend: { display: false }
	}});	
}
