package com.example.jobhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ApplicantsAdapter extends RecyclerView.Adapter<ApplicantsAdapter.MyViewHolder> {
    private List<Data.JobApplication> userList;
    public ApplicantsAdapter(@NonNull List<Data.JobApplication> userList) {
        this.userList = userList;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data.JobApplication applicant=userList.get(position);
        holder.applicantName.setText(applicant.getName());
        holder.applicantEmail.setText(applicant.getEmail());
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicationuser, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    //bind those textvalues with provided object model

//creates a view (item) and get its textvalues
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView applicantName,applicantEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantEmail=itemView.findViewById(R.id.applicantEmail);
            applicantName=itemView.findViewById(R.id.applicantName);

        }
    }
}
