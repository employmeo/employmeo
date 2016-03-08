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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.employmeo.objects.Question;
import com.employmeo.objects.Response;
import com.employmeo.util.FaceBookHelper;
import com.employmeo.util.ImageManagementUtil;

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
		  if ((val != null) && (val != "")) {
			  switch (paramname) {
			  	case "response_id":
			  		response.setResponseId(val);
			  		break;
			  	case "response_respondant_id":
			  		response.setResponseRespondantId(new BigInteger(val));
			  		break;
			  	case "response_question_id":
			  		response.setQuestion(Question.getQuestionById(val));
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
	  response.persistMe();
	  
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

		   NodeList forms = surveyConfig.getElementsByTagName("Form");
		   NamedNodeMap facebookAtts = null;
		   NamedNodeMap appAtts = null;
		   String reqtagname = null;

		   try {
			   reqtagname = "Application";
			   appAtts = surveyConfig.getElementsByTagName(reqtagname).item(0).getAttributes();	   
			   reqtagname = "facebook";
			   facebookAtts = surveyConfig.getElementsByTagName(reqtagname).item(0).getAttributes();
		   } catch (Exception e) {
			   throw new Exception("Missing required config tag: " + reqtagname);
		   }
		   
		   for (int i = 0; i < forms.getLength(); i++) {
			   Node node = forms.item(i);
			   NamedNodeMap atts = node.getAttributes();
			   Node formname = atts.getNamedItem("name");
			   if (formname != null) {
				   //String name = formname.getNodeValue();
				   for (int x = 0; x < atts.getLength(); x++) {
					   //Node att = atts.item(x);
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
    
}