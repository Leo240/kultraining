package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

            canvas.drawColor(Color.WHITE);

            canvas.drawLine(initX, initY, initX, finalY, paint);
            canvas.drawLine(initX, initY, finalX, initY, paint);

            paint.setColor( Color.GREEN);
            db.open();
            Cursor c = db.getAllData();

            if (c.moveToFirst()){
                while(c.moveToNext()){
                    
                }
            }

        }
    }
}
