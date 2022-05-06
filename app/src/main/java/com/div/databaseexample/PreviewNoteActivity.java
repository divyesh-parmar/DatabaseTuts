package com.div.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewNoteActivity extends AppCompatActivity {

    TextView tv_title, tv_content;
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_note);

        BindView();
        BuildDataBaseConnection();

        tv_title.setText(Util.selected_title);
        tv_content.setText(Util.selected_content);

    }

    public void BindView() {
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
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

    public void UpdateNote(View view) {
        UpdateNoteDialog();
    }

    public void UpdateNoteDialog() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_addnote);

        EditText et_title = dialog.findViewById(R.id.et_title);
        EditText et_content = dialog.findViewById(R.id.et_content);
        Button btninsert = dialog.findViewById(R.id.btninsert);

        et_title.setText(Util.selected_title);
        et_content.setText(Util.selected_content);

        btninsert.setText("Update");

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_title = et_title.getText().toString();
                String str_content = et_content.getText().toString();

                if (str_title.trim().equals("") || str_content.equals("")) {
                    Toast.makeText(PreviewNoteActivity.this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    dbHelper.UpdateNote(Util.selected_title, Util.selected_content, str_title, str_content);
                    tv_title.setText(str_title);
                    tv_content.setText(str_content);
                    Toast.makeText(PreviewNoteActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public void DeleteNote(View view) {

        dbHelper.DeleteNote(Util.selected_title, Util.selected_content);
        onBackPressed();

    }
}