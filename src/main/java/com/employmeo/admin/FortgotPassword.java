package com.employmeo.admin;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.employmeo.objects.User;
import com.employmeo.util.EmailUtility;

import java.util.logging.Logger;

@Path("forgotpassword")
public class FortgotPassword {

	private static Logger logger = Logger.getLogger("com.employmeo.admin");
	
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void doMethod(@FormParam("email") String email) {

		User user = User.lookupByEmail(email);
		
		if (user != null) {
			EmailUtility.sendForgotPass(user);
		}
		
		return;
	}


}	