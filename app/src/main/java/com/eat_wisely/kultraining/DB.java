package com.eat_wisely.kultraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB{

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "workoutsDb";
    private static final String TABLE_WORKOUTS = "workouts";

    public static final String KEY_ID = "_id";
    public static final String KEY_EX_1 ="ex1";
    public static final String KEY_EX_2 ="ex2";
    public static final String KEY_EX_3 ="ex3";
    public static final String KEY_SQUAT_WORK_WEIGHT = "squat_work_weight";
    public static final String KEY_BP_WORK_WEIGHT = "bp_work_weight";
    public static final String KEY_ROW_WORK_WEIGHT = "row_work_weight";
    public static final String KEY_WORKOUT_DATE = "workout_date";

    private static final String DB_CREATE = "create table " + TABLE_WORKOUTS + "(" + KEY_ID + " integer primary key," +
            KEY_EX_1 + " text," +
            KEY_EX_2 + " text," +
            KEY_EX_3 + " text," +
            KEY_SQUAT_WORK_WEIGHT + " integer," +
            KEY_BP_WORK_WEIGHT + " integer," +
            KEY_ROW_WORK_WEIGHT + " integer," +
            KEY_WORKOUT_DATE + " text)";

    private final Context mCtx;
    private DBHelper dbHelper;
    private SQLiteDatabase mDB;

    DB(Context context){
        mCtx = context;
    }

    void open(){
        dbHelper = new DBHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB  = dbHelper.getWritableDatabase();
    }

    void close(){
        if (dbHelper != null) dbHelper.close();
    }

    public Cursor getAllData(){
        return mDB.query(TABLE_WORKOUTS, null, null, null, null, null, null);
    }

    void addRec(String ex1, String ex2, String ex3, String dateValue){
        ContentValues cv = new ContentValues();

        cv.put(KEY_EX_1, ex1);
        cv.put(KEY_EX_2, ex2);
        cv.put(KEY_EX_3, ex3);
        cv.put(KEY_WORKOUT_DATE, dateValue);

        mDB.insert(TABLE_WORKOUTS, null, cv);
    }

    public void delRec(long id){
        mDB.delete(TABLE_WORKOUTS, KEY_ID + "=" + id, null);
    }

    private class DBHelper extends SQLiteOpenHelper{

        DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_WORKOUTS);

            onCreate(db);
        }
    }
}
