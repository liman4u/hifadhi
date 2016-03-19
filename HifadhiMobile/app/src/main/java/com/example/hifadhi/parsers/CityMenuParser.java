package com.example.hifadhi.parsers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.hifadhi.holder.AllCityMenu;
import com.example.hifadhi.models.CityMenuList;
import com.example.hifadhi.utils.BaseURL;
import com.example.hifadhi.utils.PrintLog;
import com.example.hifadhi.utils.PskHttpRequest;

import android.content.Context;
import android.util.Log;


public class CityMenuParser {
	public static boolean connect(Context con, String url)
			throws JSONException, IOException {

		// String result = GetText(con.getResources().openRawResource(
		// R.raw.get_participants));

		String result = "";
		try {

			System.out.println(" url is " + url);

			result = PskHttpRequest.getText(PskHttpRequest
					.getInputStreamForGetRequest(url));
		} catch (final URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (result.length() < 1) {
			return false;
			// Log.e("result is ", "parse " + result);
		}

		// Log.w("log result is menu sectionparsing", result);

		 System.out.println(" result is " + result);

		AllCityMenu.removeAll();

		final JSONObject catObject = new JSONObject(result);

		CityMenuList menuData;

		JSONArray resultArray = catObject.getJSONArray("marker");
		
		//JSONArray resultArray = catObject.getJSONArray("results");

		for (int i = 0; i < resultArray.length(); i++) {
			final JSONObject resultObject = resultArray.getJSONObject(i);
			menuData = new CityMenuList();

			try{
			JSONArray photoArray = resultObject.getJSONArray("photos");
			
			
			for (int j = 0; j < photoArray.length(); j++) {
			
				final JSONObject photoObject = photoArray.getJSONObject(j);
				
				try{
				menuData.setPhotoReference(photoObject
						.getString("photo_reference"));
				PrintLog.myLog("photo_reference : ", menuData
						.getPhotoReference()
						+ "");
				

			} catch (Exception e) {
				// TODO: handle exception
			}
			
			}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setIcon(resultObject.getString("icon").replaceFirst("..", BaseURL.HTTPLOCAL));
				PrintLog.myLog("icon : ", menuData.getIcon().replaceFirst("..", BaseURL.HTTPLOCAL) );
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				menuData.setReference(resultObject.getString("id"));
				PrintLog.myLog("reference : ", menuData.getReference() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setName(resultObject.getString("name"));
				PrintLog.myLog("Name : ", menuData.getName() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				menuData.setRating(resultObject.getString("rating"));
				PrintLog.myLog("Rating: ", menuData.getRating() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setLat(resultObject.getString("lat"));
				PrintLog.myLog("Latitude: ", menuData.getLat() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setTypes(resultObject.getString("type"));
				PrintLog.myLog("Type: ", menuData.getTypes() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setLng(resultObject.getString("lng"));
				PrintLog.myLog("Longitude: ", menuData.getLng() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setWebsite(resultObject.getString("website"));
				PrintLog.myLog("Website: ", menuData.getWebsite() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setPhone_number(resultObject.getString("phone_number"));
				PrintLog.myLog("Phone Number: ", menuData.getPhone_number() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				menuData.setVicinity(resultObject
						.getString("address"));
				PrintLog.myLog("Name of formatted_address : ", menuData
						.getVicinity()
						+ "");
			} catch (Exception e) {
				// TODO: handle exception
			}

			AllCityMenu.setCityMenuList(menuData);
			menuData = null;
		}
		return true;
	}

}
