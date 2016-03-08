<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Password Reset")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div id="maincontent" class="main-content">
    <div class="onethirdsolo">
       <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Password Reset")%></div></div></div>
       <div class="row"><div class="pane"><%=LocaleHelper.getMessage(locale, "An email has been sent with a link to reset your password. Please check your email")%></div></div>
    </div>
</div>
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>