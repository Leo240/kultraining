package com.eat_wisely.kultraining;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tab_work_sets extends Fragment {

    int squatSet1reps, squatSet2reps, squatSet3reps, bpSet1reps,
            bpSet2reps, bpSet3reps, rowSet1reps, rowSet2reps, rowSet3reps;

    Button squatSet1, squatSet2, squatSet3, bpSet1, bpSet2, bpSet3,
            rowSet1, rowSet2, rowSet3, btnSave, btnRead, btnDel;

    TextView textView;

    String[] reps;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_work_sets, container, false);
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
        btnRead = (Button) rootView.findViewById(R.id.btnRead);
        btnDel = (Button) rootView.findViewById(R.id.btnDel);
        textView = (TextView) rootView.findViewById(R.id.textView);

        dbHelper = new DBHelper(getActivity());

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
        btnRead.setOnClickListener(onClickListener);
        btnDel.setOnClickListener(onClickListener);

        return rootView;
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String squatSet1Value = squatSet1.getText().toString();
            String squatSet2Value = squatSet2.getText().toString();
            String squatSet3Value = squatSet3.getText().toString();
            String bpSet1Value = bpSet1.getText().toString();
            String bpSet2Value = bpSet2.getText().toString();
            String bpSet3Value = bpSet3.getText().toString();
            String rowSet1Value = rowSet1.getText().toString();
            String rowSet2Value = rowSet2.getText().toString();
            String rowSet3Value = rowSet3.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateValue = sdf.format(new Date());


            SQLiteDatabase database = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

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
                    contentValues.put(DBHelper.KEY_SQUAT_SET_1, squatSet1Value);
                    contentValues.put(DBHelper.KEY_SQUAT_SET_2, squatSet2Value);
                    contentValues.put(DBHelper.KEY_SQUAT_SET_3, squatSet3Value);
                    contentValues.put(DBHelper.KEY_BP_SET_1, bpSet1Value);
                    contentValues.put(DBHelper.KEY_BP_SET_2, bpSet2Value);
                    contentValues.put(DBHelper.KEY_BP_SET_3, bpSet3Value);
                    contentValues.put(DBHelper.KEY_ROW_SET_1, rowSet1Value);
                    contentValues.put(DBHelper.KEY_ROW_SET_2, rowSet2Value);
                    contentValues.put(DBHelper.KEY_ROW_SET_3, rowSet3Value);
                    contentValues.put(DBHelper.KEY_WORKOUT_DATE, dateValue);

                    database.insert(DBHelper.TABLE_WORKOUTS, null, contentValues);
                    break;
                case R.id.btnRead:
                    Cursor cursor = database.query(DBHelper.TABLE_WORKOUTS, null, null, null, null, null, null);

                    if (cursor.moveToFirst()){

                        int squatIndex1 = cursor.getColumnIndex(DBHelper.KEY_SQUAT_SET_1);
                        int squatIndex2 = cursor.getColumnIndex(DBHelper.KEY_SQUAT_SET_2);
                        int squatIndex3 = cursor.getColumnIndex(DBHelper.KEY_SQUAT_SET_3);
                        int bpIndex1 = cursor.getColumnIndex(DBHelper.KEY_BP_SET_1);
                        int bpIndex2 = cursor.getColumnIndex(DBHelper.KEY_BP_SET_2);
                        int bpIndex3 = cursor.getColumnIndex(DBHelper.KEY_BP_SET_3);
                        int rowIndex1 = cursor.getColumnIndex(DBHelper.KEY_ROW_SET_1);
                        int rowIndex2 = cursor.getColumnIndex(DBHelper.KEY_ROW_SET_2);
                        int rowIndex3 = cursor.getColumnIndex(DBHelper.KEY_ROW_SET_3);
                        int dateIndex = cursor.getColumnIndex(DBHelper.KEY_WORKOUT_DATE);

                        do{
                            Log.d("myLog", "Дата: " + cursor.getString(dateIndex) + "\n" +
                                    "Приседания:\n" +
                                    "1 сет - " + cursor.getInt(squatIndex1) +
                                    " 2 сет - " + cursor.getInt(squatIndex2) +
                                    " 3 сет - " + cursor.getInt(squatIndex3) + "\n" + "Жим лежа:\n" +
                                    "1 сет - " + cursor.getInt(bpIndex1) +
                                    " 2 сет - " + cursor.getInt(bpIndex2) +
                                    " 3 сет - " + cursor.getInt(bpIndex3) + "\n" + "Тяга в наклоне:\n" +
                                    "1 сет - " + cursor.getInt(rowIndex1) +
                                    " 2 сет - " + cursor.getInt(rowIndex2) +
                                    " 3 сет - " + cursor.getInt(rowIndex3)
                            );
                        }
                        while (cursor.moveToNext());


                    }else{
                        textView.setText("0 рядов");
                    }

                    cursor.close();
                    break;
                case R.id.btnDel:
                    database.delete(DBHelper.TABLE_WORKOUTS, null, null);
                    break;
            }
            dbHelper.close();
        }
    };
}
