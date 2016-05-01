package com.employmeo.admin;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.employmeo.EmpAdminServlet;
import com.employmeo.EmpAutoLoginFilter;
import com.employmeo.objects.User;


@Path("login")
@PermitAll
public class Login {
	
	private final Response LOGIN_FAILED = Response.status(Response.Status.UNAUTHORIZED).entity("{ message: 'Login failed' }").build();

	@POST
	  @Produces(MediaType.APPLICATION_JSON)
	  public String doPost (
			    @Context final HttpServletRequest reqt,
			    @Context final HttpServletResponse resp,
			    @FormParam("email") String email,
			    @FormParam("password") String password,
			    @DefaultValue("false") @FormParam("rememberme") boolean persistLogin
			    )
	  {  
		  HttpSession sess = reqt.getSession();
	      // Collect Expected Input Fields From Form:
		  // Validate required fields


		  // Execute business logic (lookup the user by email and password)
		  User user = User.login(email, password);
	      if (user.getUserId() !=null) {
	    	  EmpAdminServlet.login(user, sess, resp, reqt);

	    	  if (persistLogin) {
				  EmpAutoLoginFilter.setLoginCookies(resp, user);
			  } else {
				  EmpAutoLoginFilter.removeLoginCookies(resp);
	          }  
		  } else {
			  throw new WebApplicationException(LOGIN_FAILED);
		  }
		  
	      return user.getJSONString();
	  }	  
	  
}