package com.example.joshua.derivenav;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.service.AbstractService;
import com.example.joshua.derivenav.com.joshua.service.DestinationSearchService;
import com.example.joshua.derivenav.com.joshua.service.ServiceListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private TextView mTextMessage;
//    private MaterialSearchView searchView;

 private ListView lvDestinations;
// private static final String[] CELLS = new String[]{
//         "cell 0", "cell 1", "cell 2"
// };

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //runs AppIntro
        startActivity(new Intent(getApplicationContext(),IntroActivity.class));

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, IntroActivity.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

//        lvDestinations = findViewById(R.id.main_destination_list_view);
//        lvDestinations.setAdapter(new ArrayAdapter<String>(this,R.layout.cities_list_cell,R.id.text, CELLS));


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        //set listeners for search view


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                      Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                            case R.id.navigation_trips:
                                selectedFragment = TripsFragment.newInstance();
                                break;
                            case R.id.navigation_notifications:
                                selectedFragment = HomeFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.base_container, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.base_container, HomeFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

//            searchView = findViewById(R.id.search_view);
//            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    doSearch(query);
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//
//                    return false;
//                }
//            });
//
//            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//                @Override
//                public void onSearchViewShown() {
//                    //Do some magic
//                }
//
//                @Override
//                public void onSearchViewClosed() {
//                    //Do some magic
//                }
//            });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }






    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        //gets searchView data
//        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
//            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            if (matches != null && matches.size() > 0) {
//                String searchWrd = matches.get(0);
//                if (!TextUtils.isEmpty(searchWrd)) {
//                    searchView.setQuery(searchWrd, false);
//                }
//            }
//
//            return;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            case R.id.itmSearch:
                onSearchRequested();
                return true;
            default:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        this.menu = menu;
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//        return super .onCreateOptionsMenu(menu);
//    }
//    @Override
//    public void onBackPressed() {
//        //closes app
//        /*if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }*/
//        if (searchView.isSearchOpen()) {
//            searchView.closeSearch();
//        } else {
//            super.onBackPressed();
//        }
//    }

}
