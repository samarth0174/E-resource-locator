package com.example.samar.firebaseintro.ACTIVITIES;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samar.firebaseintro.DATA.SubjectRecyclerAdapter;

import com.example.samar.firebaseintro.MODEL.Subject;
import com.example.samar.firebaseintro.MODEL.Topic;
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
import java.util.Objects;

public class TopicListActivity extends Fragment {



    private FirebaseDatabase mdatabase;
    private DatabaseReference mdatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private Spinner topic_spinner;
    private TextView topic;
    private Button Links;
    private Button VideoLinks;
    String subCode;
    private Button materialbyprof;


    private  List<Topic> list_topic;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rootview=inflater.inflate(R.layout.activity_topic_list, container, false);

        Links=(Button)Rootview.findViewById(R.id.Links);
        VideoLinks=(Button)Rootview.findViewById(R.id.Video_Links);
        materialbyprof=(Button)Rootview.findViewById(R.id.prof_material);

        Bundle bundle = this.getArguments();
        subCode = bundle.getString("subjectCode");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //Retrieve an instance of your database using getInstance()
        //and reference the location you want to write to.
        mdatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mdatabase.getReference().child("Topic/" + subCode);
        mdatabaseReference.keepSynced(true);



        list_topic = new ArrayList<>();


        topic_spinner=(Spinner)Rootview.findViewById(R.id.spinner_topic);
        //topic=(TextView)findViewById(R.id.topic);

        Links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Topic info;
                if(!(topic_spinner.getSelectedItem() == null))
                {
                    info = (Topic) topic_spinner.getSelectedItem();
                    String sendlink=info.getTopiccode();
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode","Link/");
                    bundle.putString("LinkCode",sendlink);
                    LinkListActivity  lm= new LinkListActivity();
                    lm.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, lm).addToBackStack(null).commit();
                    Toast.makeText(getActivity(),sendlink,Toast.LENGTH_LONG).show();

                     }
            }
        });

        VideoLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Topic info;
                if(!(topic_spinner.getSelectedItem() == null))
                {
                    info = (Topic) topic_spinner.getSelectedItem();
                    String sendlink=info.getTopiccode();
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode","Videolink/");
                    bundle.putString("LinkCode",sendlink);
                    LinkListActivity  lm= new LinkListActivity();
                    lm.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, lm).addToBackStack(null).commit();
                    Toast.makeText(getActivity(),sendlink,Toast.LENGTH_LONG).show();
                }
            }
        });

        materialbyprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Topic info;
                if(!(topic_spinner.getSelectedItem() == null))
                {
                    info = (Topic) topic_spinner.getSelectedItem();
                    String sendlink=info.getTopiccode();
                    Bundle bundle = new Bundle();
                    bundle.putString("Mode","Downloadlink/");
                    bundle.putString("LinkCode",sendlink);
                    LinkListActivity  lm= new LinkListActivity();
                    lm.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, lm).addToBackStack(null).commit();
                    Toast.makeText(getActivity(),sendlink,Toast.LENGTH_LONG).show();
                }
            }
        });





        return Rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Choose Topic");
    }




    @Override
    public void onStart() {
        super.onStart();
        mdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Topic topic = dataSnapshot.getValue(Topic.class);
                list_topic.add(topic);

                ArrayAdapter<Topic> adapter =
                        new ArrayAdapter<Topic>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, list_topic);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );

                topic_spinner.setAdapter(adapter);




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
        list_topic.clear(); // clear list
      // let your adapter know about the changes and reload view.
    }








}
