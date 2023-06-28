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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class BFragment extends Fragment implements RecyclerViewInterface ,UsernameCallback{
    MainAdapter2 adapter2;
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
        adapter2=new MainAdapter2(options,this);
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
    public void getusername(UsernameCallback callback){
        DatabaseReference usersRef=FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username=snapshot.child("ename").getValue(String.class);

                System.out.println(username);
                Toast.makeText(getContext(),username,Toast.LENGTH_SHORT).show();
                callback.onUsernameReceived(username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    DatabaseReference jobsref;
    String username;
    @Override
    public void onItemClick(int position,String jobname,String jobdate) {

        jobsref=FirebaseDatabase.getInstance().getReference().child("Public Database");
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

      //  String username="";
        //async function like to wait till we get our username
        getusername(new UsernameCallback() {
            @Override
            public void onUsernameReceived(String username) {
                Data.JobApplication userApplication=new Data.JobApplication(username,email);
                String name=userApplication.getName();

                addUserApplicationToAllJobs(jobname,jobdate,userApplication);
            }
        });


    }

    private void addUserApplicationToAllJobs(String jobName, String jobDate, Data.JobApplication userApplication) {

        Query query = jobsref.orderByChild("jobTitle").equalTo(jobName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    Data job=childSnapshot.getValue(Data.class);
                    if(job.getData().equals(jobDate)){
                        String jobid=childSnapshot.getKey();//key is not uid
                        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        //uid is unique for each user so a unique post will be there for each user
                        DatabaseReference applicationref=jobsref.child(jobid).child("applications").child(uid);
                        applicationref.setValue(userApplication);
                        Toast.makeText(getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),success.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onUsernameReceived(String username) {

    }
}

