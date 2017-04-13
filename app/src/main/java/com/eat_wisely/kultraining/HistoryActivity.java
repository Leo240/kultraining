package com.eat_wisely.kultraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    final String ATTR_DATES = "dates";
    final String ATTR_SQUATS = "squats";
    final String ATTR_BP = "benchpress";
    final String ATTR_ROW = "row";

    String[] dates = new String[] {"Вт, 4 Апр", "Чт, 6 Апр", "Сб, 8 Апр", "Вт, 11 Апр", "Чт, 13 Апр"};
    String[] squats = new String[] {"Приседания: 8/8/8 80кг", "Приседания: 8/8/8 82.5кг",
            "Приседания: 8/8/8 85кг", "Приседания: 8/8/8 87.5кг", "Приседания: 8/8/8 90кг"};
    String[] benchpress = new String[] {"Жим лежа: 8/8/8 55кг", "Жим лежа: 8/8/8 57.5кг",
            "Жим лежа: 8/8/8 60кг", "Жим лежа: 8/8/8 62.5кг", "Жим лежа: 8/8/8 65кг"};
    String[] row = new String[] {"Тяга в наклоне: 8/8/8 50кг", "Тяга в наклоне: 8/8/8 52.5кг",
            "Тяга в наклоне: 8/8/8 55кг", "Тяга в наклоне: 8/8/8 57.5кг", "Тяга в наклоне: 8/8/8 60кг"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(dates.length);

        Map<String, Object> m;
        for(int i=0; i < dates.length; i++){
            m = new HashMap<String, Object>();
            m.put(ATTR_DATES, dates[i]);
            m.put(ATTR_SQUATS, squats[i]);
            m.put(ATTR_BP, benchpress[i]);
            m.put(ATTR_ROW, row[i]);
            data.add(m);
        }

        String[] from = {ATTR_DATES, ATTR_SQUATS, ATTR_BP, ATTR_ROW};
        int[] to = {R.id.tvDate, R.id.tvExercise_1, R.id.tvExercise_2, R.id.tvExercise_3};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.card, from, to);

        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(simpleAdapter);


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

}
