package com.example.androidlabs;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.widget.ImageView;


public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //ImageButton ib = (ImageButton)findViewById(R.id.imageButton5);
        Intent loginIntent = getIntent();
        String item1 = loginIntent.getStringExtra("item1");

        EditText enterText = (EditText)findViewById(R.id.editText4);
        enterText.setText(item1);

        mImageButton = (ImageButton)findViewById(R.id.imageButton5);
        mImageButton.setOnClickListener ( bt -> {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        Button save = (Button) findViewById(R.id.GoToChat);
        Intent save1 = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        save.setOnClickListener(c -> {
            startActivityForResult(save1, 77);
        });

        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");
    }
    ;







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult");
    }







    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function:" + "onPause");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");







    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
    }
}
