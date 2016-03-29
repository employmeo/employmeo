/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.30
 * Generated at: 2016-03-29 01:23:42 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.math.*;
import org.json.*;
import com.employmeo.*;
import com.employmeo.util.*;
import com.employmeo.objects.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/WEB-INF/includes/inc_all.jsp", Long.valueOf(1459199482000L));
    _jspx_dependants.put("/WEB-INF/includes/inc_footer.jsp", Long.valueOf(1457117884000L));
    _jspx_dependants.put("/WEB-INF/includes/inc_header.jsp", Long.valueOf(1458322524000L));
    _jspx_dependants.put("/WEB-INF/includes/inc_head.jsp", Long.valueOf(1458322524000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("com.employmeo");
    _jspx_imports_packages.add("com.employmeo.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("org.json");
    _jspx_imports_packages.add("com.employmeo.objects");
    _jspx_imports_packages.add("java.math");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" \r\n");

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

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>employmeo | ");
      out.print(LocaleHelper.getMessage(locale, "Dashboard"));
      out.write("</title>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"initial-scale=1.0, maximum-scale=1.0, width=device-width\"/>\r\n");
      out.write("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\r\n");
      out.write("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black-translucent\">\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge,chrome=1\">\r\n");
      out.write("<link rel=\"shortcut icon\" href=\"/images/favico.gif\">\r\n");
      out.write("<link rel=\"icon\" type=\"image/gif\" href=\"/images/favico.gif\">\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">\r\n");
      out.write("<link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css\" rel='stylesheet' type='text/css'>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"//cdn.datatables.net/1.10.11/css/jquery.dataTables.css\">\r\n");
      out.write("<link rel='stylesheet' href='/scripts/admin_style.css' type='text/css' media='all' />\r\n");
      out.write("\r\n");
      out.write("<script src=\"//code.jquery.com/jquery-1.12.1.min.js\"></script>\r\n");
      out.write("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("<script src=\"//cdnjs.cloudflare.com/ajax/libs/Chart.js/1.0.2/Chart.min.js\"></script>\r\n");
      out.write("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/PapaParse/4.1.2/papaparse.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf8\" src=\"//cdn.datatables.net/1.10.11/js/jquery.dataTables.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"/scripts/bootstrap-filestyle.min.js\"> </script>\r\n");
      out.write("<script src='/scripts/admin_scripts.js'></script>");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t");
      out.write("<header>\r\n");
      out.write("<nav class=\"navbar navbar-inverse\">\r\n");
      out.write("  <div class=\"container-fluid\">\r\n");
      out.write("    <!-- Brand and toggle get grouped for better mobile display -->\r\n");
      out.write("    <div class=\"navbar-header\">\r\n");
      out.write("      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">\r\n");
      out.write("        <span class=\"sr-only\">Toggle navigation</span>\r\n");
      out.write("        <span class=\"icon-bar\"></span>\r\n");
      out.write("        <span class=\"icon-bar\"></span>\r\n");
      out.write("        <span class=\"icon-bar\"></span>\r\n");
      out.write("      </button>\r\n");
      out.write("      <a class=\"navbar-brand\" href=\"/\" title=\"Employmeo\" rel=\"home\" style=\"padding-top: 0;padding-bottom: 0;\">\r\n");
      out.write("      <img height=\"100%\" src=\"/images/logo-text.png\" alt=\"Employmeo\"></a>\r\n");
      out.write("    </div>\r\n");
      out.write("    <!-- Collect the nav links, forms, and other content for toggling -->\r\n");
      out.write("    <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">\r\n");
      out.write("      <ul class=\"nav navbar-nav navbar-right\">\r\n");
      out.write("        <li><a href=\"/my_account.jsp\"><i class=\"fa fa-user\"></i>&nbsp;<span id=\"user_fname\"></span></a></li>\r\n");
      out.write("        <li><a href=\"/settings.jsp\"><i class=\"fa fa-cog\"></i></a></li>\r\n");
      out.write("        <li class=\"dropdown\">\r\n");
      out.write("          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Menu<span class=\"caret\"></span></a>\r\n");
      out.write("          <ul class=\"dropdown-menu\">\r\n");
      out.write("        \t<li><a href=\"#section1\">Dashboard</a></li>\r\n");
      out.write("        \t<li><a href=\"/positions.jsp\">Job Definitions</a></li>\r\n");
      out.write("        \t<li><a href=\"/applications.jsp\">Current Applications</a></li>\r\n");
      out.write("        \t<li><a href=\"/analytics.jsp\">Analytics</a></li>\r\n");
      out.write("        \t<li><a href=\"/data_admin.jsp\">Data Administration</a></li>\r\n");
      out.write("            <li role=\"separator\" class=\"divider\"></li>\r\n");
      out.write("            <li><a href=\"#\">Sign out</a></li>\r\n");
      out.write("          </ul>\r\n");
      out.write("        </li>\r\n");
      out.write("      </ul>\r\n");
      out.write("    </div><!-- /.navbar-collapse -->\r\n");
      out.write("  </div><!-- /.container-fluid -->\r\n");
      out.write("</nav>\r\n");
      out.write("</header>\r\n");
      out.write("<script>\r\n");
      out.write("$('#user_fname').text(getUserFname());\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container-fluid\">\r\n");
      out.write("\t\t<div class=\"row content\">\r\n");
      out.write("\t\t\t<div class=\"col-sm-3 sidenav hidden-xs\">\r\n");
      out.write("\t\t\t\t<ul class=\"nav nav-pills nav-stacked\">\r\n");
      out.write("\t\t\t\t\t<li class=\"active\"><a href=\"#section1\">Dashboard</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a href=\"/positions.jsp\">Job Definitions</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a href=\"/applications.jsp\">Current Applications</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a href=\"/analytics.jsp\">Analytics</a></li>\r\n");
      out.write("\t\t\t\t\t<li><a href=\"/data_admin.jsp\">Data Administration</a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t<br>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"col-sm-9\">\r\n");
      out.write("\t\t\t\t<div class=\"row content\">\r\n");
      out.write("\t\t\t\t\t<div class=\"col-sm-12\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t<h4>Dashboard</h4>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-sm-12 hidden-xs small\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<form class=\"form-inline pull-right\" role=\"form\" action=\"\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<label for=\"from_date\">From:</label> <input type=\"date\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"form-control\" id=\"from_date\" name=\"from_date\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<label for=\"to_date\">To:</label> <input type=\"date\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"form-control\" id=\"to_date\" name=\"to_date\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<label for=\"location\">Location:</label> <select\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"form-control\" id=\"location\" name=\"location\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>all</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>store 1</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>store 2</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>store 3</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<label for=\"position\">Position:</label> <select\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"form-control\" id=\"position\" name=\"position\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>all</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>clerk</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>cook</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<option>manager</option>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onClick='updateDash()'><i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-refresh\"></i></button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-xs-12 hidden-xs\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<hr>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-xs-12 col-sm-6 col-md-3 col-lg-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel panel-info\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"text-left\">Applicants <i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-users pull-right\"></i></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-body text-center\"><div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<h1 id=\"headerApplicants\"></h1>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<canvas id=\"dashApplicants\" width=\"100\" height=\"100\"></canvas>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-xs-12 col-sm-6 col-md-3 col-lg-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel panel-info\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"text-left\">Interviewed <i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-line-chart pull-right\"></i></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-body text-center\"><div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<h1 id=\"headerInterviews\"></h1>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<canvas id=\"dashInterviews\" width=\"100\" height=\"100\"></canvas>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-xs-12 col-sm-6 col-md-3 col-lg-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel panel-info\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"text-left\">Hired <i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-bar-chart pull-right\"></i></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-body text-center\"><div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<h1 id=\"headerHires\"></h1>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<canvas id=\"dashHires\" width=\"100\" height=\"100\"></canvas>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-xs-12 col-sm-6 col-md-3 col-lg-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel panel-success\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"text-left\">Projected Turnover <i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-refresh pull-right\"></i></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-body text-center\"><div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<h1 id=\"headerTurnover\"></h1>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<canvas id=\"dashTurnover\" width=\"100\" height=\"100\"></canvas>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"col-sm-12 col-md-12 col-lg-12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"text-left\">Historical Performance <i\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tclass=\"fa fa-bar-chart pull-right\"></i></span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"panel-body text-center\"><div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<canvas id=\"dashHistory\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\tstyle=\"width: 100%, height: auto;\"></canvas>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\tvar now = new Date();\r\n");
      out.write("\tvar from = new Date();\r\n");
      out.write("\tfrom.setTime(now.getTime()-1000*60*60*24*90);\r\n");
      out.write("\tvar fromDay = (\"0\" + from.getDate()).slice(-2);\r\n");
      out.write("\tvar fromMonth = (\"0\" + (from.getMonth() + 1)).slice(-2);\r\n");
      out.write("\tvar fromDate = from.getFullYear()+\"-\"+(fromMonth)+\"-\"+(fromDay) ;\r\n");
      out.write("\t$(\"#from_date\").val(fromDate);\r\n");
      out.write("\t\r\n");
      out.write("\tvar day = (\"0\" + now.getDate()).slice(-2);\r\n");
      out.write("\tvar month = (\"0\" + (now.getMonth() + 1)).slice(-2);\r\n");
      out.write("\tvar toDate = now.getFullYear()+\"-\"+(month)+\"-\"+(day) ;\r\n");
      out.write("\t$(\"#to_date\").val(toDate);\r\n");
      out.write("\r\n");
      out.write("\tupdateDash();\r\n");
      out.write("\tupdateHistory();\r\n");
      out.write("\t</script>\r\n");
      out.write("\r\n");
      out.write("\t");
      out.write("<footer id=\"footer\" class=\"site-footer\">\r\n");
      out.write("</footer>");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
