package com.eat_wisely.kultraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

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
            
        }

        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        ArrayList<Map<String, String>> groupData;

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
