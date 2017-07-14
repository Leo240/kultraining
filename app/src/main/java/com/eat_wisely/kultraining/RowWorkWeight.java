package com.eat_wisely.kultraining;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

public class RowWorkWeight extends WorkWeight implements View.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        tvWeight = (TextView) getActivity().findViewById(R.id.tvRowWeight);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
