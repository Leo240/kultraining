package com.eat_wisely.kultraining.model;

import android.database.Cursor;

import com.eat_wisely.kultraining.DB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for HistoryActivity
 */

public class HistoryItem {
    private String date;
    private Integer[] exercise1;
    private Integer[] exercise2;
    private Integer[] exercise3;
    private String workWeight1;
    private String workWeight2;
    private String workWeight3;
    private String title1;
    private String title2;
    private String title3;

    public static Integer[] getSetsFromJson(String exercise){
        List<Integer> setsList = new ArrayList<>();
        int i=1;
        try {
            JSONObject obj = new JSONObject(exercise);
            while( obj.getString("set" + i) != null) {
                int exerciseSet = obj.getInt("set" + i);
                setsList.add(exerciseSet);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setsList.toArray(new Integer[setsList.size()]);
    }

    private static String getWorkWeightFromJson(String exercise) {
        try {
            JSONObject obj = new JSONObject(exercise);
            if (obj.getString("workWeight") != null) {
                return obj.getString("workWeight");
            }
        } catch (JSONException e) {
           e.printStackTrace();
        }
        return null;
    }

    private static String getIdFromJson(String exercise) {
        try {
            JSONObject obj = new JSONObject(exercise);
            switch(obj.getString("exercise")) {
                case "1":
                    return "Приседания";
                case "2":
                    return "Жим лежа";
                case "3":
                    return "Тяга в наклоне";
                case "4":
                    return "Жим над головой";
                case "5":
                    return "Мертвая тяга";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDate() {
        return date;
    }

    private void setDate(String date) {
        this.date = date;
    }

    public Integer[] getExercise1() {
        return exercise1;
    }

    private void setExercise1(Integer[] exercise) {
        exercise1 = exercise;
    }

    public Integer[] getExercise2() {
        return exercise2;
    }

    private void setExercise2(Integer[] exercise) {
        exercise2 = exercise;
    }

    public Integer[] getExercise3() {
        return exercise3;
    }

    private void setExercise3(Integer[] exercise) {
        exercise3 = exercise;
    }

    public String getWorkWeight1() {
        return workWeight1;
    }

    private void setWorkWeight1(String workWeight) {
        workWeight1 = workWeight;
    }

    public String getWorkWeight2() {
        return workWeight2;
    }

    private void setWorkWeight2(String workWeight) {
        workWeight2 = workWeight;
    }

    public String getWorkWeight3() {
        return workWeight3;
    }

    private void setWorkWeight3(String workWeight) {
        workWeight3 = workWeight;
    }

    public String getTitle1() {
        return title1;
    }

    private void setTitle1(String title) {
        title1 = title;
    }

    public String getTitle2() {
        return title2;
    }

    private void setTitle2(String title) {
        title2 = title;
    }

    public String getTitle3() {
        return title3;
    }

    private void setTitle3(String title) {
        title3 = title;
    }

    public static HistoryItem fromCursor(Cursor cursor) {
        HistoryItem item = new HistoryItem();
        item.setDate(cursor.getString(cursor.getColumnIndex(DB.KEY_WORKOUT_DATE)));
        item.setExercise1(getSetsFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1))));
        item.setExercise2(getSetsFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_2))));
        item.setExercise3(getSetsFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_3))));
        item.setWorkWeight1(getWorkWeightFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1))));
        item.setWorkWeight2(getWorkWeightFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_2))));
        item.setWorkWeight3(getWorkWeightFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_3))));
        item.setTitle1(getIdFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1))));
        item.setTitle2(getIdFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_2))));
        item.setTitle3(getIdFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_3))));
        return item;
    }
}
