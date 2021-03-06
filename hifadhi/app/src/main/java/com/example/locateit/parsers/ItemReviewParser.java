package com.example.locateit.parsers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.locateit.holder.AllCityReview;
import com.example.locateit.models.ReviewList;
import com.example.locateit.utils.PskHttpRequest;

import android.content.Context;
import android.util.Log;


public class ItemReviewParser {
	public static boolean connect(Context con, String url)
			throws JSONException, IOException {

		// String result = GetText(con.getResources().openRawResource(
		// R.raw.get_participants));

		String result = "";
		try {
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

		//		

		AllCityReview.removeAll();

		final JSONObject detailsObject = new JSONObject(result);

		ReviewList reviewList;

		//final JSONObject resultObject = detailsObject.getJSONObject("reviews");

		JSONArray reviewsArray = detailsObject.getJSONArray("review");

		for (int i = 0; i < reviewsArray.length(); i++) {
			final JSONObject reviewsObject = reviewsArray.getJSONObject(i);

			reviewList = new ReviewList();

			try {
				reviewList.setAuthor_name(reviewsObject
						.getString("subscriber"));

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				reviewList.setAuthor_text(reviewsObject.getString("text"));

			} catch (Exception e) {
				// TODO: handle exception
			}

			
//			JSONArray aspectsArray = reviewsObject.getJSONArray("aspects");
//			for (int j = 0; j < aspectsArray.length(); j++) {
//				final JSONObject aspectsObject = aspectsArray.getJSONObject(i);
//				
//
//				try {
//					reviewList.setAuthor_rating(aspectsObject.getString("rating"));
//				
//
//				} catch (Exception e) {
//					// TODO: handle exception
//				}	
//				
//				
//			}

			AllCityReview.setReviewList(reviewList);
			reviewList = null;
			
		}
		return true;

	}
}
