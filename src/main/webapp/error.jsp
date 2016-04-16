<%@ page isErrorPage="true" %>

<%@ include file="/WEB-INF/includes/inc_head.jsp" %>

<div id="maincontent" class="main-content">
      <div class="containwide">
      <div class="stretch">
      <div class="row"><div class="sectionheader"><div class="center">An Error has Occurred</div></div></div>
<%
Exception e = pageContext.getException();
ErrorData ed = pageContext.getErrorData();
%>
        <div class="row"><div class="pane" style="text-align:left;">
		<h3>Oops - something went wrong</h3>
		"ERROR_OVERVIEW")%>
		<a href="<%=response.encodeURL("/contact_us.jsp")%>">contact us page.</a>
		</div></div>
        <div class="row"><div class="errorpane" style="text-align:left;">
<h3>Details of error message:</h3>
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
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>
</html>