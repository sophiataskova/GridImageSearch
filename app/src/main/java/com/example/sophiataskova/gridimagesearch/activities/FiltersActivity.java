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
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sophiataskova.gridimagesearch.R;
import com.example.sophiataskova.gridimagesearch.models.FilterSet;

public class FiltersActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static String sizeFilter;
    private static String typeFilter;
    private static String colorFilter;
    private static String siteFilter;
    private FilterSet filterSet;
    private Spinner sizeSpinner;
    private Spinner typeSpinner;
    private Spinner colorSpinner;
    private Button saveButton;
    private EditText etSiteFilter;
    private ArrayAdapter<CharSequence> sizeAdapter;
    private ArrayAdapter<CharSequence> colorAdapter;
    private ArrayAdapter<CharSequence> typeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        if (getIntent().getParcelableExtra("result") == null) {
            filterSet = new FilterSet(sizeFilter, typeFilter, colorFilter, siteFilter);
        } else {
            filterSet = getIntent().getParcelableExtra("result");
        }

        initializeFilters();
        initializeSaveButton();
    }

    private void initializeFilters() {
        etSiteFilter = (EditText) findViewById(R.id.site_et);

        sizeSpinner = (Spinner) findViewById(R.id.size_spinner);
        colorSpinner = (Spinner) findViewById(R.id.color_spinner);
        typeSpinner = (Spinner) findViewById(R.id.type_spinner);

        sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        if (filterSet != null) {
            if (filterSet.getSiteFilter() != "") {
                etSiteFilter.setText(filterSet.getSiteFilter());
            }
            if (filterSet.getSizeFilter() != null) {
                setSpinnerState(sizeAdapter, sizeSpinner, filterSet.getSizeFilter());
            }
            if (filterSet.getTypeFilter() != null) {
                setSpinnerState(typeAdapter, typeSpinner, filterSet.getTypeFilter());
            }
            if (filterSet.getColorFilter() != null) {
                setSpinnerState(colorAdapter, colorSpinner, filterSet.getColorFilter());
            }
        }
        sizeSpinner.setOnItemSelectedListener(this);
        colorSpinner.setOnItemSelectedListener(this);
        typeSpinner.setOnItemSelectedListener(this);
    }

    private void initializeSaveButton() {
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSet.setSiteFilter(etSiteFilter.getText().toString());
                Intent i = new Intent();
                i.putExtra("result", filterSet);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void setSpinnerState(ArrayAdapter<CharSequence> arrayAdapter, Spinner spinner, String value) {
        if (!value.equals(null)) {
            int spinnerPostion = arrayAdapter.getPosition(value);
            spinner.setSelection(spinnerPostion);
        }
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
