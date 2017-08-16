package com.eat_wisely.kultraining;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработка данных
 */

public class DataProcessing {

    static Integer[] getSets(String exercise){
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
}
