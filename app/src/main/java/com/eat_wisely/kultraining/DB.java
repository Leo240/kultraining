package com.eat_wisely.kultraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB{

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "workoutsDb";
    private static final String TABLE_WORKOUTS = "workouts";

    public static final String KEY_ID = "_id";
    public static final String KEY_EX_1 ="ex1";
    public static final String KEY_EX_2 ="ex2";
    public static final String KEY_EX_3 ="ex3";
    public static final String KEY_WORKOUT_DATE = "workout_date";
    public static final String KEY_WORKOUT_TYPE = "workout_type";

    private static final String DB_CREATE = "create table " + TABLE_WORKOUTS + "(" + KEY_ID + " integer primary key," +
            KEY_EX_1 + " text," +
            KEY_EX_2 + " text," +
            KEY_EX_3 + " text," +
            KEY_WORKOUT_DATE + " text," +
            KEY_WORKOUT_TYPE + " text)";

    private final Context mCtx;
    private DBHelper dbHelper;
    private SQLiteDatabase mDB;

    DB(Context context){
        mCtx = context;
    }

    String selection = null;
    String[] selectionArgs = null;


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

    public Cursor getWorkoutsOfType(String[] type){
        return mDB.query(TABLE_WORKOUTS, null, "workout_type = ?", type, null, null, null, null);
    }

    void addRec(String ex1, String ex2, String ex3, String dateValue, String workoutType){
        ContentValues cv = new ContentValues();
        selection = "workout_date = ?";
        selectionArgs = new String[]{dateValue};
        Cursor c = mDB.query(TABLE_WORKOUTS, null, selection, selectionArgs, null, null, null);

        if (c.getCount() == 0){
            c.close();
            cv.put(KEY_EX_1, ex1);
            cv.put(KEY_EX_2, ex2);
            cv.put(KEY_EX_3, ex3);
            cv.put(KEY_WORKOUT_DATE, dateValue);
            cv.put(KEY_WORKOUT_TYPE, workoutType);

            mDB.insert(TABLE_WORKOUTS, null, cv);
        }
        c.close();
        cv.put(KEY_EX_1, ex1);
        cv.put(KEY_EX_2, ex2);
        cv.put(KEY_EX_3, ex3);
        cv.put(KEY_WORKOUT_TYPE, workoutType);

        mDB.update(TABLE_WORKOUTS, cv, selection, selectionArgs);
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
