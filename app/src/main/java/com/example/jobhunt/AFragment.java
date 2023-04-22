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
import android.widget.Button;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AFragment extends Fragment {
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
        DatabaseReference refer= FirebaseDatabase.getInstance().getReference().child("Job post").child(mAuth.getUid());
        add_job_btn.setOnClickListener(v->{
            startActivity(new Intent(getContext(),insertJobPostActivity2.class));
        });

        recycler_view_fragment_a.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(refer,Data.class)
                        .build();
        adapter2=new MainAdapter(options);
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
}