package com.example.joshua.derivenav;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.iv_profilePic) ImageView iv_profilePic;
    @BindView(R.id.user_info_lv) ListView mListView;

    private static final String TAG = "Database Message: ";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;



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

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();

                ArrayList<String> arrayList = new ArrayList<>();
                //adds data to arrayList Adapter
                arrayList.add(name);
                arrayList.add(email);
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
                mListView.setAdapter(adapter);

                Picasso.with(this).load(photoUrl).into(iv_profilePic);


            }
            ;
        }

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                try{
//                    showData(dataSnapshot);
//                }catch (Exception e){
//                    Log.d(TAG,e.getMessage());
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG,databaseError.getMessage());
//            }
//        });

    }


//    private void showData(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            User userInfo = new User();
//            userInfo.setName(ds.child(userID).getValue(User.class).getName()); //sets User name
//            userInfo.setEmail(ds.child(userID).getValue(User.class).getEmail()); //sets User name
//            Log.d(TAG,"Data : name " + userInfo.getName());
//            Log.d(TAG,"Data : email " + userInfo.getEmail());
//
//            ArrayList<String> arrayList = new ArrayList<>();
//            //adds data to arrayList Adapter
//            arrayList.add(userInfo.getName());
//            arrayList.add(userInfo.getEmail());
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
//            mListView.setAdapter(adapter);
//
//        }
//    }
}
