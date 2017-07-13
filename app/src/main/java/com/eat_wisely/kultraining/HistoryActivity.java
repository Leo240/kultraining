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
import android.widget.AdapterView;
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

        String[] from = new String[] {DB.KEY_WORKOUT_DATE, DB.KEY_EX_1, DB.KEY_EX_2, DB.KEY_EX_3};
        int[] to = {R.id.tvDate, R.id.tvExercise_1, R.id.tvExercise_2, R.id.tvExercise_3};

        scAdapter = new SimpleCursorAdapter(this, R.layout.card, null, from, to, 0);

        scAdapter.setViewBinder(new MyViewBinder());

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(scAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("myTag", "position=" + position + ", id=" + id );

                Intent intent = new Intent("com.eat_wisely.action.edit");
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

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
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_history:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
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

    private static class MyCursorLoader extends CursorLoader{
        DB db;

        MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground(){
            return db.getAllData();
        }
    }

    private class MyViewBinder implements SimpleCursorAdapter.ViewBinder{

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            int KEY_EX, WORKOUT_TYPE;
            String ex;
            TextView tvEx;

            switch(view.getId()){
                case R.id.tvDate:
                    int KEY_WORKOUT_DATE = cursor.getColumnIndex(DB.KEY_WORKOUT_DATE);
                    WORKOUT_TYPE = cursor.getColumnIndex(DB.KEY_WORKOUT_TYPE);
                    String workout_date = cursor.getString(KEY_WORKOUT_DATE);
                    String workout_type = cursor.getString(WORKOUT_TYPE);
                    TextView tvDate = (TextView) view;
                    tvDate.setText(workout_date + " Type: " + workout_type);
                    return true;
                case R.id.tvExercise_1:
                    KEY_EX = cursor.getColumnIndex(DB.KEY_EX_1);
                    ex = cursor.getString(KEY_EX);
                    tvEx = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        String workWeight = obj.getString("workWeight");
                        tvEx.setText("Приседания: " + set1 + "/" + set2 + "/" + set3 + " "
                                + workWeight + "кг");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.tvExercise_2:
                    KEY_EX = cursor.getColumnIndex(DB.KEY_EX_2);
                    ex = cursor.getString(KEY_EX);
                    tvEx = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        String exID = obj.getString("exercise");

                        String workWeight = obj.getString("workWeight");

                        if ( exID.equals("2") ) {
                            tvEx.setText("Жим лежа: " + set1 + "/" + set2 + "/" + set3 + " "
                                    + workWeight + "кг");
                        } else {
                            tvEx.setText("Жим над головой: " + set1 + "/" + set2 + "/" + set3 + " "
                                    + workWeight + "кг");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.tvExercise_3:
                    KEY_EX = cursor.getColumnIndex(DB.KEY_EX_3);
                    ex = cursor.getString(KEY_EX);
                    tvEx = (TextView) view;
                    try {
                        JSONObject obj = new JSONObject(ex);
                        String set1 = obj.getString("set1");
                        String set2 = obj.getString("set2");
                        String set3 = obj.getString("set3");
                        String exID = obj.getString("exercise");
                        String workWeight = obj.getString("workWeight");

                        if ( exID.equals("3") ) {
                            tvEx.setText("Тяга в наклоне: " + set1 + "/" + set2 + "/" + set3 + " "
                                    + workWeight + "кг");
                        } else {
                            tvEx.setText("Мертвая тяга: " + set1 + "/" + set2 + "/" + set3 + " "
                                    + workWeight + "кг");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
            }
            return false;
        }
    }
}
