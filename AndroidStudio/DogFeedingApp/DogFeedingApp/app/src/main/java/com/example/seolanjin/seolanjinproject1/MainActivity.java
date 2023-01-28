package com.example.seolanjin.seolanjinproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editTextTextDogName;
    RadioButton radioButtonToday, radioButtonDayC, radioButtonNow, radioButtonTimeC;
    CalendarView calendarView;
    String userDate;
    TimePicker timepicker;
    String userTime;
    Button buttonSettings, buttonSave, buttonHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTextDogName = findViewById(R.id.editTextTextDogName);

        radioButtonToday = findViewById(R.id.radioButtonToday);
        radioButtonDayC = findViewById(R.id.radioButtonDayC);
        radioButtonNow = findViewById(R.id.radioButtonNow);
        radioButtonTimeC = findViewById(R.id.radioButtonTimeC);

        calendarView = findViewById(R.id.calendarView);

        timepicker = findViewById(R.id.timepicker);

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonSave = findViewById(R.id.buttonSave);
        buttonHistory = findViewById(R.id.buttonHistory);

        calendarView.setVisibility(View.INVISIBLE);
        timepicker.setVisibility(View.INVISIBLE);

        radioButtonDayC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
                timepicker.setVisibility(View.INVISIBLE);
            }
        });
        radioButtonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.INVISIBLE);
                userDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String tempMonth = Integer.toString(month);
                String tempDay = Integer.toString(dayOfMonth);
                if (month + 1 < 10)  tempMonth = "0" + (month + 1); //formatting
                if (dayOfMonth < 10)  tempDay = "0" + dayOfMonth; //formatting
                userDate = Integer.toString(year) + "-" + tempMonth + "-" + tempDay;
            }
        });
        radioButtonTimeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.INVISIBLE);
                timepicker.setVisibility(View.VISIBLE);
            }
        });
        radioButtonNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker.setVisibility(View.INVISIBLE);
                userTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            }
        });
        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String tempMin = Integer.toString(minute);
                String tempHour = Integer.toString(hourOfDay);
                if (minute < 10)  tempMin = "0" + minute; //formatting
                if (hourOfDay < 10)  tempHour = "0" + hourOfDay; //formatting
                userTime = tempHour + ":" + tempMin;
            }
        });

    }

    public void onButtonSave(View view){
        try{
            InputStreamReader isr = new InputStreamReader(openFileInput("project_dog_feeding_app.txt"));
            char[] inputBuffer = new char[100];
            String savedData = "";
            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0){
                String readString = String.copyValueOf(inputBuffer,0,charRead);
                savedData += readString;
            }
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("project_dog_feeding_app.txt", MODE_PRIVATE));
            if (userDate == null)
                userDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            if (userTime == null)
                userTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            osw.write(savedData + userDate + "," + userTime + "," + editTextTextDogName.getText().toString() + "\n");
            osw.flush();
            osw.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Saved Data:\n " + userDate + " " + userTime + " " + editTextTextDogName.getText().toString(), Toast.LENGTH_LONG).show();
    }

    public void setButtonHistory(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void onButtonSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}