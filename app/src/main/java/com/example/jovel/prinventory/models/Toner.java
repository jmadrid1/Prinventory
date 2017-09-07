package com.example.jovel.prinventory.models;

/**
 * Created by Jovel on 6/28/2017.
 */

public class Toner {

    private int mId;
    private String mMake;
    private String mModel;
    private int mColor;
    private String mTonerModel;
    private int mBlack;
    private int mCyan;
    private int mYellow;
    private int mMagenta;

    public Toner(){
    }

    public int getId(){
        return mId;
    }

    public void setId(int id){
        this.mId = id;
    }

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

    public int getColor(){
        return mColor;
    }

    public void setColor(int color){
        this.mColor = color;
    }

    public String getTonerModel() {
        return mTonerModel;
    }

    public void setTonerModel(String tonerModel) {
        this.mTonerModel = tonerModel;
    }

    public int getBlack() {
        return mBlack;
    }

    public void setBlack(int black) {
        this.mBlack = black;
    }

    public int getCyan() {
        return mCyan;
    }

    public void setCyan(int cyan) {
        this.mCyan = cyan;
    }

    public int getYellow() {
        return mYellow;
    }

    public void setYellow(int yellow) {
        this.mYellow = yellow;
    }

    public int getMagenta() {
        return mMagenta;
    }

    public void setMagenta(int magenta) {
        this.mMagenta = magenta;
    }

}
