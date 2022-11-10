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

public class MainAdapter extends FirebaseRecyclerAdapter<Data,MainAdapter.MyViewHolder>{

    public MainAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position, @NonNull Data model) {
        holder.data.setText(model.getData().trim());
        holder.jobDescription.setText(model.getJobDescription().trim());
        holder.jobTitle.setText(model.getJobTitle().trim());
        holder.salary.setText(model.getSalary().trim());
        holder.skillsRequired.setText(model.getSkillsRequired().trim());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new MyViewHolder(view);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView data,id, jobDescription, jobTitle, salary, skillsRequired;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data=(TextView) itemView.findViewById(R.id.dataText);
            jobDescription=(TextView) itemView.findViewById(R.id.jobDescriptionText);
            jobTitle=(TextView) itemView.findViewById(R.id.jobTitleText);
            salary=(TextView) itemView.findViewById(R.id.salaryText);
            skillsRequired=(TextView) itemView.findViewById(R.id.skillsRequiredText);
        }
    }
}

