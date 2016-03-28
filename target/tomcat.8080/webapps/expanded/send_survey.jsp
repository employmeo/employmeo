<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<html>
<head>
<title>Employmeo | <%=LocaleHelper.getMessage(locale, "Contact Us")%></title>
<%@ include file="/WEB-INF/includes/inc_head.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/includes/inc_header.jsp" %>

<div id="maincontent" class="main-content">
   <div class="containwide">
    <div class="onethirdsolo">
       <div class="row"><div class="sectionheader"><div class="center"><%=LocaleHelper.getMessage(locale, "Contact Us")%></div></div></div>
       <div class="row"><div class="pane">
       
<form name="newparent" method="post" action="/mp"><table><tbody>
<TR><TD colspan="2" class="center" align="center"><h1><%=LocaleHelper.getMessage(locale, "Make a request")%></h1></td></tr>
<TR><TD colspan="2" class="center" align="center">&nbsp;</td></tr>
<TR><TD class="left" align="left"><%=LocaleHelper.getMessage(locale, "Request Type")%>:</TD>
  <TD align="right" class="right"><SELECT name="request_type">
    <OPTION value="suggestion"><%=LocaleHelper.getMessage(locale, "Suggestion")%></OPTION>
    <OPTION value="issue"><%=LocaleHelper.getMessage(locale, "Report an Issue")%></OPTION>
    <OPTION value="help"><%=LocaleHelper.getMessage(locale, "Need Help")%></OPTION>
    <OPTION value="other"><%=LocaleHelper.getMessage(locale, "Other Request")%></OPTION>
  </SELECT></TD></TR>
<TR><TD class="left" align="left"><%=LocaleHelper.getMessage(locale, "Message")%>:</TD><TD align="right" class="right"><textarea rows="4" required></textarea></TD></TR>


<TR><TD class="left" align="left"><%=LocaleHelper.getMessage(locale, "E-Mail")%>:</TD><TD align="right" class="right"> <input type="email" size="20" name="email"></TD></TR>
<TR><TD class="left" align="left"><%=LocaleHelper.getMessage(locale, "First Name")%>:</TD><TD align="right" class="right"> <input type="text" size="20" name="firstname"></TD></TR>
<TR><TD class="left" align="left"><%=LocaleHelper.getMessage(locale, "Last Name")%>:</TD><TD align="right" class="right"> <input type="text" size="20" name="lastname"></TD></TR>

<TR><TD colspan="2" class="center" align="center">
   <%if (loggedIn) {%><INPUT type='hidden' name='user_id' value='<%=user.getUserId()%>'> <%} %>
   <INPUT type='hidden' name='formname' value='contactus'>
   <input type="hidden" name="fromJSP" value="<%=request.getRequestURI()%>">
   <button class="fbbutton" type="submit"><%=LocaleHelper.getMessage(locale, "submit")%></button>
   <button class="fbbutton" onclick='window.history.back();return false;'><%=LocaleHelper.getMessage(locale, "cancel")%></button>
   </td></tr>
</tbody></TABLE></form>
       
       </div></div>
       
       
    </div>
   </div>
</div>
<%@ include file="/WEB-INF/includes/inc_footer.jsp" %>
</html>

