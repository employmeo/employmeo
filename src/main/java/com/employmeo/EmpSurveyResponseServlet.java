package com.employmeo;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import com.employmeo.objects.Response;
import com.employmeo.util.FaceBookHelper;

public class EmpSurveyResponseServlet extends HttpServlet {

  public static final long serialVersionUID = 0;
  private Document surveyConfig = null;
  private final String FILENAME = "/WEB-INF/employmeo.xml";
  private static Logger logger = Logger.getLogger("EmpSurveyResponseServlet");
  
  public void doGet (HttpServletRequest req,
                     HttpServletResponse res)
    throws ServletException, IOException
  {
    doPost(req, res);
  }

  public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
	  res.setCharacterEncoding("UTF-8");
      PrintWriter out = res.getWriter();
	  Enumeration<String> parameters = req.getParameterNames();
	  Response response = new Response();
	  
	  while (parameters.hasMoreElements()) {
		  String paramname = parameters.nextElement();
		  String val = req.getParameter(paramname);
		  System.out.println(paramname +": "+ val);
		  if ((val != null) && (val != "")) {
			  switch (paramname) {
			  	case "response_id":
			  		response.setResponseId(new BigInteger(val));
			  		break;
			  	case "response_respondant_id":
			  		response.setResponseRespondantId(new BigInteger(val));
			  		break;
			  	case "response_question_id":
			  		response.setResponseQuestionId(new BigInteger(val));
			  		break;
			  	case "response_value":
			  		response.setResponseValue(new Integer(val));
			  		break;
			  	case "response_text":
			  		response.setResponseText(val);
			  		break;
			  	default:
			  		break;
			  }
		  }
	  }

	  System.out.println(response.getJSON());
	  if (response.getResponseId() == null) {
		  response.persistMe();
	  } else {
		  response.mergeMe();
	  }
	  
	  out.print(response.getJSON());
  }
  
  @Override
  public void init (ServletConfig config) throws ServletException {
	  TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	  logger.log(Level.INFO, "Survey Servlet Initializing");	  
	  try {
		   File configFile = new File(config.getServletContext().getRealPath(FILENAME));
		   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		   DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		   surveyConfig = dBuilder.parse(configFile);
		   surveyConfig.getDocumentElement().normalize();

		   NamedNodeMap facebookAtts = null;

		   try {
			   facebookAtts = surveyConfig.getElementsByTagName("facebook").item(0).getAttributes();
			   String appID = facebookAtts.getNamedItem("appID").getNodeValue(); 
			   String appSecret = facebookAtts.getNamedItem("appSecret").getNodeValue(); 
			   FaceBookHelper.staticInit(appID, appSecret);
		   } catch (Exception e) {
			   throw new Exception("Missing required facebook config");
		   }
		   
		   		   
	  } catch (Exception e) {
		  throw new ServletException(e);
	  }  
	  
  }
    
}