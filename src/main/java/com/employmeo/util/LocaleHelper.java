package com.employmeo.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LocaleHelper {
	
	private static HashMap<Locale,HashMap<String,String>> messageLists = new HashMap<Locale,HashMap<String,String>>();
	private static Logger logger = Logger.getLogger("com.employmeo.util.MPLocaleHelper");
    public static HashMap<String,Locale> localeSet = new HashMap<String,Locale>();	
	
	private LocaleHelper() {
	}
	
	public static String getMessage(Locale locale, String message) {
	
		HashMap<String,String> messages = getMessages(locale);
		
		String localizedMessage = messages.get(message);
		if (localizedMessage == null) {
			localizedMessage = message;
			messages.put(message, message);
			logger.warning("<message locale=\""+ locale +"\" key=\""+message+"\" value=\""+message+"\" />");
		}		
		return localizedMessage;
	}
	
	 
    public static Locale getLocaleForString(String localeName) {
        Locale locale = localeSet.get(localeName);
        if (locale == null) {
        	locale = new Locale(localeName);
        	localeSet.put(localeName, locale);
        }
        return locale;
    }
	
	private static HashMap<String,String> getMessages(Locale locale) {

		HashMap<String,String> messages = messageLists.get(locale);

		if (messages == null) {
			messages = new HashMap<String,String> ();
			messageLists.put(locale, messages);
		}

		return messages;
	}
	
	public static void staticInit (String messageConfigFile) throws Exception {

		  try {
			   File configFile = new File(messageConfigFile);

			   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			   
			   Document messageConfig = dBuilder.parse(configFile);
			   
			   messageConfig.getDocumentElement().normalize();

			   NodeList messages = messageConfig.getElementsByTagName("message");
			   
			   for (int i = 0; i < messages.getLength(); i++) {
				   Node node = messages.item(i);
				   NamedNodeMap atts = node.getAttributes();
				   String messageKey = atts.getNamedItem("key").getNodeValue();
				   String messageLocale = atts.getNamedItem("locale").getNodeValue();
				   String messageValue = atts.getNamedItem("value") .getNodeValue();
				   Locale locale = new Locale(messageLocale);
				   getMessages(locale).put(messageKey, messageValue);
			   }
			   logger.info("Initialized with " + messages.getLength() + " localized strings.");
			   
		  } catch (Exception e) {
			  throw new Exception(e);
		  }  
		  
	  }
	
	public static String getFormattedDate(Locale locale, java.util.Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String getFormattedDate(Locale locale) {
		return getFormattedDate(locale, 0.0);
	}

	public static String getFormattedDate(Locale locale, Double timezoneOffset) {
		long offset = Math.round(1000*60*60*timezoneOffset);
		java.util.Date now = new java.util.Date(java.util.Calendar.getInstance().getTime().getTime()+offset);
		return getFormattedDate(locale, now);
	}

	public static java.sql.Date getDateFromString(Locale locale, String date) throws Exception {
		java.util.Date uDate =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
		return new java.sql.Date(uDate.getTime());
	}

	public static String getDayFromDate(Locale locale, java.util.Date date) {
		return new SimpleDateFormat("EEE", locale).format(date);
	}

	public static String getLocalizedDate(Locale locale, java.util.Date date) {
		return new SimpleDateFormat("EEE, MMMM dd", locale).format(date);
	}

	public static String getLocalizedDate(Locale locale, int day, Double timezoneOffset) {
		long time = java.util.Calendar.getInstance().getTime().getTime();
		time = time - day*24*60*60*1000 + Math.round(timezoneOffset*1000*60*60);
		java.util.Date date = new java.util.Date(time);
		return getLocalizedDate(locale, date);
	}
	
	public static String getDayFromDate(Locale locale, int day, Double timezoneOffset) {
		long time = java.util.Calendar.getInstance().getTime().getTime();
		time = time - day*24*60*60*1000 + Math.round(timezoneOffset*1000*60*60);
		java.util.Date date = new java.util.Date(time);
		return getDayFromDate(locale, date);
	}
	
	public static String getDateString(Locale locale, int day, Double timezoneOffset) {
		long time = java.util.Calendar.getInstance().getTime().getTime();
		time = time - day*24*60*60*1000 + Math.round(timezoneOffset*1000*60*60);
		java.util.Date date = new java.util.Date(time);
		return getFormattedDate(locale, date);
	}
	
	public static String getLocalizedDate(Locale locale, int day) {
		return getLocalizedDate(locale, day, 0.0);
	}
	
	public static String getDayFromDate(Locale locale, int day) {
		return getDayFromDate(locale, day, 0.0);
	}
	
	public static String getDateString(Locale locale, int day) {
		return getDateString(locale, day, 0.0);
	}
		
}