<html>
<head>
<title>Employmeo | Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, width=device-width"/>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="shortcut icon" type="image/gif" href="/images/favico.gif">
<link rel="stylesheet" type='text/css' href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type='text/css' href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<link rel='stylesheet' type='text/css' href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel='stylesheet' type='text/css' href='/css/admin_style.css' media='all' />
<link rel='stylesheet' type='text/css' href='/css/custom.css' media='all' />
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src='/js/stub_scripts.js'></script>
<script type="text/javascript" src='/js/admin_scripts.js'></script>
<script type="text/javascript" src='/js/custom.js'></script>
</head>
<body class="coverpage" style="background-image:url('/images/background-<%=new java.util.Random().nextInt(8)+1%>.jpg');">
  <div class="">
    <a class="hiddenanchor" id="toregister"></a>
    <a class="hiddenanchor" id="tologin"></a>

    <div id="wrapper">
      <div id="login" style="background: black;color: white;opacity: .85;">
	  <div class="col-xs-12 text-center"><img src='/images/emp-logo-sm.png' style="width:65%;"></div>
      <div class="col-xs-1"></div><div class="col-xs-10">
        <section class="login_content">
		  <form name="login" method="post" action="/mp">
            <h1>Sign In</h1>
			<div><input class="form-control" type="email" name="email" required placeholder="Email Address"></div>
			<div><input class="form-control" type="password" name="password" placeholder="Password" required></div>
			<div class="text-right"></div>
            <div>
              <div class="col-xs-6"><button class="btn btn-default submit" type="submit">Sign In</button></div>
              <div class="col-xs-6"><input type="checkbox" id="rememberme" name="rememberme" value="y" checked><label for="rememberme">Stay Logged In</label></div>
            </div>
            <div class="clearfix"></div>
            <div class="separator"><p class="change_link">New to site?
                <a href="http://www.employmeo.com/contact-us"> Create Account </a></p>
            </div>
		<INPUT type='hidden' name='formname' value='login'>
		<input type="hidden" name="fromJSP" value="<%=request.getRequestURI()%>">
          </form>
        </section>

      </div>
      <div class="col-xs-1"></div></div>
    </div>
  </div>

</body>


</html>