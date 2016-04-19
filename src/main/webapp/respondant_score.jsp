<%@ include file="/WEB-INF/includes/inc_head.jsp"%>
                <div class="">
                    <div class="page-title">
                        <div class="title_left">
                            <h3>Candidate Results</h3>
                        </div>
                        <div class="title_right">
                            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search for...">
                                    <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Go!</button>
                        </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                    <div class="row">
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="x_panel">
                                <div class="x_title">
                                    <h2>Assessment Score</h2>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="x_content">
                                    <div class="col-md-3 col-sm-3 col-xs-12">
										<div class="row content">
						  					<div class="profilebadge" id='candidateicon'>
						    					<i class="fa fa-spinner fa-spin"></i></div>
	                        				<div style='float:left; padding-left:5px;width:72%;'> <h3 id="candidatename">Candidate Name</h3>
	                        				</div>
										</div>
										<div class="row content">
	                        	                <ul class="list-unstyled user_data">
	                        	                	<li></li>
                                            		<li><span id='candidateemail'>email address</span> <i class="fa fa-envelope user-profile-icon"></i></li>
                                            		<li><span id='candidateaddress'>address</span> <i class="fa fa-map-marker user-profile-icon"></i></li>
                                        		</ul>
                                        </div>
										<div class="row content"><hr></div>
  					<div class="row content">
  					<h4>Profile Probability</h4>
  					<span id='candidateemail'>position</span>, <span id='candidateemail'>location applied to</span>
					</div>
  					<div class="row content">
  					<div id="profilesquares">
					<div class="square btn-success">
					  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-spinner fa-spin"></i></div></div></div></div>
					<div class="square btn-info">
					  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-spinner fa-spin"></i></div></div></div></div>
					<div class="square btn-warning">
					  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-spinner fa-spin"></i></div></div></div></div>
					<div class="square btn-danger">
					  <div class="squarecontent"><div class="squaretable"><div class="squaretext text-center"><i class="fa fa-spinner fa-spin"></i></div></div></div></div>
					</div>
					</div>
<hr>



                                    </div>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <div class="profile_title">
                                             <div class="col-md-12"><h2>Applicant Profile</h2></div>
                                        </div>
                                        <div class="profile_content">
                                        	<div><canvas id="respondantProfile"></canvas></div>
                                        </div>
                                     </div>
                                     <div class="col-md-12 col-sm-12 col-xs-12">
                                        <div class="profile_title">
                                             <div class="col-md-12"><h2>Similar Candidates</h2></div>
                                        </div>
                                        <div class="profile_content">
                                        	<div><canvas id="positionTenure"></canvas></div></div>
                                        </div>
                                     </div>
                                    </div>
                                </div>
                            </div>
			</div>
<%@ include file="/WEB-INF/includes/inc_header.jsp"%>
<script>
var urlParams; 
(window.onpopstate = function () {
    var match,
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
})();
var respondantId = urlParams.respondant_id;
if (respondantId != null) {
  processRespondant(respondantId);
}
</script>

</html>