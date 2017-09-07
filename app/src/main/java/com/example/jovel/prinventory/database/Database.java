package com.example.jovel.prinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jovel.prinventory.models.Printer;
import com.example.jovel.prinventory.models.Toner;
import com.example.jovel.prinventory.models.Vendor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jovel on 6/28/2017.
 */

public class Database extends SQLiteOpenHelper{

    private static final String DATABASE = "com.example.jovel.prinventory.inventory.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PRINTER_TABLE = "printer_table";
    private static final String TONER_TABLE = "toner_table";
    private static final String VENDOR_TABLE = "vendor_table";

    //COMMON
    private static final String _ID = "id";

    //PRINTER TABLE COLUMNS
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final String SERIAL = "serial";
    private static final String COLOR_PRINTER = "color";
    private static final String STATUS = "status";
    private static final String OWNERSHIP = "ownership";
    private static final String DEPARTMENT = "department";
    private static final String LOCATION = "location";
    private static final String FLOOR = "floor";
    private static final String IP = "ip";

    //TONER TABLE COLUMNS
    private static final String TONER_MAKE = "make";
    private static final String MODEL_TONER = "model";
    private static final String COLOR = "color";
    private static final String TONER_MODEL = "tmodel";
    private static final String BLACK = "black";
    private static final String CYAN = "cyan";
    private static final String YELLOW = "yellow";
    private static final String MAGENTA = "magenta";

    //VENDOR TABLE COLUMNS
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String ZIPCODE = "zipcode";

    private static final String CREATE_PRINTER_TABLE = "CREATE TABLE " + PRINTER_TABLE + "(" + _ID + " INTEGER PRIMARY KEY," +
            MAKE + " TEXT, " +
            MODEL + " TEXT, " +
            SERIAL + " TEXT, " +
            COLOR_PRINTER + " INTEGER DEFAULT 0, " +
            STATUS + " INTEGER DEFAULT 0, " +
            OWNERSHIP + " TEXT, " +
            DEPARTMENT + " TEXT, " +
            LOCATION + " TEXT, " +
            FLOOR + " TEXT, " +
            IP + " INTEGER)";

    private static final String CREATE_TONER_TABLE = "CREATE TABLE " + TONER_TABLE + "(" + _ID + " INTEGER PRIMARY KEY," +
            TONER_MAKE + " TEXT, " +
            MODEL_TONER + " TEXT, " +
            COLOR + " INTEGER DEFAULT 0, " +
            TONER_MODEL + " TEXT, " +
            BLACK + " INTEGER DEFAULT 0, " +
            CYAN + " INTEGER DEFAULT 0, " +
            YELLOW + " INTEGER DEFAULT 0, " +
            MAGENTA + " INTEGER DEFAULT 0)";

