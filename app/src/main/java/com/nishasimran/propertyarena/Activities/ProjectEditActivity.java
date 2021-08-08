package com.nishasimran.propertyarena.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectViewModel;
import com.nishasimran.propertyarena.R;
import com.nishasimran.propertyarena.Utils.Utils;
import com.nishasimran.propertyarena.Values.Values;
import com.nishasimran.propertyarena.customClasses.ConfigView;

import java.util.ArrayList;
import java.util.Locale;

public class ProjectEditActivity extends AppCompatActivity {

    private final String TAG = "ProEditAct";
    private final int DEFAULT_PROJECT_ID = -5;

    private int projectId;
    private Project project;

    private EditText projectNameEditText, developerNameEditText, zoneEditText, rateEditText, possessionEditText, statusEditText, paymentEditText, schemeEditText, landParcelEditText, towersEditText, unitsEditText, specsEditText;
    private RadioButton preLaunchRadio, launchRadio, relaunchRadio, rtmiRadio, underConstRadio, residentialRadio, commercialRadio, officesRadio;
    private TextView launchTextView, statusTextView, sectorTextView;
    private DatePickerDialog datePicker;
    private Button saveButton;
    private ImageView addConfigImageView;
    private LinearLayout configContainer;
    private AlertDialog noProjectDialog;

    private final ArrayList<ConfigView> configViews = new ArrayList<>();

    // values
    private String projectName, developerName, launchType, zone, status, paymentPlan, scheme, sector, specs;
    private Float rate, landParcel;
    private String[] config;
    private int[] carpet;
    private Long possessionDate;
    private Integer towers, units;

    // new values
    private String newProjectName, newDeveloperName, newLaunchType, newZone, newStatus, newPaymentPlan, newScheme, newSector, newSpecs;
    private Float newRate, newLandParcel;
    private String[] newConfig;
    private int[] newCarpet;
    private Long newPossessionDate;
    private Integer newTowers, newUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.edit_project);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        projectId = getIntent().getIntExtra(Values.KEY_PROJECT_ID, DEFAULT_PROJECT_ID);
        if (projectId == DEFAULT_PROJECT_ID) {
            finish();
            Toast.makeText(this, getString(R.string.no_project_str), Toast.LENGTH_SHORT).show();
        }

        initViews();

        initProjectListener();
    }

    private void initProjectListener() {
        ProjectViewModel.getInstance(this, getApplication()).getAllProjects().observe(this, projects1 -> {
            Log.d(TAG, "projects: " + projects1);
            project = ProjectViewModel.getInstance(this, getApplication()).findProject(projectId, projects1);
            if (project != null) {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(project.getProjectName());
                updateData(project);

            } else {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle("Project Details");
            }
        });
    }

    private void updateData(Project project) {
        if (project != null) {
            projectNameEditText.setText(project.getProjectName());
            developerNameEditText.setText(project.getDeveloperName());
            zoneEditText.setText(project.getZone());
            if (project.getLaunchType().toLowerCase().trim().startsWith("launch"))
                launchRadio.setChecked(true);
            else if (project.getLaunchType().toLowerCase().trim().startsWith("pre"))
                preLaunchRadio.setChecked(true);
            else{
                relaunchRadio.setChecked(true);
            }
            rateEditText.setText(String.valueOf(project.getRate()));
            String possessionDate = Utils.getFormattedDate(project.getPossessionDate());
            possessionEditText.setText(possessionDate);
            if (project.getStatus().trim().toLowerCase().startsWith("rtmi"))
                rtmiRadio.setChecked(true);
            else
                underConstRadio.setChecked(true);
            if (project.getSector().trim().toLowerCase().startsWith("residential"))
                residentialRadio.setChecked(true);
            else if (project.getSector().trim().toLowerCase().startsWith("commercial"))
                commercialRadio.setChecked(true);
            else{
                officesRadio.setChecked(true);
            }
            landParcelEditText.setText(String.valueOf(project.getLandParcel()));
            towersEditText.setText(String.valueOf(project.getTowers()));
            unitsEditText.setText(String.valueOf(project.getUnits()));
            paymentEditText.setText(project.getPaymentPlan());
            schemeEditText.setText(project.getScheme());
            specsEditText.setText(project.getSpecifications());

            carpet = Utils.intArrayToArray(project.getCarpet());
            config = Utils.stringArrayToArray(project.getConfig());

            for (String ele : config) {
                ConfigView configView = new ConfigView(this);
                configView.setConfig(ele);
                configViews.add(configView);
            }
            for (int i = 0; i <= carpet.length; i++) {
                if (i < configViews.size()) {
                    ConfigView configView = configViews.get(i);
                    configView.setCarpet(carpet[i]);
                    configContainer.addView(configView.getView());
                } else {
                    break;
                }
            }
        }
    }

    private void initViews() {
        projectNameEditText = findViewById(R.id.a_p_project_name);
        developerNameEditText = findViewById(R.id.a_p_developer_name);
        preLaunchRadio = findViewById(R.id.a_p_launch_prelaunch);
        launchRadio = findViewById(R.id.a_p_launch_launch);
        relaunchRadio = findViewById(R.id.a_p_launch_relaunch);
        zoneEditText = findViewById(R.id.a_p_zone);
        rateEditText = findViewById(R.id.a_p_rate);
        configContainer = findViewById(R.id.a_p_conf_container);
        addConfigImageView = findViewById(R.id.a_p_add_conf);
        possessionEditText = findViewById(R.id.a_p_possession);
        rtmiRadio = findViewById(R.id.a_p_status_completed);
        underConstRadio = findViewById(R.id.a_p_status_in_progress);
        statusEditText = findViewById(R.id.a_p_status);
        paymentEditText = findViewById(R.id.a_p_payment_plan);
        schemeEditText = findViewById(R.id.a_p_scheme);
        residentialRadio = findViewById(R.id.a_p_sector_residential);
        commercialRadio = findViewById(R.id.a_p_sector_commercial);
        officesRadio = findViewById(R.id.a_p_sector_offices);
        landParcelEditText = findViewById(R.id.a_p_land_parcel);
        towersEditText = findViewById(R.id.a_p_towers);
        unitsEditText = findViewById(R.id.a_p_units);
        specsEditText = findViewById(R.id.a_p_specs);
        saveButton = findViewById(R.id.a_p_save);

        launchTextView = findViewById(R.id.a_p_launch_head);
        statusTextView = findViewById(R.id.a_p_status_head);
        sectorTextView = findViewById(R.id.a_p_sector_head);
    }
}