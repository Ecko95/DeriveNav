package com.example.joshua.derivenav.Search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class DestinationSearchService extends AbstractService {
    private String query;
    private JSONArray results;

    public DestinationSearchService(String query) {
        try {
            this.query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public JSONArray getResults() {
        return results;
    }

    @Override
    public void run() {
        URL url;
        boolean error = false;
        HttpURLConnection httpURLConnection = null;
        StringBuilder result = new StringBuilder();

        try {

            url = new URL("https://api.sandbox.amadeus.com/v1.2/points-of-interest/yapq-search-text?apikey=UOJASf28IviDWrP4lFnEYGGJuLgrSxpH&city_name=" + query + "&number_of_results=10");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            JSONObject jsonObject = new JSONObject(result.toString());

            if (jsonObject.has("Response") && jsonObject.getString("Response").equals("False")) {
                error = true;
            } else {
                //set parent objects to look for
                results = jsonObject.getJSONArray("points_of_interest");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            results = null;
            error = true;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        super.serviceCallComplete(error);
    }
}
