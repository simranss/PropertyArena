package com.nishasimran.propertyarena.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Values.TABLE_CLIENTS)
public class Client {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Values.COLUMN_ID)
    private int id;
    @ColumnInfo(name = Values.COLUMN_CLIENT_NAME)
    private String name;
    @ColumnInfo(name = Values.COLUMN_RES_ADD)
    private String resAddress;
    @ColumnInfo(name = Values.COLUMN_OFF_ADD)
    private String offAddress;
    @ColumnInfo(name = Values.COLUMN_EMPLOYMENT)
    private String employment;
    @ColumnInfo(name = Values.COLUMN_CLIENT_PHONE)
    private String phone;
    @ColumnInfo(name = Values.COLUMN_CLIENT_EMAIL)
    private String email;
    @ColumnInfo(name = Values.COLUMN_REQ_LOCALITY)
    private String locality;
    @ColumnInfo(name = Values.COLUMN_REQ_CONF)
    private String config;
    @ColumnInfo(name = Values.COLUMN_REQ_CARPET)
    private float carpet;
    @ColumnInfo(name = Values.COLUMN_REQ_BUDGET)
    private String budget;
    @ColumnInfo(name = Values.COLUMN_REQ_STATUS)
    private String status;
    @ColumnInfo(name = Values.COLUMN_REQ_SPECS)
    private String specs;
    @ColumnInfo(name = Values.COLUMN_REQ_REMARK)
    private String remarks;



    public Client(String name, String resAddress, String offAddress, String employment, String phone, String email, String locality, String config, float carpet, String budget, String status, String specs, String remarks) {
        this.name = name;
        this.resAddress = resAddress;
        this.offAddress = offAddress;
        this.employment = employment;
        this.phone = phone;
        this.email = email;
        this.locality = locality;
        this.config = config;
        this.carpet = carpet;
        this.budget = budget;
        this.status = status;
        this.specs = specs;
        this.remarks = remarks;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResAddress() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress = resAddress;
    }

    public String getOffAddress() {
        return offAddress;
    }

    public void setOffAddress(String offAddress) {
        this.offAddress = offAddress;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public float getCarpet() {
        return carpet;
    }

    public void setCarpet(float carpet) {
        this.carpet = carpet;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
