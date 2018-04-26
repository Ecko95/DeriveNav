package com.example.joshua.derivenav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joshua.derivenav.com.joshua.api.model.Trip;
import com.example.joshua.derivenav.com.joshua.api.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;

    @BindView(R.id.user_info_lv) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//      //enables data persistence using cache
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
//        scoresRef.keepSynced(true);

        //initialise Firebase DB
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        ButterKnife.bind(this);

//        mRef = FirebaseDatabase
//                .getInstance()
//                .getReference("Trips")
//                .child(userID);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            User userInfo = new User();
            userInfo.setName(ds.child(userID).getValue(User.class).getName()); //sets User name
            userInfo.setEmail(ds.child(userID).getValue(User.class).getEmail()); //sets User name

            Log.d(TAG,"Data : name " + userInfo.getName());
            Log.d(TAG,"Data : email " + userInfo.getEmail());

            ArrayList<String> arrayList = new ArrayList<>();
            //adds data to arrayList Adapter
            arrayList.add(userInfo.getName());
            arrayList.add(userInfo.getEmail());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
            mListView.setAdapter(adapter);

        }
    }
}
