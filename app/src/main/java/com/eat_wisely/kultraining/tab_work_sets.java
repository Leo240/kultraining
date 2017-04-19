package com.eat_wisely.kultraining;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class tab_work_sets extends Fragment {

    int squatSet1reps, squatSet2reps, squatSet3reps, bpSet1reps,
            bpSet2reps, bpSet3reps, rowSet1reps, rowSet2reps, rowSet3reps;

    Button squatSet1, squatSet2, squatSet3, bpSet1, bpSet2, bpSet3,
            rowSet1, rowSet2, rowSet3, btnSave;

    TextView tvSquatWeight, tvBenchWeight, tvRowWeight;

    String[] reps;

    DB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_work_sets, container, false);

        db = new DB(getActivity());

        squatSet1 = (Button) rootView.findViewById(R.id.squatSet1);
        squatSet2 = (Button) rootView.findViewById(R.id.squatSet2);
        squatSet3 = (Button) rootView.findViewById(R.id.squatSet3);
        bpSet1 = (Button) rootView.findViewById(R.id.bpSet1);
        bpSet2 = (Button) rootView.findViewById(R.id.bpSet2);
        bpSet3 = (Button) rootView.findViewById(R.id.bpSet3);
        rowSet1 = (Button) rootView.findViewById(R.id.rowSet1);
        rowSet2 = (Button) rootView.findViewById(R.id.rowSet2);
        rowSet3 = (Button) rootView.findViewById(R.id.rowSet3);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        tvSquatWeight = (TextView) rootView.findViewById(R.id.tvSquatWeight);
        tvBenchWeight = (TextView) rootView.findViewById(R.id.tvBenchWeight);
        tvRowWeight = (TextView) rootView.findViewById(R.id.tvRowWeight);

        db.open();
        Cursor c = db.getAllData();
        if(c.getCount() == 0){
            tvSquatWeight.setText("20кг");
            tvBenchWeight.setText("20кг");
            tvRowWeight.setText("20кг");
        }else {
            c.moveToLast();
            int KEY_EX_1 = c.getColumnIndex(DB.KEY_EX_1);
            String ex_1 = c.getString(KEY_EX_1);

            int KEY_EX_2 = c.getColumnIndex(DB.KEY_EX_2);
            String ex_2 = c.getString(KEY_EX_2);

            int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);
            String ex_3 = c.getString(KEY_EX_3);

            c.close();

            try {
                JSONObject obj_ex_1 = new JSONObject(ex_1);
                if(obj_ex_1.getString("exercise").equalsIgnoreCase("1") ){
                    tvSquatWeight.setText(obj_ex_1.getString("workWeight"));
                }

                JSONObject obj_ex_2 = new JSONObject(ex_2);
                if(obj_ex_2.getString("exercise").equalsIgnoreCase("2") ){
                    tvBenchWeight.setText(obj_ex_2.getString("workWeight"));
                }

                JSONObject obj_ex_3 = new JSONObject(ex_3);
                if(obj_ex_3.getString("exercise").equalsIgnoreCase("3") ){
                    tvRowWeight.setText(obj_ex_3.getString("workWeight"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        db.close();

        Resources res = getResources();
        reps = res.getStringArray(R.array.rep_range);

        squatSet1reps = reps.length;
        squatSet2reps = reps.length;
        squatSet3reps = reps.length;
        bpSet1reps = reps.length;
        bpSet2reps = reps.length;
        bpSet3reps = reps.length;
        rowSet1reps = reps.length;
        rowSet2reps = reps.length;
        rowSet3reps = reps.length;

        squatSet1.setOnClickListener(onClickListener);
        squatSet2.setOnClickListener(onClickListener);
        squatSet3.setOnClickListener(onClickListener);
        bpSet1.setOnClickListener(onClickListener);
        bpSet2.setOnClickListener(onClickListener);
        bpSet3.setOnClickListener(onClickListener);
        rowSet1.setOnClickListener(onClickListener);
        rowSet2.setOnClickListener(onClickListener);
        rowSet3.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);
        tvSquatWeight.setOnClickListener(onClickListener);
        tvBenchWeight.setOnClickListener(onClickListener);
        tvRowWeight.setOnClickListener(onClickListener);

        return rootView;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateValue = sdf.format(new Date());

        JSONObject ex1 = new JSONObject();
        JSONObject ex2 = new JSONObject();
        JSONObject ex3 = new JSONObject();
        try {
            ex1.put("set1",squatSet1.getText().toString());
            ex1.put("set2",squatSet2.getText().toString());
            ex1.put("set3",squatSet3.getText().toString());
            ex1.put("workWeight", tvSquatWeight.getText().toString());

            int set1 = Integer.parseInt(ex1.getString("set1"));
            int set2 = Integer.parseInt(ex1.getString("set2"));
            int set3 = Integer.parseInt(ex1.getString("set3"));

            if(set1 == 8 && set2 == 8 && set3 == 8){
                ex1.put("success", true);
            }

            ex1.put("success", false);
        }catch (Exception e){
            System.out.println("Error:" + e);
        }

        try {
            ex2.put("set1",bpSet1.getText().toString());
            ex2.put("set2",bpSet2.getText().toString());
            ex2.put("set3",bpSet3.getText().toString());
            ex2.put("workWeight",tvBenchWeight.getText().toString());

            int set1 = Integer.parseInt(ex2.getString("set1"));
            int set2 = Integer.parseInt(ex2.getString("set2"));
            int set3 = Integer.parseInt(ex2.getString("set3"));

            if(set1 == 8 && set2 == 8 && set3 == 8){
                ex2.put("success", true);
            }

            ex2.put("success", false);
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }

        try {
            ex3.put("set1",rowSet1.getText().toString());
            ex3.put("set2",rowSet2.getText().toString());
            ex3.put("set3",rowSet3.getText().toString());
            ex3.put("workWeight",tvRowWeight.getText().toString());

            int set1 = Integer.parseInt(ex3.getString("set1"));
            int set2 = Integer.parseInt(ex3.getString("set2"));
            int set3 = Integer.parseInt(ex3.getString("set3"));

            if(set1 == 8 && set2 == 8 && set3 == 8){
                ex3.put("success", true);
            }

            ex3.put("success", false);
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }

        String e1 = ex1.toString();
        String e2 = ex2.toString();
        String e3 = ex3.toString();

        switch (v.getId()){
            case R.id.squatSet1:
                squatSet1reps--;
                squatSet1.setText(reps[squatSet1reps]);

                if (squatSet1reps == 0){
                    squatSet1.setText("0");
                    squatSet1reps = reps.length;
                }
                break;
            case R.id.squatSet2:
                squatSet2reps--;
                squatSet2.setText(reps[squatSet2reps]);

                if (squatSet2reps == 0){
                    squatSet2.setText("0");
                    squatSet2reps = reps.length;
                }
                break;
            case R.id.squatSet3:
                squatSet3reps--;
                squatSet3.setText(reps[squatSet3reps]);

                if (squatSet3reps == 0){
                    squatSet3.setText("0");
                    squatSet3reps = reps.length;
                }
                break;
            case R.id.bpSet1:
                bpSet1reps--;
                bpSet1.setText(reps[bpSet1reps]);

                if (bpSet1reps == 0){
                    bpSet1.setText("0");
                    bpSet1reps = reps.length;
                }
                break;
            case R.id.bpSet2:
                bpSet2reps--;
                bpSet2.setText(reps[bpSet2reps]);

                if (bpSet2reps == 0){
                    bpSet2.setText("0");
                    bpSet2reps = reps.length;
                }
                break;
            case R.id.bpSet3:
                bpSet3reps--;
                bpSet3.setText(reps[bpSet3reps]);

                if (bpSet3reps == 0){
                    bpSet3.setText("0");
                    bpSet3reps = reps.length;
                }
                break;
            case R.id.rowSet1:
                rowSet1reps--;
                rowSet1.setText(reps[rowSet1reps]);

                if (rowSet1reps == 0){
                    rowSet1.setText("0");
                    rowSet1reps = reps.length;
                }
                break;
            case R.id.rowSet2:
                rowSet2reps--;
                rowSet2.setText(reps[rowSet2reps]);

                if (rowSet2reps == 0){
                    rowSet2.setText("0");
                    rowSet2reps = reps.length;
                }
                break;
            case R.id.rowSet3:
                rowSet3reps--;
                rowSet3.setText(reps[rowSet3reps]);

                if (rowSet3reps == 0){
                    rowSet3.setText("0");
                    rowSet3reps = reps.length;
                }
                break;
            case R.id.btnSave:
                db.open();
                db.addRec(e1, e2, e3, dateValue);
                db.close();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tvSquatWeight:
                DialogFragment fragment = new ChooseWorkWeight();
                fragment.show(getFragmentManager(), "myDialog");
        }

        }
    };
}
