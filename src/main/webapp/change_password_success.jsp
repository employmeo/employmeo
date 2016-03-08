<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Change Password")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div id="maincontent" class="main-content">
    <div class="onethirdsolo">
       <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Password successfully changed")%></div></div></div>
    </div>
    <div class="row"><div class="pane"><button class="fbbutton" onclick='location="<%=response.encodeURL("/scoreboard.jsp")%>";return false;'><%=LocaleHelper.getMessage(locale, "return")%></button></div></div>
</div>
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>