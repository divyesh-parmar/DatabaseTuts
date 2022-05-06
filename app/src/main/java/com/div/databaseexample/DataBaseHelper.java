package com.div.databaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "notes.db";
    private static String DB_PATH = "";

    @SuppressWarnings("unused")
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DB_NAME, null, 1);
        this.myContext = context;

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        Log.e("Path of Db :", DB_PATH);

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist       
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.toString();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() {
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try {
            myInput = myContext.getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDataBase() {

        String myPath = DB_PATH + DB_NAME;
        try {
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `naughtynotes`", null);
        return res;
    }

    public Cursor getNote(int notenum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM `naughtynotes` WHERE id=" + notenum, null);
        return res;
    }

    public boolean InsertNote(String u_title, String u_content) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "INSERT INTO naughtynotes(title,contents) VALUES('"
                + u_title + "','" + u_content + "')";
        sqLiteDatabase.execSQL(query);

        return true;
    }

    public boolean DeleteNote(String u_title, String u_content) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "DELETE FROM naughtynotes WHERE title='" + u_title + "' AND contents='" + u_content + "'";

        sqLiteDatabase.execSQL(query);

        return true;
    }

    public boolean UpdateNote(String u_title, String u_content, String new_title, String new_content) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "UPDATE naughtynotes SET title='" + new_title + "',contents='" + new_content + "' WHERE title='" + u_title + "' AND contents='" + u_content + "'";

        sqLiteDatabase.execSQL(query);

        return true;
    }

   /* public boolean InsertNumber(String number) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        String strSQL = "insert into block_list(number) values('" + number + "')";
        myDatabase.execSQL(strSQL);
        return true;
    }

    public boolean InsertSetScreen(String number, String pos, String url,String type) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        String strSQL = "insert into set_screen(number,pos,url,type) values('"
                + number + "','" + pos + "','" + url + "','" + type + "')";
        myDatabase.execSQL(strSQL);
        return true;
    }

    public boolean DeleteNumber(String number) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        String strSQL = "delete from block_list where number ='" + number + "'";
        myDatabase.execSQL(strSQL);
        return true;
    }

    public boolean UpdateSetScreen(String number, String pos, String url,String type) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        String strSQL = "update set_screen set pos= '" + pos + "',url = '"
                + url + "',type = '" + type + "' where number = '"
                + number + "'";
        myDatabase.execSQL(strSQL);
        return true;
    }

    public boolean DeleteSetScreen(String number) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        String strSQL = "delete from set_screen where number ='" + number + "'";
        myDatabase.execSQL(strSQL);
        return true;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}