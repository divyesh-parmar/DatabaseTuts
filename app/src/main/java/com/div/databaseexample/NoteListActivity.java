package com.div.databaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity {

    RecyclerView rv_notes;
    FloatingActionButton btncreate;
    DataBaseHelper dbHelper;
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        BuildDataBaseConnection();
        BindViews();
        SetClicks();

    }

    public void BuildDataBaseConnection() {

        dbHelper = new DataBaseHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.openDataBase();

    }

    private void SetClicks() {

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNote();
            }
        });
    }

    public void AddNote() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_addnote);

        EditText et_title = dialog.findViewById(R.id.et_title);
        EditText et_content = dialog.findViewById(R.id.et_content);
        Button btninsert = dialog.findViewById(R.id.btninsert);

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_title = et_title.getText().toString();
                String str_content = et_content.getText().toString();

                if (str_title.trim().equals("") || str_content.equals("")) {
                    Toast.makeText(NoteListActivity.this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    dbHelper.InsertNote(str_title, str_content);
                    onResume();
                }

            }
        });

        dialog.show();
    }

    public void BindViews() {

        rv_notes = findViewById(R.id.rv_notes);
        btncreate = findViewById(R.id.btncreate);
    }

    @Override
    protected void onResume() {
        super.onResume();

        titleList.clear();
        contentList.clear();

        Cursor c = dbHelper.getAllData();

        if (c.moveToFirst()) {
            do {
                String tmp_title = c.getString(1);
                String tmp_content = c.getString(2);

                titleList.add(tmp_title);
                contentList.add(tmp_content);
            } while (c.moveToNext());
        }

        rv_notes.setLayoutManager(new GridLayoutManager(this, 1));
        rv_notes.setAdapter(new NotesAdapter(this, titleList, contentList));

    }

    public void BackMe(View view) {
        onBackPressed();
    }
}