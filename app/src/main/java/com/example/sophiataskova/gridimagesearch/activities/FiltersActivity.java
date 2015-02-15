package com.example.sophiataskova.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sophiataskova.gridimagesearch.R;
import com.example.sophiataskova.gridimagesearch.models.FilterSet;

public class FiltersActivity extends Activity implements AdapterView.OnItemSelectedListener {

//    public static Size sizeFilter;
//    public static Type typeFilter;
//    public static Color colorFilter;
    public static String sizeFilter;
    public static String typeFilter;
    public static String colorFilter;
    private FilterSet filterSet;
    private Spinner sizeSpinner;
    private Spinner typeSpinner;
    private Spinner colorSpinner;
    private Button saveButton;

    public enum Size {
        NONE, SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }

    public enum Type {
        NONE, FACES, PHOTO, CLIP_ART, LINE_ART
    }

    public enum Color {
        NONE, BLACK, BLUE, RED, GREEN, YELLOW, BROWN, ORANGE, PINK, WHITE, GREY
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        filterSet = new FilterSet(sizeFilter, typeFilter, colorFilter);

        initializeSpinner(sizeSpinner, R.id.size_spinner, R.array.sizes_array);
        initializeSpinner(typeSpinner, R.id.color_spinner, R.array.colors_array);
        initializeSpinner(colorSpinner, R.id.type_spinner, R.array.types_array);
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("result", filterSet);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void initializeSpinner(Spinner spinner, int spinnerId, int optionsArrayId) {
        spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                optionsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.size_spinner) {
            Log.i("INFO", "size spinner changed to" + parent.getSelectedItem().toString());
            sizeFilter = parent.getSelectedItem().toString();
            filterSet.setSizeFilter(sizeFilter);
        }
        if (parent.getId() == R.id.type_spinner) {
            Log.i("INFO", "type spinner changed to" + parent.getSelectedItem().toString());
            typeFilter = parent.getSelectedItem().toString();
            filterSet.setTypeFilter(typeFilter);
        }
        if (parent.getId() == R.id.color_spinner) {
            Log.i("INFO", "color spinner changed to" + parent.getSelectedItem().toString());
            colorFilter = parent.getSelectedItem().toString();
            filterSet.setColorFilter(colorFilter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
