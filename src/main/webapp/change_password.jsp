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
       <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Change Password")%></div></div></div>
       <div class="row"><div class="pane">
<form name="changepassword" method="post" action="/mp">
<table><tbody>
<TR><TD colspan="2" class="center" align="center"><h1><%=LocaleHelper.getMessage(locale, "Change Password")%></h1></td></tr>
<TR><TD colspan="2" class="center" align="center">&nbsp;</td></tr>
<% 
String hashword = request.getParameter("hashword");
if (hashword != null) {
	session.setAttribute("hashword", hashword);
} else {
%>
<TR><TD class="left" align="left" ><%=LocaleHelper.getMessage(locale, "Password")%>:</TD><TD class="right" align="right" ><input type="password" size="15" name="old_password" required></TD></TR>
<%
}
%>
<TR><TD class="left" align="left" ><%=LocaleHelper.getMessage(locale, "New Password")%>:</TD><TD class="right" align="right" ><input type="password" size="15" name="new_password" required></TD></TR>
<TR><TD class="left" align="left" ><%=LocaleHelper.getMessage(locale, "Confirm Password")%>:</TD><TD class="right" align="right" ><input type="password" size="15" name="confirm_password" required></TD></TR>
<TR><TD colspan="2" align="right">
   <INPUT type='hidden' name='formname' value='changepassword'>
   <input type="hidden" name="fromJSP" value="<%=request.getRequestURI()%>">
   <button class="fbbutton" type="submit"><%=LocaleHelper.getMessage(locale, "change")%></button>
   <button class="fbbutton" onclick='window.history.back();return false;'><%=LocaleHelper.getMessage(locale, "cancel")%></button>
   </td></tr>
</tbody></TABLE>
</form>
</div></div>
    </div>
</div>
</html>