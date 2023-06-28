package com.example.jobhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class allJobsBy1Employee extends AppCompatActivity {
    private FloatingActionButton add_job_btn;
    private RecyclerView recycler_view_job_post_by_1_employer;
    MainAdapter adapter2;
    private FirebaseAuth mAuth;
    Toolbar toolbar_post_job_by_1_employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs_by1_employee);
        add_job_btn=findViewById(R.id.add_job_btn_Employer2);
        toolbar_post_job_by_1_employer=findViewById(R.id.toolbar_post_job_by_1_employer);
        setSupportActionBar(toolbar_post_job_by_1_employer);
        getSupportActionBar().setTitle("Your Posted Jobs ");
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        recycler_view_job_post_by_1_employer = (RecyclerView) findViewById(R.id.recycler_view_job_post_by_1_employer);
        DatabaseReference refer= FirebaseDatabase.getInstance().getReference().child("Job post").child(mAuth.getUid());
        add_job_btn.setOnClickListener(v -> {
            startActivity(new Intent(this, insertJobPostActivity2.class));
        });
        recycler_view_job_post_by_1_employer.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(refer, Data.class)
                        .build();
        //adapter2=new MainAdapter(options, recyclerViewInterface);
        //recycler_view_job_post_by_1_employer.setAdapter(adapter2);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
    }
    }