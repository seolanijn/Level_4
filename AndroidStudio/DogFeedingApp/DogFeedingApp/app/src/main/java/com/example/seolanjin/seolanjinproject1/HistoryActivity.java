package com.example.seolanjin.seolanjinproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewManager;
    ArrayList<Record> recordArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById( R.id.recyclerView );
        recyclerViewManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( recyclerViewManager );
        recyclerView.setHasFixedSize( true );
        recordArrayList = new ArrayList<Record>();

        findViewById(R.id.buttonMainH).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { finish(); }
        });

        try{
            InputStreamReader isr = new InputStreamReader(openFileInput("project_dog_feeding_app.txt"));
            char[] inputBuffer = new char[1000];
            String str = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0)
                str += String.copyValueOf(inputBuffer,0,charRead);
            if (str != "") {
                String[] perUserData = str.split("\n");
                int id = 1;
                for (String data : perUserData) {
                    String[] temp = data.split(",");
                    recordArrayList.add(new Record(id++, temp[0], temp[1], temp[2]));
                }
            }
            isr.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        recyclerViewAdapter = new MyAdapter( getApplicationContext(),recordArrayList );
        recyclerView.setAdapter( recyclerViewAdapter );
    }

    public void onButtonRemove(View view){
        // remove from arraylist
        recordArrayList.remove(recordArrayList.size()-1);

        // remove from file
        try{
            String temp = "";
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("project_dog_feeding_app.txt", MODE_PRIVATE));

            for (Record val : recordArrayList)
                temp += val.getsDate() + "," + val.getTime() + "," + val.getDogName() + "\n";

            osw.write(temp);
            osw.flush();
            osw.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        // re-load this activity
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void onButtonReset(View view){
        // remove from arraylist
        recordArrayList.clear();

        // remove from file
        try{
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("project_dog_feeding_app.txt", MODE_PRIVATE));
            osw.write("");
            osw.flush();
            osw.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        // re-load this activity
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}