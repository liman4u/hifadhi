package com.example.hifadhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.locateit.R;

/**
 * Created by mac on 3/19/16.
 */
public class PayNowActivity extends Activity {

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paynow);

        String content=getIntent().getStringExtra("content");

        TextView htmlTextView = (TextView)findViewById(R.id.html_text);
        htmlTextView.setText(Html.fromHtml(content));

    }

}
