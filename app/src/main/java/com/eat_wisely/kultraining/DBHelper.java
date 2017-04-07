package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "workoutsDb";
    public static final String TABLE_WORKOUTS = "workouts";

    public static final String KEY_ID = "_id";
    public static final String KEY_SQUAT_SET_1 ="squat_set_1";
    public static final String KEY_SQUAT_SET_2 ="squat_set_2";
    public static final String KEY_SQUAT_SET_3 ="squat_set_3";
    public static final String KEY_BP_SET_1 ="bp_set_1";
    public static final String KEY_BP_SET_2 ="bp_set_2";
    public static final String KEY_BP_SET_3 ="bp_set_3";
    public static final String KEY_ROW_SET_1 ="row_set_1";
    public static final String KEY_ROW_SET_2 ="row_set_2";
    public static final String KEY_ROW_SET_3 ="row_set_3";
    public static final String KEY_WORKOUT_DATE = "workout_date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_WORKOUTS + "(" + KEY_ID + " integer primary key," +
                    KEY_SQUAT_SET_1 + " text," + KEY_SQUAT_SET_2 + " text," + KEY_SQUAT_SET_3
                    + " text," + KEY_BP_SET_1 + " text," + KEY_BP_SET_2 + " text," + KEY_BP_SET_3
                    + " text," + KEY_ROW_SET_1 + " text," + KEY_ROW_SET_2 + " text," + KEY_ROW_SET_3
                    + " text," + KEY_WORKOUT_DATE + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_WORKOUTS);

        onCreate(db);
    }
}
