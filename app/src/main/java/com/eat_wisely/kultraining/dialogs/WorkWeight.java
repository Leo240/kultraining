package com.eat_wisely.kultraining.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eat_wisely.kultraining.R;

public class WorkWeight extends DialogFragment implements View.OnClickListener {

    float weight;
    EditText etWeight;
    protected TextView tvWeight;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.fragment_choose_work_weight, null);
        builder.setView(dialogView);

        ImageButton ibMinus = (ImageButton) dialogView.findViewById(R.id.ibMinus);
        ImageButton ibPlus = (ImageButton) dialogView.findViewById(R.id.ibPlus);
        etWeight = (EditText) dialogView.findViewById(R.id.etWeight);

        String s = tvWeight.getText().toString();
        etWeight.setText(s.replace("кг", ""));
        weight = Float.parseFloat(etWeight.getText().toString());

        ibMinus.setOnClickListener(this);
        ibPlus.setOnClickListener(this);

        builder.setTitle("Введите вес")
                .setPositiveButton("Записать", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String workWeight = etWeight.getText().toString();
                        String text = getResources().getString(R.string.unit_kg, workWeight);
                        tvWeight.setText(text);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WorkWeight.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibMinus:
                weight -= 2.5;
                etWeight.setText(Float.toString(weight).replace(".0", ""));
                break;
            case R.id.ibPlus:
                weight += 2.5;
                etWeight.setText(Float.toString(weight).replace(".0", ""));
                break;
        }
    }
}
