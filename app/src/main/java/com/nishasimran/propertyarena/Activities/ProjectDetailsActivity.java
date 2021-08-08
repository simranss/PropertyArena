package com.nishasimran.propertyarena.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectViewModel;
import com.nishasimran.propertyarena.R;
import com.nishasimran.propertyarena.Utils.Utils;
import com.nishasimran.propertyarena.Values.Values;

import java.util.Arrays;

public class ProjectDetailsActivity extends AppCompatActivity {

    private final String TAG = "ProDetailsAct";
    private final int DEFAULT_PROJECT_ID = -5;

    private int projectId;
    private Project project;

    private TextView developerTextView, zoneTextView, launchTextView, possessionDateTextView, statusTextView, sectorTextView, landParcelTextView, towersTextView, unitsTextView, paymentTextView, schemeTextView, specsTextView;
    private TableLayout tableLayout;
    private ConstraintLayout noProjectScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
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

    private void noProject(boolean value) {
        if (value) {
            noProjectScreen.setVisibility(View.VISIBLE);
        } else {
            noProjectScreen.setVisibility(View.GONE);
        }
    }

    private void initProjectListener() {
        ProjectViewModel.getInstance(this, getApplication()).getAllProjects().observe(this, projects1 -> {
            Log.d(TAG, "projects: " + projects1);
            project = ProjectViewModel.getInstance(this, getApplication()).findProject(projectId, projects1);
            if (project != null) {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(project.getProjectName());
                noProject(false);
                updateData(project);

            } else {
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle("Project Details");
                noProject(true);
            }
        });
    }

    private void updateData(Project project) {
        if (project != null) {
            developerTextView.setText(project.getDeveloperName());
            zoneTextView.setText(project.getZone());
            launchTextView.setText(project.getLaunchType());
            setUpTableLayout(project);
            String possessionDate = Utils.getFormattedDate(project.getPossessionDate());
            possessionDateTextView.setText(possessionDate);
            statusTextView.setText(project.getStatus());
            sectorTextView.setText(project.getSector());
            String landParcel = project.getLandParcel() + "\nacres";
            String towers = project.getTowers() + "\ntowers";
            String units = project.getUnits() + "\nunits";
            landParcelTextView.setText(landParcel);
            towersTextView.setText(towers);
            unitsTextView.setText(units);
            paymentTextView.setText(project.getPaymentPlan());
            schemeTextView.setText(project.getScheme());
            specsTextView.setText(project.getSpecifications());
        }
    }

