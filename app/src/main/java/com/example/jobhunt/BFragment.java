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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BFragment extends Fragment {
    MainAdapter adapter2;
    RecyclerView recycler_view_fragment_b;
    public BFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_b, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference refer2= FirebaseDatabase.getInstance().getReference().child("Public Database");
        recycler_view_fragment_b=view.findViewById(R.id.recycler_view_fragment_b);
        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(refer2,Data.class)
                        .build();
        adapter2=new MainAdapter(options);
        recycler_view_fragment_b.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view_fragment_b.setAdapter(adapter2);
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