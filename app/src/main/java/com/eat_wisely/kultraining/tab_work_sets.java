package com.eat_wisely.kultraining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eat_wisely.kultraining.dialogs.BenchpressWorkWeight;
import com.eat_wisely.kultraining.dialogs.RowWorkWeight;
import com.eat_wisely.kultraining.dialogs.SquatWorkWeight;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class tab_work_sets extends Fragment {

    long id;

    Button btnSave;

    TextView tvExec1Weight, tvExec2Weight, tvExec3Weight, exec1_title, exec2_title, exec3_title;

    String workoutType, action, workoutDate;

    Toolbar toolbar;

    LinearLayout panel_1, panel_2, panel_3;
    SharedPreferences sharedPreferences;
    ViewGroup.LayoutParams layoutParams;

    Timer timer;
    MyTimerTask myTimerTask;

    JSONObject ex1, ex2, ex3;

    DB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_work_sets, container, false);

        Intent intent = getActivity().getIntent();
        action = intent.getAction();

        PreferenceManager.setDefaultValues(this.getActivity(), R.xml.preferences, false);

        panel_1 = (LinearLayout) rootView.findViewById(R.id.buttonPanel_1);
        panel_2 = (LinearLayout) rootView.findViewById(R.id.buttonPanel_2);
        panel_3 = (LinearLayout) rootView.findViewById(R.id.buttonPanel_3);
        layoutParams = new ViewGroup.LayoutParams(screenSize(48), screenSize(48));

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(onClickListener);

        tvExec1Weight = (TextView) rootView.findViewById(R.id.tvSquatWeight);
        tvExec1Weight.setOnClickListener(onClickListener);

        tvExec2Weight = (TextView) rootView.findViewById(R.id.tvBenchWeight);
        tvExec2Weight.setOnClickListener(onClickListener);

        tvExec3Weight = (TextView) rootView.findViewById(R.id.tvRowWeight);
        tvExec3Weight.setOnClickListener(onClickListener);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        exec1_title = (TextView) rootView.findViewById(R.id.exec1_title);
        exec2_title = (TextView) rootView.findViewById(R.id.exec2_title);
        exec3_title = (TextView) rootView.findViewById(R.id.exec3_title);

        db = new DB(getActivity());

        switch (action) {
            case "com.eat_wisely.action.workout_a":
                workoutType = "A";
                break;
            case "com.eat_wisely.action.workout_b":
                workoutType = "B";
                break;
            case "com.eat_wisely.action.edit":
                id = intent.getLongExtra("id", 1);

                db.open();

                workoutType = db.getFieldValue(id, DB.KEY_WORKOUT_TYPE);
                workoutDate = db.getFieldValue(id, DB.KEY_WORKOUT_DATE);

                String ex_1 = db.getFieldValue(id, DB.KEY_EX_1);
                String ex_2 = db.getFieldValue(id, DB.KEY_EX_2);
                String ex_3 = db.getFieldValue(id, DB.KEY_EX_3);

                db.close();
                toolbar.setTitle(workoutDate);

                final Integer[] squatSets = DataProcessing.getSets(ex_1);
                createButtons(panel_1, squatSets);
                setWorkWeight(ex_1, tvExec1Weight);

                final Integer[] bpSets = DataProcessing.getSets(ex_2);
                createButtons(panel_2, bpSets);
                setWorkWeight(ex_2, tvExec2Weight);

                final Integer[] rowSets = DataProcessing.getSets(ex_3);
                createButtons(panel_3, rowSets);
                setWorkWeight(ex_3, tvExec3Weight);

                break;
        }

        setExerciseNames(workoutType);

        if (!action.equals("com.eat_wisely.action.edit")){
            db.open();
            Cursor c = db.getAllData();
            if(c.getCount() == 0){
                tvExec1Weight.setText(R.string.default_weight);
                tvExec2Weight.setText(R.string.default_weight);
                tvExec3Weight.setText(R.string.default_weight);
            }else {
                c.moveToLast();
                int KEY_EX_1 = c.getColumnIndex(DB.KEY_EX_1);
                String ex_1 = c.getString(KEY_EX_1);

                int KEY_EX_2 = c.getColumnIndex(DB.KEY_EX_2);
                String ex_2 = "";

                int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);
                String ex_3 = "";

                if ( c.isLast() && !c.isFirst() ){
                    c.moveToPrevious();
                    ex_2 = c.getString(KEY_EX_2);
                    ex_3 = c.getString(KEY_EX_3);
                    c.moveToLast();
                } else if ( c.isLast() && c.isFirst() ){
                    ex_2 = c.getString(KEY_EX_2);
                    ex_3 = c.getString(KEY_EX_3);
                }
                c.close();
                setWorkWeight(ex_1, tvExec1Weight);
                setWorkWeight(ex_2, tvExec2Weight);
                setWorkWeight(ex_3, tvExec3Weight);
            }
            db.close();
        }

        /*if (savedInstanceState != null){
            exec1Set1.setText(savedInstanceState.getString("exec1Set1"));
        }*/

        return rootView;
    }

    public int screenSize(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return (int) Math.ceil(dp * logicalDensity);
    }

    void setWorkWeight(String exercise, TextView tv) {
        try {
            JSONObject obj = new JSONObject(exercise);
            String workWeight = obj.getString("workWeight");
            String text = getResources().getString(R.string.unit_kg, workWeight);
            if (!obj.getString("workWeight").isEmpty()) {
                tv.setText(text);
            } else {
                tv.setText(R.string.default_weight);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setExerciseNames(String workoutType) {
        exec1_title.setText("Приседания");

        if ( workoutType.equals("A") ) {
            exec2_title.setText("Жим лежа");
            exec3_title.setText("Тяга в наклоне");
        } else {
            exec2_title.setText("Жим над головой");
            exec3_title.setText("Мертвая тяга");
        }
    }

    void saveSets(LinearLayout panel, JSONObject exercise) {
        List<String> sets = getButtonsText(panel);
        try {
            for (int i = 0; i < sets.size(); i++) {
                exercise.put("set" + (i+1), sets.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setExerciseCode(exercise);
        setExerciseWeight(exercise);
        compareSets(exercise, sets);
    }

    void compareSets(JSONObject exercise, List<String> sets) {
        int i = 0;
        while(i < sets.size()) {
            try {
                if (!sets.get(i).equals("8")) {
                    exercise.put("success", false);
                    break;
                } else {
                    exercise.put("success", true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    void setExerciseCode(JSONObject exercise) {
        try {
            if (exercise.equals(ex1)) {
                exercise.put("exercise", "1");
            } else if (exercise.equals(ex2)) {
                if (workoutType.equals("A")) {
                    exercise.put("exercise", "2");
                } else {
                    exercise.put("exercise", "4");
                }
            } else if (exercise.equals(ex3)) {
                if (workoutType.equals("A")) {
                    exercise.put("exercise", "3");
                } else {
                    exercise.put("exercise", "5");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setExerciseWeight(JSONObject exercise) {
        try {
            if (exercise.equals(ex1)) {
                exercise.put("workWeight", tvExec1Weight.getText().toString().replace("кг", ""));
            } else if (exercise.equals(ex2)) {
                exercise.put("workWeight", tvExec2Weight.getText().toString().replace("кг", ""));
            } else if (exercise.equals(ex3)) {
                exercise.put("workWeight", tvExec3Weight.getText().toString().replace("кг", ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void createButtons(LinearLayout panel, final Integer[] sets) {
        for (int i = 0; i < sets.length; i++) {
            final Button btn = new Button(this.getActivity());
            btn.setId(i);
            btn.setText(Integer.toString(sets[i]));
            btn.setLayoutParams(layoutParams);
            final int index = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn.setText(Integer.toString(--sets[index]));
                }
            });

            panel.addView(btn);
        }
    }

    void createButtons(LinearLayout panel, int setsNumber, final int repsNumber, final Integer[] reps) {
        if (panel.getChildCount() == 0) {
            for (int i = 0; i < setsNumber; i++) {
                reps[i] = repsNumber;
                final Button btn = new Button(this.getActivity());
                btn.setId(i);
                btn.setText("");
                btn.setLayoutParams(layoutParams);
                final int index = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        btn.setText(Integer.toString(reps[index]));
                        reps[index]--;
                        startTimer();

                        if (reps[index] == -1) {
                            reps[index] = repsNumber;
                        }
                    }
                });
                panel.addView(btn);
            }
        }
    }

    List<String> getButtonsText(LinearLayout panel) {
        List<String> sets = new ArrayList<>();
        int i=0;
        while(panel.getChildAt(i) != null) {
            Button btn = (Button) panel.getChildAt(i);
            String btnText = btn.getText().toString();
            sets.add(btnText);
            i++;
        }
        return sets;
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        ex1 = new JSONObject();
        setExerciseCode(ex1);

        if (ex1.opt("exercise").equals("1")) {
            int exercise1SetsNumber = Integer.parseInt(sharedPreferences.getString("squats_sets", "1"));
            final int exercise1RepsNumber = Integer.parseInt(sharedPreferences.getString("squats_reps", "0"));
            final Integer[] exercise1Reps = new Integer[exercise1SetsNumber];
            createButtons(panel_1, exercise1SetsNumber, exercise1RepsNumber, exercise1Reps);
        }

        ex2 = new JSONObject();
        setExerciseCode(ex2);

        if (ex2.opt("exercise").equals("2")) {
            int exercise2SetsNumber = Integer.parseInt(sharedPreferences.getString("benchpress_sets", "1"));
            final int exercise2RepsNumber = Integer.parseInt(sharedPreferences.getString("benchpress_reps", "0"));
            final Integer[] exercise2Reps = new Integer[exercise2SetsNumber];
            createButtons(panel_2, exercise2SetsNumber, exercise2RepsNumber, exercise2Reps);

        } else {
            int exercise2SetsNumber = Integer.parseInt(sharedPreferences.getString("ohp_sets", "1"));
            final int exercise2RepsNumber = Integer.parseInt(sharedPreferences.getString("ohp_reps", "0"));
            final Integer[] exercise2Reps = new Integer[exercise2SetsNumber];
            createButtons(panel_2, exercise2SetsNumber, exercise2RepsNumber, exercise2Reps);
        }

        ex3 = new JSONObject();
        setExerciseCode(ex3);

        if (ex3.opt("exercise").equals("3")) {
            int exercise3SetsNumber = Integer.parseInt(sharedPreferences.getString("row_sets", "1"));
            final int exercise3RepsNumber = Integer.parseInt(sharedPreferences.getString("row_reps", "0"));
            final Integer[] exercise3Reps = new Integer[exercise3SetsNumber];
            createButtons(panel_3, exercise3SetsNumber, exercise3RepsNumber, exercise3Reps);
        } else {
            int exercise3SetsNumber = Integer.parseInt(sharedPreferences.getString("deadlift_sets", "1"));
            final int exercise3RepsNumber = Integer.parseInt(sharedPreferences.getString("deadlift_reps", "0"));
            final Integer[] exercise3Reps = new Integer[exercise3SetsNumber];
            createButtons(panel_3, exercise3SetsNumber, exercise3RepsNumber, exercise3Reps);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putString("exec1Set1", exec1Set1.getText().toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (timer != null){
            timer.cancel();
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            workoutDate = toolbar.getTitle().toString();
            DialogFragment fragment;

            switch (v.getId()){
                case R.id.btnSave:
                    db.open();

                    saveSets(panel_1, ex1);
                    saveSets(panel_2, ex2);
                    saveSets(panel_3, ex3);

                    String e1 = ex1.toString();
                    String e2 = ex2.toString();
                    String e3 = ex3.toString();

                    if (action.equals("com.eat_wisely.action.edit")) {
                        Log.d("myLog", "Edit ex1" + e1);
                        Log.d("myLog", "Edit ex2" + e2);
                        Log.d("myLog", "Edit ex3" + e3);
                        db.editRec(id, e1, e2, e3, workoutDate, workoutType);
                    } else {
                        Log.d("myLog", "Add ex1" + e1);
                        Log.d("myLog", "Add ex2" + e2);
                        Log.d("myLog", "Add ex3" + e3);
                        db.addRec(e1, e2, e3, workoutDate, workoutType);
                    }
                    db.close();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case R.id.tvSquatWeight:
                    fragment = new SquatWorkWeight();
                    fragment.show(getFragmentManager(), "squatDialog");
                    break;
                case R.id.tvBenchWeight:
                    fragment = new BenchpressWorkWeight();
                    fragment.show(getFragmentManager(), "benchDialog");
                    break;
                case R.id.tvRowWeight:
                    fragment = new RowWorkWeight();
                    fragment.show(getFragmentManager(), "rowDialog");
                    break;
            }
        }
    };

    void startTimer(){
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    private class MyTimerTask extends TimerTask{

        long startTime = System.currentTimeMillis();
        long MILLIS_TO_MINUTES = 60000;
        long MILLIS_TO_SECONDS = 1000;

        int seconds;
        int minutes;

        String secondsText;

        @Override
        public void run() {
            long passed = System.currentTimeMillis() - startTime;

            seconds = (int) ((passed / MILLIS_TO_SECONDS) % 60);
            minutes = (int) ((passed / MILLIS_TO_MINUTES) % 60);

            if (seconds < 10) {
                secondsText = "0" + String.valueOf(seconds);
            } else {
                secondsText = String.valueOf(seconds);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() != null){
                        Snackbar.make(getActivity().findViewById(R.id.main_content),
                                String.valueOf(minutes) + " : " + secondsText,
                                Snackbar.LENGTH_SHORT)
                                .setAction("X", new TimerCloseListener())
                                .show();
                    }
                }
            });
        }
    }

    private class TimerCloseListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            timer.cancel();
        }
    }
}
