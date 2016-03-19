/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.hifadhi.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * 
 * @author Liman Adamu Labaran
 */
public class ServerUtils {

	
	 private static String apiURL = BaseURL.HTTPLOCAL+"api/review.php/addreviews";
	 //private static String apiURL ="http://172.16.77.115:7070/knoxxi/MobileTransaction?";
	// private static String apiURL =
	// "http://10.0.0.77:8080/MobileTransaction?";

	private static final Logger log = Logger.getLogger(ServerUtils.class
			.getName());

	public static String fireToServer(String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(apiURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			log.info("Accessing API @ "+ apiURL);
			log.info("Sending request with :" + urlParameters);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			log.info("Response from Local Server:" + response.toString());
			return response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			// log.severe("Error: " + e.getMessage());
			return null;

		} finally {

			if (connection != null) {

				connection.disconnect();
			}
		}
	}

}