    private void setUpTableLayout(Project project1) {
        if (project1 != null) {
            int[] carpet = Utils.intArrayToArray(project1.getCarpet());
            String[] config = Utils.stringArrayToArray(project1.getConfig());
            Log.d(TAG, "carpet: " + Arrays.toString(carpet));
            Log.d(TAG, "config: " + Arrays.toString(config));
            int size = Math.max(carpet.length, config.length);
            for (int i = 0; i < size; i++) {
                Log.d(TAG, "setUpTableLayout index: " + i);

                Log.d(TAG, "setUpTableLayout: for " + ((carpet.length - 1) >= i));
                Log.d(TAG, "setUpTableLayout: for " + (carpet.length - 1));
                Log.d(TAG, "setUpTableLayout: for " + i);
                Log.d(TAG, "setUpTableLayout: for " + carpet.length);

                TableRow row1 = new TableRow(this);
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                row1.setLayoutParams(lp1);

                TextView configTextView1, carpetTextView1, actualPriceTextView1, taxPriceTextView1;

                View border1 = new View(this);
                border1.setLayoutParams(new TableRow.LayoutParams(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), TableRow.LayoutParams.MATCH_PARENT));
                border1.setBackground(AppCompatResources.getDrawable(this, R.color.black));

                View border2 = new View(this);
                border2.setLayoutParams(new TableRow.LayoutParams(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), TableRow.LayoutParams.MATCH_PARENT));
                border2.setBackground(AppCompatResources.getDrawable(this, R.color.black));

                View border3 = new View(this);
                border3.setLayoutParams(new TableRow.LayoutParams(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics())), TableRow.LayoutParams.MATCH_PARENT));
                border3.setBackground(AppCompatResources.getDrawable(this, R.color.black));

                configTextView1 = new TextView(this);
                configTextView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                configTextView1.setGravity(Gravity.CENTER_HORIZONTAL);
                configTextView1.setPaddingRelative(10, 8, 10, 8);
                configTextView1.setTextColor(getColor(R.color.black));
                configTextView1.setTextSize(18);
                if ((config.length - 1) >= i)
                    configTextView1.setText(config[i]);
                else
                    configTextView1.setText("-");

                carpetTextView1 = new TextView(this);
                carpetTextView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                carpetTextView1.setGravity(Gravity.CENTER_HORIZONTAL);
                carpetTextView1.setPaddingRelative(10, 8, 10, 8);
                carpetTextView1.setTextColor(getColor(R.color.black));
                carpetTextView1.setTextSize(18);
                if ((carpet.length - 1) >= i)
                    carpetTextView1.setText(String.valueOf(carpet[i]));
                else
                    carpetTextView1.setText("-");

                actualPriceTextView1 = new TextView(this);
                actualPriceTextView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                actualPriceTextView1.setGravity(Gravity.CENTER_HORIZONTAL);
                actualPriceTextView1.setPaddingRelative(10, 8, 10, 8);
                actualPriceTextView1.setTextColor(getColor(R.color.black));
                actualPriceTextView1.setTextSize(18);
                String actualPrice;
                if ((carpet.length - 1) >= i) {
                    Log.d(TAG, "carpet[i]: " + carpet[i]);
                    Log.d(TAG, "project.getRate(): " + project1.getRate());
                    actualPrice = ((carpet[i] * project1.getRate()) / 100000) + "L";
                    Log.d(TAG, "actualPrice: " + actualPrice);
                    actualPriceTextView1.setText(actualPrice);
                } else
                    actualPriceTextView1.setText("-");

                taxPriceTextView1 = new TextView(this);
                taxPriceTextView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                taxPriceTextView1.setGravity(Gravity.CENTER_HORIZONTAL);
                taxPriceTextView1.setPaddingRelative(10, 8, 10, 8);
                taxPriceTextView1.setTextColor(getColor(R.color.black));
                taxPriceTextView1.setTextSize(18);
                String taxPrice;
                if ((carpet.length - 1) >= i) {
                    // TODO: calculate the taxPrice according to the rate and the tax
                    taxPrice = ((carpet[i] * project1.getRate() * (Utils.getStampDutyTax(getApplication()) + Utils.getGstTax(getApplication()))) + Utils.getRegFee(getApplication())) + "L";
                    taxPriceTextView1.setText(taxPrice);
                } else
                    taxPriceTextView1.setText("-");

                row1.addView(configTextView1);
                row1.addView(border1);
                row1.addView(carpetTextView1);
                row1.addView(border2);
                row1.addView(actualPriceTextView1);
                row1.addView(border3);
                row1.addView(taxPriceTextView1);
                tableLayout.addView(row1);
            }
        }
    }

    private void initViews() {
        developerTextView = findViewById(R.id.pro_det_developer);
        zoneTextView = findViewById(R.id.pro_det_zone);
        launchTextView = findViewById(R.id.pro_det_launch);
        tableLayout = findViewById(R.id.pro_det_conf_price_table);
        possessionDateTextView = findViewById(R.id.pro_det_possession);
        statusTextView = findViewById(R.id.pro_det_status);
        sectorTextView = findViewById(R.id.pro_det_sector);
        landParcelTextView = findViewById(R.id.pro_det_land_parcel);
        towersTextView = findViewById(R.id.pro_det_towers);
        unitsTextView = findViewById(R.id.pro_det_units);
        paymentTextView = findViewById(R.id.pro_det_payment);
        schemeTextView = findViewById(R.id.pro_det_scheme);
        specsTextView = findViewById(R.id.pro_det_specs);

        noProjectScreen = findViewById(R.id.pro_det_no_project_scr);
        noProjectScreen.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pro_det_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            Intent intent = new Intent(this, ProjectEditActivity.class);
            intent.putExtra(Values.KEY_PROJECT_ID, projectId);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.menu_delete) {
            if (project != null) {
                String title = "Delete " + project.getProjectName();
                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage("Are you sure you want to delete this project?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> ProjectViewModel.getInstance(this, getApplication()).delete(project))
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.delete)
                        .show();
                return true;
            } else return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}