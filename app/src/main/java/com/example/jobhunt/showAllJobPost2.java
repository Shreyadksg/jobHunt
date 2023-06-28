package com.example.jobhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class showAllJobPost2 extends AppCompatActivity {
    private Toolbar show_all_jobs_toolbar;
    private RecyclerView recycler_view_all_jobs;
    MainAdapter adapter2;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_job_post2);
        show_all_jobs_toolbar=findViewById(R.id.show_all_jobs_toolbar);
        setSupportActionBar(show_all_jobs_toolbar);
        getSupportActionBar().setTitle("All Job Posts");
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        DatabaseReference Refer2=FirebaseDatabase.getInstance().getReference().child("Public Database");
        recycler_view_all_jobs=findViewById(R.id.recycler_view_all_jobs);
        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(Refer2,Data.class )
                        .build();
        //adapter2=new MainAdapter(options, recyclerViewInterface);
        //recycler_view_all_jobs.setLayoutManager(new LinearLayoutManager(this));
        //recycler_view_all_jobs.setAdapter(adapter2);
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