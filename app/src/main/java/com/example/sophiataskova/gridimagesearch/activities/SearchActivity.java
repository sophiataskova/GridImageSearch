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
import com.example.sophiataskova.gridimagesearch.models.EndlessScrollListener;
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

    public static final int FILTER_RESULT_CODE = 123;
    private static String IMAGE_SIZE_PARAM = "&imgsz=";
    private static String IMAGE_TYPE_PARAM = "&imgtype=";
    private static String IMAGE_COLOR_PARAM = "&imgcolor=";
    private static String IMAGE_SITE_PARAM = "&as_sitesearch=";
    private static String OFFSET = "&start=";

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter imageResultsAdapter;
    private FilterSet filterSet;
    private static String mUrl;
    private static String mPaginatedUrl;
    private int mOffset;


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
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
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

    public void customLoadMoreDataFromApi(int offset) {
        mOffset = mOffset + 8;
        makeRequest(mOffset);
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
    }

    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();

//        https://ajax.googleapis.com/ajax/services/search/images?q=fuzzy%20monkey&v=1.0
        mUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        if (filterSet != null) {
            if (!(filterSet.getSizeFilter().equals("none"))) {
                mUrl = mUrl.concat(IMAGE_SIZE_PARAM + filterSet.getSizeFilter());
            }
            if (!(filterSet.getColorFilter().equals("none"))) {
                mUrl = mUrl.concat(IMAGE_COLOR_PARAM + filterSet.getColorFilter());
            }
            if (!(filterSet.getTypeFilter().equals("none"))) {
                mUrl = mUrl.concat(IMAGE_TYPE_PARAM + filterSet.getTypeFilter());
            }
            if (!(filterSet.getSiteFilter().equals(""))) {
                mUrl = mUrl.concat(IMAGE_SITE_PARAM + filterSet.getSiteFilter());
            }
        }
        Log.i("INFO", "searchUrl is " + mUrl);

        makeRequest(0);
    }

    private void makeRequest(final int offset) {
        mOffset = offset;
        mPaginatedUrl = mUrl.concat(OFFSET + mOffset);
        Log.i("INFO", "searchUrl is " + mPaginatedUrl);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(mPaginatedUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    if (mOffset == 0) {
                        imageResults.clear();
                    }
                    imageResultsAdapter.addAll(ImageResult.fromJsonArray(imageResultsJson));
                    if (imageResultsAdapter.isEmpty()) {
                        findViewById(R.id.no_results).setVisibility(View.VISIBLE);
                        gvResults.setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.no_results).setVisibility(View.GONE);
                        gvResults.setVisibility(View.VISIBLE);
                    }
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
