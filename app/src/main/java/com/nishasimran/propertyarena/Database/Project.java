package com.nishasimran.propertyarena.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.nishasimran.propertyarena.Values.Values;

@Entity(tableName = Values.TABLE_PROJECTS)
public class Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Values.COLUMN_ID)
    private int id;
    @ColumnInfo(name = Values.COLUMN_PROJECT_NAME)
    private String projectName;
    @ColumnInfo(name = Values.COLUMN_DEVELOPER_NAME)
    private String developerName;
    @ColumnInfo(name = Values.COLUMN_ZONE)
    private String zone;
    @ColumnInfo(name = Values.COLUMN_CONFIGURATION)
    private String config;
    @ColumnInfo(name = Values.COLUMN_CARPET)
    private int carpet;
    @ColumnInfo(name = Values.COLUMN_RATE)
    private float rate;
    @ColumnInfo(name = Values.COLUMN_POSSESSION_DATE)
    private long possessionDate;
    @ColumnInfo(name = Values.COLUMN_STATUS)
    private String status;
    @ColumnInfo(name = Values.COLUMN_PAYMENT_PLAN)
    private String paymentPlan;
    @ColumnInfo(name = Values.COLUMN_SCHEME)
    private String scheme;
    @ColumnInfo(name = Values.COLUMN_SECTOR)
    private String sector;
    @ColumnInfo(name = Values.COLUMN_LAUNCH_TYPE)
    private String launchType;
    @ColumnInfo(name = Values.COLUMN_LAND_PARCEL)
    private float landParcel;
    @ColumnInfo(name = Values.COLUMN_TOWERS)
    private int towers;
    @ColumnInfo(name = Values.COLUMN_UNITS)
    private int units;
    @ColumnInfo(name = Values.COLUMN_SPECIFICATION)
    private String specifications;


    public Project(String projectName, String developerName, String zone, String config, int carpet, float rate, long possessionDate, String status, String paymentPlan, String scheme, String sector, String launchType, float landParcel , int towers, int units, String specifications) {
        this.projectName = projectName;
        this.developerName = developerName;
        this.zone = zone;
        this.config = config;
        this.carpet = carpet;
        this.rate = rate;
        this.possessionDate = possessionDate;
        this.status = status;
        this.paymentPlan = paymentPlan;
        this.scheme = scheme;
        this.sector = sector;
        this.launchType = launchType;
        this.landParcel = landParcel;
        this.towers = towers;
        this.units = units;
        this.specifications = specifications;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public int getCarpet() {
        return carpet;
    }

    public void setCarpet(int carpet) {
        this.carpet = carpet;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(long possessionDate) {
        this.possessionDate = possessionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getLaunchType() {
        return launchType;
    }

    public void setLaunchType(String launchType) {
        this.launchType = launchType;
    }

    public float getLandParcel() {
        return landParcel;
    }

    public void setLandParcel(float landParcel) {
        this.landParcel = landParcel;
    }

    public int getTowers() {
        return towers;
    }

    public void setTowers(int towers) {
        this.towers = towers;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
