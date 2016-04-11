// Stub Functions to be removed
function getApplicantData(){
	return [
            {
                value: 100,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 300,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 250,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 20,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getInterviewData(){
	return [
            {
                value: 5,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 150,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 180,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 18,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getHireData(){
	return [
            {
                value: 2,
                color:"#ff0000",
                highlight: "#ff5050",
                label: "Red Flag"
            },
            {
                value: 75,
                color: "#ffff00",
                highlight: "#ffff66",
                label: "Churner"
            },
            {
                value: 125,
                color: "#3399ff",
                highlight: "#0066ff",
                label: "Long Timer"
            },
            {
                value: 15,
                color: "#00ff00",
                highlight: "#66ff99",
                label: "Rising Star"
            }
        ];
}

function getTurnoverData() {
	return {
	    labels: ["Projection", "Target"],
	    datasets: [
	        {
	            label: "Months",
	            fillColor: "rgba(120,220,220,0.5)",
	            strokeColor: "rgba(120,220,220,0.8)",
	            highlightFill: "rgba(120,220,220,0.75)",
	            highlightStroke: "rgba(120,220,220,1)",
	            data: [8.8, 6]
	        }
	    ]
	};
}

function getHistoryData() {
	return {
	    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
	    datasets: [
	        {
	            label: "Hire Ratio",
	            fillColor: "rgba(120,220,220,0.5)",
	            strokeColor: "rgba(120,220,220,0.8)",
	            highlightFill: "rgba(120,220,220,0.75)",
	            highlightStroke: "rgba(120,220,220,1)",
	            data: [6.1,6.0,6.0,5.8,6.1,4.5,4.3,3.9,3.5,3.2,3.1,3.2]
	        },
	        {
	            label: "Projected Tenure",
	            fillColor: "rgba(120,120,220,0.5)",
	            strokeColor: "rgba(120,120,220,0.8)",
	            highlightFill: "rgba(120,120,220,0.75)",
	            highlightStroke: "rgba(120,120,220,1)",
	            data: [6.1,6.0,6.0,5.8,6.1,7.5,7.3,7.9,7.5,8.2,8.1,2.2]
	        }

	    ]
	};
}

function getPositionTenureData() {
	return {
	    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
	    datasets: [
	        {
	            label: "count",
	            fillColor: "rgba(220,220,220,0.2)",
	            strokeColor: "rgba(220,220,220,1)",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(220,220,220,1)",
	            data: [45,15,25,40,45,55,65,50,40,30,20,15,13,14,11,10,9]
	        }
	    ]
	};
}

function getApplicantProfileData() {
	return {
	    labels: ["Openness", "Conscientousness", "Confidence", "Sociability", "Compassion"],
	    datasets: [
	        {
	            label: "Applicant",
	            fillColor: "rgba(220,220,220,0.2)",
	            strokeColor: "rgba(220,220,220,1)",
	            pointColor: "rgba(220,220,220,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(220,220,220,1)",
	            data: [1, 3, 5, 4, 2]
	        },
	        {
	            label: "Average Score",
	            fillColor: "rgba(151,187,205,0.2)",
	            strokeColor: "rgba(151,187,205,1)",
	            pointColor: "rgba(151,187,205,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(151,187,205,1)",
	            data: [4, 3, 4, 2, 1]
	        }
	    ]
	};
}


function getRespondantScore() {
	var jResp = {
		respondant: {
			respondant_person_fname: 'Joe',
			respondant_person_lname: 'White',
			respondant_survey_name: 'Basic Application'
		},
		position: getPositionDetails(),
		scores: getRandomScores()
	};
	return jResp;
}

function getRandomScores() {
	return {
		'Conscentiousness' : 4.5,
		'Stability' : 4.7,
		'Extraversion' : 4.9,
		'Openness' : 3.7,
		'Drive' : 5				
	};
}

function getPositionDetails() {
	
	var position = {
			position_name: 'Clerk',
			position_corefactors: [	'Conscentiousness', 'Stability', 'Extraversion', 'Openness', 'Drive'],
			position_profiles: [{
			    	 profile_name: 'Rising Star',
			    	 profile_class: 'square risingstar',
			    	 profile_color: 'rgba(61,191,63,1)',
			    	 profile_highlight: 'rgba(61,191,63,0.5)',
			    	 profile_scores: {
						'Conscentiousness' : 4.9,
						'Stability' : 4.9,
						'Extraversion' : 5.0,
						'Openness' : 4.7,
						'Drive' : 5			
			    	 },
			    	 profile_tenure_data: {
			    		    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
			    		    datasets: [{ data: [2,3,1,1,2,2,3,4,4,5,10,15,25,30,33,30,25] }]
			    	}
				},
			     {
			    	 profile_name: 'Long Timer',
			    	 profile_class: 'square longtimer',
			    	 profile_color: 'rgba(63, 127, 191, 1)',
			    	 profile_highlight: 'rgba(63, 127, 191, 0.6)',
			    	 profile_scores: {
						'Conscentiousness' : 3.5,
						'Stability' : 3.7,
						'Extraversion' : 3.9,
						'Openness' : 3.2,
						'Drive' : 3.8				
			    	 },
			    	 profile_tenure_data: {
			    		    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
			    		    datasets: [{ data: [3,5,10,20,35,45,65,70,50,40,30,25,18,14,11,10,9] }]
			    	 }
			     },
			     {
			    	 profile_name: 'Churner',
			    	 profile_class: 'square churn',
			    	 profile_color: 'rgba(191, 191, 63, 1)',
			    	 profile_highlight: 'rgba(191, 191, 63, 0.7)',
			    	 profile_scores: {
						'Conscentiousness' : 2.5,
						'Stability' : 2.7,
						'Extraversion' : 2.9,
						'Openness' : 1.7,
						'Drive' : 2.7				
			    	 },
			    	 profile_tenure_data: {
			    		    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
			    		    datasets: [{ data: [60,75,55,30,15,10,5,3,1,0,0,0,0,0,0,0,0] }]
			    	 }
			     },
				{
			    	 profile_name: 'Red Flag',
			    	 profile_class: 'square redflag',
			    	 profile_color: 'rgba(191, 63, 63, 1)',
			    	 profile_highlight: 'rgba(191, 63, 63, 0.8)',
			    	 profile_scores: {
						'Conscentiousness' : 1.5,
						'Stability' : 1.7,
						'Extraversion' : 1.9,
						'Openness' : 0.7,
						'Drive' : 2				
			    	 },
			    	 profile_tenure_data: {
			    		    labels: ["1","2","3","4","5","6","7","8","9","10","11","12","14","15","16","17","18"],
			    		    datasets: [{ data: [80,10,7,3,0,0,0,0,0,0,0,0,0,0,0,0,0] }]
			    	 }
			     }]
		};
	
	return position;
	
}