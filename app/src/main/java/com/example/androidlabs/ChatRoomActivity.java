package com.example.androidlabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    List<ChatMessages> listMessage = new ArrayList<>();
    ListView listView;
    EditText editText;
    Button sendBtn;
    Button receiveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        sendBtn = (Button)findViewById(R.id.SendBtn);
        receiveBtn = (Button)findViewById(R.id.ReceiveBtn);
        listView = (ListView)findViewById(R.id.ListView);
        editText = (EditText)findViewById(R.id.ChatInput);


        sendBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            ChatMessages a = new ChatMessages(message, true);
            listMessage.add(a);
            editText.setText("");
            ChatAdapter chatAdpt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(chatAdpt);
        });

        receiveBtn.setOnClickListener(d -> {
            String message = editText.getText().toString();
            ChatMessages a = new ChatMessages(message, false);
            listMessage.add(a);
            editText.setText("");
            ChatAdapter chatAdpt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(chatAdpt);
        });
    }


}
