package com.employmeo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.employmeo.objects.User;

public class FaceBookHelper {
	private static String appID;
	private static String appSecret;
	private static final String siteAddress = "http://www.imotiv8r.com";
	private static final String allPermissions = "email,publish_stream,publish_actions";
	public static final String appSignup = "/mp?formname=fbsignup";
	public static final String appLogin = "/mp?formname=fblogin";
	public static final String appGrant = "/mp?formname=fbgrant";
	public static final String openGraph = "https://graph.facebook.com/";
	public static final String getGraph = "me?fields=id,email,first_name,last_name,permissions,username&";
	public static final String feedPost = "/feed?";
	public static final String goalPost = "/i-motivator-com:set?";
	public static final String taskPost = "/i-motivator-com:create?";
	public static final String registerPost = "/i-motivator-com:signed_up?";
	public static final String rewardPost = "/i-motivator-com:give?";
	private static String serverToken = null;
	private static Date tokenExpires = Calendar.getInstance().getTime();
	public static final long tokenAge = 24*3600*1000;
	private static Logger logger = Logger.getLogger("FaceBookHelper");

	private FaceBookHelper() {
		
	}
	
	public static void staticInit(String id, String secret) {
		appID = id;
		appSecret = secret;
		getServerToken();
	    logger.info("MPFaceBookHelper Initialized with Token: " + serverToken);
	}
	
	public static String getFBLoginURL (String myServer) {
		String fbURL = null;
		try {
			fbURL = "http://www.facebook.com/dialog/oauth?client_id=" + appID + "&redirect_uri=" + URLEncoder.encode("http://"+myServer+appLogin, "UTF-8") + "&scope=" + allPermissions;
		} catch (Exception e) {
			fbURL = e.getMessage();
		}
		return fbURL;
	}

	public static String getFBSignupURL (String myServer) {
		String fbURL = null;
		try {
			fbURL = "http://www.facebook.com/dialog/oauth?client_id=" + appID + "&redirect_uri=" + URLEncoder.encode("http://"+myServer+appSignup, "UTF-8") + "&scope=" + allPermissions;
		} catch (Exception e) {
			fbURL = e.getMessage();
		}
		return fbURL;
	}

	public static String getFBGrantURL (String myServer) {
		String fbURL = null;
		try {
			fbURL = "http://www.facebook.com/dialog/oauth?client_id=" + appID + "&redirect_uri=" + URLEncoder.encode("http://"+myServer+appGrant, "UTF-8") + "&scope=" + allPermissions;
		} catch (Exception e) {
			fbURL = e.getMessage();
		}
		return fbURL;
	}

	public static String postOpenGraph (String facebookID, String app, String query) {
		String url = null;
		String token = getServerToken();
		url = openGraph+facebookID+app;
		String params = token+query;
		return postURLResponse(url, params);
	}

	public static String postUserGraph (String token, String app, String query) {
		String url = null;
		url = openGraph+"me"+app;
		String params = token+query;
		return postURLResponse(url, params);
	}
	
	public static String getAccessToken(String code, String form, String myServer) throws Exception {
        String token = null;
        String g = "https://graph.facebook.com/oauth/access_token?client_id=" + appID + "&redirect_uri=" + URLEncoder.encode("http://"+myServer+form, "UTF-8") + "&client_secret=" + appSecret +"&code=" + code;
        token = getURLResponse(g);
        
        if (token.startsWith("{"))
            throw new Exception("Facebook error requesting: " + token + " with code: " + code);

        return token;
	}
	
	public static String getServerToken() {
		// Check if expired
		Date now = Calendar.getInstance().getTime();
		if (now.after(tokenExpires) || (serverToken == null)) {
			String g = "https://graph.facebook.com/oauth/access_token?client_id=" + appID + "&client_secret=" + appSecret +"&grant_type=client_credentials";
			serverToken = getURLResponse(g);
	        
	        if (serverToken.startsWith("{")) {
	        	serverToken = null;
	        } else {
	        	tokenExpires = new Date(now.getTime()+tokenAge);
	        }
		}
        return serverToken;
	}
	
