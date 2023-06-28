package com.example.jobhunt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AFragment extends Fragment implements RecyclerViewInterface{
    private FloatingActionButton add_job_btn;
    private RecyclerView recycler_view_fragment_a;
    MainAdapter adapter2;
    private FirebaseAuth mAuth;

    public AFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view=inflater.inflate(R.layout.fragment_a,container,false);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_job_btn=view.findViewById(R.id.add_job_btn_Employer2);
        mAuth=FirebaseAuth.getInstance();
        recycler_view_fragment_a=(RecyclerView) view.findViewById(R.id.recycler_view_fragment_a);
        recycler_view_fragment_a.setItemAnimator(null);
        DatabaseReference refer= FirebaseDatabase.getInstance().getReference().child("Job post").child(mAuth.getUid());
        add_job_btn.setOnClickListener(v->{
            startActivity(new Intent(getContext(),insertJobPostActivity2.class));
        });

        recycler_view_fragment_a.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(refer,Data.class)
                        .build();
        adapter2=new MainAdapter(options,this);
        recycler_view_fragment_a.setAdapter(adapter2);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter2.stopListening();
    }

    @Override
    public void onItemClick(int pos, String jobname, String jobdate) {
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(getContext(), jobdate, Toast.LENGTH_SHORT).show();
        DatabaseReference appl=FirebaseDatabase.getInstance().getReference().child("Job post").child(uid);
        Query q=appl.orderByChild("jobTitle").equalTo(jobname);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childsnapshot: snapshot.getChildren()){
                    Data job=childsnapshot.getValue(Data.class);
                    if(job.getData().equals(jobdate)){
                        String jobid=childsnapshot.getKey();//id of job->not uid of user

                        Intent intent=new Intent(getContext(),Applicants.class);
                        intent.putExtra("jobid",jobid);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}