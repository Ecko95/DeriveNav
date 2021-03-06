package com.example.joshua.derivenav;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.joshua.derivenav.UserTrips.UserTripsFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tapadoo.alerter.Alerter;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //defines a menu so we can hide it or display it according to fragments
    private Menu menu;
    private FirebaseAuth mAuth;

    @BindView(R.id.base_container)
    View mRootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); //init views
        setSupportActionBar(toolbar);

        // welcomes user if logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            showSnackbar("Welcome " + user.getDisplayName() + "!");
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

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
                                selectedFragment = UserTripsFragment.newInstance();
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

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
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

    }

    public void hideMenu() {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_help).setVisible(false);
    }

    private void showSnackbar(String loginMessage) {
        Snackbar.make(mRootView, loginMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                    //Toast.makeText(this, searchWrd, Toast.LENGTH_SHORT).show();
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (item.getItemId()){
            case android.R.id.home:
                //Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;

            case R.id.action_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {

                                // user is now signed out
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                return true;
            case R.id.action_about:
                intent.setData(Uri.parse("https://github.com/Ecko95/DeriveNav"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    getApplicationContext().startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_api:
                intent.setData(Uri.parse("https://sandbox.amadeus.com/api-catalog"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    getApplicationContext().startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "sorry link not found", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
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
        searchView.setMenuItem(item);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        hideMenu();

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


}
