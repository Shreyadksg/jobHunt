package com.example.jobhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class employee_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView btn=findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(v->{
            Intent in=new Intent(this, sign_up_employee.class);
            startActivity(in);
        });
    }
}