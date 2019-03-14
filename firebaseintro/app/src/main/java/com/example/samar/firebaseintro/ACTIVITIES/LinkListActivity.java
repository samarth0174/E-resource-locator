package com.example.samar.firebaseintro.ACTIVITIES;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samar.firebaseintro.DATA.LinkRecyclerAdapter;
import com.example.samar.firebaseintro.DATA.SubjectRecyclerAdapter;
import com.example.samar.firebaseintro.MODEL.Link;
import com.example.samar.firebaseintro.MODEL.Subject;
import com.example.samar.firebaseintro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class LinkListActivity extends Fragment {

    private FirebaseDatabase mdatabase;
    private DatabaseReference mdatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    private RecyclerView recyclerView;
    private List<Link> linklist;
    String subCode;
    public String mode;
    private LinkRecyclerAdapter linkRecyclerAdapter;



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rootview=inflater.inflate(R.layout.activity_link_list, container, false);
        Bundle bundle = this.getArguments();
        subCode = bundle.getString("LinkCode");
        mode=bundle.getString("Mode");
        return Rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Select Resource");


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //Retrieve an instance of your database using getInstance()
        //and reference the location you want to write to.
        mdatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mdatabase.getReference().child(mode + subCode);
        mdatabaseReference.keepSynced(true);



        linklist = new ArrayList<>();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.linkrecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

        @Override
    public void onStart() {
        super.onStart();
        mdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


               Link link = dataSnapshot.getValue(Link.class);
                //Log.d("child key", dataSnapshot.getKey());
                linklist.add(link);
                linkRecyclerAdapter = new LinkRecyclerAdapter(getActivity(), linklist);
                recyclerView.setAdapter(linkRecyclerAdapter);
                linkRecyclerAdapter.notifyDataSetChanged();


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
        // Toast.makeText(SubjectActivity.this, "pause", Toast.LENGTH_LONG).show();
        clearData();

    }

    public void clearData() {
        linklist.clear(); // clear list
        linkRecyclerAdapter.notifyDataSetChanged(); // let your adapter know about the changes and reload view.
    }

}
