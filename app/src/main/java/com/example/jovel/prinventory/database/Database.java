package com.example.jovel.prinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jovel.prinventory.models.Printer;
import com.example.jovel.prinventory.models.Toner;
import com.example.jovel.prinventory.models.Vendor;

import java.util.ArrayList;
import java.util.List;

import static com.example.jovel.prinventory.database.DBSchema.*;

/**
 * Created by Jovel on 6/28/2017.
 */

public class Database extends SQLiteOpenHelper{

    private static final String CREATE_PRINTER_TABLE = "CREATE TABLE " + PRINTER_TABLE +
                                        "(" + _ID + " INTEGER PRIMARY KEY," +
                                        PRINTER_MAKE + " TEXT, " +
                                        PRINTER_MODEL + " TEXT, " +
                                        PRINTER_SERIAL + " TEXT, " +
                                        PRINTER_COLOR + " INTEGER DEFAULT 0, " +
                                        PRINTER_STATUS + " INTEGER DEFAULT 0, " +
                                        PRINTER_OWNERSHIP + " TEXT, " +
                                        PRINTER_DEPARTMENT + " TEXT, " +
                                        PRINTER_LOCATION + " TEXT, " +
                                        PRINTER_FLOOR + " TEXT, " +
                                        PRINTER_IP + " INTEGER)";

    private static final String CREATE_TONER_TABLE = "CREATE TABLE " + TONER_TABLE +
                                        "(" + _ID + " INTEGER PRIMARY KEY," +
                                        TONER_MAKE + " TEXT, " +
                                        TONER_MODEL + " TEXT, " +
                                        TONER_COLOR + " INTEGER DEFAULT 0, " +
                                        TONER_TMODEL + " TEXT, " +
                                        TONER_BLACK + " INTEGER DEFAULT 0, " +
                                        TONER_CYAN + " INTEGER DEFAULT 0, " +
                                        TONER_YELLOW + " INTEGER DEFAULT 0, " +
                                        TONER_MAGENTA + " INTEGER DEFAULT 0)";

    private static final String CREATE_VENDOR_TABLE = "CREATE TABLE " + VENDOR_TABLE +
                                        "(" + _ID + " INTEGER PRIMARY KEY," +
                                        VENDOR_NAME + " TEXT, " +
                                        VENDOR_PHONE + " TEXT, " +
                                        VENDOR_EMAIL + " TEXT, " +
                                        VENDOR_STREET + " TEXT, " +
                                        VENDOR_CITY + " TEXT, " +
                                        VENDOR_STATE + " TEXT, " +
                                        VENDOR_ZIPCODE + " TEXT" + ")";