    private static final String CREATE_VENDOR_TABLE = "CREATE TABLE " + VENDOR_TABLE + "(" + _ID + " INTEGER PRIMARY KEY," +
            NAME + " TEXT, " +
            PHONE + " TEXT, " +
            EMAIL + " TEXT, " +
            STREET + " TEXT, " +
            CITY + " TEXT, " +
            STATE + " TEXT, " +
            ZIPCODE + " TEXT" + ")";

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

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_PRINTER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TONER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_VENDOR_TABLE);
        onCreate(db);
    }


    public void addPrinter(Printer printer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(MAKE, printer.getMake());
            values.put(MODEL, printer.getModel());
            values.put(SERIAL, printer.getSerial());
            values.put(COLOR_PRINTER, printer.getColor());
            values.put(STATUS, printer.getStatus());
            values.put(OWNERSHIP, printer.getOwnership());
            values.put(DEPARTMENT, printer.getDepartment());
            values.put(LOCATION, printer.getLocation());
            values.put(FLOOR, printer.getFloor());
            values.put(IP, printer.getIp());

            db.insert(PRINTER_TABLE, null, values);
        }finally {
            db.close();
        }
    }

    public Printer getPrinter(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PRINTER_TABLE + " WHERE " + _ID + " =" + id;
        Cursor cursor = db.rawQuery(sql, null);

        try {

            if(cursor != null && cursor.moveToFirst()) {

                Printer printers = new Printer();
                printers.setId(Integer.parseInt(cursor.getString(0)));
                printers.setMake(cursor.getString(1));
                printers.setModel(cursor.getString(2));
                printers.setSerial(cursor.getString(3));
                printers.setColor(Integer.parseInt(cursor.getString(4)));
                printers.setStatus(Integer.parseInt(cursor.getString(5)));
                printers.setOwnership(cursor.getString(6));
                printers.setDepartment(cursor.getString(7));
                printers.setLocation(cursor.getString(8));
                printers.setFloor(cursor.getString(9));
                printers.setIp(Integer.parseInt(cursor.getString(10)));

                return printers;
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

                Printer print = new Printer();
                print.setId(Integer.parseInt(cursor.getString(0)));
                print.setMake(cursor.getString(1));
                print.setModel(cursor.getString(2));
                print.setSerial(cursor.getString(3));
                print.setColor(Integer.parseInt(cursor.getString(4)));
                print.setStatus(Integer.parseInt(cursor.getString(5)));
                print.setOwnership(cursor.getString(6));
                print.setDepartment(cursor.getString(7));
                print.setLocation(cursor.getString(8));
                print.setFloor(cursor.getString(9));
                print.setIp(Integer.parseInt(cursor.getString(10)));

                printers.add(print);
            }
        } finally{
            cursor.close();
            db.close();
        }
        Log.i("DB", "Printers list grabbed");

        return printers;


    }

    public void deletePrinter(Printer printer) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(PRINTER_TABLE, _ID + " =?", new String[]{String.valueOf(printer.getId())});
        }finally {
            db.close();
        }
    }


    public int updatePrinter(Printer printer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(MAKE, printer.getMake());
            values.put(MODEL, printer.getModel());
            values.put(SERIAL, printer.getSerial());
            values.put(COLOR_PRINTER, printer.getColor());
            values.put(STATUS, printer.getStatus());
            values.put(OWNERSHIP, printer.getOwnership());
            values.put(DEPARTMENT, printer.getDepartment());
            values.put(LOCATION, printer.getLocation());
            values.put(FLOOR, printer.getFloor());
            values.put(IP, printer.getIp());

            return db.update(PRINTER_TABLE, values, _ID + " =?", new String[]{String.valueOf(printer.getId())});
        }finally {
            db.close();
        }
    }

    public void addToner(Toner toner){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(TONER_MAKE, toner.getMake());
            values.put(MODEL_TONER, toner.getModel());
            values.put(COLOR, toner.getColor());
            values.put(TONER_MODEL, toner.getTonerModel());
            values.put(BLACK, toner.getBlack());
            values.put(CYAN, toner.getCyan());
            values.put(YELLOW, toner.getYellow());
            values.put(MAGENTA, toner.getMagenta());

            db.insert(TONER_TABLE, null, values);
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

    public void deleteToner(Toner toner) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TONER_TABLE, _ID + " =?", new String[]{String.valueOf(toner.getId())});
        }finally {
            db.close();
        }
    }


    public int updateToner(Toner toner){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(TONER_MAKE, toner.getMake());
            values.put(MODEL_TONER, toner.getModel());
            values.put(COLOR, toner.getColor());
            values.put(TONER_MODEL, toner.getTonerModel());
            values.put(BLACK, toner.getBlack());
            values.put(CYAN, toner.getCyan());
            values.put(YELLOW, toner.getYellow());
            values.put(MAGENTA, toner.getMagenta());

            return db.update(TONER_TABLE, values, _ID + " =?", new String[]{String.valueOf(toner.getId())});
        }finally {
            db.close();
        }
    }

    public void addVendor(Vendor vendor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(NAME, vendor.getName());
            values.put(PHONE, vendor.getPhone());
            values.put(EMAIL, vendor.getEmail());
            values.put(STREET, vendor.getStreet());
            values.put(CITY, vendor.getCity());
            values.put(STATE, vendor.getState());
            values.put(ZIPCODE, vendor.getZipcode());

            db.insert(VENDOR_TABLE, null, values);
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

                Vendor vend = new Vendor();
                vend.setId(Integer.parseInt(cursor.getString(0)));
                vend.setName(cursor.getString(1));
                vend.setPhone(cursor.getString(2));
                vend.setEmail(cursor.getString(3));
                vend.setStreet(cursor.getString(4));
                vend.setCity(cursor.getString(5));
                vend.setState(cursor.getString(6));
                vend.setZipcode(cursor.getString(7));

                vendors.add(vend);
            }
        } finally{
            cursor.close();
            db.close();
        }
        return vendors;
    }

    public void deleteVendor(Vendor vendor) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(VENDOR_TABLE, _ID + " =?", new String[]{String.valueOf(vendor.getId())});
        }finally {
            db.close();
        }
    }


    public int updateVendor(Vendor vendor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(NAME, vendor.getName());
            values.put(PHONE, vendor.getPhone());
            values.put(EMAIL, vendor.getEmail());
            values.put(STREET, vendor.getStreet());
            values.put(CITY, vendor.getCity());
            values.put(STATE, vendor.getState());
            values.put(ZIPCODE, vendor.getZipcode());

            return db.update(VENDOR_TABLE, values, _ID + " =?", new String[]{String.valueOf(vendor.getId())});
        }finally {
            db.close();
        }
    }

}
