package com.piteravto.rockabilla.checkingvehicles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by MishustinAI on 19.12.2016.
 */

public class ItemListActivity extends Activity {
    ListView choiceList;
    TextView selection;
    String[] foods = { "Молоко", "Сметана", "Колбаска", "Сыр", "Мышка",
            "Ананас", "Икра черная", "Икра кабачковая", "Яйцо" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_list);

        selection = (TextView) findViewById(R.id.textView1);
        choiceList = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, foods);
        // choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        choiceList.setAdapter(adapter);
    }
}