    public Database(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRINTER_TABLE);
        db.execSQL(CREATE_TONER_TABLE);
        db.execSQL(CREATE_VENDOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PRINTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TONER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + VENDOR_TABLE);
        onCreate(db);
    }

    public Printer getPrinter(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PRINTER_TABLE + " WHERE " + _ID + " =" + id;
        Cursor cursor = db.rawQuery(sql, null);

        try {

            if(cursor != null && cursor.moveToFirst()) {

                Printer printer = new Printer();
                printer.setId(Integer.parseInt(cursor.getString(0)));
                printer.setMake(cursor.getString(1));
                printer.setModel(cursor.getString(2));
                printer.setSerial(cursor.getString(3));
                printer.setColor(Integer.parseInt(cursor.getString(4)));
                printer.setStatus(Integer.parseInt(cursor.getString(5)));
                printer.setOwnership(cursor.getString(6));
                printer.setDepartment(cursor.getString(7));
                printer.setLocation(cursor.getString(8));
                printer.setFloor(cursor.getString(9));
                printer.setIp(Integer.parseInt(cursor.getString(10)));

                return printer;
            }
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

    public List<Printer> getAllPrinters(){

        List<Printer> printers = new ArrayList<>();

        String sql = "SELECT * FROM " + PRINTER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {

                Printer printer = new Printer();
                printer.setId(Integer.parseInt(cursor.getString(0)));
                printer.setMake(cursor.getString(1));
                printer.setModel(cursor.getString(2));
                printer.setSerial(cursor.getString(3));
                printer.setColor(Integer.parseInt(cursor.getString(4)));
                printer.setStatus(Integer.parseInt(cursor.getString(5)));
                printer.setOwnership(cursor.getString(6));
                printer.setDepartment(cursor.getString(7));
                printer.setLocation(cursor.getString(8));
                printer.setFloor(cursor.getString(9));
                printer.setIp(Integer.parseInt(cursor.getString(10)));

                printers.add(printer);
            }
        } finally{
            cursor.close();
            db.close();
        }

        return printers;
    }

    public void addPrinter(Printer printer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(PRINTER_MAKE, printer.getMake());
            values.put(PRINTER_MODEL, printer.getModel());
            values.put(PRINTER_SERIAL, printer.getSerial());
            values.put(PRINTER_COLOR, printer.getColor());
            values.put(PRINTER_STATUS, printer.getStatus());
            values.put(PRINTER_OWNERSHIP, printer.getOwnership());
            values.put(PRINTER_DEPARTMENT, printer.getDepartment());
            values.put(PRINTER_LOCATION, printer.getLocation());
            values.put(PRINTER_FLOOR, printer.getFloor());
            values.put(PRINTER_IP, printer.getIp());

            db.insert(PRINTER_TABLE, null, values);
        }finally {
            db.close();
        }
    }

    public int updatePrinter(Printer printer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(PRINTER_MAKE, printer.getMake());
            values.put(PRINTER_MODEL, printer.getModel());
            values.put(PRINTER_SERIAL, printer.getSerial());
            values.put(PRINTER_COLOR, printer.getColor());
            values.put(PRINTER_STATUS, printer.getStatus());
            values.put(PRINTER_OWNERSHIP, printer.getOwnership());
            values.put(PRINTER_DEPARTMENT, printer.getDepartment());
            values.put(PRINTER_LOCATION, printer.getLocation());
            values.put(PRINTER_FLOOR, printer.getFloor());
            values.put(PRINTER_IP, printer.getIp());

            return db.update(PRINTER_TABLE, values, _ID + " =?", new String[]{String.valueOf(printer.getId())});
        }finally {
            db.close();
        }
    }

    public void deletePrinter(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(PRINTER_TABLE, _ID + " =?", new String[]{String.valueOf(id)});
        }finally {
            db.close();
        }
    }

    public Toner getToner(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TONER_TABLE + " WHERE " + _ID + " =" + id;
        Cursor cursor = db.rawQuery(sql, null);

        try {

            if(cursor != null && cursor.moveToFirst()) {  //index out of bounds error here fam

                Toner toner = new Toner();
                toner.setId(Integer.parseInt(cursor.getString(0)));
                toner.setMake(cursor.getString(1));
                toner.setModel(cursor.getString(2));
                toner.setColor(Integer.parseInt(cursor.getString(3)));
                toner.setTonerModel(cursor.getString(4));
                toner.setBlack(Integer.parseInt(cursor.getString(5)));
                toner.setCyan(Integer.parseInt(cursor.getString(6)));
                toner.setYellow(Integer.parseInt(cursor.getString(7)));
                toner.setMagenta(Integer.parseInt(cursor.getString(8)));

                return toner;
            }
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

    public List<Toner> getAllToners(){

        List<Toner> toners = new ArrayList<>();

        String sql = "SELECT * FROM " + TONER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {

            while (cursor.moveToNext()) {

                Toner tone = new Toner();
                tone.setId(Integer.parseInt(cursor.getString(0)));
                tone.setMake(cursor.getString(1));
                tone.setModel(cursor.getString(2));
                tone.setColor(Integer.parseInt(cursor.getString(3)));
                tone.setTonerModel(cursor.getString(4));
                tone.setBlack(Integer.parseInt(cursor.getString(5)));
                tone.setCyan(Integer.parseInt(cursor.getString(6)));
                tone.setYellow(Integer.parseInt(cursor.getString(7)));
                tone.setMagenta(Integer.parseInt(cursor.getString(8)));

                toners.add(tone);
            }
        } finally{
            cursor.close();
            db.close();
        }
        return toners;
    }

    public void addToner(Toner toner){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(TONER_MAKE, toner.getMake());
            values.put(TONER_MODEL, toner.getModel());
            values.put(TONER_COLOR, toner.getColor());
            values.put(TONER_TMODEL, toner.getTonerModel());
            values.put(TONER_BLACK, toner.getBlack());
            values.put(TONER_CYAN, toner.getCyan());
            values.put(TONER_YELLOW, toner.getYellow());
            values.put(TONER_MAGENTA, toner.getMagenta());

            db.insert(TONER_TABLE, null, values);
        }finally {
            db.close();
        }
    }

    public int updateToner(Toner toner){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(TONER_MAKE, toner.getMake());
            values.put(TONER_MODEL, toner.getModel());
            values.put(TONER_COLOR, toner.getColor());
            values.put(TONER_TMODEL, toner.getTonerModel());
            values.put(TONER_BLACK, toner.getBlack());
            values.put(TONER_CYAN, toner.getCyan());
            values.put(TONER_YELLOW, toner.getYellow());
            values.put(TONER_MAGENTA, toner.getMagenta());

            return db.update(TONER_TABLE, values, _ID + " =?", new String[]{String.valueOf(toner.getId())});
        }finally {
            db.close();
        }
    }

    public void deleteToner(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TONER_TABLE, _ID + " =?", new String[]{String.valueOf(id)});
        }finally {
            db.close();
        }
    }

    public Vendor getVendor(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + VENDOR_TABLE + " WHERE " + _ID + " =" + id;
        Cursor cursor = db.rawQuery(sql, null);

        try {

            if(cursor != null && cursor.moveToFirst()) {

                Vendor vendor = new Vendor();
                vendor.setId(Integer.parseInt(cursor.getString(0)));
                vendor.setName(cursor.getString(1));
                vendor.setPhone(cursor.getString(2));
                vendor.setEmail(cursor.getString(3));
                vendor.setStreet(cursor.getString(4));
                vendor.setCity(cursor.getString(5));
                vendor.setState(cursor.getString(6));
                vendor.setZipcode(cursor.getString(7));

                return vendor;
            }
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

    public List<Vendor> getAllVendors(){

        List<Vendor> vendors = new ArrayList<>();

        String sql = "SELECT * FROM " + VENDOR_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            while (cursor.moveToNext()) {

                Vendor vendor = new Vendor();
                vendor.setId(Integer.parseInt(cursor.getString(0)));
                vendor.setName(cursor.getString(1));
                vendor.setPhone(cursor.getString(2));
                vendor.setEmail(cursor.getString(3));
                vendor.setStreet(cursor.getString(4));
                vendor.setCity(cursor.getString(5));
                vendor.setState(cursor.getString(6));
                vendor.setZipcode(cursor.getString(7));

                vendors.add(vendor);
            }
        } finally{
            cursor.close();
            db.close();
        }
        return vendors;
    }

    public void addVendor(Vendor vendor){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(VENDOR_NAME, vendor.getName());
            values.put(VENDOR_PHONE, vendor.getPhone());
            values.put(VENDOR_EMAIL, vendor.getEmail());
            values.put(VENDOR_STREET, vendor.getStreet());
            values.put(VENDOR_CITY, vendor.getCity());
            values.put(VENDOR_STATE, vendor.getState());
            values.put(VENDOR_ZIPCODE, vendor.getZipcode());

            db.insert(VENDOR_TABLE, null, values);
        }finally {
            db.close();
        }
    }

    public int updateVendor(Vendor vendor){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(VENDOR_NAME, vendor.getName());
            values.put(VENDOR_PHONE, vendor.getPhone());
            values.put(VENDOR_EMAIL, vendor.getEmail());
            values.put(VENDOR_STREET, vendor.getStreet());
            values.put(VENDOR_CITY, vendor.getCity());
            values.put(VENDOR_STATE, vendor.getState());
            values.put(VENDOR_ZIPCODE, vendor.getZipcode());

            return db.update(VENDOR_TABLE, values, _ID + " =?", new String[]{String.valueOf(vendor.getId())});
        }finally {
            db.close();
        }
    }

    public void deleteVendor(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(VENDOR_TABLE, _ID + " =?", new String[]{String.valueOf(id)});
        }finally {
            db.close();
        }
    }

}
