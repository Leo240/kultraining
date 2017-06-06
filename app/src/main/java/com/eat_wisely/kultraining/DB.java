package com.eat_wisely.kultraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DB{

    private static final int DATABASE_VERSION = 6;
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

    Cursor getAllData(){
        return mDB.query(TABLE_WORKOUTS, null, null, null, null, null, null);
    }

    public Cursor getRecord(long id){
        return mDB.query(TABLE_WORKOUTS, null, DB.KEY_ID + " = ?", new String[] {String.valueOf(id)}, null, null, null, null);
    }

    /*public Cursor getWorkoutsOfType(String[] type){
        return mDB.query(TABLE_WORKOUTS, null, "workout_type = ?", type, null, null, null, null);
    }*/

    void addRec(String ex1, String ex2, String ex3, String dateValue, String workoutType){
        ContentValues cv = new ContentValues();

        cv.put(KEY_EX_1, ex1);
        cv.put(KEY_EX_2, ex2);
        cv.put(KEY_EX_3, ex3);
        cv.put(KEY_WORKOUT_DATE, dateValue);
        cv.put(KEY_WORKOUT_TYPE, workoutType);

        mDB.insert(TABLE_WORKOUTS, null, cv);

    }

    public void editRec(long id, String ex1, String ex2, String ex3, String dateValue, String workoutType){
        ContentValues cv = new ContentValues();

        cv.put(KEY_EX_1, ex1);
        cv.put(KEY_EX_2, ex2);
        cv.put(KEY_EX_3, ex3);
        cv.put(KEY_WORKOUT_DATE, dateValue);
        cv.put(KEY_WORKOUT_TYPE, workoutType);

        mDB.update(TABLE_WORKOUTS, cv, DB.KEY_ID + " = ?", new String[] {String.valueOf(id)});
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
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (1, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"82.5\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"7\",\"workWeight\":\"60\",\"exercise\":\"2\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"5\",\"workWeight\":\"55\",\"exercise\":\"3\",\"success\":false}', '29/04/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (2, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"82.5\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"5\",\"workWeight\":\"35\",\"exercise\":\"4\",\"success\":false}', '{\"set1\":\"0\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"82.5\",\"exercise\":\"5\"}', '02/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (3, '{\"set1\":\"6\",\"set2\":\"7\",\"set3\":\"7\",\"workWeight\":\"85\",\"exercise\":\"1\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"7\",\"workWeight\":\"60\",\"exercise\":\"2\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"7\",\"workWeight\":\"55\",\"exercise\":\"3\",\"success\":false}', '04/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (4, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"85\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"35\",\"exercise\":\"4\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"0\",\"set3\":\"0\",\"workWeight\":\"85\",\"exercise\":\"5\",\"success\":false}', '06/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (5, '{\"set1\":\"0\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"85\",\"exercise\":\"1\"}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"60\",\"exercise\":\"2\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"52.5\",\"exercise\":\"3\",\"success\":true}', '11/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (6, '{\"set1\":\"8\",\"set2\":\"7\",\"set3\":\"5\",\"workWeight\":\"85\",\"exercise\":\"1\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"35\",\"exercise\":\"4\",\"success\":false}', '{\"set1\":\"0\",\"set2\":\"0\",\"set3\":\"0\",\"workWeight\":\"85\",\"exercise\":\"5\",\"success\":false}', '13/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (7, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"85\",\"exercise\":\"1\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"55\",\"exercise\":\"2\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"7\",\"workWeight\":\"55\",\"exercise\":\"3\",\"success\":false}', '16/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (8, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"77.5\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"30\",\"exercise\":\"4\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"85\",\"exercise\":\"5\"}', '18/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (9, '{\"set1\":\"0\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"80\",\"exercise\":\"1\"}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"57.5\",\"exercise\":\"2\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"55\",\"exercise\":\"3\",\"success\":true}', '20/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (10, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"80\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"32.5\",\"exercise\":\"4\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"87.5\",\"exercise\":\"5\"}', '23/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (11, '{\"set1\":\"0\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"80\",\"exercise\":\"1\"}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"5\",\"workWeight\":\"60\",\"exercise\":\"2\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"57.5\",\"exercise\":\"3\",\"success\":true}', '25/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (12, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"80\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"5\",\"workWeight\":\"35\",\"exercise\":\"4\",\"success\":false}', '{\"set1\":\"0\",\"set2\":\"\",\"set3\":\"\",\"workWeight\":\"87.5\",\"exercise\":\"5\"}', '27/05/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (13, '{\"set1\":\"0\",\"set2\":\"0\",\"set3\":\"0\",\"workWeight\":\"80\",\"exercise\":\"1\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"60\",\"exercise\":\"2\",\"success\":true}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"60\",\"exercise\":\"3\",\"success\":false}', '30/05/2017', 'A');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (14, '{\"set1\":\"0\",\"set2\":\"0\",\"set3\":\"0\",\"workWeight\":\"80\",\"exercise\":\"1\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"35\",\"exercise\":\"4\",\"success\":true}', '{\"set1\":\"0\",\"set2\":\"0\",\"set3\":\"0\",\"workWeight\":\"87.5\",\"exercise\":\"5\",\"success\":false}', '01/06/2017', 'B');");
            db.execSQL("INSERT INTO workouts (_id, ex1, ex2, ex3, workout_date, workout_type) VALUES (15, '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"8\",\"workWeight\":\"70\",\"exercise\":\"1\",\"success\":true}', '{\"set1\":\"6\",\"set2\":\"6\",\"set3\":\"4\",\"workWeight\":\"62.5\",\"exercise\":\"2\",\"success\":false}', '{\"set1\":\"8\",\"set2\":\"8\",\"set3\":\"6\",\"workWeight\":\"60\",\"exercise\":\"3\",\"success\":false}', '03/06/2017', 'A');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("drop table if exists " + TABLE_WORKOUTS);

            onCreate(db);

        }
    }
}
