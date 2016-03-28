<header>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/" title="Employmeo" rel="home" style="padding-top: 0;padding-bottom: 0;">
      <img height="100%" src="/images/logo-text.png" alt="Employmeo"></a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/my_account.jsp"><i class="fa fa-user"></i>&nbsp;<span id="user_fname"></span></a></li>
        <li><a href="/settings.jsp"><i class="fa fa-cog"></i></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Menu<span class="caret"></span></a>
          <ul class="dropdown-menu">
        	<li><a href="#section1">Dashboard</a></li>
        	<li><a href="/positions.jsp">Job Definitions</a></li>
        	<li><a href="/applications.jsp">Current Applications</a></li>
        	<li><a href="/analytics.jsp">Analytics</a></li>
        	<li><a href="/data_admin.jsp">Data Administration</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Sign out</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
</header>
<script>
$('#user_fname').text(getUserFname());
</script>