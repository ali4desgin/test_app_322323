package com.example.ali4desgin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityStarter extends AppCompatActivity {

    Button loginBtn,RegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_starter);
        RegisterBtn = (Button) findViewById(R.id.registerBtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityStarter.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityStarter.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
