<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title> Employmeo | <%=LocaleHelper.getMessage(locale, "Sign Up")%></Title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div id="maincontent" class="main-content">
<div class="onethirdsolo">
  <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Sign up - Step 1.")%></div></div></div>
  <div class="row"><div class="pane"></div></div>
  <div class="row"><div class="pane">
    <h1><%=LocaleHelper.getMessage(locale, "or sign up using your")%></h1><a style="width:inherit;" class="fbbutton" href="<%=FaceBookHelper.getFBSignupURL(request.getServerName())%>">
    <img width="20px" height="20px" src="/images/FB-f-Logo__blue_29.png"><%=LocaleHelper.getMessage(locale, " facebook account")%></a>
  </div></div>
</div>
</div>
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>