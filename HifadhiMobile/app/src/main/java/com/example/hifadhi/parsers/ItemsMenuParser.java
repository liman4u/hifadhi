package com.example.hifadhi.parsers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.hifadhi.models.ItemMenuList;
import com.example.hifadhi.holder.AllItemsMenu;
import com.example.hifadhi.utils.BaseURL;
import com.example.hifadhi.utils.PrintLog;
import com.example.hifadhi.utils.PskHttpRequest;

import android.content.Context;


public class ItemsMenuParser {
	public static boolean connect(Context con, String url)
			throws JSONException, IOException {

		// String result = GetText(con.getResources().openRawResource(
		// R.raw.get_participants));

		String result="";
		try {
			//result = PskHttpRequest.getText(PskHttpRequest
			//		.getInputStreamForGetRequest(url));

			result = "{\"marker_item\": [{\"id\":1,\"name\":\"Hawaian\",\"restaurant_id\":\"6\",\"menu_category_id\":\"1\",\"price\":\"30.00 GHS\",\"icon\":\"http://troppa-nl.com/wp-content/uploads/2015/08/pizza-stock.jpg\",\"description\":\"Sticks of gizzard\",\"status\":null,\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\",\"deleted_at\":null}," +
					"{\"id\":2,\"name\":\"Italian Pizza\",\"restaurant_id\":\"7\",\"menu_category_id\":\"1\",\"price\":\"15.00 GHS\",\"icon\":\"..\\/images\\/11340552279965959_13.pic.jpg\",\"description\":\"Some hot italian pizza\",\"status\":null,\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\",\"deleted_at\":null}," +
					"{\"id\":3,\"name\":\"BBQ Chicken\",\"restaurant_id\":\"8\",\"menu_category_id\":\"2\",\"price\":\"50.00 GHS\",\"icon\":\"..\\/images\\/396024_498838933466890_600510079_n.jpg\",\"description\":\"First room\",\"status\":null,\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\",\"deleted_at\":null}," +
					"{\"id\":4,\"name\":\"Marinara\",\"restaurant_id\":\"9\",\"menu_category_id\":\"3\",\"price\":\"30.00 GHS\",\"icon\":\"..\\/images\\/396024_498838933466890_600510079_n.jpg\",\"description\":\"ATM at GT Bank\",\"status\":null,\"created_at\":\"-0001-11-30 00:00:00\",\"updated_at\":\"-0001-11-30 00:00:00\",\"deleted_at\":null}]}";
		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (result.length() < 1) {
			return false;
			// Log.e("result is ", "parse " + result);
		}

		// Log.w("log result is menu sectionparsing", result);

		 System.out.println(" result is " + result);

		AllItemsMenu.removeAll();

		final JSONObject catObject = new JSONObject(result);

		ItemMenuList menuData;

		JSONArray resultArray = catObject.getJSONArray("marker_item");
		
		//JSONArray resultArray = catObject.getJSONArray("results");

		for (int i = 0; i < resultArray.length(); i++) {
			final JSONObject resultObject = resultArray.getJSONObject(i);
			menuData = new ItemMenuList();

		
			
			try {
				menuData.setIcon(resultObject.getString("icon").replaceFirst("..", BaseURL.HTTPLOCAL));
				PrintLog.myLog("icon : ", menuData.getIcon() );
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
				PrintLog.myLog("Name of token : ", menuData.getName() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				menuData.setPrice(resultObject.getString("price"));
				PrintLog.myLog("Price: ", menuData.getPrice() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				menuData.setDescription(resultObject
						.getString("description"));
				PrintLog.myLog("Description: ", menuData
						.getDescription()
						+ "");
			} catch (Exception e) {
				// TODO: handle exception
			}

			AllItemsMenu.setItemsMenuList(menuData);
			menuData = null;
		}
		return true;
	}

}
