package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraphicsActivity extends AppCompatActivity {

    Paint paint;
    DB db;
    int currentX;
    float currentY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        initializeView();


    }

    void initializeView(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);

        setContentView( new Panel( this ) );
    }

    class Panel extends View {
        JSONObject obj_ex_1;
        List<Float> weightList;

        public Panel(Context context) {
            super(context);
            initializePanel();
        }

        void initializePanel(){
            weightList = new ArrayList<>();
            db = new DB(this.getContext());
            db.open();
            Cursor c = db.getAllData();



            if (c.moveToFirst()){
                //int KEY_WORKOUT_DATE = c.getColumnIndex(DB.KEY_WORKOUT_DATE);
                int KEY_EX_1 = c.getColumnIndex(DB.KEY_EX_1);
                /*int KEY_EX_2 = c.getColumnIndex(DB.KEY_EX_2);
                int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);*/


                //JSONObject obj_ex_2 = new JSONObject(ex_2);
                //JSONObject obj_ex_3 = new JSONObject(ex_3);


                do {
                    try {
                        obj_ex_1 = new JSONObject(c.getString(KEY_EX_1));
                        float ex1_work_weight = Float.parseFloat(obj_ex_1.getString("workWeight"));

                        weightList.add(ex1_work_weight);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while(c.moveToNext());

            }
            c.close();
            currentY = weightList.get(0);
        }

        @Override
        public void onDraw(Canvas canvas) {
            int canvasW = canvas.getWidth();
            int canvasH = canvas.getHeight();

            int initY = canvasH - 20;
            int initX = 20;
            int finalX = canvasW - 20;
            int finalY = 20;

            currentX = initX;




            canvas.drawColor(Color.WHITE);

            canvas.drawLine(initX, initY, initX, finalY, paint);
            canvas.drawLine(initX, initY, finalX, initY, paint);

            paint.setColor( Color.GREEN);

            for (float weight : weightList) {
                //Log.d("myLog", "" + weight + ", ");
                canvas.drawLine(currentX + initX, initY - currentY, currentX + initX + 14, initY - weight, paint);
                currentX +=14;
                currentY = weight;
            }

        }
    }
}
