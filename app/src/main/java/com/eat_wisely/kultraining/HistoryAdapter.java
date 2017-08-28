package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eat_wisely.kultraining.model.HistoryItem;

/**
 *
 */

public class HistoryAdapter extends RecyclerViewCursorAdapter<HistoryAdapter.MyViewHolder> {


    public HistoryAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, Cursor cursor) {
        HistoryItem historyItem = HistoryItem.fromCursor(cursor);
        viewHolder.tvDate.setText(historyItem.getDate());
        Integer[] ex1_sets = historyItem.getExercise1();
        String allSets = "";
        for (int set : ex1_sets) {
            allSets += set + " ";
        }
        String text1 = historyItem.getTitle1() + ": " + allSets + historyItem.getWorkWeight1();
        viewHolder.tvExercise1.setText(text1);

        allSets = "";
        Integer[] ex2_sets = historyItem.getExercise2();
        for (int set : ex2_sets) {
            allSets += set + " ";
        }
        String text2 = historyItem.getTitle2() + ": " + allSets + historyItem.getWorkWeight2();
        viewHolder.tvExercise2.setText(text2);

        allSets = "";
        Integer[] ex3_sets = historyItem.getExercise3();
        for (int set : ex3_sets) {
            allSets += set + " ";
        }
        String text3 = historyItem.getTitle3() + ": " + allSets + historyItem.getWorkWeight3();
        viewHolder.tvExercise3.setText(text3);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvExercise1, tvExercise2, tvExercise3;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvExercise1 = (TextView) itemView.findViewById(R.id.tvExercise_1);
            tvExercise2 = (TextView) itemView.findViewById(R.id.tvExercise_2);
            tvExercise3 = (TextView) itemView.findViewById(R.id.tvExercise_3);
        }
    }
}
