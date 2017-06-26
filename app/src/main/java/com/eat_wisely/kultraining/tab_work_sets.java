package com.eat_wisely.kultraining;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class tab_work_sets extends Fragment {

    int bpSet1reps, bpSet2reps, bpSet3reps, rowSet1reps, rowSet2reps, rowSet3reps  ;
    Integer[] squatReps;
    long id;

    Button exec2Set1, exec2Set2, exec2Set3,
            exec3Set1, exec3Set2, exec3Set3, btnSave;

    TextView tvExec1Weight, tvExec2Weight, tvExec3Weight, exec1_title, exec2_title, exec3_title;

    String workoutType , action, workWeight, text, dateValue, e1;

    String[] reps;

    Toolbar toolbar;

    LinearLayout panel_1;
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
        layoutParams = new ViewGroup.LayoutParams(screenSize(48), screenSize(48));

        /*exec1Set1 = (Button) rootView.findViewById(R.id.squatSet1);
        exec1Set2 = (Button) rootView.findViewById(R.id.squatSet2);
        exec1Set3 = (Button) rootView.findViewById(R.id.squatSet3);*/
        exec2Set1 = (Button) rootView.findViewById(R.id.bpSet1);
        exec2Set2 = (Button) rootView.findViewById(R.id.bpSet2);
        exec2Set3 = (Button) rootView.findViewById(R.id.bpSet3);
        exec3Set1 = (Button) rootView.findViewById(R.id.rowSet1);
        exec3Set2 = (Button) rootView.findViewById(R.id.rowSet2);
        exec3Set3 = (Button) rootView.findViewById(R.id.rowSet3);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        tvExec1Weight = (TextView) rootView.findViewById(R.id.tvSquatWeight);
        tvExec2Weight = (TextView) rootView.findViewById(R.id.tvBenchWeight);
        tvExec3Weight = (TextView) rootView.findViewById(R.id.tvRowWeight);

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
                Cursor c = db.getRecord(id);
                c.moveToFirst();
                int KEY_WORKOUT_TYPE = c.getColumnIndex(DB.KEY_WORKOUT_TYPE);
                int KEY_WORKOUT_DATE = c.getColumnIndex(DB.KEY_WORKOUT_DATE);
                int KEY_EX_1 = c.getColumnIndex(DB.KEY_EX_1);
                int KEY_EX_2 = c.getColumnIndex(DB.KEY_EX_2);
                int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);

                String ex_1 = c.getString(KEY_EX_1);
                String ex_2 = c.getString(KEY_EX_2);
                String ex_3 = c.getString(KEY_EX_3);
                workoutType = c.getString(KEY_WORKOUT_TYPE);
                dateValue = c.getString(KEY_WORKOUT_DATE);
                toolbar.setTitle(dateValue);
                try {
                    JSONObject ex1 = new JSONObject(ex_1);
                    JSONObject obj_ex_2 = new JSONObject(ex_2);
                    JSONObject obj_ex_3 = new JSONObject(ex_3);

                    final Integer[] squatSets = getSets(ex1);

                    for (int i = 0; i < squatSets.length; i++) {
                        final Button squat_b = new Button(this.getActivity());
                        squat_b.setId(i);
                        squat_b.setText(Integer.toString(squatSets[i]));
                        squat_b.setLayoutParams(layoutParams);
                        final int index = i;
                        squat_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                squat_b.setText(Integer.toString(squatSets[index]--));
                            }
                        });

                        panel_1.addView(squat_b);
                    }

                    workWeight = ex1.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    tvExec1Weight.setText(text);

                    exec2Set1.setText(obj_ex_2.getString("set1"));
                    exec2Set2.setText(obj_ex_2.getString("set2"));
                    exec2Set3.setText(obj_ex_2.getString("set3"));
                    workWeight = obj_ex_2.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    tvExec2Weight.setText(text);

                    exec3Set1.setText(obj_ex_3.getString("set1"));
                    exec3Set2.setText(obj_ex_3.getString("set2"));
                    exec3Set3.setText(obj_ex_3.getString("set3"));
                    workWeight = obj_ex_3.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    tvExec3Weight.setText(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                c.close();
                db.close();
                break;
        }

        exec1_title.setText("Приседания");

        if ( workoutType.equals("A") ) {
            exec2_title.setText("Жим лежа");
            exec3_title.setText("Тяга в наклоне");
        } else {
            exec2_title.setText("Жим над головой");
            exec3_title.setText("Мертвая тяга");
        }

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
                int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);

                String ex_2 = "";
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

                try {
                    JSONObject obj_ex_1 = new JSONObject(ex_1);
                    workWeight  = obj_ex_1.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    if(obj_ex_1.getString("exercise").equals("1") ){
                        tvExec1Weight.setText(text);
                    }

                    JSONObject obj_ex_2 = new JSONObject(ex_2);
                    workWeight = obj_ex_2.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    if (!obj_ex_2.getString("workWeight").isEmpty()) {
                        tvExec2Weight.setText(text);
                    } else {
                        tvExec2Weight.setText(R.string.default_weight);
                    }

                    JSONObject obj_ex_3 = new JSONObject(ex_3);
                    workWeight = obj_ex_3.getString("workWeight");
                    text = getResources().getString(R.string.unit_kg, workWeight);
                    if (!obj_ex_3.getString("workWeight").isEmpty()) {
                        tvExec3Weight.setText(text);
                    } else {
                        tvExec3Weight.setText(R.string.default_weight);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                c.close();
            }

            db.close();
        }


        Resources res = getResources();
        reps = res.getStringArray(R.array.rep_range);

        bpSet1reps = reps.length;
        bpSet2reps = reps.length;
        bpSet3reps = reps.length;
        rowSet1reps = reps.length;
        rowSet2reps = reps.length;
        rowSet3reps = reps.length;

        exec2Set1.setOnClickListener(onClickListener);
        exec2Set2.setOnClickListener(onClickListener);
        exec2Set3.setOnClickListener(onClickListener);
        exec3Set1.setOnClickListener(onClickListener);
        exec3Set2.setOnClickListener(onClickListener);
        exec3Set3.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);
        tvExec1Weight.setOnClickListener(onClickListener);
        tvExec2Weight.setOnClickListener(onClickListener);
        tvExec3Weight.setOnClickListener(onClickListener);

        /*if (savedInstanceState != null){
            exec1Set1.setText(savedInstanceState.getString("exec1Set1"));
        }*/


        return rootView;
    }

    Integer[] getSets(JSONObject exercise){
        List<Integer> setsList = new ArrayList<>();
        int i=1;
        try {
            while( exercise.getString("set" + Integer.toString(i)) != null) {
                int squatSet = exercise.getInt("set" + Integer.toString(i));
                setsList.add(squatSet);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setsList.toArray(new Integer[setsList.size()]);
    }

    public int screenSize(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return (int) Math.ceil(dp * logicalDensity);
    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        int squatSets = Integer.parseInt(sharedPreferences.getString("squats_sets", "1"));
        squatReps = new Integer[squatSets];
        ex1 = new JSONObject();

        if (panel_1.getChildCount() == 0){
            for (int i = 0; i < squatSets; i++){
                squatReps[i] = Integer.parseInt(sharedPreferences.getString("squats_reps", "0"));
                final Button squat_b = new Button(this.getActivity());
                squat_b.setId(i);
                squat_b.setText("");
                squat_b.setLayoutParams(layoutParams);
                final int index= i;
                squat_b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        squat_b.setText(Integer.toString(squatReps[index]));
                        try {
                            ex1.put("set" + Integer.toString(index + 1), squat_b.getText());
                            ex1.put("exercise", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        squatReps[index]--;
                        startTimer();

                        if (squatReps[index] == -1){
                            squatReps[index] = Integer.parseInt(sharedPreferences.getString("squats_reps", "0"));
                        }
                    }
                });
                panel_1.addView(squat_b);
            }
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

            dateValue = toolbar.getTitle().toString();
            DialogFragment fragment;

            ex2 = new JSONObject();
            ex3 = new JSONObject();

            try {
                ex2.put("set1",exec2Set1.getText().toString());
                ex2.put("set2",exec2Set2.getText().toString());
                ex2.put("set3",exec2Set3.getText().toString());
                ex2.put("workWeight",tvExec2Weight.getText().toString().replace("кг", ""));

                if ( workoutType.equals("A") ) {
                    ex2.put("exercise", "2");
                } else {
                    ex2.put("exercise", "4");
                }

                int set1 = Integer.parseInt(ex2.getString("set1"));
                int set2 = Integer.parseInt(ex2.getString("set2"));
                int set3 = Integer.parseInt(ex2.getString("set3"));

                if(set1 == 8 && set2 == 8 && set3 == 8){
                    ex2.put("success", true);
                } else {
                    ex2.put("success", false);
                }
            }catch (Exception e) {
                System.out.println("Error:" + e);
            }

            try {
                ex3.put("set1",exec3Set1.getText().toString());
                ex3.put("set2",exec3Set2.getText().toString());
                ex3.put("set3",exec3Set3.getText().toString());
                ex3.put("workWeight",tvExec3Weight.getText().toString().replace("кг", ""));

                if ( workoutType.equals("A") ){
                    ex3.put("exercise", "3");
                } else {
                    ex3.put("exercise", "5");
                }

                int set1 = Integer.parseInt(ex3.getString("set1"));
                int set2 = Integer.parseInt(ex3.getString("set2"));
                int set3 = Integer.parseInt(ex3.getString("set3"));

                if(set1 == 8 && set2 == 8 && set3 == 8){
                    ex3.put("success", true);
                } else {
                    ex3.put("success", false);
                }
            }catch (Exception e) {
                System.out.println("Error:" + e);
            }

            String e2 = ex2.toString();
            String e3 = ex3.toString();

            switch (v.getId()){
                case R.id.bpSet1:
                    exec2Set1.setText(reps[--bpSet1reps]);

                    startTimer();

                    if (bpSet1reps == 0){
                        exec2Set1.setText("0");
                        bpSet1reps = reps.length;
                    }
                    break;
                case R.id.bpSet2:
                    exec2Set2.setText(reps[--bpSet2reps]);

                    startTimer();

                    if (bpSet2reps == 0){
                        exec2Set2.setText("0");
                        bpSet2reps = reps.length;
                    }
                    break;
                case R.id.bpSet3:
                    exec2Set3.setText(reps[--bpSet3reps]);

                    startTimer();

                    if (bpSet3reps == 0){
                        exec2Set3.setText("0");
                        bpSet3reps = reps.length;
                    }
                    break;
                case R.id.rowSet1:
                    exec3Set1.setText(reps[--rowSet1reps]);

                    startTimer();

                    if (rowSet1reps == 0){
                        exec3Set1.setText("0");
                        rowSet1reps = reps.length;
                    }
                    break;
                case R.id.rowSet2:
                    exec3Set2.setText(reps[--rowSet2reps]);

                    startTimer();

                    if (rowSet2reps == 0){
                        exec3Set2.setText("0");
                        rowSet2reps = reps.length;
                    }
                    break;
                case R.id.rowSet3:
                    exec3Set3.setText(reps[--rowSet3reps]);

                    startTimer();

                    if (rowSet3reps == 0){
                        exec3Set3.setText("0");
                        rowSet3reps = reps.length;
                    }
                    break;
                case R.id.btnSave:

                    db.open();
                    if (action.equals("com.eat_wisely.action.edit")) {
                        try {
                            ex1.put("workWeight", tvExec1Weight.getText().toString().replace("кг", ""));
                            int set1 = Integer.parseInt(ex1.getString("set1"));
                            int set2 = Integer.parseInt(ex1.getString("set2"));
                            int set3 = Integer.parseInt(ex1.getString("set3"));

                            if(set1 == 8 && set2 == 8 && set3 == 8){
                                ex1.put("success", true);
                            } else {
                                ex1.put("success", false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        e1 = ex1.toString();
                        db.editRec(id, e1, e2, e3, dateValue, workoutType);
                    } else {
                        try {
                            ex1.put("workWeight", tvExec1Weight.getText().toString().replace("кг", ""));
                            int set1 = Integer.parseInt(ex1.getString("set1"));
                            int set2 = Integer.parseInt(ex1.getString("set2"));
                            int set3 = Integer.parseInt(ex1.getString("set3"));

                            if(set1 == 8 && set2 == 8 && set3 == 8){
                                ex1.put("success", true);
                            } else {
                                ex1.put("success", false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        e1 = ex1.toString();
                        db.addRec(e1, e2, e3, dateValue, workoutType);
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
        timer.schedule(myTimerTask,1000, 1000);
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
