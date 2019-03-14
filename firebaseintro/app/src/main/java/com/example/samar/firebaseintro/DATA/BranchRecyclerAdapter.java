package com.example.samar.firebaseintro.DATA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samar.firebaseintro.ACTIVITIES.SubjectActivity;
import com.example.samar.firebaseintro.MODEL.Branch;
import com.example.samar.firebaseintro.R;

import java.util.List;

public class BranchRecyclerAdapter extends RecyclerView.Adapter<BranchRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Branch> branchList;


    public BranchRecyclerAdapter(Context context, List<Branch> branchList) {
        this.context = context;
        this.branchList = branchList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.branch_card,viewGroup,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Branch  branch = branchList.get(position);
        String branchCode = null;

        viewHolder.branch_name.setText(branch.getBranchName());

        branchCode=branch.getBranchCode();
        //Log.d("branchCode",branchCode);
        final String finalBranchCode = branchCode;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(), finalBranchCode,Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(context, SubjectActivity.class);
//                intent.putExtra("BranchCode",finalBranchCode);
//                context.startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString("BranchCode", finalBranchCode);
                SubjectActivity sm = new SubjectActivity();
                sm.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, sm).commit();


            }
        });                                                                                             



    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }



    //Viewholder Class

    public class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView branch_name;
        //public ImageView arrow;
        String  branchCode;

        public ViewHolder(final View view,Context ctx) {
            super(view);

            context=ctx;

            branch_name=(TextView)view.findViewById(R.id.spinner_text);

        }



    }
}