	public static String getGraph(String token) {
		String g = openGraph + getGraph + token;
		return getURLResponse(g);
	}
	
	public static User getUserFromGraph(String graph) {
		User user = null;
		try {
			user = new User();
			JSONObject json = new JSONObject(graph);
            user.setUserLocale(json.getString("id"));
            user.setUserName(json.getString("username"));
            user.setUserFname(json.getString("first_name"));
            user.setUserLname(json.getString("last_name"));
            user.setUserEmail(json.getString("email"));
            user.setUserPassword(json.getString("id"));
            user.setUserAvatarUrl("http://graph.facebook.com/" + json.getString("username") + "/picture");
            /*
            JSONObject data =  json.optJSONObject("permissions").getJSONArray("data").optJSONObject(0);
            Boolean feed = false;
            Boolean story = false;
            if (data !=null) {
            	feed = data.has("publish_stream");
            	story = data.has("publish_actions");
            }
            user.setAttribute("publish_stream", feed);
            user.setAttribute("publish_actions", story);
            */
        } catch (JSONException e) {
     	    logger.log(Level.SEVERE, "Error Converting Facebook JSON", e);
        }
        
        return user;
	}

	public static void mergeUserWithGraph(String graph, User user) {
		try {
			JSONObject json = new JSONObject(graph);
            //user.setAttribute("facebookId", json.getString("id"));
            user.setUserName(json.getString("username"));
            user.setUserFname(json.getString("first_name"));
            user.setUserLname(json.getString("last_name"));
            user.setUserAvatarUrl("http://graph.facebook.com/" + json.getString("username") + "/picture");
            /*
            JSONObject data =  json.optJSONObject("permissions").getJSONArray("data").optJSONObject(0);
            Boolean feed = false;
            Boolean story = false;
            if (data !=null) {
            	feed = data.has("publish_stream");
            	story = data.has("publish_actions");
            }
            user.setAttribute("publish_stream", feed);
            user.setAttribute("publish_actions", story);
            */ 
        } catch (JSONException e) {
     	    logger.log(Level.SEVERE, "Error Converting Facebook JSON", e);
        }     
        return;
	}
	
	public static String getURLResponse(String url) {
        StringBuffer b = new StringBuffer();
        String response = null;
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();

			BufferedReader in = null;
			if (c.getResponseCode() > 399){
				in = new BufferedReader(new InputStreamReader(c.getErrorStream()));
				logger.warning("Failed to get: " + url);
			} else {
			    in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			}
		    String inputLine;
            while ((inputLine = in.readLine()) != null)	b.append(inputLine);            
            in.close();
            response = b.toString();
		} catch (Exception e) {
     	    logger.log(Level.SEVERE, "Error Converting Facebook JSON", e);
			response = "{ \"message\":\"" + e.getMessage() +"\"}";
		}

		return response;
		
	}
	
	public static String postURLResponse(String url, String params) {

		StringBuffer b = new StringBuffer();
        String response = null;
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());
			out.write(params);
			out.flush();

			BufferedReader in = null;
			if (c.getResponseCode() > 399){
				in = new BufferedReader(new InputStreamReader(c.getErrorStream()));
				logger.warning("Failed to post: " + params + "\n to: " + url);
			} else {
			    in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			}
		    String inputLine;
		    while ((inputLine = in.readLine()) != null)	b.append(inputLine);            
		
		    out.close();
		    in.close();
		    response = b.toString();
		} catch (Exception e) {
     	    logger.log(Level.SEVERE, "Error Converting Facebook JSON", e);
			response = "{ \"message\":\"" + e.getMessage() +"\"}";
		}

		return response;
	}

	public static String externalizeURL(String url) {
		if (url.indexOf("http") == -1) {
			return siteAddress + url;
		}
		return url;
	}
	

}