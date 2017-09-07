package com.example.jovel.prinventory.models;

/**
 * Created by Jovel on 6/28/2017.
 */

public class Vendor {

    private int mId;
    private String mName;
    private String mPhone;
    private String mEmail;
    private String mStreet;
    private String mCity;
    private String mState;
    private String mZipcode;


    public Vendor(){
    }

    public int getId() {
        return mId;
    }

    public void setId(int id){
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        this.mStreet = street;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public void setZipcode(String zipcode) {
        this.mZipcode = zipcode;
    }

}
