package com.example.samar.firebaseintro.ACTIVITIES;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private EditText email;
    private EditText password;
    private Button login;
    private Button signout;
    private TextView newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginit);
       // signout = (Button) findViewById(R.id.signout);
        newUser = (TextView) findViewById(R.id.newuser);


        mAuth = FirebaseAuth.getInstance();
//
        //Retrieve an instance of your database using getInstance()
        //and reference the location you want to write to.
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");


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


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String emailString = email.getText().toString();
                String pwd = password.getText().toString();


                if (!emailString.equals("") && !pwd.equals("")) {
                    mAuth.signInWithEmailAndPassword(emailString, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            final ProgressDialog pd = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
                            pd.setMessage("Authenticating.....");
                            pd.show();
                            if (!task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Signed in ", Toast.LENGTH_LONG).show();


                                  startActivity(new Intent(MainActivity.this, Navigation.class));

                            }
                        }
                    });
                }
                else  if(emailString.isEmpty()||pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Credentials", Toast.LENGTH_LONG).show();
                }


            }
        });


        //signout
//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//
//                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_LONG).show();
//            }
//        });


        //Create new user
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

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

//
//    public void login() {
//        Log.d(TAG, "Login");
//
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }
//
//        login.setEnabled(false);
//
//        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
//
//
//        String email_enter = email.getText().toString();
//        String password_enter = password.getText().toString();
//
//        // TODO: Implement your own authentication logic here.
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
//    }
//
//
//    public boolean validate() {
//        boolean valid = true;
//
//        String email_enter = email.getText().toString();
//        String password_enter = password.getText().toString();
//
//        if (email_enter.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_enter).matches()) {
//            email.setError("enter a valid email address");
//            valid = false;
//        } else {
//            email.setError(null);
//        }
//
//        if (password_enter.isEmpty() || password_enter.length() < 4) {
//            password.setError(" greater than 4 char required");
//            valid = false;
//        } else {
//            password.setError(null);
//        }
//
//        return valid;
//    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }




}
