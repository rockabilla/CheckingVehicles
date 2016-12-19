package com.piteravto.rockabilla.checkingvehicles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

/**
 * Created by MishustinAI on 19.12.2016.
 */

public class ItemListActivity extends Activity {
    ListView choiceList;
    TextView selection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_list);

        try {
            String[] menuItemsName = getIntent().getExtras().getStringArray("menuItemNameList");
            int[] menuItemsValues =  getIntent().getExtras().getIntArray("menuItemValueList");
            if (menuItemsName!=null) {
                selection = (TextView) findViewById(R.id.textView1);
                choiceList = (ListView) findViewById(R.id.listView1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_multiple_choice, menuItemsName);
                // choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                choiceList.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(ItemListActivity.this, "menuItemsName is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ItemListActivity.this, "ItemListActivity onCreate error", Toast.LENGTH_LONG).show();
        }
    }
}
