package com.employmeo;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;
import javax.jws.WebService;
import javax.servlet.*;

import com.employmeo.objects.Question;
import com.employmeo.objects.Response;

@WebService
public class EmpSurveyRestService {

  public static final long serialVersionUID = 0;
  private static Logger logger = Logger.getLogger("EmpSurveyRestService");
  
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
			  		response.setResponseId(new BigInteger(val));
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

	  response.persistMe();
	  logger.log(Level.INFO, "Saved response: " + response);
	  out.print(response.getJSON());
  }
  
  public void init (ServletConfig config) throws ServletException {
	  TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	  try {
		  System.out.println("Initialize Web Service?");		   
		   
	  } catch (Exception e) {
		  throw new ServletException(e);
	  }  
	  
  }
    
}