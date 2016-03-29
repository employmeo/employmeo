<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Forgot Password")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div id="maincontent" class="main-content">
    <div class="onethirdsolo">
       <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Forgot Password")%></div></div></div>
       <div class="row"><div class="pane">
 
 <form name="forgotpassword" method="post" action="/mp">
        <div class="row"><div class="left"></div><div class="center">&nbsp;</div><div class="right"></div></div>
<div class="row">
  <div class="left"><label for="email"><%=LocaleHelper.getMessage(locale, "Email Address")%>:</label></div>
  <div class="right"><input type="email" size="15" name="email" required></div>
</div>
        <div class="row"><div class="left"></div><div class="center">&nbsp;</div><div class="right"></div></div>
<div class="row">
  <div class="left"></div>
  <div class="center">
  <button class="fbbutton" onclick='window.history.back();return false;'><%=LocaleHelper.getMessage(locale, "cancel")%></button>
  <button class="fbbutton" type="submit"><%=LocaleHelper.getMessage(locale, "reset password")%></button></div>
  <div class="right"></div>
</div>
<INPUT type='hidden' name='formname' value='forgotpassword'>
<input type="hidden" name="fromJSP" value="<%=request.getRequestURI()%>">
</form>
       
       </div></div>
    </div>
</div>
</html>