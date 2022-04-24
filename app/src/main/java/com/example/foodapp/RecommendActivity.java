package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
public class RecommendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        findViewById(R.id.btn_GoTo).setOnClickListener(view->{
            startActivity(new Intent(RecommendActivity.this, SignInActivity.class));
        });
    }
}