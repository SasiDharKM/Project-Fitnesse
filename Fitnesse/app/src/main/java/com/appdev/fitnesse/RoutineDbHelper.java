package com.appdev.fitnesse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SasiDKM on 19-07-2016.
 */
public class RoutineDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="routineDB";
    private static final String TABLE_NAME="myRoutine";
    private static final int DATABASE_VERSION=1;

    public static final String KEY_TIME="time";
    public static final String KEY_DISTANCE="distance";
    public static final String KEY_AVGSPD="avg_spd";
    public static final String PROJECTION[]= {
            KEY_TIME,
            KEY_DISTANCE,
            KEY_AVGSPD
    };
    private static final String CREATE_TABLE = "create table "+TABLE_NAME+
            "("+KEY_TIME+" text not null,"
            +KEY_DISTANCE+" text,"
            +KEY_AVGSPD+" text);";
    public RoutineDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public Cursor fetchAllRows(){
        SQLiteDatabase db=getWritableDatabase();
        return db.query(TABLE_NAME,PROJECTION,null,null,null,null,KEY_TIME);
    }

    public boolean createContact(String time,String distance,String avgspd){
        ContentValues values =new ContentValues();
        values.put(KEY_TIME,time);
        values.put(KEY_DISTANCE,distance);
        values.put(KEY_AVGSPD,distance);
        SQLiteDatabase db=getWritableDatabase();
        long i=db.insert(TABLE_NAME,null,values);
        return !(i==-1);
    }
}
