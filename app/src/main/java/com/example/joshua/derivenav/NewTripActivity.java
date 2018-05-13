package com.example.joshua.derivenav;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;

import android.widget.SearchView;
import android.widget.Toast;

import com.example.joshua.derivenav.NewTrip.Adapters.StepperAdapter;
import com.example.joshua.derivenav.NewTrip.CityDestinationsFragment;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewTripActivity extends AppCompatActivity implements StepDataManager, StepperLayout.StepperListener{

    //step data manager override's


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

    @Override
    public void saveTripTitle(String title) {
        mTripTitle = title;
    }

    @Override
    public String getTripTitle() {
        return mTripTitle;
    }

    @Override
    public void saveTripDesc(String description) {
        mTripDesc = description;
    }

    @Override
    public String getDesc() {
        return mTripDesc;
    }

    @Override
    public void saveTripCategory(String category) {
        mTripCategory = category;
    }

    @Override
    public String getCategory() {
        return mTripCategory;
    }

    @Override
    public void saveDestinationList(ArrayList<DestinationModel> newDestinationList) {
        mNewDestinationList = newDestinationList;
    }
    @Override
    public ArrayList<DestinationModel> getNewDestinationList() {
        return mNewDestinationList;
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
    private String mTripTitle;
    private String mTripDesc;
    private String mTripCategory;
    private ArrayList<DestinationModel> mNewDestinationList = new ArrayList<>();
    private DestinationModel mNewDestinationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar_newtrip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Trip");

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
                NewTripActivity.this.finish();
            }
        });
        materialSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(NewTripActivity.this, "you selected the following City:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        try{
            Bundle bundle = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
            if(bundle != null) {
                String SelectedSearch = bundle.getString("SelectedSearch");
                //Toast.makeText(getApplicationContext(), SelectedSearch, Toast.LENGTH_SHORT).show();
                mChosenSearch = SelectedSearch;

            }else{
                //disables next fragment button
                stepperLayout.setNextButtonEnabled(false);
                stepperLayout.setNextButtonVerificationFailed(true);
//                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Log.d(TAG,"Error catch");
        }
        try{
            Bundle bundle = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
            if(bundle != null) {
                String SelectedSearch = bundle.getString("SelectedSearch");
                //Toast.makeText(getApplicationContext(), SelectedSearch, Toast.LENGTH_SHORT).show();
                mChosenSearch = SelectedSearch;

            }else{
//                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Log.d(TAG,"Error catch");
        }

    }

    public void disableSteps() {
        stepperLayout.setNextButtonVerificationFailed(true);
        stepperLayout.setCompleteButtonVerificationFailed(true);
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
                    //Toast.makeText(this, searchWrd, Toast.LENGTH_SHORT).show();
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
            case R.id.action_help:
                helpTutorial();
                return true;
            default:
                //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MaterialMenuInflater
                .with(this)
                .setDefaultColor(Color.WHITE)
                // Inflate the menu
                .inflate(R.menu.menu, menu);

        this.menu = menu;
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        materialSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        menu.findItem(R.id.action_logout).setVisible(false);
        //hideMenu();
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

    public void hideSearch(){
        menu.findItem(R.id.action_search).setVisible(false);
    }

    public void helpTutorial() {
        try {
            SimpleTarget simpleTarget = new SimpleTarget.Builder(this)
                    .setPoint(1000f, 135f) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
                    .setRadius(70f) // radius of the Target
                    .setTitle("Search for a city") // title
                    .setDescription("Try me out!") // description
                    .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                        @Override
                        public void onStarted(SimpleTarget target) {
                            // do something
                        }

                        @Override
                        public void onEnded(SimpleTarget target) {
                            // do something
                        }
                    })
                    .build();
            SimpleTarget secondTarget = new SimpleTarget.Builder(this)
                    .setPoint(960f, 1970f) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
                    .setRadius(90f) // radius of the Target
                    .setTitle("Click next") // title
                    .setDescription("to see your search results") // description
                    .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                        @Override
                        public void onStarted(SimpleTarget target) {
                            // do something
                        }

                        @Override
                        public void onEnded(SimpleTarget target) {
                            // do something
                        }
                    })
                    .build();

            Spotlight.with(this)
                    .setOverlayColor(ContextCompat.getColor(this, R.color.background)) // background overlay color
                    .setDuration(1000L) // duration of Spotlight emerging and disappearing in ms
                    .setAnimation(new DecelerateInterpolator(2f)) // animation of Spotlight
                    .setTargets(simpleTarget, secondTarget) // set targets. see below for more info
                    .setClosedOnTouchedOutside(true) // set if target is closed when touched outside
                    .setOnSpotlightStartedListener(new OnSpotlightStartedListener() { // callback when Spotlight starts
                        @Override
                        public void onStarted() {
                            //Toast.makeText(getContext(), "spotlight is started", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setOnSpotlightEndedListener(new OnSpotlightEndedListener() { // callback when Spotlight ends
                        @Override
                        public void onEnded() {
                            //Toast.makeText(getContext(), "spotlight is ended", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start(); // start Spotlight
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

}

