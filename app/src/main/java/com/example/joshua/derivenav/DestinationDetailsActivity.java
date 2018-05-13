package com.example.joshua.derivenav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DestinationDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_destination_img)
    ImageView iv_destination_img;
    @BindView(R.id.txt_destination_title)
    TextView txt_destination_title;
    @BindView(R.id.toolbar_details)
    Toolbar toolbar_details;
    @BindView(R.id.txt_destination_desc)
    TextView txt_destination_desc;

    private static final String TAG = "DestinationDetailsActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //make description textview scrollable
        txt_destination_desc.setMovementMethod(new ScrollingMovementMethod());

        getDetails();

    }


    private void getDetails() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents ");
        if (getIntent().hasExtra("img_url") && getIntent().hasExtra("destination_title")) {
            String imgUrl = getIntent().getStringExtra("img_url");
            String destinationTitle = getIntent().getStringExtra("destination_title");
            String destinationDesc = getIntent().getStringExtra("destination_desc");
            updateView(imgUrl, destinationTitle, destinationDesc);
        }
    }

    private void updateView(String imgUrl, String destinationTitle, String destinationDesc) {
        txt_destination_title.setText(destinationTitle);
        txt_destination_desc.setText(destinationDesc);
        Picasso.with(getApplicationContext()).load(imgUrl).into(iv_destination_img);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                Log.d(TAG, "option selected: null" + item.getItemId());
                return false;
        }
    }
}


