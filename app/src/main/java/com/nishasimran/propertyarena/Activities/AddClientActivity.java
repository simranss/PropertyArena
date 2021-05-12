package com.nishasimran.propertyarena.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.nishasimran.propertyarena.CustomView.ConfigView;
import com.nishasimran.propertyarena.Database.Client;
import com.nishasimran.propertyarena.Database.ClientViewModel;
import com.nishasimran.propertyarena.Database.Values;
import com.nishasimran.propertyarena.R;

import java.util.ArrayList;

public class AddClientActivity extends AppCompatActivity {

    private final String TAG = "AddClientAct";

    // views
    private RadioGroup reqStatusRadioGroup;
    private TextView employmentTextView, reqStatusTextView;
    private Button saveButton;
    private ImageView reqAddConfigImageView;
    private LinearLayout reqConfigContainer;
    private Spinner employmentSpinner, reqBudgetUnitSpinner;
    private EditText nameEditText, resAddressEditText, offAddressEditText, phoneEditText, emailEditText, reqLocalityEditText, reqCarpetEditText, reqBudgetEditText, reqSpecsEditText, reqRemarksEditText;

    // values
    private String name, resAddress, offAddress, employment, phone, email, reqLocality, reqBudget, reqStatus, reqSpecs, reqRemarks, budgetUnit;
    private float reqCarpet = 0.0f;
    private final ArrayList<String> reqConfig = new ArrayList<>();

    ArrayAdapter<String> employmentAdapter, budgetUnitAdapter;
    private final ArrayList<ConfigView> configViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setDefaultsForViews();
    }

    private void setDefaultsForViews() {
        employmentAdapter = new ArrayAdapter<>(AddClientActivity.this, android.R.layout.simple_spinner_item, Values.EMPLOYMENT_OPTIONS);
        employmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employmentSpinner.setAdapter(employmentAdapter);
        employmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employment = Values.EMPLOYMENT_OPTIONS[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        reqAddConfigImageView.setOnClickListener(v -> {
            ConfigView configView1 = new ConfigView(AddClientActivity.this);
            configViews.add(configView1);
            reqConfigContainer.addView(configView1.getView());
        });


        ConfigView configView = new ConfigView(AddClientActivity.this);
        configViews.add(configView);
        reqConfigContainer.addView(configView.getView());


        budgetUnitAdapter = new ArrayAdapter<>(AddClientActivity.this, android.R.layout.simple_spinner_item, Values.PRICING_UNITS);
        budgetUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reqBudgetUnitSpinner.setAdapter(budgetUnitAdapter);
        reqBudgetUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                budgetUnit = Values.PRICING_UNITS[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        saveButton.setOnClickListener(v -> {
            if (validateViews()) {
                name = nameEditText.getText().toString().trim();
                resAddress = resAddressEditText.getText().toString().trim();
                offAddress = offAddressEditText.getText().toString().trim();
                phone = Values.COUNTRY_CODE + phoneEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                reqLocality = reqLocalityEditText.getText().toString().trim();
                reqCarpet = Float.parseFloat(reqCarpetEditText.getText().toString().trim());
                reqBudget = reqBudgetEditText.getText().toString().trim() + ((budgetUnit == null)?Values.PRICING_UNITS[0]:budgetUnit);
                reqSpecs = reqSpecsEditText.getText().toString().trim();
                reqRemarks = reqRemarksEditText.getText().toString().trim();

                Client client = new Client(name, resAddress, offAddress, employment, phone, email, reqLocality, reqConfig.toString(), reqCarpet, reqBudget, reqStatus, reqSpecs, reqRemarks);
                ClientViewModel.getInstance(AddClientActivity.this, getApplication()).insert(client);

                finish();
            } else {
                Toast.makeText(AddClientActivity.this, Values.MESSAGE_FILL_ALL, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateViews() {
        if (isEditTextEmpty(nameEditText)) {
            nameEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(resAddressEditText)) {
            resAddressEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(offAddressEditText)) {
            offAddressEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        employmentTextView.setError(null);
        if (employment == null || employment.equals(Values.EMPLOYMENT_OPTIONS[0]) || employment.trim().isEmpty()) {
            employmentTextView.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(phoneEditText)) {
            phoneEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(emailEditText)) {
            emailEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(reqLocalityEditText)) {
            reqLocalityEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        for (int i = 0; i < reqConfigContainer.getChildCount(); i++) {
            String configValue = configViews.get(i).getConfig();
            if (configValue != null) {
                reqConfig.add(configValue);
            } else {
                if (i != 0) {
                    reqConfigContainer.removeViewAt(i);
                    configViews.remove(i);
                    i--;
                }
            }
        }
        if (reqConfig.isEmpty()) {
            configViews.get(0).setError();
            return false;
        }
        if (isEditTextEmpty(reqCarpetEditText)) {
            reqCarpetEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(reqBudgetEditText)) {
            reqBudgetEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        reqStatusTextView.setError(null);
        if (reqStatusRadioGroup.getCheckedRadioButtonId() == -1) {
            reqStatusTextView.setError(Values.ERROR_REQUIRED);
            return false;
        } else {
            RadioButton reqStatusTypeRadio = reqStatusRadioGroup.findViewById(reqStatusRadioGroup.getCheckedRadioButtonId());
            reqStatus = reqStatusTypeRadio.getText().toString().trim();
        }
        if (isEditTextEmpty(reqSpecsEditText)) {
            reqSpecsEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(reqRemarksEditText)) {
            reqRemarksEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }

        return true;
    }

    private boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private void initViews() {
        nameEditText = findViewById(R.id.a_c_name);
        resAddressEditText = findViewById(R.id.a_c_res_add);
        offAddressEditText = findViewById(R.id.a_c_off_add);
        employmentSpinner = findViewById(R.id.a_c_employment);
        phoneEditText = findViewById(R.id.a_c_phone);
        emailEditText = findViewById(R.id.a_c_email);
        reqLocalityEditText = findViewById(R.id.a_c_req_locality);
        reqAddConfigImageView = findViewById(R.id.a_c_req_add_conf);
        reqConfigContainer = findViewById(R.id.a_c_req_conf_container);
        reqCarpetEditText = findViewById(R.id.a_c_req_carpet);
        reqBudgetEditText = findViewById(R.id.a_c_req_budget);
        reqBudgetUnitSpinner = findViewById(R.id.a_c_req_budget_unit);
        reqStatusRadioGroup = findViewById(R.id.a_c_req_status_radio_grp);
        reqSpecsEditText = findViewById(R.id.a_c_req_specs);
        reqRemarksEditText = findViewById(R.id.a_c_req_remarks);
        saveButton = findViewById(R.id.a_c_save);

        employmentTextView = findViewById(R.id.a_c_employment_head);
        reqStatusTextView = findViewById(R.id.a_c_req_status_head);
    }
}