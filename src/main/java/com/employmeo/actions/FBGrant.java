package com.employmeo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.objects.User;
import com.employmeo.util.FaceBookHelper;

public class FBGrant extends MPFormAction {

	public static void execute(HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		String code = req.getParameter("code");
    	String token = null;
    	String graph = null;
    	User user = (User) sess.getAttribute("User");
  
        if (code == null || code.equals("")) {
            fRes.addMessage("Facebook Connect Not Approved");
            fRes.setSuccess(false);
        } else {
        	try {
        		token = FaceBookHelper.getAccessToken(code, FaceBookHelper.appGrant, req.getServerName());
            	fRes.put("fbToken", token);
               	graph = FaceBookHelper.getGraph(token);
               	FaceBookHelper.mergeUserWithGraph(graph,user);
               	user.persistMe();
               	sess.setAttribute("fbToken", token);
        	} catch (Exception e) {
                fRes.setSuccess(false);
        		fRes.addMessage(e.getMessage());// an error occurred, handle this
        	}
        }

        if (!fRes.wasSuccessful()) {
        	fRes.setRedirectJSP("/edit_user.jsp");
        }
        
        return;
    }

}
