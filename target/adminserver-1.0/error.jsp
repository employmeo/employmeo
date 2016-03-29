<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Error")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
<div id="maincontent" class="main-content">
      <div class="containwide">
      <div class="stretch">
      <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "An Error has Occurred")%></div></div></div>
<%
Exception e = pageContext.getException();
ErrorData ed = pageContext.getErrorData();
%>
        <div class="row"><div class="pane" style="text-align:left;">
		<h3><%=LocaleHelper.getMessage(locale, "Oops - something went wrong")%></h3>
		<%=LocaleHelper.getMessage(locale, "ERROR_OVERVIEW")%>
		<a href="<%=response.encodeURL("/contact_us.jsp")%>"><%=LocaleHelper.getMessage(locale, "contact us page.")%></a>
		</div></div>
        <div class="row"><div class="errorpane" style="text-align:left;">
<h3><%=LocaleHelper.getMessage(locale, "Details of error message")%>:</h3>
<%
if (ed != null) {
%>
Requested <%=ed.getRequestURI() %> with status code of: <%=ed.getStatusCode() %> <br>
<%
}
if (e != null) {
%>
<pre>Exception: <%=e.getMessage()%></pre>
<%
}
%>
          </div></div>
        </div>
     </div>
</div>
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>