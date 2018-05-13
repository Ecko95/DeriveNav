package com.example.joshua.derivenav;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripDestinationDetailsActivity extends AppCompatActivity {


    @BindView(R.id.iv_destination_img)
    ImageView iv_destination_img;
    @BindView(R.id.txt_destination_title)
    TextView txt_destination_title;
    @BindView(R.id.txt_destination_desc)
    TextView txt_destination_desc;


    private static final String TAG = "TripDestinationDetailsA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_destination_details);

        //init ButterKnife views
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        try {
            //set title of activity
            getSupportActionBar().setTitle("Destination Details");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @OnClick(R.id.btn_trip_wiki)
    public void clickWikiPage(View view) {
        String wikiPageLink = getIntent().getStringExtra("destination_wiki_page_link");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(wikiPageLink));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            getApplicationContext().startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btn_trip_maps)
    public void clickGoogleMapsLink(View view) {
        String googleMapsLink = getIntent().getStringExtra("destination_google_maps_link");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(googleMapsLink));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            getApplicationContext().startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
        }
    }


}
