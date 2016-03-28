<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<%
User newParent = (User) session.getAttribute("newParent");
String link = (String) session.getAttribute("link");
%>
<html>
<body>
Dear <%=newParent.getUserFname() %>,<br>
<%=user.getUserFname()%>&nbsp;<%=user.getUserLname()%> has invited you to join employmeo.com.
Your temporary login information is:<br>
<b>username:</b> <%=newParent.getUserEmail() %><br>
<b>password:</b> <%=newParent.getUserPassword() %><br>

<a href="<%=link %>">click here</a> to complete the registration, or copy and paste this link into your web browser:<br>
<%=link %><br>
<br>
Thank you<br>
employmeo<br>
</body>
</html>
