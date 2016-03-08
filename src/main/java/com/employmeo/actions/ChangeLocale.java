package com.employmeo.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.employmeo.EmpFormResponse;
import com.employmeo.util.LocaleHelper;

public class ChangeLocale extends MPFormAction {
	 
	 public static void execute (HttpServletRequest req, HttpServletResponse res, HttpSession sess, EmpFormResponse fRes) {
		   String localeString = req.getParameter("new_locale");
		   Locale newLocale = LocaleHelper.getLocaleForString(localeString); 
		   sess.setAttribute("locale", newLocale);
		 return;
	  }


}
