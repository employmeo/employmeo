package com.employmeo;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.lang.reflect.Method;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.employmeo.objects.User;
import com.employmeo.util.FaceBookHelper;
import com.employmeo.util.ImageManagementUtil;

@WebServlet(value="/mp")
@MultipartConfig(fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5)
public class EmpAdminServlet extends HttpServlet {

  public static final long serialVersionUID = 0;
  private List<String> restrictedFormList = null;
  private Document mpConfig = null;
  private final String FILENAME = "/WEB-INF/employmeo.xml";

  private HashMap<String,String> formClasses = null;
  private HashMap<String,String> formSuccess = null;
  private static Logger logger = Logger.getLogger("EmpAdminServlet");
  
  public void doGet (HttpServletRequest req,
                     HttpServletResponse res)
    throws ServletException, IOException
  {
    doPost(req, res);
  }

  public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
	  res.setCharacterEncoding("UTF-8");
      HttpSession sess = req.getSession();
      PrintWriter out = res.getWriter();
	  Boolean loggedIn = (Boolean) sess.getAttribute("LoggedIn");	  
	  String formname = req.getParameter("formname");
	  String fromJSP = req.getParameter("fromJSP");
	  String noRedirect = req.getParameter("noRedirect");
	  String redirectJSP = req.getParameter("redirectJSP");

	  Enumeration<String> parameters = req.getParameterNames();
	  EmpFormResponse fRes = new EmpFormResponse();
	  fRes.setFormName(formname);

	  while (parameters.hasMoreElements()) {
		  String paramname = parameters.nextElement();
		  fRes.put(paramname, req.getParameter(paramname));
	  }

	  String action = getClass(formname);
	  if (action != null) {
	      logger.info("Executing " + formname + " using: " + action + "\n and (input): " + fRes);
		  if (restrictedFormList.contains(formname) && !loggedIn) {
		      fRes.setValid(false);
			  fRes.setSuccess(false);
			  fRes.setRedirectJSP("/login.jsp");
			  fRes.addMessage("Must be logged in to submit form: " + formname);
		  } else {
			  try {
				  Method method = Class.forName(action).getDeclaredMethod("execute", new Class[] {HttpServletRequest.class, HttpServletResponse.class, HttpSession.class, EmpFormResponse.class});
				  method.invoke(null, req, res, sess, fRes);
			  } catch (Exception e) {
				  e.printStackTrace(System.out);
			  }			  
		  }
	  } else {
		  	fRes.setValid(false);
			fRes.addMessage("Unknown action");
			fRes.setRedirectJSP("/error.jsp");
	  }
	  
	  if  (req.isRequestedSessionIdValid()) sess.setAttribute("response", fRes);

	  if (!fRes.isRedirected()) {
		  if (!fRes.wasValid() || !fRes.wasSuccessful()) {
			  fRes.setRedirectJSP(fromJSP);
		  } else if (noRedirect == null) {
		      if (redirectJSP == null) redirectJSP = getSuccessPage(formname);
		      fRes.setRedirectJSP(redirectJSP);
		  }
	  }

      logger.info("Completed " + formname + " using: " + action + "\n and (output): " + fRes);
	  
	  if (fRes.isRedirected()) {
		  req.getRequestDispatcher(fRes.getRedirectJSP()).forward(req, res);
      } else {
    	  out.print(fRes.getHTML());
      }
  }
  
  public static void login(User user, HttpSession sess, HttpServletResponse res, HttpServletRequest req) {
	  
	  
	  sess.setAttribute("LoggedIn", new Boolean(true));
	  sess.setAttribute("User", user);
	  try {
		  Locale locale = new Locale(user.getUserLocale());
		  sess.setAttribute("locale", locale);
	  } catch (Exception e) {
		  logger.warning("Failed to set locale for user: " + user + "\n" + e.toString());
	  }
	  if (user.getUserFname() != null) {
		  Cookie cookie = new Cookie("user_fname", user.getUserFname());
		  //cookie.setMaxAge(60*60); 
		  res.addCookie(cookie);
	  }
	  //ImageManagementUtil.prepCustomImage(user.getUserAvatarUrl(), req);
	  	  
	  return;
  }
  
  @Override
  public void init (ServletConfig config) throws ServletException {
	  TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	  restrictedFormList = new ArrayList<String>();
	  formClasses = new HashMap<String,String>();
	  formSuccess = new HashMap<String,String>();

	  try {
		   File configFile = new File(config.getServletContext().getRealPath(FILENAME));
		   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		   DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		   mpConfig = dBuilder.parse(configFile);
		   mpConfig.getDocumentElement().normalize();

		   NodeList forms = mpConfig.getElementsByTagName("Form");
		   NamedNodeMap facebookAtts = null;
		   NamedNodeMap appAtts = null;
		   String reqtagname = null;

		   try {
			   reqtagname = "Application";
			   appAtts = mpConfig.getElementsByTagName(reqtagname).item(0).getAttributes();	   
			   reqtagname = "facebook";
			   facebookAtts = mpConfig.getElementsByTagName(reqtagname).item(0).getAttributes();
		   } catch (Exception e) {
			   throw new Exception("Missing required config tag: " + reqtagname);
		   }
		   
		   for (int i = 0; i < forms.getLength(); i++) {
			   Node node = forms.item(i);
			   NamedNodeMap atts = node.getAttributes();
			   Node formname = atts.getNamedItem("name");
			   if (formname != null) {
				   String name = formname.getNodeValue();
				   for (int x = 0; x < atts.getLength(); x++) {
					   Node att = atts.item(x);
					   if ("class".equals(att.getNodeName())) formClasses.put(name, att.getNodeValue());
					   if ("success".equals(att.getNodeName())) formSuccess.put(name, att.getNodeValue());
					   if ("loginrequired".equals(att.getNodeName())) restrictedFormList.add(name);   
				   }
			   }
		   }

		   String appID = facebookAtts.getNamedItem("appID").getNodeValue(); 
		   String appSecret = facebookAtts.getNamedItem("appSecret").getNodeValue(); 
		   FaceBookHelper.staticInit(appID, appSecret);

		   ImageManagementUtil.staticInit(config.getServletContext().getRealPath(ImageManagementUtil.AVATARPATH));   
		   
		   StringBuffer initParams = new StringBuffer("AppInfo: ");
		   for (int i = 0; i < appAtts.getLength(); i++) {
			   Node att = appAtts.item(i);
			   initParams.append(att.getNodeName() +"=" + att.getNodeValue() + " ");
		   }
		   logger.info(initParams.toString());
		   
	  } catch (Exception e) {
		  throw new ServletException(e);
	  }  
	  
  }
  
  private String getClass(String form) {
	  return formClasses.get(form);
  }
  
  private String getSuccessPage(String form) {
	  return formSuccess.get(form);	  
  }
  
}