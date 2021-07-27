package com.nishasimran.propertyarena.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.nishasimran.propertyarena.customClasses.ConfigView;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectViewModel;
import com.nishasimran.propertyarena.Utils.Utils;
import com.nishasimran.propertyarena.Values.Values;
import com.nishasimran.propertyarena.R;

import java.util.ArrayList;

public class AddProjectActivity extends AppCompatActivity {

    private final String TAG = "AddProjectAct";

    // views
    private RadioGroup launchRadioGroup, statusRadioGroup, sectorRadioGroup;
    private RadioButton statusInProgressRadio;
    private TextView launchTextView, statusTextView, sectorTextView;
    private DatePickerDialog datePicker;
    private Button saveButton;
    private ImageView addConfigImageView;
    private LinearLayout configContainer;
    private EditText projectNameEditText, developerNameEditText, zoneEditText, rateEditText, possessionDateEditText, statusEditText, paymentPlanEditText, schemeEditText, landParcelEditText, towersEditText, unitsEditText, specsEditText;

    // values
    private String projectName, developerName, launchType, zone, status, paymentPlan, scheme, sector, specs;
    private float rate = 0.0f, landParcel = 0.0f;
    private final ArrayList<String> config = new ArrayList<>();
    private final ArrayList<Integer> carpet = new ArrayList<>();
    private long possessionDate = 0L;
    private int towers = 0, units = 0;

