package com.example.ryanluu2017.baymax;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Front end widgets
    private EditText mEmailEt;
    private EditText mPasswordEt;

    //Data stores
    private String email;
    private String password;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
        initializeFirebase();

    }

    private void initializeFirebase(){
        this.mAuth=FirebaseAuth.getInstance();
    }

    private void initializeUI(){

        mEmailEt=(EditText) findViewById(R.id.email_address_et);
        mPasswordEt=(EditText) findViewById(R.id.password_et);


    }

    //Creates new user and makes intent to next activity
    public void onMainSignInClick(View v){

        //Extracts user data
        extractDataFromUI();
        createUser(this.email,this.password);

        //Makes intent to next activity
        Intent homeIntent=new Intent(this,HomeActivity.class);
        startActivity(homeIntent);

    }

    //Helper function to create user and email
    private void createUser(String email, String password){


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();
                            Log.d("a", user.getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("a", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    //Helper function to extract data from UI
    private void extractDataFromUI(){
        this.email=mEmailEt.getText().toString();
        this.password=mPasswordEt.getText().toString();
        Log.i("email",this.email);
    }

}
