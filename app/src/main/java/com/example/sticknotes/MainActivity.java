package com.example.sticknotes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    //ListView listView;
    //SharedPreferences sharedPreferences;

     static ArrayList<String> notes = new ArrayList<String>();
     static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.sticknotes", Context.MODE_PRIVATE);
       // sharedPreferences = this.getSharedPreferences("com.example.sticknotes", Context.MODE_PRIVATE);
        HashSet<String>set =  (HashSet<String>)sharedPreferences.getStringSet("set",null);
        if(set==null){
            notes.add("Notes");
        }
        else {
            notes = new ArrayList<String>(set);
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),NotesEditor.class);
                intent.putExtra("itemid",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int deleteindex = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("delete")
                        .setMessage("do you want to delete")
                        .setIcon(android.R.drawable.btn_star)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(deleteindex);
                                arrayAdapter.notifyDataSetChanged();
                                HashSet<String > set = new HashSet<String>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("set",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()== R.id.add){
            Intent intent = new Intent(getApplicationContext(),NotesEditor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}