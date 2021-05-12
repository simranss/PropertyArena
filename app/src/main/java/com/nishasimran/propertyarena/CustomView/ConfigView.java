package com.nishasimran.propertyarena.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nishasimran.propertyarena.Database.Values;
import com.nishasimran.propertyarena.R;

import static com.nishasimran.propertyarena.Database.Values.CONFIG_TYPES;

public class ConfigView {

    private final EditText configEditText;
    private String configUnit;
    private final View view;

    ArrayAdapter<String> configSpinnerAdapter;

    @SuppressLint("InflateParams")
    public ConfigView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.project_config, null);
        Spinner configSpinner = view.findViewById(R.id.a_e_config_spinner);
        configEditText = view.findViewById(R.id.a_e_config);

        configSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                configUnit = CONFIG_TYPES[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        configSpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, CONFIG_TYPES);
        configSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        configSpinner.setAdapter(configSpinnerAdapter);
    }

    public View getView() {
        return view;
    }

    public String getConfig() {
        float value = getEditTextValue();
        if (value == 0.0f) {
            return null;
        }
        return (value + getConfigUnit());
    }

    public float getEditTextValue() {
        if (configEditText.getText().toString().trim().isEmpty()) {
            return 0.0f;
        }
        return Float.parseFloat(configEditText.getText().toString().trim());
    }

    public String getConfigUnit() {
        if (configUnit == null) {
            return CONFIG_TYPES[0];
        }
        return configUnit;
    }

    public void setError() {
        if (configEditText.getError() != null) {
            configEditText.setError(null);
        }
        configEditText.setError(Values.ERROR_REQUIRED);
    }
}
