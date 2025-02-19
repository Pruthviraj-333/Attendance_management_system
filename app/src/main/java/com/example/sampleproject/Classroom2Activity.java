package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Classroom2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom2);

        Intent intent = getIntent();
        String room=intent.getStringExtra("room");
        Toast.makeText(this, ""+room, Toast.LENGTH_SHORT).show();
    }
}