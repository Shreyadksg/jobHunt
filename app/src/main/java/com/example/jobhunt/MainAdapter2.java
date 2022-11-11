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

public class MainAdapter2 extends FirebaseRecyclerAdapter<Data,MainAdapter2.MyViewHolder2> {
    public MainAdapter2(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter2.MyViewHolder2 holder, int position, @NonNull Data model) {
        holder.data.setText(model.getData());
        holder.jobDescription.setText(model.getJobDescription().trim());
        holder.jobTitle.setText(model.getJobTitle());
        holder.salary.setText(model.getSalary());
        holder.skillsRequired.setText(model.getSkillsRequired());
    }

    @NonNull
    @Override
    public MainAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new MyViewHolder2(view);
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder{
        TextView data, jobDescription, jobTitle, salary, skillsRequired;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            data=(TextView) itemView.findViewById(R.id.dataText);
            jobDescription=(TextView) itemView.findViewById(R.id.jobDescriptionText);
            jobTitle=(TextView) itemView.findViewById(R.id.jobTitleText);
            salary=(TextView) itemView.findViewById(R.id.salaryText);
            skillsRequired=(TextView) itemView.findViewById(R.id.skillsRequiredText);
        }

    }
}
