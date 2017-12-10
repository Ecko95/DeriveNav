package com.example.joshua.derivenav;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.service.AbstractService;
import com.example.joshua.derivenav.com.joshua.service.DestinationSearchService;
import com.example.joshua.derivenav.com.joshua.service.ServiceListener;

import org.json.JSONException;

public class SearchableActivity extends ListActivity implements ServiceListener{

    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    public void doSearch(String query){
        String[] result = new String[]{"Searching..."};

        DestinationSearchService destinationSearchService = new DestinationSearchService(query);

        DestinationSearchService.addListener(this);
        thread = new Thread(destinationSearchService);
        thread.start();
        Toast.makeText(this, "thread started", Toast.LENGTH_SHORT).show();
        setListAdapter(new ArrayAdapter<String>(this,R.layout.cities_list_cell,R.id.text,result));
    }


    @Override
    public void serviceComplete(AbstractService abstractService) {
        if(!abstractService.hasError()) {
            DestinationSearchService destinationSearchService = (DestinationSearchService) abstractService;

            String[] result = new String[destinationSearchService.getResults().length()];

            for (int i = 0; i < destinationSearchService.getResults().length(); i++) {
                try {
                    result[i] = destinationSearchService.getResults().getJSONObject(i).getString("name");
                } catch (JSONException ex) {
                    result[i] = "error";
                }
            }

            setListAdapter(new ArrayAdapter<String>(this, R.layout.cities_list_cell, R.id.text, result));
        }else{

            String[] result = new String[]{"No results"};
            setListAdapter(new ArrayAdapter<String>(this,R.layout.cities_list_cell,R.id.text,result));
        }
    }
}
