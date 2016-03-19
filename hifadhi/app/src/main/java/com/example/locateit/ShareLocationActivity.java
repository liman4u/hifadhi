package com.example.locateit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.locateit.utils.AllConstants;


public class ShareLocationActivity extends Activity {

	private EditText fullNameE;
	private EditText phoneNumberE;
	private Button shareB;

	
	
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share_location);
		con = this;
		
		Toast.makeText(ShareLocationActivity.this, "Loading..",
				 Toast.LENGTH_LONG).show();
		
		setUpViews();

		
	}



	private void setUpViews(){
		fullNameE = (EditText) findViewById(R.id.fullname);
		phoneNumberE = (EditText) findViewById(R.id.phonenumber);
		
		
		shareB = (Button) findViewById(R.id.btnShare);
		shareB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new ShareLocationAsyncTask().execute(fullNameE.getText().toString(),phoneNumberE.getText().toString());

			}
		});

		
		
	}

	
	boolean success = false;
	
	private class ShareLocationAsyncTask extends
			AsyncTask<String, Integer, Void> {

		private final ProgressDialog dialog = new ProgressDialog(
				ShareLocationActivity.this);
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
			

			System.out.println("Sharing async task  started,processing now.............");
			boolean checker = true;

			try {

				String resp = "";
				String fullName = "";
				String phoneNumber = "";
				
				
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

				

					

				// retrieve and checking the field
				if (phoneNumberP != null) {

					// Retrieve the data sent from the client
					phoneNumber = phoneNumberP.trim();

					// check posted element
					checker = phoneNumber.isEmpty() ? true : false;

					// checker to ascertain if none of the elements posted are
					// empty
					if (checker) {
						returnMessage = "Provide phone number";

					}
				} else {
					returnMessage = "Phone number not provided";
					
				}

				

				if (!checker) {


					//sending sms of location to phone number
					responseCode = "00";
					
					SmsManager smsManager = SmsManager.getDefault();
				    StringBuffer smsBody = new StringBuffer();
				    smsBody.append(fullName+" is at : http://maps.google.com?q="); 
				    smsBody.append(AllConstants.UPlat);
				    smsBody.append(",");
				    smsBody.append(AllConstants.UPlng);
				    
				    System.out.println(smsBody.toString());
				    
				    smsManager.sendTextMessage(phoneNumber, null, smsBody.toString(), null, null);

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

                returnMessage = "Your location has been sent to "+phoneNumberE.getText();
               
                
                success = true ;
				

            }  else {

                returnMessage = "We are unable to send your location at the moment please try again in a few minutes";
                
            }

			if(success){
				System.out.println(returnMessage);
				
				phoneNumberE.setText("");
				fullNameE.setText("");
				
				AlertDialogManager.showAlertDialog(ShareLocationActivity.this, "Success",
						returnMessage, true);
				
				
				
			}else{
				System.out.println(returnMessage);
				AlertDialogManager.showAlertDialog(ShareLocationActivity.this, "Error",
						returnMessage, false);
			}

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
