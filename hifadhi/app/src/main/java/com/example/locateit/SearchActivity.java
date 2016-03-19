package com.example.locateit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locateit.ListActivity.RestaurantAdapter;
import com.example.locateit.holder.AllCityMenu;
import com.example.locateit.models.CityMenuList;
import com.example.locateit.parsers.CityMenuParser;
import com.example.locateit.utils.AlertMessage;
import com.example.locateit.utils.AllConstants;
import com.example.locateit.utils.AllURL;
import com.example.locateit.utils.CacheImageDownloader;
import com.example.locateit.utils.PrintLog;
import com.example.locateit.utils.SharedPreferencesHelper;


public class SearchActivity extends FragmentActivity {

	
	private EditText searchItemE;
	private Button searchB;
	private ListView list;
	private Context con;
	private Bitmap defaultBit;
	private RestaurantAdapter adapter;
	private ProgressDialog pDialog;
	private CacheImageDownloader downloader;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_by_marker);
		con = this;
		
		Toast.makeText(SearchActivity.this, "Loading..",
				 Toast.LENGTH_LONG).show();
		
		setUpViews();

		
	}



	private void setUpViews(){
		list = (ListView) findViewById(R.id.menuListView);
		
		downloader = new CacheImageDownloader();
		
		searchItemE = (EditText) findViewById(R.id.searchitem);
		
		result = (TextView) findViewById(R.id.results);
		result.setVisibility(View.INVISIBLE);
		
		searchB = (Button) findViewById(R.id.btnSearch);
		searchB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				parseQuery();

			}
		});

		
		
		
	}

	
	private void parseQuery() {
		// TODO Auto-generated method stub
		if (!SharedPreferencesHelper.isOnline(con)) {
			AlertMessage.showMessage(con, "Error", "No internet connection");
			return;
		}
		
		if (searchItemE.getText() == null) {
			AlertMessage.showMessage(con, "Error", "Enter search term");
			return;
		}

		pDialog = ProgressDialog.show(this, "", "Loading..", false, false);

		final Thread d = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					if (CityMenuParser.connect(con, AllURL.nearByURLLocal2(searchItemE.getText().toString()))) {

						//if (CityMenuParser.connect(con, AllURL.nearByURL(
							//	AllConstants.UPlat, AllConstants.UPlng,
								//AllConstants.query, AllConstants.apiKey))) {
						
						PrintLog.myLog("Size of City : ", AllCityMenu
								.getAllCityMenu().size()
								+ "");
						
						result.setVisibility(View.VISIBLE);
						searchItemE.setText("");

					}else{
						result.setVisibility(View.VISIBLE);
						result.setText("No Result Found!");
					}

				} catch (final Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if (pDialog != null) {
							pDialog.cancel();
						}
						if (AllCityMenu.getAllCityMenu().size() == 0) {

						} else {

							adapter = new RestaurantAdapter(con);
							list.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}

					}
				});

			}
		});
		d.start();
	}
	
	class RestaurantAdapter extends ArrayAdapter<CityMenuList> {
		private final Context con;

		public RestaurantAdapter(Context context) {
			super(context, R.layout.rowlist, AllCityMenu.getAllCityMenu());
			con = context;
			// TODO Auto-generated constructor stub

		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				final LayoutInflater vi = (LayoutInflater) con
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.rowlist, null);
			}

			if (position < AllCityMenu.getAllCityMenu().size()) {
				final CityMenuList CM = AllCityMenu.getCityMenuList(position);

				Typeface title = Typeface.createFromAsset(getAssets(),
						"fonts/ROBOTO-REGULAR.TTF");
				Typeface add = Typeface.createFromAsset(getAssets(),
						"fonts/ROBOTO-LIGHT.TTF");

				// ----Address----

				final TextView address = (TextView) v
						.findViewById(R.id.rowAddress);

				try {
					address
							.setText(CM.getVicinity().toString()
									.trim());
					address.setTypeface(add);
				} catch (Exception e) {
					// TODO: handle exception
				}

				try {

					AllConstants.photoReferrence = CM.getPhotoReference()
							.toString().trim();
					PrintLog
							.myLog("PPRRRef", AllConstants.photoReferrence + "");
				} catch (Exception e) {
					// TODO: handle exception
				}

				// ---Image---

				final ImageView icon = (ImageView) v
						.findViewById(R.id.rowImageView);
				
				try {

					AllConstants.iconUrl = CM.getIcon()
							.toString().trim();
					PrintLog
							.myLog("iconURL:", AllConstants.iconUrl + "");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				try {

					String imgUrl = AllConstants.iconUrl;

					if (AllConstants.photoReferrence.length() != 0) {

//						downloader.download(AllConstants.iconUrl.trim(), icon);
						downloader.download(imgUrl.trim(), icon);

						AllConstants.cPhotoLink = imgUrl;
					}

					else {
						downloader.download(AllConstants.iconUrl.trim(), icon);
						AllConstants.cPhotoLink = AllConstants.iconUrl;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				// ------Rating ---
				final RatingBar listRatings = (RatingBar) v
						.findViewById(R.id.ratingBarL);

				String rating = CM.getRating();

				try {

					Float count = Float.parseFloat(rating);

					listRatings.setRating(count);

				} catch (Exception e) {

				}

				// ----Name----

				final TextView name = (TextView) v.findViewById(R.id.rowName);
				try {
					name.setText(CM.getName().toString().trim());
					name.setTypeface(title);
				} catch (Exception e) {
					// TODO: handle exception
				}
				v.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						AllConstants.referrence = CM.getReference().toString()
								.trim();
						
						AllConstants.lat = CM.getLat().toString();
						AllConstants.lng = CM.getLng().toString();
						AllConstants.cCell = CM.getPhone_number().toString();
						AllConstants.cWeb = CM.getWebsite().toString();
						AllConstants.address = CM.getVicinity().toString();
						
						try {
							AllConstants.photoReferrence = CM
									.getPhotoReference().toString().trim();
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							AllConstants.detailsiconUrl = CM
									.getIcon().toString().trim();
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
						AllConstants.query = CM.getTypes();
						
						if(CM.getTypes().equals("3") || CM.getTypes().equals("2")){
							
							AllConstants.name = CM.getName().toString();
							AllConstants.icon = CM.getIcon();
							
							
							
							final Intent iii = new Intent(con,
									ListDetailsActivity.class);
							iii.putExtra("POSITION", position);
							iii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(iii);
						}else{
							final Intent iii = new Intent(con,
									ItemListActivity.class);
							iii.putExtra("POSITION", position);
							iii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(iii);
						}
						
						
						

						// }

					}
				});

			}

			// TODO Auto-generated method stub
			return v;
		}

	}

	public void btnHome(View v) {

		Intent next = new Intent(con, LocateITActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);

	}

	public void btnBack(View v) {
		finish();

	}
	
}
