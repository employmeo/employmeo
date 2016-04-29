package com.employmeo.integration;

import java.io.IOException;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.employmeo.objects.Partner;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class RestAuthenticationProvider implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users !!").build();

	@Context
    private ResourceInfo resourceInfo;  
	
	@Override
	public void filter(ContainerRequestContext req) throws IOException {

        Method method = resourceInfo.getResourceMethod();
        System.out.println("Method Called:" + method.getName());
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                req.abortWith(ACCESS_FORBIDDEN);
                System.out.println("Deny Everyone");
                return;
            }
              
            //Get request headers
            final MultivaluedMap<String, String> headers = req.getHeaders();
            //Fetch authorization header
            final List<String> auth = headers.get(AUTHORIZATION_PROPERTY);
            
            //If no authorization information present; block access
            if(auth == null || auth.isEmpty())
            {
                req.abortWith(ACCESS_DENIED);
                System.out.println("No Auth header");
                return;
            }
              
            //Split username and password tokens
            final StringTokenizer tokenizer = decode(auth);
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
            Partner partner = Partner.loginPartner(username, password);

            if(partner == null) {
                req.abortWith(ACCESS_DENIED);
                System.out.println("Partner Login Failed");
                return;
            } else if(method.isAnnotationPresent(RolesAllowed.class))
            {
            	RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                  
                //Is user valid?
                if( ! isPartnerAllowed(partner, rolesSet))
                {
                    req.abortWith(ACCESS_DENIED);
                    System.out.println("Access Level Failed");
                    return;
                }
            }
        }
        System.out.println("Resource Allowed:" + method.getName());        
	}
	
    private boolean isPartnerAllowed(Partner partner, final Set<String> rolesSet)
    {
        boolean isAllowed = false;         
        if(partner != null) {
            isAllowed = true;
        }
        return isAllowed;
    }
    
	 public static StringTokenizer decode(List<String> auth) {
         final String encodedUserPassword = auth.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
         String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
         final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
         return tokenizer;
	 }
}