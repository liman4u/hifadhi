package com.example.hifadhi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.locateit.R;
import com.example.hifadhi.holder.AllItemsMenu;
import com.example.hifadhi.models.ItemMenuList;
import com.example.hifadhi.parsers.ItemsMenuParser;
import com.example.hifadhi.utils.AlertMessage;
import com.example.hifadhi.utils.AllConstants;
import com.example.hifadhi.utils.AllURL;
import com.example.hifadhi.utils.CacheImageDownloader;
import com.example.hifadhi.utils.PrintLog;
import com.example.hifadhi.utils.SharedPreferencesHelper;

public class ItemListActivity extends Activity {
	/** Called when the activity is first created. */

	private ListView list;
	private Context con;
	private Bitmap defaultBit;
	private ItemsAdapter adapter;
	private ProgressDialog pDialog;
	private CacheImageDownloader downloader;
	
	

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listlayout);
		con = this;
		initUI();
	 }
	private void initUI() {
		// TODO Auto-generated method stub

		list = (ListView) findViewById(R.id.menuListView);
		
		final TextView title = (TextView) findViewById(R.id.pageTitle);

		try {
			String t = null;
			if(AllConstants.query.equals("1")){
				t= "MENUS";
			}else if(AllConstants.query.equals("2")){
				t= "ROOMS";
			}else if(AllConstants.query.equals("3")){
				t= "ATMS";
			}
			
			title.setText(t);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		downloader = new CacheImageDownloader();
//		defaultBit = BitmapFactory.decodeResource(getResources(),
//				R.drawable.img);

		// parseQuery(AllConstants.query);
		parseQuery();

		PrintLog.myLog("Query in activity : ", AllConstants.referrence);

	}

	private void parseQuery() {
		// TODO Auto-generated method stub
		if (!SharedPreferencesHelper.isOnline(con)) {
			AlertMessage.showMessage(con, "Error", "No internet connection");
			return;
		}

		pDialog = ProgressDialog.show(this, "", "Loading..", false, false);

		final Thread d = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					if (ItemsMenuParser.connect(con, AllURL.itemsURLLocal(AllConstants.referrence))) {

						
						PrintLog.myLog("Size of Items : ", AllItemsMenu
								.getAllItemsMenu().size()
								+ "");

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
						if (AllItemsMenu.getAllItemsMenu().size() == 0) {

						} else {

							adapter = new ItemsAdapter(con);
							list.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}

					}
				});

			}
		});
		d.start();
	}

	class ItemsAdapter extends ArrayAdapter<ItemMenuList> {
		private final Context con;

		public ItemsAdapter(Context context) {
			super(context, R.layout.itemrowlist, AllItemsMenu.getAllItemsMenu());
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
				v = vi.inflate(R.layout.itemrowlist, null);
			}

			if (position < AllItemsMenu.getAllItemsMenu().size()) {
				final ItemMenuList IM = AllItemsMenu.getItemsMenuList(position);

				Typeface title = Typeface.createFromAsset(getAssets(),
						"fonts/ROBOTO-REGULAR.TTF");
				Typeface add = Typeface.createFromAsset(getAssets(),
						"fonts/ROBOTO-LIGHT.TTF");

				// ----Address----

				final TextView description = (TextView) v
						.findViewById(R.id.description);

				try {
					description
							.setText(IM.getDescription().toString()
									.trim());
					description.setTypeface(add);
				} catch (Exception e) {
					// TODO: handle exception
				}

				

				// ---Image---

				final ImageView icon = (ImageView) v
						.findViewById(R.id.rowImageView);
				
				try {

					AllConstants.iconUrl = IM.getIcon()
							.toString().trim();
					downloader.download(AllConstants.iconUrl.trim(), icon);
					
					PrintLog
							.myLog("iconURL:", AllConstants.iconUrl + "");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				

				// ----Name----

				final TextView name = (TextView) v.findViewById(R.id.rowName);
				try {
					name.setText(IM.getName().toString().trim());
					name.setTypeface(title);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				// ----Price----

				final TextView price = (TextView) v.findViewById(R.id.price);
				try {
					price.setText(IM.getPrice().toString().trim());
					price.setTypeface(title);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				v.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						AllConstants.referrence = IM.getReference().toString()
								.trim();
						
						AllConstants.name = IM.getName();
						AllConstants.icon = IM.getIcon();
						AllConstants.price = IM.getPrice();
						
						
						try {
							AllConstants.detailsiconUrl = IM
									.getIcon().toString().trim();
						} catch (Exception e) {
							// TODO: handle exception
						}
						final Intent iii = new Intent(con,
								ListDetailsActivity.class);
						iii.putExtra("POSITION", position);
						iii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(iii);

						// }

					}
				});

			}

			// TODO Auto-generated method stub
			return v;
		}

	}

	public void btnHome(View v) {

		Intent next = new Intent(con, HifadhiActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);

	}

	public void btnBack(View v) {
		finish();

	}
}