    private final ArrayList<ConfigView> configViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (!toolbar.isTitleCentered())
            toolbar.setTitleCentered(true);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        initViews();
        setDefaultsForViews();
    }

    private void setDefaultsForViews() {

        ConfigView configView = new ConfigView(AddProjectActivity.this);
        configViews.add(configView);
        configContainer.addView(configView.getView());


        addConfigImageView.setOnClickListener(v -> {
            ConfigView configView1 = new ConfigView(AddProjectActivity.this);
            configViews.add(configView1);
            configContainer.addView(configView1.getView());
        });


        possessionDateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            // date picker dialog
            datePicker = new DatePickerDialog(AddProjectActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        Log.d(TAG, "day: " + dayOfMonth + ", month: " + (monthOfYear + 1) + ", year: " + year1);Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, year1);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        possessionDate = myCalendar.getTimeInMillis();
                        String formattedDate = Utils.getFormattedDate(myCalendar.getTimeInMillis());
                        possessionDateEditText.setText(formattedDate);
                    }, year, month, day);
            datePicker.show();
        });


        statusEditText.setVisibility(View.GONE);


        statusInProgressRadio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                statusEditText.setVisibility(View.VISIBLE);
            } else {
                statusEditText.setVisibility(View.GONE);
            }
        });


        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                projectName = projectNameEditText.getText().toString().trim();
                developerName = developerNameEditText.getText().toString().trim();
                zone = zoneEditText.getText().toString().trim();
                rate = Float.parseFloat(rateEditText.getText().toString().trim());
                paymentPlan = paymentPlanEditText.getText().toString().trim();
                scheme = schemeEditText.getText().toString().trim();
                landParcel = Float.parseFloat(landParcelEditText.getText().toString().trim());
                towers = Integer.parseInt(towersEditText.getText().toString().trim());
                units = Integer.parseInt(unitsEditText.getText().toString().trim());
                specs = specsEditText.getText().toString().trim();

                Project project = new Project(projectName, developerName, zone, config.toString(), carpet.toString(), rate, possessionDate, status, paymentPlan, scheme, sector, launchType, landParcel, towers, units, specs);

                ProjectViewModel.getInstance(this, getApplication()).insert(project);

                Toast.makeText(AddProjectActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddProjectActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs() {
        if (isEditTextEmpty(projectNameEditText)) {
            projectNameEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(developerNameEditText)) {
            developerNameEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (launchTextView.getError() != null)
            launchTextView.setError(null);
        if (launchRadioGroup.getCheckedRadioButtonId() == -1) {
            launchTextView.setError(Values.ERROR_REQUIRED);
            return false;
        } else {
            RadioButton launchTypeRadio = launchRadioGroup.findViewById(launchRadioGroup.getCheckedRadioButtonId());
            launchType = launchTypeRadio.getText().toString().trim();
        }
        if (isEditTextEmpty(zoneEditText)) {
            zoneEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(rateEditText)) {
            rateEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        for (int i = 0; i < configContainer.getChildCount(); i++) {
            String configValue = configViews.get(i).getConfig();
            Integer carpetValue = configViews.get(i).getCarpet();
            if (configValue != null && carpetValue != null) {
                config.add(configValue);
                carpet.add(carpetValue);
            } else if (configValue == null && carpetValue == null) {
                if (i != 0) {
                    configContainer.removeViewAt(i);
                    configViews.remove(i);
                    i--;
                }
            } else if (carpetValue != null) {
                configViews.get(i).setConfigError();
                config.clear();
                carpet.clear();
                return false;
            } else {
                configViews.get(i).setCarpetError();
                config.clear();
                carpet.clear();
                return false;
            }
        }
        if (config.isEmpty()) {
            return false;
        }
        if (carpet.isEmpty()) {
            return false;
        }
        if (possessionDateEditText.getError() != null)
            possessionDateEditText.setError(null);
        if (possessionDate == 0L) {
            possessionDateEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (statusTextView.getError() != null)
            statusTextView.setError(null);
        if (statusRadioGroup.getCheckedRadioButtonId() == -1) {
            statusTextView.setError(Values.ERROR_REQUIRED);
            return false;
        } else {
            RadioButton statusTypeRadio = statusRadioGroup.findViewById(statusRadioGroup.getCheckedRadioButtonId());
            if (statusTypeRadio.getId() == statusInProgressRadio.getId() || statusTypeRadio.getText().toString().trim().equals(getString(R.string.status_in_progress)) || statusTypeRadio.getText().toString().trim().equals(statusInProgressRadio.getText().toString().trim())) {
                if (isEditTextEmpty(statusEditText)) {
                    statusEditText.setError(Values.ERROR_REQUIRED);
                    return false;
                } else {
                    status = statusEditText.getText().toString().trim();
                }
            } else {
                status = statusTypeRadio.getText().toString().trim();
            }
        }
        if (isEditTextEmpty(paymentPlanEditText)) {
            paymentPlanEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(schemeEditText)) {
            schemeEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (sectorTextView.getError() != null)
            sectorTextView.setError(null);
        if (sectorRadioGroup.getCheckedRadioButtonId() == -1) {
            sectorTextView.setError(Values.ERROR_REQUIRED);
            return false;
        } else {
            RadioButton sectorRadio = sectorRadioGroup.findViewById(sectorRadioGroup.getCheckedRadioButtonId());
            sector = sectorRadio.getText().toString().trim();
        }
        if (isEditTextEmpty(landParcelEditText)) {
            landParcelEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(towersEditText)) {
            towersEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(unitsEditText)) {
            unitsEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }
        if (isEditTextEmpty(specsEditText)) {
            specsEditText.setError(Values.ERROR_REQUIRED);
            return false;
        }

        return true;
    }

    private void initViews() {
        projectNameEditText = findViewById(R.id.a_p_project_name);
        developerNameEditText = findViewById(R.id.a_p_developer_name);
        launchRadioGroup = findViewById(R.id.a_p_launch_radio_grp);
        zoneEditText = findViewById(R.id.a_p_zone);
        rateEditText = findViewById(R.id.a_p_rate);
        configContainer = findViewById(R.id.a_p_conf_container);
        addConfigImageView = findViewById(R.id.a_p_add_conf);
        possessionDateEditText = findViewById(R.id.a_p_possession);
        statusRadioGroup = findViewById(R.id.a_p_status_radio_grp);
        statusEditText = findViewById(R.id.a_p_status);
        paymentPlanEditText = findViewById(R.id.a_p_payment_plan);
        schemeEditText = findViewById(R.id.a_p_scheme);
        sectorRadioGroup = findViewById(R.id.a_p_sector_radio_grp);
        landParcelEditText = findViewById(R.id.a_p_land_parcel);
        towersEditText = findViewById(R.id.a_p_towers);
        unitsEditText = findViewById(R.id.a_p_units);
        specsEditText = findViewById(R.id.a_p_specs);
        saveButton = findViewById(R.id.a_p_save);

        launchTextView = findViewById(R.id.a_p_launch_head);
        statusTextView = findViewById(R.id.a_p_status_head);
        sectorTextView = findViewById(R.id.a_p_sector_head);

        statusInProgressRadio = findViewById(R.id.a_p_status_in_progress);

    }

    private boolean isEditTextEmpty(@NonNull EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
}