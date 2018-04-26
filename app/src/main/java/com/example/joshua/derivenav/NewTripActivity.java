package com.example.joshua.derivenav;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Toast;

import com.example.joshua.derivenav.NewTrip.Adapters.StepperAdapter;
import com.example.joshua.derivenav.NewTrip.StepDataManager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.tapadoo.alerter.Alerter;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewTripActivity extends AppCompatActivity implements StepDataManager, StepperLayout.StepperListener{
    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {
        finish();
    }

    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveStepData(String data) {
        mData = data;
    }

    @Override
    public String getData() {
        return mData;
    }


    private static final String TAG = "";
    private String mChosenSearch;
    @BindView(R.id.search_view_new)
    MaterialSearchView materialSearchView;
    @BindView(R.id.toolbar_newtrip) Toolbar toolbar_newtrip;
    @BindView(R.id.stepperLayout) StepperLayout stepperLayout;
    private Menu menu;

//    Data objects about to be pass to other fragments
    private static final String CURRENT_STEP_POSITION_KEY = "position";
    private static final String DATA = "data";
    private String mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar_newtrip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int startingStepPosition = savedInstanceState != null ? savedInstanceState.getInt(CURRENT_STEP_POSITION_KEY) : 0;
        mData = savedInstanceState != null ? savedInstanceState.getString(DATA) : null;
        stepperLayout.setAdapter(new StepperAdapter(getSupportFragmentManager(), this), startingStepPosition);


        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent searchIntent = new Intent(getApplicationContext(), SearchableActivity.class);
                searchIntent.putExtra(SearchManager.QUERY, query);
                searchIntent.setAction(Intent.ACTION_SEARCH);
                startActivity(searchIntent);
                NewTripActivity.this.finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        materialSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NewTripActivity.this, "you selected the following City:" + position, Toast.LENGTH_SHORT).show();

            }
        });




    }

    public String getChosenSearch(){
        return mChosenSearch;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    materialSearchView.setQuery(searchWrd, false);
                    Toast.makeText(this, searchWrd, Toast.LENGTH_SHORT).show();
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this) // Provide the activity context
                // Set the fall-back color for all the icons. Colors set inside the XML will always have higher priority
                .setDefaultColor(Color.WHITE)
                // Inflate the menu
                .inflate(R.menu.menu, menu);

        this.menu = menu;
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        materialSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        menu.findItem(R.id.action_logout).setVisible(false);
        hideSearch();
        return true;
    }

    @Override
    public void onBackPressed() {
        final int currentStepPosition = stepperLayout.getCurrentStepPosition();
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.getCurrentStepPosition());
        outState.putString(DATA, mData);
        super.onSaveInstanceState(outState);
    }

    public void hideSearch(){
        menu.findItem(R.id.action_search).setVisible(false);
    }





}

