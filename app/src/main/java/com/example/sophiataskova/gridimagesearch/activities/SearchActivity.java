package com.example.sophiataskova.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.sophiataskova.gridimagesearch.R;
import com.example.sophiataskova.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.sophiataskova.gridimagesearch.models.FilterSet;
import com.example.sophiataskova.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter imageResultsAdapter;
    public static final int FILTER_RESULT_CODE = 123;
    private FilterSet filterSet;
    private static String imageSizeParam = "&imgsz=";
    private static String imageTypeParam = "&imgtype=";
    private static String imageColorParam = "&imgcolor=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpViews();
        imageResults = new ArrayList<ImageResult>();
        imageResultsAdapter = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(imageResultsAdapter);

    }

    private void setUpViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                intent.putExtra("url", result);
                startActivity(intent);
            }
        });
    }

    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        https://ajax.googleapis.com/ajax/services/search/images?q=fuzzy%20monkey&v=1.0
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query;
        if (!(filterSet.getSizeFilter().equals("none"))) {
            searchUrl = searchUrl.concat(imageSizeParam + filterSet.getSizeFilter());
        }
        if (!(filterSet.getColorFilter().equals("none"))) {
            searchUrl = searchUrl.concat(imageColorParam + filterSet.getColorFilter());
        }
        if (!(filterSet.getTypeFilter().equals("none"))) {
            searchUrl = searchUrl.concat(imageTypeParam + filterSet.getTypeFilter());
        }
        Log.i("INFO", "searchUrl is "+searchUrl );

//        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        asyncHttpClient.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    imageResultsAdapter.addAll(ImageResult.fromJsonArray(imageResultsJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, FiltersActivity.class);
            startActivityForResult(intent, FILTER_RESULT_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                filterSet = data.getParcelableExtra("result");

            }
        }
    }
}
