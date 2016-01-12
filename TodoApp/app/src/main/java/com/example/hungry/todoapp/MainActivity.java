package com.example.hungry.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
private ListView itemsListView;
private ArrayList<String> listItems;
private ArrayAdapter<String> arrayAdapter;
private Button addItems;
private EditText itemsEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItems=new ArrayList<>();
        itemsListView=(ListView)findViewById(R.id.itemsList);
        itemsEditText=(EditText)findViewById(R.id.itemEditText);
        addItems=(Button)findViewById(R.id.addItemButton);
        addItems.setOnClickListener(this);
        readItems();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItems);
        itemsListView.setAdapter(arrayAdapter);
        itemsListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addItemButton){
            if(itemsEditText.getText().toString().length()>0&&!itemsEditText.getText().toString().isEmpty()) addItems();
            else itemsEditText.setError("Please Enter some text");
        }
    }


    private void readItems(){
       File fileDir=getFilesDir();
       File file=new File(fileDir,"todo.txt");
        try {
            listItems=new ArrayList<>(FileUtils.readLines(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void writeItems(){
        File fileDir=getFilesDir();
        File file=new File(fileDir,"todo.txt");
        try {
            FileUtils.writeLines(file, listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addItems() {
        listItems.add(itemsEditText.getText().toString());
        itemsEditText.setText("");
        writeItems();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        listItems.remove(position);
        arrayAdapter.notifyDataSetChanged();
        writeItems();
        return true;
    }
}
