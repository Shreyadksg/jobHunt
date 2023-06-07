package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileEmployee extends AppCompatActivity {

    EditText name,about,uni,resume,contact,image;
    Button submitprofileData;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_employee);
        name = findViewById(R.id.NameEditTextProfile);
        about = findViewById(R.id.AboutMeEditTxtProfile);
        uni = findViewById(R.id.UniversityEdtTxtProfile);
        resume = findViewById(R.id.ResumeEdtTxtProfile);
        contact = findViewById(R.id.ContactEdtTxtProfile);
        submitprofileData=findViewById(R.id.submit_profileData);


        submitprofileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                //getting all values
                String ename = name.getText().toString();
                String eabout = about.getText().toString();
                String euni = uni.getText().toString();
                String eresume = resume.getText().toString();
                String econtact = contact.getText().toString();
                String imagei="";
                userhelper helper = new userhelper(ename, eabout, euni, eresume, econtact,imagei);
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(helper);
                Intent intent =new Intent(profileEmployee.this,postJobActivity.class);
                startActivity(intent);
            }
        });

    }
}