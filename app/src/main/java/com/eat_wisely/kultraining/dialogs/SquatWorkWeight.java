package com.eat_wisely.kultraining.dialogs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.eat_wisely.kultraining.R;

public class SquatWorkWeight extends WorkWeight implements View.OnClickListener {

    @NonNull
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
