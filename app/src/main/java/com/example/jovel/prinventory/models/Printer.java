package com.example.jovel.prinventory.models;

/**
 * Created by Jovel on 6/28/2017.
 */

public class Printer {

    private int mId;
    private String mMake;
    private String mModel;
    private String mSerial;
    private int mColor;
    private int mStatus;
    private String mOwnership;
    private String mDepartment;
    private String mLocation;
    private String mFloor;
    private int mIp;

    public Printer(){
    }

    public int getId(){ return mId; }

    public void setId(int id) { this.mId = id;}

    public String getMake() {
        return mMake;
    }

    public void setMake(String make) {
        this.mMake = make;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        this.mModel = model;
    }

    public String getSerial() {
        return mSerial;
    }

    public void setSerial(String serial) {
        this.mSerial = serial;
    }

    public int getColor(){
        return mColor;
    }

    public void setColor(int color){
        this.mColor = color;
    }

    public int getStatus(){
        return mStatus;
    }

    public void setStatus(int status){
        this.mStatus = status;
    }

    public String getOwnership() {
        return mOwnership;
    }

    public void setOwnership(String ownership) {
        this.mOwnership = ownership;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        this.mDepartment = department;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public String getFloor() {
        return mFloor;
    }

    public void setFloor(String floor) {
        this.mFloor = floor;
    }

    public int getIp() {
        return mIp;
    }

    public void setIp(int ip) {
        this.mIp = ip;
    }
}
