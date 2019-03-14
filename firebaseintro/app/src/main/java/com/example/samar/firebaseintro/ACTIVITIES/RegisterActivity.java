package com.example.samar.firebaseintro.ACTIVITIES;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samar.firebaseintro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private static final String TAG = "RegisterActivity";
    private Button register;
    private TextView alUser;
    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name=(EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.emailid1);
        password = (EditText) findViewById(R.id.password1);
        register = (Button) findViewById(R.id.registerit);
        alUser = (TextView) findViewById(R.id.aluser);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    Log.d(TAG, "user Signed in");
                } else {
                    Log.d(TAG, "user Signed Out");
                }

            }
        };




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String emailString = email.getText().toString();
                String pwd = password.getText().toString();


                if (!emailString.equals("") && !pwd.equals("")) {
                    mAuth.createUserWithEmailAndPassword(emailString, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Failed to Create account", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Account Successfully Created", Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }
                else  if(emailString.isEmpty()||pwd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Credentials", Toast.LENGTH_LONG).show();
                }


            }
        });

















        alUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
