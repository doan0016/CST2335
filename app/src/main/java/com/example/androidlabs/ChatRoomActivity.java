package com.example.androidlabs;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    List<ChatMessages> listMessage = new ArrayList<>();
    ListView listView;
    EditText editText;
    Button sendBtn;
    Button receiveBtn;
    ChatDB db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        sendBtn = (Button) findViewById(R.id.SendBtn);
        receiveBtn = (Button) findViewById(R.id.ReceiveBtn);
        listView = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.ChatInput);
        db = new ChatDB(this);
        viewData();


        sendBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            if (!message.equals("")) {
                db.insertData(message, true);
                editText.setText("");
                listMessage.clear();
                viewData();

            }
        });

        receiveBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            if (!message.equals("")) {
                db.insertData(message, false);
                editText.setText("");
                listMessage.clear();
                viewData();

            }
        });
        Log.d("ChatRoomActivity", "onCreate");
    }

    private void viewData() {
        Cursor c = db.viewData();

        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                ChatMessages model = new ChatMessages(c.getString(1), c.getInt(2) == 0 );
                listMessage.add(model);
                ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
                listView.setAdapter(adt);

            }
        }
    }







}