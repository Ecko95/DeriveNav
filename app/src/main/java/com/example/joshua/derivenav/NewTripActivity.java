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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshua.derivenav.NewTrip.StepFragment1;
import com.example.joshua.derivenav.NewTrip.StepFragment2;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tapadoo.alerter.Alerter;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;
import me.drozdzynski.library.steppers.interfaces.OnCancelAction;
import me.drozdzynski.library.steppers.interfaces.OnChangeStepAction;
import me.drozdzynski.library.steppers.interfaces.OnClickContinue;
import me.drozdzynski.library.steppers.interfaces.OnFinishAction;
import me.drozdzynski.library.steppers.interfaces.OnSkipStepAction;

public class NewTripActivity extends AppCompatActivity {


    private static final String TAG = "";
    private String mChosenSearch;
    @BindView(R.id.search_view_new)
    MaterialSearchView materialSearchView;
    @BindView(R.id.toolbar_newtrip) Toolbar toolbar_newtrip;

    private Menu menu;

    @BindView(R.id.steppersView) SteppersView steppersView;


    SteppersItem step1 = new SteppersItem();
    SteppersItem step2 = new SteppersItem();
    SteppersItem step3 = new SteppersItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        ButterKnife.bind(this);


        step1.setLabel("Search for a City");
//        step1.setSubLabel("Subtitle of step 1");
        step1.setFragment(new StepFragment1());
        step1.setPositiveButtonEnable(false);


        step2.setLabel("Title of step 2");
        step2.setSubLabel("Subtitle of step 2");
        step2.setFragment(new StepFragment2());
        step2.setPositiveButtonEnable(true);


        step3.setLabel("Title of step 3");
        step3.setSubLabel("Subtitle of step 3");
        step3.setFragment(new StepFragment2());
        step3.setPositiveButtonEnable(false);
        step3.setPositiveButtonEnable(true);

        try{
            Bundle bundle = getIntent().getExtras(); // Getting the Bundle object that pass from another activity
            if(bundle != null) {
                step1.setPositiveButtonEnable(true);
                String SelectedSearch = bundle.getString("SelectedSearch");
                Toast.makeText(getApplicationContext(), SelectedSearch, Toast.LENGTH_SHORT).show();
                mChosenSearch = SelectedSearch;

            }else{
                Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Log.d(TAG,"Error catch");
        }


        steppersView = (SteppersView) findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                NewTripActivity.this.startActivity(new Intent(NewTripActivity.this, MainActivity.class));
                NewTripActivity.this.finish();
            }
        });

        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                NewTripActivity.this.startActivity(new Intent(NewTripActivity.this, MainActivity.class));
                NewTripActivity.this.finish();
            }
        });

        steppersViewConfig.setOnChangeStepAction(new OnChangeStepAction() {
            @Override
            public void onChangeStep(int position, SteppersItem activeStep) {
                Toast.makeText(NewTripActivity.this,
                        "Step changed to: " + activeStep.getLabel() + " (" + position + ")",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Setup Support Fragment Manager for fragments in steps
        steppersViewConfig.setFragmentManager(getSupportFragmentManager());

        ArrayList<SteppersItem> steps = new ArrayList<>();





        steps.add(step1);
        steps.add(step2);
        steps.add(step3);

        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();

        setSupportActionBar(toolbar_newtrip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



    public static void dialog(boolean value, Context context){
        Activity activity = (Activity) context;
        if(!value){
            Alerter.create(activity)
                    .setTitle("You're Offline!")
                    .setBackgroundColorRes(R.color.colorAccent)
                    .enableSwipeToDismiss()
                    .show();
        }else {
            Alerter.create(activity)
                    .setTitle("You're back Online!")
                    .setIcon(R.drawable.alerter_ic_face)
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .enableSwipeToDismiss()
                    .show();
        }
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

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
