<!DOCTYPE html>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%@ page import="org.json.*" %>
<%@ page import="com.employmeo.*" %>
<%@ page import="com.employmeo.util.*" %>
<%@ page import="com.employmeo.objects.*" %>
<%@page contentType="text/html; charset=UTF-8" %> 
<%
   Locale locale = (Locale) session.getAttribute("locale");
   if (locale == null) locale = Locale.ENGLISH;
   Boolean loggedIn = (Boolean) session.getAttribute("LoggedIn");
   if (loggedIn == null) loggedIn = false;
   User user = (User) session.getAttribute("User");
   Account account = null;
   if (user != null) {
	   account = user.getAccount();
   }
 
   EmpFormResponse fRes = (EmpFormResponse) session.getAttribute("response");
   Double timezoneOffset = (Double) session.getAttribute("timezoneOffset");
   
   if (fRes !=null) {
     session.removeAttribute("response");
   }
%>