package com.piteravto.rockabilla.checkingvehicles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
}
