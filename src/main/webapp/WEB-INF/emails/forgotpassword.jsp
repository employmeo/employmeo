<%@ include file="/WEB-INF/includes/inc_all.jsp" %>
<%
User forgottenUser = (User) session.getAttribute("forgot_user");
String link = (String) session.getAttribute("link");
%>
<html>
<body>
Dear <%=forgottenUser.getUserFname() %>,<br>
A password reset was recently requested on your behalf. Please <a href="<%=link %>">click here</a> to continue on to resetting your password at iMotivator.com. Or copy and paste this link into your web browser: <br>
<%=link %><br>
<br>
If you did not request a password reset, please disregard, and delete this email.
<br>
Thank you<br>
iMotivator<br>
</body>
</html>
