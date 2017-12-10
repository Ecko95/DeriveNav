package com.example.joshua.derivenav.com.joshua.service;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Joshua on 10/12/2017.
 */

public class DestinationSearchService extends AbstractService {
    private String query;
    private JSONArray results;
    public DestinationSearchService (String query){
        try{
            this.query = URLEncoder.encode(query, "UTF-8");
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
    }
    public JSONArray getResults(){
        return results;
    }
    @Override
    public void run() {
        URL url;
        boolean error = false;
        HttpURLConnection httpURLConnection = null;
        StringBuilder result = new StringBuilder();

        try{

            url = new URL("https://api.sandbox.amadeus.com/v1.2/points-of-interest/yapq-search-text?apikey=nEhIPL2nYIWbA11ILzCQcgu0e3lHuAcA&city_name=" + query);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null){
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());

            if(jsonObject.has("Response") && jsonObject.getString("Response").equals("False")){
                error = true;
            }else{
                results = jsonObject.getJSONArray("Search");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            results = null;
            error = true;
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }

        super.serviceCallComplete(error);
    }
}
