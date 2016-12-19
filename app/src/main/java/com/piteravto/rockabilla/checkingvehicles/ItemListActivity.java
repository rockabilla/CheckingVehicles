package com.piteravto.rockabilla.checkingvehicles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
            if (menuItemsName!=null && menuItemsValues!=null) {

                choiceList = (ListView) findViewById(R.id.listView1);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        R.layout.list_item, menuItemsName);

                choiceList.setAdapter(adapter);

                for (int i = 0; i<menuItemsValues.length; i++)
                {
                    choiceList.setItemChecked(i, menuItemsValues[i] == 1);
                }
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
