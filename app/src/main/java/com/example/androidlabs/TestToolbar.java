package com.example.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    String toastOverflow = "This is the overflow msg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

/*        Snackbar sb = Snackbar.make(tBar, "Hello world", Snackbar.LENGTH_LONG)
                .setAction("Action text", e -> Log.e("Menu Example", "Clicked Undo"));
        sb.show();*/



        Toast.makeText(this, "Welcome to Menu Example", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);




	    /* slide 15 material:
	    MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView sView = (SearchView)searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }  });

	    */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.item1:
                Toast.makeText(this, "This is the initial msg", Toast.LENGTH_LONG).show();
                break;

            case R.id.item2:
                alertExample();
                break;

            case R.id.item3:
               // findViewById(android.R.id.content);
                Snackbar sb = Snackbar.make(findViewById(R.id.item3), "Go Back?", Snackbar.LENGTH_LONG)
                        .setAction("GoBack", e -> Log.e("Menu Example", "Clicked Undo"));
                sb.setAction("GoBack",f -> finish());
               sb.show();




                break;
            case R.id.item4:


                Toast.makeText(this, toastOverflow , Toast.LENGTH_LONG).show();

                break;
        }
        return true;
    }

    public void alertExample() {


        View middle = getLayoutInflater().inflate(R.layout.test_toolbar_extra, null);
      //  Button btn = (Button) middle.findViewById(R.id.extraBtn);
        EditText et = (EditText) middle.findViewById(R.id.typeHere);

      //  btn.setOnClickListener(clk -> et.setText("You clicked my button!"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(R.id.imageView2)
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Accept
                        toastOverflow = et.getText().toString();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }




    }


