package com.employmeo.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddressUtil {

	private static String googleApiKey;
	
	static {
		googleApiKey = System.getenv("GOOGLE_MAPS_KEY"); 
	}
	
	public static String getMapsKey() {
		return googleApiKey;
	}
	
	public static void validate(JSONObject address) {		
		if (address.has("lat") && address.has("lng")) return;

		String formattedAddress = address.optString("street") + " " +
				address.optString("city") + ", " +
				address.optString("state") + " " +
				address.optString("zip");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://maps.googleapis.com/maps/api/geocode/json").queryParam("key", AddressUtil.getMapsKey());
		try {
			;
			String result = target.queryParam("address", formattedAddress).request(MediaType.APPLICATION_JSON).get(String.class);
			JSONObject json = new JSONObject(result);
			JSONArray results = json.getJSONArray("results");	
			if (results.length() != 1) {
				// TODO - either multiple results, or no result. error handling needed?
				System.out.println("Multiple Options Found!!!");
			} else {
				address.put("formatted_address", results.getJSONObject(0).getString("formatted_address"));
				JSONObject geo = results.getJSONObject(0).getJSONObject("geometry");
				address.put("lat", geo.getJSONObject("location").getString("lat"));
				address.put("lng", geo.getJSONObject("location").getString("lng"));
			}
		} catch (Exception bre) {
			// TODO failed to validate address with lat & lng
		}
		
	}

	public static JSONObject getAddressFromLatLng(double lat, double lng) {		

		String latLng = lat + "," + lng;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://maps.googleapis.com/maps/api/geocode/json").queryParam("key", AddressUtil.getMapsKey());
		JSONObject address = new JSONObject();
		try {
			String result = target.queryParam("latlng", latLng).request(MediaType.APPLICATION_JSON).get(String.class);
			JSONObject json = new JSONObject(result);
			JSONArray results = json.getJSONArray("results");	
			if (results.length() != 1) {
				// TODO handle when zero or multiple addresses hit with lat & lng
				System.out.println("Multiple Options Found!!!");
			} else {
				address.put("formatted_address", results.getJSONObject(0).getString("formatted_address"));
				JSONObject geo = results.getJSONObject(0).getJSONObject("geometry");
				address.put("lat", geo.getJSONObject("location").getString("lat"));
				address.put("lng", geo.getJSONObject("location").getString("lng"));
			}
		} catch (Exception bre) {
			// TODO handle when failed to find an address with lat & lng
		}
		return address;
	}
}
