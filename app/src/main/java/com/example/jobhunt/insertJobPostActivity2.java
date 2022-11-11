package com.example.jobhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobhunt.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class insertJobPostActivity2 extends AppCompatActivity {
    private Toolbar insert_job_toolbar;
    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;
    private  EditText skillsRequiredText;
    private  EditText salaryEditText;
    private Button post_job_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPost;
    private DatabaseReference mPublicDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post2);
        jobTitleEditText=findViewById(R.id.jobTitleEditText);
        jobDescriptionEditText=findViewById(R.id.jobDescriptionEditText);
        skillsRequiredText=findViewById(R.id.skillsRequiredEditText);
        salaryEditText=findViewById(R.id.salaryEditText);
        insert_job_toolbar=findViewById(R.id.insert_job_post_toolbar);
        mAuth=FirebaseAuth.getInstance();
        mPublicDatabase= FirebaseDatabase.getInstance().getReference().child("Public Database");
        FirebaseUser mUser= mAuth.getCurrentUser();
        String uId=mUser.getUid();
        mJobPost= FirebaseDatabase.getInstance().getReference().child("Job post").child(uId);

        post_job_btn=findViewById(R.id.post_job_btn);
        setSupportActionBar(insert_job_toolbar);
        getSupportActionBar().setTitle("Post Job");
        InsertJob();
    }

    private void InsertJob() {
        jobTitleEditText=findViewById(R.id.jobTitleEditText);
        jobDescriptionEditText=findViewById(R.id.jobDescriptionEditText);
        skillsRequiredText=findViewById(R.id.skillsRequiredEditText);
        salaryEditText=findViewById(R.id.salaryEditText);
        post_job_btn=findViewById(R.id.post_job_btn);
        post_job_btn.setOnClickListener(v->{

            String jobDescription9=jobDescriptionEditText.getText().toString().trim();
            String skillsRequired9=skillsRequiredText.getText().toString().trim();
            String salary9=salaryEditText.getText().toString().trim();
            String jobTitle9=jobTitleEditText.getText().toString().trim();
            if(TextUtils.isEmpty(jobTitle9)){
                jobDescriptionEditText.setError("Required Field ...");
                return;
            }
            if(TextUtils.isEmpty(jobDescription9)){
                jobDescriptionEditText.setError("Required Field ...");
                return;
            }
            if(TextUtils.isEmpty(skillsRequired9)){
                skillsRequiredText.setError("Required Field ...");
                return;
            }
            if(TextUtils.isEmpty(salary9)){
                salaryEditText.setError("Required Field ...");
                return;
            }
            String id=mJobPost.push().getKey();
            String date= DateFormat.getDateInstance().format(new Date());
            Data data=new Data(date,id,jobDescription9,jobTitle9,salary9,skillsRequired9);
            mJobPost.child(id).setValue(data);

            mPublicDatabase.child(id).setValue(data);
            Toast.makeText(insertJobPostActivity2.this,"Successful",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,allJobsBy1Employee.class));
        });
    }
    }

    /*<activity
            android:name=".postJobActivity"
                    android:exported="false">
<meta-data
        android:name="android.app.lib_name"
        android:value="" />
</activity>
*/
