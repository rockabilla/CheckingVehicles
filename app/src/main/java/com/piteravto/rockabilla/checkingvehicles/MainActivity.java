package com.piteravto.rockabilla.checkingvehicles;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.piteravto.rockabilla.checkingvehicles.api.ServerApi;
import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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
        String vehicleId = editText.getText().toString();
        Toast.makeText(MainActivity.this, vehicleId, Toast.LENGTH_LONG).show();
        try {
            //RequestBody body = RequestBody.create(MediaType.parse("text/plain"), vehicleId);
            ServerApi.getApi().getMenuesItems(getString(R.string.stk), getString(R.string.php), vehicleId).enqueue(new Callback<List<MenuItem>>() {
                @Override
                public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                    try {
                        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
                        menuItemList.addAll(response.body());
                        Toast.makeText(MainActivity.this, menuItemList.size() + " " + menuItemList.get(0).toString(), Toast.LENGTH_LONG).show();
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
