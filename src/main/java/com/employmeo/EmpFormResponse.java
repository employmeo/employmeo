package com.employmeo;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmpFormResponse extends JSONObject {
	
	public EmpFormResponse () {
		this.setFormName(null);
		this.setRedirectJSP(null);	
		setSuccess(true);
		setValid(true);
	}
	
	public String getFormName() {
		return optString("formname");
	}

	public void setFormName(String formname) {
		put("formname",formname);
	}

	public String getRedirectJSP() {
		return optString("redirectJSP");
	}

	public void setRedirectJSP(String redirectJSP) {
		put("redirectJSP", redirectJSP);
	}

	public boolean isRedirected() {
		return has("redirectJSP");
	}

	public void setSuccess(boolean success) {
		put("success", success);
	}

	public void setValid(boolean valid) {
		put("valid", valid);
	}

	public void setHTML(String text) {
		put("HTMLResponse", text);
	}
	
	public boolean wasSuccessful() {
		return getBoolean("success");
	}

	public boolean wasValid() {
		return getBoolean("valid");
	}
	
	public String getHTML() {
		return optString("HTMLResponse");
	}
	
	public void addInvalidField(String param) {
		accumulate("invalidfields", param);
		return;
	}

	public JSONArray getInvalidFields() {
		JSONArray messages = optJSONArray("invalidfields");
		if (messages == null) {
			messages = new JSONArray();
			if (has("invalidfields")) {
				String message = getString("invalidfields");
				messages.put(message);
			}
		}
		return messages;
	}
	
	public void addMessage(String message) {
		accumulate("messages", message);
	}
	
	public JSONArray getMessages () {
		JSONArray messages = optJSONArray("messages");
		if (messages == null) {
			messages = new JSONArray();
			if (has("messages")) {
				String message = optString("messages");
				messages.put(message);
			}
		}
		return messages;
	}

}
