 package com.example.locateit.utils;

public class AllURL {

	/***
	 * 
	 * Login URL
	 */
	public static String loginURL(String email, String password) {
		return BaseURL.HTTP + "login.php?EmailAddress=" + email + "&Password="
				+ password;
	}

	
	/***
	 * 
	 * View NearBy List
	 */

	public static String nearByURL(String UPLat,String UPLng,String query,String apiKey) {
		return BaseURL.HTTP + "nearbysearch/json?location="+UPLat+","+UPLng+"&rankby=distance&types="+query+"&sensor=false&key="+apiKey;
	}

	
	/***
	 * 
	 * View NearBy List :: Local Data
	 */

	public static String nearByURLLocal(String query,String latitude,String longitude) {
		return BaseURL.HTTPLOCAL + "api/marker.php/markers/search/"+query+"/"+latitude+"/"+longitude;
	}
	
	/***
	 * 
	 * View NearBy List :: Local Data
	 */

	public static String nearByURLLocal2(String query) {
		return BaseURL.HTTPLOCAL + "api/marker.php/markers/search2/"+query;
	}
	
	
	/***
	 * 
	 * View Items List :: Local Data
	 */

	public static String itemsURLLocal(String query) {
		return BaseURL.HTTPLOCAL + "api/marker_item.php/markerItems/search/"+query;
	}
	
	
	/***
	 * 
	 * View Items Reviews :: Local Data
	 */

	public static String itemsReviewURLLocal(String reference) {
		return BaseURL.HTTPLOCAL + "api/review.php/reviews/"+reference;
	}
	
	
	/***
	 * 
	 * View CityGuide List
	 */

	public static String cityGuideURL(String query,String apiKey) {
		return BaseURL.HTTP + "textsearch/json?query="+query+"+in+"+AllConstants.cityname+"&sensor=true&key="+apiKey;
	}

	/***
	 * 
	 * View CityGuide Details : 
	 */

	public static String cityGuideDetailsURL(String reference, String apiKey) {
		return BaseURL.HTTP + "details/json?reference="+reference+"&sensor=true&key="+apiKey;
	}
	
	/***
	 * 
	 * View CityGuide Details : Local Data
	 */

	public static String cityGuideDetailsURLLocal(String reference) {
		return  BaseURL.HTTPLOCAL + "api/marker.php/markers/"+reference;
	}


}
