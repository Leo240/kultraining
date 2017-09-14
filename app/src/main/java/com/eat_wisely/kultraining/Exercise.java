package com.eat_wisely.kultraining;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON parser
 */

abstract class Exercise {

    private Integer[] exerciseSets;
    private int exerciseReps;
    private int exerciseId;
    private double workWeight;
    private boolean success;

    static final int SQUATS = 1;
    static final int BENCH_PRESS = 2;

    /*String exerciseName() {
        switch (exerciseId) {
            case SQUATS:
                return "Приседания";
            case BENCH_PRESS:
                return "Жим лежа";
        }
        return null;
    }*/

    private Exercise (int type) {
        exerciseId = type;
    }

    abstract int getExerciseId();

    private void setExerciseIdFromJson(String exercise) {
        try {
            JSONObject obj = new JSONObject(exercise);
            if(obj.getInt("exercise") != -1) {
                setExerciseId(obj.getInt("exercise"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setExerciseId(int id) {
        this.exerciseId = id;
    }

    public Integer[] getExerciseSets() {
        return exerciseSets;
    }

    private void setExerciseSetsFromJson(String exercise){
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
        setExerciseSets(setsList.toArray(new Integer[setsList.size()]));
    }

    private void setExerciseSets(Integer[] exerciseSets) {
        this.exerciseSets = exerciseSets;
    }

    public int getExerciseReps() {
        return exerciseReps;
    }

    public void setExerciseReps(int exerciseReps) {
        this.exerciseReps = exerciseReps;
    }

    public double getWorkWeight() {
        return workWeight;
    }

    private void setWorkWeightFromJson(String exercise) {
        try {
            JSONObject obj = new JSONObject(exercise);
            if (obj.getDouble("workWeight") != -1) {
                setWorkWeight(obj.getDouble("workWeight"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWorkWeight(double workWeight) {
        this.workWeight = workWeight;
    }

    public boolean isSuccess() {
        return success;
    }

    private void setSuccessFromJson(String exercise) {
        try {
            JSONObject obj = new JSONObject(exercise);
            setSuccess(obj.getBoolean("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    static Exercise create(int type/*Cursor cursor*/) {
        if (type == SQUATS) {
            return new Squats();
        } else {
            //return new Exercise(type);
        }

        /*exercise.setExerciseIdFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1)));
        exercise.setExerciseSetsFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1)));
        exercise.setWorkWeightFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1)));
        exercise.setSuccessFromJson(cursor.getString(cursor.getColumnIndex(DB.KEY_EX_1)));*/

    }
}
