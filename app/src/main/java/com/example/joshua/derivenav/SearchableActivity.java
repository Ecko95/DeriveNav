package com.example.joshua.derivenav;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshua.derivenav.Search.AbstractService;
import com.example.joshua.derivenav.Search.DestinationSearchService;
import com.example.joshua.derivenav.Search.ServiceListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.steamcrafted.materialiconlib.MaterialMenuInflater;

import org.json.JSONException;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchableActivity extends ListActivity implements ServiceListener {

    private Thread thread;
    private Menu menu;
    @BindView(android.R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable); //activity_main , activity_new_trip

        handleIntent(getIntent());

        Intent intent = getIntent();
        if (intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //send data to fragment
            String selectedSearch = query;

            Intent moreDetailsIntent = new Intent(SearchableActivity.this, NewTripActivity.class);

            Bundle dataBundle = new Bundle();
            dataBundle.putString("SelectedSearch", selectedSearch);
            moreDetailsIntent.putExtras(dataBundle);
            startActivity(moreDetailsIntent);
            finish();
        }

        ButterKnife.bind(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
//            doSearch(query);
        }
    }

    @Override
    public void serviceComplete(AbstractService abstractService) {

        if (!abstractService.hasError()) {
            DestinationSearchService destinationSearchService = (DestinationSearchService) abstractService;
            String[] result = new String[destinationSearchService.getResults().length()];
            for (int i = 0; i < destinationSearchService.getResults().length(); i++) {
                try {
                    //search for the wanted object name, name, location, etc

                    result[i] = destinationSearchService.getResults().getJSONObject(i).getString("title");
                    //result[i] = destinationSearchService.getResults().getJSONObject(i).getString("recipeName");
                } catch (JSONException ex) {
                    result[i] = "error";
                }
            }

            setListAdapter(new ArrayAdapter<String>(this, R.layout.cities_list_cell, R.id.text, result));

        } else {
            String[] result = new String[]{"No Results"};
            setListAdapter(new ArrayAdapter<String>(this, R.layout.cities_list_cell, R.id.text, result));
        }

    }

    @OnClick(R.id.icon_search_close)
    public void closeSearch(View view) {
        finish();
    }
}

