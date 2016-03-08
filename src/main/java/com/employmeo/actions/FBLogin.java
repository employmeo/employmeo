package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.EmpAdminServlet;
import com.employmeo.util.FaceBookHelper;

public class FBLogin extends MPFormAction {

	public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		String code = req.getParameter("code");
    	String token = null;
    	String graph = null;
    	User user = null;
  
        if (code == null || code.equals("")) {
            fRes.addMessage("Facebook Connect Not Approved");
            fRes.setSuccess(false);
        } else {
        	try {
        		token = FaceBookHelper.getAccessToken(code, FaceBookHelper.appLogin, req.getServerName());
            	fRes.put("fbToken", token);
                if (token != null) {
                	graph = FaceBookHelper.getGraph(token);
                   	user = FaceBookHelper.getUserFromGraph(graph);
                   	sess.setAttribute("fbToken", token);
                }
        	} catch (Exception e) {
                fRes.setSuccess(false);
        		fRes.addMessage(e.getMessage());// an error occurred, handle this
        	}
        }
        if (user != null) {
        	user = User.login(user.getUserEmail(), user.getUserPassword());
        	if (user.getUserId() != null) {
				  EmpAdminServlet.login(user, sess, res, req);
        	} else {
        		fRes.setSuccess(false);
        		fRes.addMessage("failed to login");
        	}
        } else {
            fRes.setSuccess(false);
  	  	}
    	  
        if (!fRes.wasSuccessful()) {
  		    fRes.setRedirectJSP("/login.jsp");
        } else {
            String deniedPage = (String) sess.getAttribute("deniedPage");
            if (deniedPage != null) {
            	fRes.setRedirectJSP(deniedPage);
            	sess.removeAttribute("deniedPage");
            }
        }
  
        return;
    }

}