package com.piteravto.rockabilla.checkingvehicles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.piteravto.rockabilla.checkingvehicles.api.ServerApi;
import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String vehicleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText myEditText = (EditText) findViewById(R.id.vehicle_id);
        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });
    }

    public void onClickClearButton(View view) {
        EditText editText = (EditText) findViewById(R.id.vehicle_id);
        editText.getText().clear();
    }

    public void onClickContinueButton(View view) {
        EditText editText = (EditText) findViewById(R.id.vehicle_id);
        vehicleId = editText.getText().toString();
        Toast.makeText(MainActivity.this, vehicleId, Toast.LENGTH_LONG).show();
        try {
            //RequestBody body = RequestBody.create(MediaType.parse("text/plain"), vehicleId);
            ServerApi.getApi().getMenuesItems(getString(R.string.stk), getString(R.string.get_menu_items), vehicleId).enqueue(new Callback<List<MenuItem>>() {
                @Override
                public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                    try {
                        List<MenuItem> menuItemList = new ArrayList<>();
                        menuItemList.addAll(response.body());

                        Intent intent = new Intent(MainActivity.this, ItemListActivity.class);

                        String[] menuItemNameList = new String[menuItemList.size()];
                        int[] menuItemValueList = new int[menuItemList.size()];

                        //Toast.makeText(MainActivity.this, "0", Toast.LENGTH_LONG).show();
                        int i = 0;
                        for(MenuItem menuItem : menuItemList)
                        {
                            //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_LONG).show();
                            menuItemNameList[i] = menuItem.getName();
                            menuItemValueList[i] = menuItem.getValue();
                            i++;
                        }
                        //Toast.makeText(MainActivity.this, menuItemNameList.size(), Toast.LENGTH_LONG).show();



                        intent.putExtra("menuItemNameList", menuItemNameList);
                        intent.putExtra("menuItemValueList", menuItemValueList);
                        intent.putExtra("vehicleId", vehicleId);


                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "onResponse error", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "click error", Toast.LENGTH_LONG).show();
        }
    }
}
