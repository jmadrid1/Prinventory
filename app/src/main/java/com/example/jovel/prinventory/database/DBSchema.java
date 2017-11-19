package com.example.jovel.prinventory.database;

/**
 * Created by Jovel on 11/16/2017.
 */

public class DBSchema {

    public static final String DATABASE = "com.example.jovel.prinventory.inventory.db";
    public static final int DATABASE_VERSION = 1;
    public static final String PRINTER_TABLE = "printer_table";
    public static final String TONER_TABLE = "toner_table";
    public static final String VENDOR_TABLE = "vendor_table";

    //COMMON
    public static final String _ID = "id";

    //PRINTER TABLE COLUMNS
    public static final String PRINTER_MAKE = "make";
    public static final String PRINTER_MODEL = "model";
    public static final String PRINTER_SERIAL = "serial";
    public static final String PRINTER_COLOR = "color";
    public static final String PRINTER_STATUS = "status";
    public static final String PRINTER_OWNERSHIP = "ownership";
    public static final String PRINTER_DEPARTMENT = "department";
    public static final String PRINTER_LOCATION = "location";
    public static final String PRINTER_FLOOR = "floor";
    public static final String PRINTER_IP = "ip";

    //TONER TABLE COLUMNS
    public static final String TONER_MAKE = "make";
    public static final String TONER_MODEL = "model";
    public static final String TONER_COLOR = "color";
    public static final String TONER_TMODEL = "tmodel";
    public static final String TONER_BLACK = "black";
    public static final String TONER_CYAN = "cyan";
    public static final String TONER_YELLOW = "yellow";
    public static final String TONER_MAGENTA = "magenta";

    //VENDOR TABLE COLUMNS
    public static final String VENDOR_NAME = "name";
    public static final String VENDOR_PHONE = "phone";
    public static final String VENDOR_EMAIL = "email";
    public static final String VENDOR_STREET = "street";
    public static final String VENDOR_CITY = "city";
    public static final String VENDOR_STATE = "state";
    public static final String VENDOR_ZIPCODE = "zipcode";

}
