package com.eat_wisely.kultraining;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class tab_warmup_sets extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tab_warmup_sets, container, false);
        return rootView;
    }

    List<String> warmupWeights(double weight) {
        double warmupWeight = 0;
        List<String> warmupSets = new ArrayList<>();
        while (warmupWeight < weight) {
            warmupWeight += 20;
            if (warmupWeight < weight) {
                warmupSets.add(String.valueOf(warmupWeight));
            }
        }
        return warmupSets;
    }
}
