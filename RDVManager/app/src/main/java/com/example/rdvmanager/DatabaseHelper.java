package com.example.rdvmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    // Table Name
    public static final String TABLE_NAME = "RDV";
    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String RTIME = "time";
    public static final String RDATE= "date";

    public static final String ADDRESS= "address";

    public static final String CONTACT="contact";

    public static final String PHONENUMBER="phoneNumber";

    public static final String ISDONE = "isDone";

    // Database Information
    static final String DB_NAME = "rdvs.DB";
    // database version
    static final int DB_VERSION = 1;
    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RTIME + " TEXT NOT NULL, " + TITLE +
            " TEXT NOT NULL, " + RDATE + " TEXT, " + CONTACT + " CHAR(250), " + ADDRESS +
            " TEXT, " + PHONENUMBER + " TEXT, " + ISDONE + " INTEGER DEFAULT 0);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION) ;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public void add(RDV rdv){
        ContentValues contentValues= new ContentValues();
        contentValues.put(TITLE,rdv.getTitle());
        contentValues.put(RDATE,rdv.getDate());
        contentValues.put(RTIME,rdv.getTime());
        contentValues.put(CONTACT,rdv.getContact());
        contentValues.put(PHONENUMBER,rdv.getPhoneNumber());
        contentValues.put(ADDRESS,rdv.getAddress());
        contentValues.put(ISDONE,rdv.isDone());
        database.insert(TABLE_NAME,null,contentValues);
    }
    public void delete(long _id)
    {
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
    }
    public int update(RDV rdv) {
        long _id= rdv.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE,rdv.getTitle());
        contentValues.put(RDATE,rdv.getDate());
        contentValues.put(RTIME,rdv.getTime());
        contentValues.put(CONTACT,rdv.getContact());
        contentValues.put(PHONENUMBER,rdv.getPhoneNumber());
        contentValues.put(ADDRESS,rdv.getAddress());
        contentValues.put(ISDONE,rdv.isDone());
        int count = database.update(TABLE_NAME, contentValues, this._ID + " = " + _id, null);
        return count;
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + DatabaseHelper.TABLE_NAME + "'");
    }

    public Cursor getAllRDV(){
        String[] projection = { _ID, TITLE, RDATE, RTIME, CONTACT, PHONENUMBER, ADDRESS, ISDONE};
        Cursor cursor = database.query(TABLE_NAME,projection,null,null,null,null,null,null);
        return cursor;
    }


}
