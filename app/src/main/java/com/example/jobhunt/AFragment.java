package com.example.jobhunt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AFragment extends Fragment {

    Button postJobBtn;
    Button showAllJobsBtn;
    public AFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_a, container, false);
        postJobBtn=(Button) view.findViewById(R.id.postJobBtn);
        postJobBtn.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(), insertJobPostActivity2.class);
            startActivity(intent);
        });
        showAllJobsBtn=view.findViewById(R.id.showAllJobsBtn);
        showAllJobsBtn.setOnClickListener(v->{
            Intent intent=new Intent(getActivity(),showAllJobPost2.class);
            startActivity(intent);
        });
        return view;
    }
}