package com.eat_wisely.kultraining;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class WorkWeight extends DialogFragment implements View.OnClickListener {

    float weight;
    EditText etWeight;

    public interface WorkWeightListener{
        public void onDialogPositiveClick(DialogFragment dialog, String workWeight);
    }

    WorkWeightListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.fragment_choose_work_weight, null);
        builder.setView(dialogView);

        ImageButton ibMinus = (ImageButton) dialogView.findViewById(R.id.ibMinus);
        ImageButton ibPlus = (ImageButton) dialogView.findViewById(R.id.ibPlus);
        etWeight = (EditText) dialogView.findViewById(R.id.etWeight);
        TextView tvSquatWeight = (TextView) getActivity().findViewById(R.id.tvSquatWeight);

        String s = tvSquatWeight.getText().toString();
        etWeight.setText(s.replace("кг", ""));
        weight = Float.parseFloat(etWeight.getText().toString());

        ibMinus.setOnClickListener(this);
        ibPlus.setOnClickListener(this);

        builder.setTitle("Введите вес")
                .setPositiveButton("Записать", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String workWeight = etWeight.getText().toString();
                        listener.onDialogPositiveClick(WorkWeight.this, workWeight);
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
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener =(WorkWeightListener) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
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
