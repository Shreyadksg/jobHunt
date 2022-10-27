package com.example.jobhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class sign_up_employee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employee);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView btnToLoginIn=findViewById(R.id.textViewLogin);
        btnToLoginIn.setOnClickListener(v->{
            Intent in1 =new Intent(this,employee_login.class);
            startActivity(in1);
        });
    }

}