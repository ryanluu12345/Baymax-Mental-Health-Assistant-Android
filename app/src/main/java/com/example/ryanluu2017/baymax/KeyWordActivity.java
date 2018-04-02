package com.example.ryanluu2017.baymax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KeyWordActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView mKeyWordLv;

    //Firebase variables
    FirebaseAuth mAuth;
    DatabaseReference mUsersRef;

    //ArrayList for the strings
    ArrayList<String> keyWordList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_notifications:
                    mTextMessage.setText("Stress Words");
                    return true;
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Intent homeIntent= new Intent(KeyWordActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent dashIntent= new Intent(KeyWordActivity.this,DashboardActivity.class);
                    startActivity(dashIntent);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_word);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View view = navigation.findViewById(R.id.navigation_notifications);
        view.performClick();

        initializeUI();
        initializeFb();
        loadDataFromFb();
    }

    private void loadDataFromFb(){

        mUsersRef.child(mAuth.getUid()).child("vocabulary").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                keyWordList=new ArrayList<String>();
                for (DataSnapshot word:dataSnapshot.getChildren()){

                    String listItem="";
                    listItem+=word.getKey();
                    listItem+="; Frequency: ";
                    listItem+=word.child("frequency").getValue();
                    listItem+="; Type:";
                    listItem+=word.child("0").getValue();
                    keyWordList.add(listItem);
                }

                setListViewAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setListViewAdapter(){

        ArrayAdapter myAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,keyWordList);
        mKeyWordLv.setAdapter(myAdapter);

    }

    private void initializeFb(){

        mAuth=FirebaseAuth.getInstance();
        //Gets the database reference to the user
        mUsersRef = FirebaseDatabase.getInstance().getReference("users");

    }

    //Loads from UI
    private void initializeUI(){

        mKeyWordLv=(ListView) findViewById(R.id.key_word_lv);
        keyWordList=new ArrayList<String>();

    }

}
