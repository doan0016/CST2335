package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

public class ChatDB extends SQLiteOpenHelper {
    private static final String FILENAME = "ChatDB";
    private static final String TABLENAME = "Messages";


    private static final String COL_MESSAGE = "Message";
    private static final String COL_ISSEND = "IsSend";
    private static final String COL_MESSAGEID = "MessageID";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLENAME + " (" + COL_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MESSAGE + " TEXT, " + COL_ISSEND + " INTEGER);";

    public ChatDB(Context ctx) {
        super(ctx, FILENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);

    }

    public boolean insertData(String message, boolean isSend) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MESSAGE, message);
        if (isSend)
            cv.put(COL_ISSEND, 0);
        else
            cv.put(COL_ISSEND, 1);

        long result = db.insert(TABLENAME, null, cv);

        return result != -1;
    }


    public Cursor viewData() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLENAME;
        Cursor cursor = db.rawQuery(query, null);
        printCursor(cursor);
        return cursor;



    }
  public Cursor printCursor(Cursor c){
       //c = db.query(false, "TABLENAME", new String[] {"Message", "IsSend", "MessageID" }, “FirstName like ? or lastName like ?”, new String[] {“Eric”, “Torunski”}, null, null, null, null)
        // c = db.rawQuery("select * from TABLENAME where _id = ?", new String[] { MyOpenHelper.TABLE_NAME, 1 });
      SQLiteDatabase db = this.getReadableDatabase();
      Log.d("Database Version:", Integer.toString(db.getVersion()));
      Log.d("Number of columns: ", Integer.toString(c.getColumnCount()));
      for (int i = 0; i < c.getColumnCount(); i++){
          Log.d("Column "+(i+1)+": ", c.getColumnName(i));
      }
      Log.d("Number of rows:", Integer.toString(c.getCount()));
      Log.d("Cursor Object", DatabaseUtils.dumpCursorToString(c));

        return c;

    }
}



