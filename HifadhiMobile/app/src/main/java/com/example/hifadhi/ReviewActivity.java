package com.example.hifadhi;

import org.json.JSONObject;

import com.example.hifadhi.utils.ServerUtils;
import com.example.locateit.R;
import com.example.hifadhi.utils.AllConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ReviewActivity extends FragmentActivity {

	private EditText fullNameE;
	private EditText phoneNumberE;
	private EditText emailAddressE;
	private EditText reviewE;
	private Spinner ratingS;
	private Button reviewB;

	private String fullName;
	private String phoneNumber;
	private String emailAddress;
	private String review;
	private String rating;
	
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_review);
		con = this;
		
		Toast.makeText(ReviewActivity.this, "Loading..",
				 Toast.LENGTH_LONG).show();
		
		setUpViews();

		
	}



	private void setUpViews(){
		fullNameE = (EditText) findViewById(R.id.fullname);
		phoneNumberE = (EditText) findViewById(R.id.phonenumber);
		emailAddressE = (EditText) findViewById(R.id.emailAddress);
		reviewE = (EditText) findViewById(R.id.review);
		
		
		reviewB = (Button) findViewById(R.id.btnReview);
		reviewB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new ReviewAsyncTask().execute(fullNameE.getText().toString(),phoneNumberE.getText().toString(),emailAddressE.getText().toString(),reviewE.getText().toString(),rating);

			}
		});

		
		ratingS = (Spinner) findViewById(R.id.rating);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> ratingAdapter = ArrayAdapter.createFromResource(this,
		        R.array.rating, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		ratingS.setAdapter(ratingAdapter);
		ratingS.setOnItemSelectedListener(new OnItemSelectedListener() {

	        @Override
	        public void onItemSelected(AdapterView<?> parent,
	                View view, int pos, long id) {
	            rating = parent.getItemAtPosition(pos).toString();
	        	
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
		
	}

	
	boolean success = false;
	
	private class ReviewAsyncTask extends
			AsyncTask<String, Integer, Void> {

		private final ProgressDialog dialog = new ProgressDialog(
				ReviewActivity.this);
		String responseCode = "";
		String returnMessage = "";

		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Please Wait...");
			this.dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {

			String fullNameP = params[0];
			String phoneNumberP = params[1];
			String emailAddressP = params[2];
			String reviewP = params[3];
			String ratingP= params[4];
			

			System.out.println("Review async task  started,processing now.............");
			boolean checker = true;

			try {

				String resp = "";
				String fullName = "";
				String phoneNumber = "";
				String emailAddress = "";
				String review ="";
				String rating= "";
				
				
				// retrieve and checking the field
				if (fullNameP != null) {

					// Retrieve the data sent from the client
					fullName = fullNameP.trim();

					// check posted element
					checker = fullName.isEmpty() ? true : false;

					// checker to ascertain if none of the elements posted are
					// empty
					if (checker) {
						returnMessage = "Provide full name";

						
					}
				} else {
					returnMessage = "Full name not provided";
					
				}

				phoneNumber = phoneNumberP.trim();
				
				emailAddress = emailAddressP.trim();
				
				
					rating = ratingP.trim();

					

				// retrieve and checking the field
				if (reviewP != null) {

					// Retrieve the data sent from the client
					review = reviewP.trim();

					// check posted element
					checker = review.isEmpty() ? true : false;

					// checker to ascertain if none of the elements posted are
					// empty
					if (checker) {
						returnMessage = "Provide review";

					}
				} else {
					returnMessage = "Review not provided";
					
				}

				

				if (!checker) {


					resp = ServerUtils.fireToServer("fullName=" + fullName + "&phoneNumber=" + phoneNumber + "&emailAddress=" + emailAddress + "&text=" + review + "&reference=" + AllConstants.referrence + "&rating=" + rating);

					JSONObject rJson = new JSONObject(resp);

					responseCode = rJson.getString("rc");

				}

			} catch (Exception ex) {
				returnMessage = "We are unable to add  your review at the moment please try again in a few minutes";

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();

			}
			
			if (responseCode.equals("00")) {

                returnMessage = "Your review was added successful.";
               
                success = true ;
				

            }  else {

                returnMessage = "We are unable to add  your review at the moment please try again in a few minutes";
                
            }

			if(success){
				System.out.println(returnMessage);
				AlertDialogManager.showAlertDialog(ReviewActivity.this, "Success",
						returnMessage, true);
				
				
				
			}else{
				System.out.println(returnMessage);
				AlertDialogManager.showAlertDialog(ReviewActivity.this, "Error",
						returnMessage, false);
			}

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
