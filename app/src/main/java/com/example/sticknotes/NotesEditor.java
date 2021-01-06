package com.example.sticknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NotesEditor extends AppCompatActivity {
     int itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        itemId = intent.getIntExtra("itemid",-1);
        //startActivity(intent);
        if(itemId!=-1){
            editText.setText(MainActivity.notes.get(itemId));
        }
        else {
            MainActivity.notes.add(" ");
            itemId = MainActivity.notes.size()-1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainActivity.notes.set( itemId, String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }
}