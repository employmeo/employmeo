package com.employmeo;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.employmeo.objects.User;

import java.util.*;

@WebFilter(filterName = "autoLoginFilter" , urlPatterns = {"*"})
public class EmpAutoLoginFilter implements Filter {

	private List<String> unrestrictedPageList = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
            String restrictedPages = config.getInitParameter("unrestrictedPages");
            String[] pages = restrictedPages.split(",");
            unrestrictedPageList = Arrays.asList(pages);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
    	Boolean loggedIn = (Boolean) session.getAttribute("LoggedIn");
    	if (loggedIn == null) {
    		loggedIn = false;
        	session.setAttribute("LoggedIn", loggedIn);
    	}

    	String requestedContext = request.getContextPath();
        String requestURI = request.getRequestURI();
        String page = requestURI.substring(requestedContext.length());
        
        if (session.isNew()) {
     	   	session.setAttribute("LoggedIn", loggedIn);
     	   	Locale locale = null;
     	   	if (locale == null) locale = Locale.ENGLISH;
            session.setAttribute("locale", locale);
            
        	try {
        	   if( cookies == null ){
            	 Cookie cookie = new Cookie("CreateDate",Calendar.getInstance().getTime().toString());
        	     cookie.setMaxAge(60*60*24*90); 
        	     response.addCookie( cookie );
        	     cookies = new Cookie[] {cookie};

        	   } else {
        		   String email = null;
        		   String hashword = null;
        		   for (Cookie cookie : cookies) {
        			   String name = cookie.getName(); 
        			   if (name.equals("email")) {
  						   email = cookie.getValue();
        			   }
        			   if (name.equals("hashword")) {
        				   hashword = cookie.getValue();
        			   }
        		   }
        		   if (hashword != null) {
        			   User user = User.loginHashword(email, hashword);
       	  		  	   if (user.getUserId() != null) {
       	  		  		   EmpAdminServlet.login(user, session, response, request);
       	  		  		   loggedIn = (Boolean) session.getAttribute("LoggedIn");
       	  		  	   }
        		   }
        	   }
        	   Cookie visit = new Cookie("LastVisitDate",Calendar.getInstance().getTime().toString());
        	   visit.setMaxAge(60*60*24*90); 
        	   response.addCookie( visit );
        	} catch (Exception e) {
        		session.setAttribute("autologin", e.getMessage());
        	}
        }

        boolean authRequired = false;
        if (!loggedIn) {
        	// check if restricted page
            authRequired = !unrestrictedPageList.contains(page);
        }
        
        if (authRequired) {
        	session.setAttribute("deniedPage", page);
        	req.getRequestDispatcher("/login.jsp").forward(req, res);
        } else {
        	chain.doFilter(req, res); // all done, now just continue request.
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
    
    public static void setLoginCookies(HttpServletResponse res, User user) {
    	  if (user.getUserPassword() == null) return;
    	  String hashword = user.getUserPassword();
		  Cookie cookie = new Cookie("email", user.getUserEmail());
		  cookie.setMaxAge(60*60*24*90); 
		  res.addCookie(cookie);
		  cookie = new Cookie("hashword", hashword);
		  cookie.setMaxAge(60*60*24*90); 
		  res.addCookie(cookie);    	
    	return;
    }

    public static void removeLoginCookies(HttpServletResponse res) {
		  Cookie cookie = new Cookie("email", null);
		  cookie.setMaxAge(1); 
		  res.addCookie(cookie);
		  cookie = new Cookie("hashword", null);
		  cookie.setMaxAge(1); 
		  res.addCookie(cookie);    	
  	return;
  }
}