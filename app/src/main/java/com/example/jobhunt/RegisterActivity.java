package com.example.jobhunt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    Button employer;
    Button employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        Button button=findViewById(R.id.button);


        //when btn click go to dialog box with layout popup.xml
        button.setOnClickListener(c->{
            createNewDialog();
        });

    }
    //code for dialog box/popup containing 2 buttons employee and employer
    public void createNewDialog(){
        dialogBuilder =new AlertDialog.Builder(this);
        final View choosePopUp = getLayoutInflater().inflate(R.layout.popup,null);
        employer=(Button)choosePopUp.findViewById(R.id.button2);
        employee=(Button)choosePopUp.findViewById(R.id.button3);
        dialogBuilder.setView(choosePopUp);
        dialog=dialogBuilder.create();
        dialog.show();
        employee.setOnClickListener(v->{
            Intent intent1=new Intent(this,employee_login.class);
            startActivity(intent1);
        });
        employer.setOnClickListener(v->{
            Intent intent=new Intent(this,employer_login.class);
            startActivity(intent);
        });
    }
}