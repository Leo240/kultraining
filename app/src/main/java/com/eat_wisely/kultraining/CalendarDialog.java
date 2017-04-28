package com.eat_wisely.kultraining;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;


public class CalendarDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int day, month, year;
    String date;
    Toolbar toolbar;
    Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        return  new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month += 1;
        String formattedMonth = "" + month;
        String formattedDay = "" + day;
        if (month < 10) {
            formattedMonth = "0" + month;
        } else if(day < 10) {
            formattedDay = "0" + day;
        }
        date = formattedDay + "/" + formattedMonth + "/" + year;

        toolbar.setTitle(date);
    }

}
