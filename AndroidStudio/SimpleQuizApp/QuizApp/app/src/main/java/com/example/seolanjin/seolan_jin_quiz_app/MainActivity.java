package com.example.seolanjin.seolan_jin_quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewHint;
    CheckBox checkBoxHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewHint = findViewById(R.id.imageViewHint);
        checkBoxHint = findViewById(R.id.checkBoxHint);
    }

    public void onRadioClick(View _view) {
        switch (_view.getId()) {
            case R.id.radioButtonJalapino:
            case R.id.radioButtonJackfruit:
                Toast.makeText(getApplicationContext(), "Incorrect. Try Again!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButtonJellyfish:
                Toast.makeText(getApplicationContext(), "Correct.\nThe burger is made of jellyfish jam!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void onCheckBoxClick(View _view) {
        if(checkBoxHint.isChecked()) {
            imageViewHint.setVisibility(View.VISIBLE);
        }
        else {
            imageViewHint.setVisibility(View.INVISIBLE);
        }

    }

}