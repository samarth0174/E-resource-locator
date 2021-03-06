package com.example.samar.firebaseintro.ACTIVITIES;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samar.firebaseintro.DATA.BranchRecyclerAdapter;
import com.example.samar.firebaseintro.MODEL.Branch;
import com.example.samar.firebaseintro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BranchActivity extends Fragment {

    private FirebaseDatabase mdatabase;
    private DatabaseReference mdatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private RecyclerView recyclerView;
    private List<Branch> branchlist;
    private BranchRecyclerAdapter branchRecyclerAdapter;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_branch);
//
//
//        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();
//        //Retrieve an instance of your database using getInstance()
//        //and reference the location you want to write to.
//        mdatabase = FirebaseDatabase.getInstance();
//        mdatabaseReference = mdatabase.getReference().child("Branch");
//        mdatabaseReference.keepSynced(true);
//
//
//        branchlist = new ArrayList<>();
//
//        recyclerView = (RecyclerView) findViewById(R.id.branchrecycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rootview=inflater.inflate(R.layout.activity_branch, container, false);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //Retrieve an instance of your database using getInstance()
        //and reference the location you want to write to.
        mdatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mdatabase.getReference().child("Branch");
        mdatabaseReference.keepSynced(true);


        branchlist = new ArrayList<>();

        recyclerView = (RecyclerView)Rootview.findViewById(R.id.branchrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    return Rootview;
}


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Choose Branch");
    }

    @Override
    public void onStart() {
        super.onStart();
        mdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Branch branch = dataSnapshot.getValue(Branch.class);
               // Log.d("child key", dataSnapshot.getKey());
                branchlist.add(branch);

                branchRecyclerAdapter = new BranchRecyclerAdapter(getActivity(), branchlist);
                recyclerView.setAdapter(branchRecyclerAdapter);
                branchRecyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();
        clearData();

    }

    public void clearData() {
        branchlist.clear(); // clear list
        branchRecyclerAdapter.notifyDataSetChanged(); // let your adapter know about the changes and reload view.
    }


}
