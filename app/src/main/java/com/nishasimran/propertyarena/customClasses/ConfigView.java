package com.nishasimran.propertyarena.customClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nishasimran.propertyarena.Values.Values;
import com.nishasimran.propertyarena.R;

import static com.nishasimran.propertyarena.Values.Values.CONFIG_TYPES;

public class ConfigView {

    private final EditText configEditText;
    private final EditText carpetEditText;
    private final TextView carpetUnit;
    private Spinner configSpinner;
    private String configUnit;
    private final View view;

    ArrayAdapter<String> configSpinnerAdapter;

    @SuppressLint("InflateParams")
    public ConfigView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.project_config, null);
        configSpinner = view.findViewById(R.id.p_c_config_spinner);
        configEditText = view.findViewById(R.id.p_c_config);
        carpetEditText = view.findViewById(R.id.p_c_carpet);
        carpetUnit = view.findViewById(R.id.p_c_carpet_unit);

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
        Float value = getConfigEditTextValue();
        if (value == null) {
            return null;
        }
        return (value + getConfigUnit());
    }

    public Float getConfigEditTextValue() {
        if (configEditText.getText().toString().trim().isEmpty()) {
            return null;
        }
        return Float.parseFloat(configEditText.getText().toString().trim());
    }

    public Integer getCarpet() {
        if (carpetEditText.getText().toString().trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(carpetEditText.getText().toString().trim());
    }

    public String getConfigUnit() {
        if (configUnit == null) {
            return CONFIG_TYPES[0];
        }
        return configUnit;
    }

    public void setConfigError() {
        if (configEditText.getError() != null) {
            configEditText.setError(null);
        }
        configEditText.setError(Values.ERROR_REQUIRED);
    }

    public void setCarpetError() {
        if (carpetEditText.getError() != null) {
            carpetEditText.setError(null);
        }
        carpetEditText.setError(Values.ERROR_REQUIRED);
    }

    public void hideCarpet() {
        carpetEditText.setVisibility(View.GONE);
        carpetUnit.setVisibility(View.GONE);
    }

    public void setConfig(String config) {
        if (config.toLowerCase().endsWith("hk")) {
            config = config.split("B")[0];
            configSpinner.setSelection(0);
        } else {
            config = config.split("R")[0];
            configSpinner.setSelection(1);
        }
        configEditText.setText(config);
    }

    public void setCarpet(int carpet) {
        carpetEditText.setText(String.valueOf(carpet));
    }
}
