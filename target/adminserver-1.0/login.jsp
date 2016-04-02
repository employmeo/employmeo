<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<%
String title = LocaleHelper.getMessage(locale, "Login");
String deniedPage = (String) session.getAttribute("deniedPage");
int bkimg = new Random().nextInt(8)+1;

if (deniedPage != null) title = LocaleHelper.getMessage(locale, "Login Required");
%>
<title>Employmeo | <%=title %></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body class="coverpage" style="background-image:url('/images/background-<%=bkimg %>.jpg');">
<div id="loginpanel" class="loginpanel">
    <div class="container-fluid" style="margin=5%;">
       <div class="row text-center"><img src="/images/emp-logo-sm.png"></div>
<form name="login" method="post" action="/mp">
			<div class="row">&nbsp;</div>
			<div class="input-group">
  				<span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
  				<input class="form-control" type="email" name="email" required placeholder="<%=LocaleHelper.getMessage(locale, "Email Address")%>">
			</div>
			<div class="row">&nbsp;</div>
			<div class="row"></div>
			<div class="input-group">
  				<span class="input-group-addon"><i class="fa fa-key fa-fw"></i></span>
  				<input class="form-control" type="password" name="password" placeholder="<%=LocaleHelper.getMessage(locale, "Password")%>" required>
			</div>
			<div class="row">&nbsp;</div>
			<div class="input-group text-right">
			<input type="checkbox" id="rememberme" name="rememberme" value="y" checked><label for="rememberme" style="color: white;"><%=LocaleHelper.getMessage(locale, "Stay Logged In")%></label>
			</div>
			<div class="row">&nbsp;</div>
			<div class="input-group" style="width:100%;">
				<button class="btn btn-primary" type="submit" style="width:100%;"><%=LocaleHelper.getMessage(locale,"Sign In")%></button>
			</div>
       		<div class="row">&nbsp;</div>
		<INPUT type='hidden' name='formname' value='login'>
		<input type="hidden" name="fromJSP" value="<%=request.getRequestURI()%>">
</form>
    </div>
</div>
</html>