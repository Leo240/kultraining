package com.eat_wisely.kultraining;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GraphicsActivity extends AppCompatActivity {

    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        initializeView();
    }

    void initializeView(){
        paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(1);

        setContentView( new Panel( this ) );
    }

    class Panel extends View {

        public Panel(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.drawCircle(300, 100, 50, paint);
        }
    }
}
