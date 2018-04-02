package com.example.ryanluu2017.baymax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//TODO clean up array list after
public class HomeActivity extends AppCompatActivity {

    //UI Widgets
    private TextView mTextMessage;

    //Firebase variables
    FirebaseAuth mAuth;
    DatabaseReference mUsersRef;

    //List of Users
    ArrayList<Responses> responsesArrayList;

    //RecyclerView variables
    private RecyclerView mBrowseRv;
    private LinearLayoutManager mBrowseLlm;
    private BrowseResponseSentimentRvAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent intent=new Intent(HomeActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText("Stress Words");

                    Intent keyWordIntent= new Intent(HomeActivity.this,KeyWordActivity.class);
                    startActivity(keyWordIntent);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Instantiates the new arraylist of responses
        responsesArrayList = new ArrayList<Responses>();

        //Gets current authenticated user
        mAuth = FirebaseAuth.getInstance();

        //Gets the database reference to the user
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");

        //Loads data from firebase
        loadDataFromFirebase();

        //Sets up recycler view
        initializeRecyclerView();

        Log.i("here","here");
    }

    private void loadDataFromFirebase() {

        mUsersRef.child(mAuth.getUid()).child("response_history").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                responsesArrayList = new ArrayList<Responses>();

                for(DataSnapshot responses:dataSnapshot.getChildren()){

                    //Creates a response and then sets the id to the object
                    Responses resp=new Responses();
                    resp.setItemId(responses.getKey().toString());


                    for(DataSnapshot information:responses.getChildren()){

                        Log.i("yo",information.getKey().toString());
                        if (information.getKey().toString().equals("response")){
                                resp.setResponseText(information.getValue().toString());
                        } else if (information.getKey().toString().equals("sentiment")){
                            resp.setSentiment(information.getValue().toString());
                        } else if (information.getKey().toString().equals("timestamp")){
                            resp.setTimestamp(information.getValue().toString());
                        }



                    }

                    //Pushes object onto array list
                    responsesArrayList.add(resp);

                    //Initializes the adapter
                    initializeAdapter();

                }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
        }
        );

    }

    private void initializeRecyclerView() {
        //Links the recycler view to frontend
        mBrowseRv = (RecyclerView) findViewById(R.id.browse_response_sentiment_rv);
        mBrowseRv.setHasFixedSize(true);

        //Creates the layout manager
        mBrowseLlm = new LinearLayoutManager(this);
        mBrowseRv.setLayoutManager(mBrowseLlm);
    }

    //Initializes adapter
    private void initializeAdapter(){
        adapter=new BrowseResponseSentimentRvAdapter(responsesArrayList);
        mBrowseRv.setAdapter(adapter);
    }

}
