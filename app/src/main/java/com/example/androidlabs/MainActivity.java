package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidlabs.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    EditText typeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_email);

        typeField = (EditText) findViewById(R.id.editText);
        sp = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = sp.getString("ReserveName", "");

        typeField.setText(savedString);

        Button save = (Button) findViewById(R.id.button2);
        save.setOnClickListener(bt -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

        });




    }


    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        String whatWasTyped = typeField.getText().toString();
        editor.putString("ReserveName", whatWasTyped);
        editor.commit();


    }
}




