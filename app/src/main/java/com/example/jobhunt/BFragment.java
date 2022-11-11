package com.example.jobhunt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BFragment extends Fragment {
    Button showAllJobsEmployee;
    public BFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_b, container, false);
        showAllJobsEmployee=view.findViewById(R.id.showAllJobsEmployee);
        showAllJobsEmployee.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(),showAllJobPost2.class);
            startActivity(intent);
        });
        return view;
    }
}