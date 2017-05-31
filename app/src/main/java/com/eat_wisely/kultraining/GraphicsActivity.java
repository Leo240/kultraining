package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraphicsActivity extends AppCompatActivity {

    Paint paint;
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        initializeView();

        db = new DB(this);
    }

    void initializeView(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);

        setContentView( new Panel( this ) );
    }

    class Panel extends View {
        JSONObject obj_ex_1;
        List<Integer> weightList;

        public Panel(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {
            int canvasW = canvas.getWidth();
            int canvasH = canvas.getHeight();

            int initY = canvasH - 20;
            int initX = 20;
            int finalX = canvasW - 20;
            int finalY = 20;
            weightList = new ArrayList<>();

            canvas.drawColor(Color.WHITE);

            canvas.drawLine(initX, initY, initX, finalY, paint);
            canvas.drawLine(initX, initY, finalX, initY, paint);

            paint.setColor( Color.GREEN);
            db.open();
            Cursor c = db.getAllData();



            if (c.moveToFirst()){
                int KEY_WORKOUT_DATE = c.getColumnIndex(DB.KEY_WORKOUT_DATE);
                int KEY_EX_1 = c.getColumnIndex(DB.KEY_EX_1);
                int KEY_EX_2 = c.getColumnIndex(DB.KEY_EX_2);
                int KEY_EX_3 = c.getColumnIndex(DB.KEY_EX_3);

                String ex_1 = c.getString(KEY_EX_1);
                String ex_2 = c.getString(KEY_EX_2);
                String ex_3 = c.getString(KEY_EX_3);
                String workoutDate = c.getString(KEY_WORKOUT_DATE);


                //JSONObject obj_ex_2 = new JSONObject(ex_2);
                //JSONObject obj_ex_3 = new JSONObject(ex_3);

                try {
                    obj_ex_1 = new JSONObject(ex_1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                while(c.moveToNext()){
                    int ex1_set1 = Integer.parseInt(obj_ex_1.getString("set1"));
                    int ex1_set2 = Integer.parseInt(obj_ex_1.getString("set2"));
                    int ex1_set3 = Integer.parseInt(obj_ex_1.getString("set3"));
                    int ex1_work_weight = Integer.parseInt(obj_ex_1.getString("workWeight"));
                    int volume = ex1_set1 * ex1_set2 * ex1_set3 * ex1_work_weight;
                    weightList.add(volume);

                }
            }

        }
    }
}
