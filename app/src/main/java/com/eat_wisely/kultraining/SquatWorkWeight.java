package com.eat_wisely.kultraining;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SquatWorkWeight extends WorkWeight implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        tvWeight = (TextView) getActivity().findViewById(R.id.tvSquatWeight);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
