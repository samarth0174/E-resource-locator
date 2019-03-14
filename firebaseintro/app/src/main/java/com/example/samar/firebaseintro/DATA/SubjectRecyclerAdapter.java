package com.example.samar.firebaseintro.DATA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samar.firebaseintro.ACTIVITIES.SubjectActivity;
import com.example.samar.firebaseintro.ACTIVITIES.TopicListActivity;
import com.example.samar.firebaseintro.MODEL.Subject;
import com.example.samar.firebaseintro.R;

import java.util.List;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Subject> subjectList;

    public SubjectRecyclerAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        this.subjectList = subjectList;
    }


    public SubjectRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.subject_card,viewGroup,false);

        return new SubjectRecyclerAdapter.ViewHolder(view,context);
    }



    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerAdapter.ViewHolder viewHolder, int i) {
        String subjectCode = null;

        Subject subject = subjectList.get(i);

        subjectCode=subject.getSubjectCode();
        viewHolder.subject_name.setText(subject.getSubjectName());

        final String finalSubjectCode = subjectCode;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), finalSubjectCode,Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putString("subjectCode", finalSubjectCode);
                TopicListActivity tm = new TopicListActivity();
                tm.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, tm).addToBackStack(null).commit();




            }
        });





    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject_name;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context=ctx;

            subject_name=(TextView)itemView.findViewById(R.id.spinner_text);

        }
    }




}
