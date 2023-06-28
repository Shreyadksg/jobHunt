package com.example.jobhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhunt.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter2 extends FirebaseRecyclerAdapter<Data,MainAdapter2.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    public MainAdapter2(@NonNull FirebaseRecyclerOptions<Data> options,RecyclerViewInterface recyclerViewInterface) {
        super(options);
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    protected void onBindViewHolder(@NonNull MainAdapter2.MyViewHolder holder, int position, @NonNull Data model) {
        holder.data.setText(model.getData());
        holder.jobDescription.setText(model.getJobDescription().trim());
        holder.jobTitle.setText(model.getJobTitle());
        holder.salary.setText(model.getSalary());
        holder.skillsRequired.setText(model.getSkillsRequired());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new MyViewHolder(view);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView data, jobDescription, jobTitle, salary, skillsRequired;
        Button apply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data=(TextView) itemView.findViewById(R.id.dataText);
            jobDescription=(TextView) itemView.findViewById(R.id.jobDescriptionText);
            jobTitle=(TextView) itemView.findViewById(R.id.jobTitleText);
            salary=(TextView) itemView.findViewById(R.id.salaryText);
            skillsRequired=(TextView) itemView.findViewById(R.id.skillsRequiredText);
            apply=itemView.findViewById(R.id.appliers);
            apply.setText("Apply");
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos,jobTitle.getText().toString().trim(),data.getText().toString().trim());
                        }
                    }
                }
            });
        }

    }
}
