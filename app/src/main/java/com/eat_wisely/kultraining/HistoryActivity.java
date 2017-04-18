package com.eat_wisely.kultraining;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;


public class HistoryActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    DB db;
    ListView lvMain;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DB(this);
        db.open();

        String[] from = new String[] {DB.KEY_WORKOUT_DATE, DB.KEY_EX_1, DB.KEY_EX_2,
                DB.KEY_EX_3};
        int[] to = {R.id.tvDate, R.id.tvExercise_1, R.id.tvExercise_2, R.id.tvExercise_3};

        scAdapter = new SimpleCursorAdapter(this, R.layout.card, null, from, to, 0);

        scAdapter.setViewBinder(new MyViewBinder());

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(scAdapter);

        getSupportLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.action_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.action_history:
                Intent intentHistory = new Intent(this, HistoryActivity.class);
                startActivity(intentHistory);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl){
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){

    }

    static class MyCursorLoader extends CursorLoader{
        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground(){
            Cursor cursor = db.getAllData();
            return cursor;
        }
    }

    class MyViewBinder implements SimpleCursorAdapter.ViewBinder{



        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            switch(view.getId()){
                case R.id.tvDate:
                    int KEY_WORKOUT_DATE = cursor.getColumnIndex(db.KEY_WORKOUT_DATE);
                    String workout_date = cursor.getString(KEY_WORKOUT_DATE);
                    TextView tvDate = (TextView) view;
                    tvDate.setText(workout_date);
                    return true;
                case R.id.tvExercise_1:
                    int KEY_EX_1 = cursor.getColumnIndex(db.KEY_EX_1);
                    String ex_1 = cursor.getString(KEY_EX_1);
                    TextView tvEx1 = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex_1);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        tvEx1.setText("Приседания: " + set1 + "/" + set2 + "/" + set3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.tvExercise_2:
                    int KEY_EX_2 = cursor.getColumnIndex(db.KEY_EX_2);
                    String ex_2 = cursor.getString(KEY_EX_2);
                    TextView tvEx2 = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex_2);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        tvEx2.setText("Жим лежа: " + set1 + "/" + set2 + "/" + set3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.tvExercise_3:
                    int KEY_EX_3 = cursor.getColumnIndex(db.KEY_EX_3);
                    String ex_3 = cursor.getString(KEY_EX_3);
                    TextView tvEx3 = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex_3);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        tvEx3.setText("Тяга в наклоне: " + set1 + "/" + set2 + "/" + set3);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
            }
            return false;
        }
    }
}
