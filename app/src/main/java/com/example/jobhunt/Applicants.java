package com.example.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Applicants extends AppCompatActivity {
    RecyclerView recyclerView;
    ApplicantsAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        String jobid=getIntent().getStringExtra("jobid");
        ArrayList<Data.JobApplication> applicantList=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Public Database").child(jobid).child("applications");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicantList.clear();
                for(DataSnapshot childsnapshot: snapshot.getChildren()){
                    String uid=childsnapshot.getKey();
                   String name= childsnapshot.child("name").getValue(String.class);
                   String email=childsnapshot.child("email").getValue(String.class);
                    Data.JobApplication user=new Data.JobApplication(name,email);
                    applicantList.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(this, applicantList.size(), Toast.LENGTH_SHORT).show();
        for(Data.JobApplication job: applicantList){
            System.out.println(job.getEmail());
            System.out.println(job.getName());
        }
       recyclerView=findViewById(R.id.recycler_applicants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // System.out.println(applicantList);
        adapter=new ApplicantsAdapter(applicantList);
        recyclerView.setAdapter(adapter);
    }

}