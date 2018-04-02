package com.example.ryanluu2017.baymax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mPositiveCountTv;
    private TextView mNegativeCountTv;
    private TextView mPositivePercentTv;
    private ProgressBar mProgressBar;

    //Firebase variables
    FirebaseAuth mAuth;
    DatabaseReference mUsersRef;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Intent homeIntent= new Intent(DashboardActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initializes ui
        initializeUI();

        //Initializes firebase variables
        initializeFb();

        //Load data from DB
        loadDataFromDb();
    }

    private void initializeUI(){

        mPositiveCountTv=(TextView) findViewById(R.id.positive_count_tv);
        mNegativeCountTv=(TextView) findViewById(R.id.negative_count_tv);
        mPositivePercentTv=(TextView) findViewById(R.id.positive_percentage_tv);
        mProgressBar=(ProgressBar) findViewById(R.id.determinateBar);

    }

    private void loadDataFromDb(){
        mUsersRef.child(mAuth.getUid()).child("response_history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int positive_count=0;
                int negative_count=0;
                int total_count=0;
                
                for(DataSnapshot responses:dataSnapshot.getChildren()){

                        for (DataSnapshot info:responses.getChildren()){

                            if (info.getKey().toString().equals("sentiment")){

                                if(info.getValue().toString().equals("Positive") || info.getValue().toString().equals("Strongly Positive") || info.getValue().toString().equals("Mildly Positive")){

                                    positive_count+=1;

                                } else{

                                    negative_count+=1;

                                }

                            }

                        }


                    }

                    
                    total_count=positive_count+negative_count;
                    int percentage=(int) 100*positive_count/total_count;
                    mNegativeCountTv.setText(String.valueOf(negative_count)+" counts");
                    mPositiveCountTv.setText(String.valueOf(positive_count)+" counts");
                    mProgressBar.setProgress(((int) (percentage)));
                    mPositivePercentTv.setText(String.valueOf((int)percentage)+" percent positive");
                    Log.i("pos count", String.valueOf(positive_count));
                    Log.i("neg count",String.valueOf(negative_count));
                    Log.i("percent",String.valueOf(percentage));
                    Log.i("total",String.valueOf(total_count));

                    if (percentage>80){
                        Toast.makeText(getApplicationContext(),"Great job for staying positive",Toast.LENGTH_SHORT).show();
                    } else if (percentage>60){
                        Toast.makeText(getApplicationContext(),"You have been positive lately",Toast.LENGTH_SHORT).show();

                    } else if (percentage>40){
                        Toast.makeText(getApplicationContext(),"You seem to be a bit down. How are you?",Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(getApplicationContext(),"Please let me know if you need help. I am worried.",Toast.LENGTH_SHORT).show();

                    }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                }
                }
        );


    }

    private void initializeFb(){

        mAuth=FirebaseAuth.getInstance();
        //Gets the database reference to the user
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");

    }

}
