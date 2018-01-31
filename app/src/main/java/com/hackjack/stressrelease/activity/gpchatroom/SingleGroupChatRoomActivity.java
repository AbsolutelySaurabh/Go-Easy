package com.hackjack.stressrelease.activity.gpchatroom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.absolutelysaurabh.stressrelease.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackjack.stressrelease.activity.login.LoginActivity;
import com.hackjack.stressrelease.adapter.a.adapter.GroupsAdapter;
import com.hackjack.stressrelease.adapter.a.adapter.MessageAdapter;
import com.hackjack.stressrelease.base.MainActivity;
import com.hackjack.stressrelease.model.Message;
import com.hackjack.stressrelease.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by absolutelysaurabh on 30/1/18.
 */

public class SingleGroupChatRoomActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private ImageView mSendButton;

    public static MessageAdapter adapter;

    //add Firebase Database stuff
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private String mUsername;
    public static RecyclerView my_recycler_view;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    //to access the database
    private FirebaseDatabase mFirebaseDatabase;
    //the reference object is a class that references to the specific part of the database;
    private DatabaseReference mMessagesDatabaseReferences;
    //to trigger the message changes
    private ChildEventListener mChildEventListener;
    private GoogleApiClient mGoogleApiClient;
    private static String gp_id;

    public static ArrayList<Message> al_messages;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_group);

        mUsername = ANONYMOUS;

        gp_id = getIntent().getStringExtra("GROUP_ID").toString();
        String gp_name = getIntent().getStringExtra("GROUP_NAME").toString();

        // Adding Toolbar to Main screen
        toolbar = (Toolbar) findViewById(R.id.actionBar);
        toolbar.setTitle(gp_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
       // mMessageListView = (ListView) findViewById(R.id.messageListView);
        //mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (ImageView) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        final List<Message> friendlyMessages = new ArrayList<>();
        al_messages = new ArrayList<>();

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
//        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO: Fire an intent to show an image picker
//            }
//        });

        mFirebaseDatabase =  FirebaseDatabase.getInstance();
        mMessagesDatabaseReferences = mFirebaseDatabase.getReference().child("groups").child(gp_id).child("chat");

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
        final String user_name = prefs.getString("name", null);

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        userID = user.getUid();

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click

                Message friendlyMessage = new Message(mMessageEditText.getText().toString(), user_name, "NOTHING", userID);
                postToDatabase(friendlyMessage);
                mMessageEditText.setText("");
            }
        });

        my_recycler_view = findViewById(R.id.messages_recycler_view);

        if(al_messages.size()!=0) {

            my_recycler_view.setHasFixedSize(true);
            my_recycler_view.setNestedScrollingEnabled(true);
            //my_recycler_view.scrollToPosition(al_messages.size()-1);

            adapter = new MessageAdapter(this, al_messages);
            my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            my_recycler_view.setAdapter(adapter);

        }

        //to trigger the message changes;
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //called when a child (message) is added

//                Message f = dataSnapshot.getValue(Message.class);
//                al_messages.add(f);
//                adapter = new MessageAdapter();
//                adapter.notifyDataSetChanged();
//
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

//                Message f = dataSnapshot.getValue(Message.class);
//                al_messages.add(f);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                //when removed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //called when one of the messages change position in the list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //indicates some sort of error occured when trying to make some changes
                //typicalley called when we don;t hae permission to read the data

            }
        };

        //adding the child event listener to the database via the reference;
        mMessagesDatabaseReferences.addChildEventListener(mChildEventListener);

        getAllMesssages();

    }

    public void getAllMesssages(){

        mMessagesDatabaseReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                al_messages.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Message posts = dataSnapshot1.getValue(Message.class);
                    al_messages.add(posts);
                }

                if(al_messages.size()!=0) {

                    my_recycler_view.setHasFixedSize(true);
                    my_recycler_view.setNestedScrollingEnabled(true);

                    my_recycler_view.scrollToPosition(al_messages.size()-1);

                    adapter = new MessageAdapter(getApplicationContext(), al_messages);
                    my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    my_recycler_view.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void postToDatabase(Message message){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.child("groups").child(gp_id).child("chat").push().setValue(message);
    }

    @Override
    public boolean onSupportNavigateUp(){

        finish();
        return true;
    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }
}
