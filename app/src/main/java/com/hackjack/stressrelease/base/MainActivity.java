package com.hackjack.stressrelease.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.absolutelysaurabh.stressrelease.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackjack.stressrelease.activity.login.LoginActivity;
import com.hackjack.stressrelease.fragments.ChatFragment;
import com.hackjack.stressrelease.fragments.FidgetFragment;
import com.hackjack.stressrelease.fragments.GroupsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  GroupsFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener, FidgetFragment.OnFragmentInteractionListener{

    protected Toolbar actionBar;
    protected DrawerLayout drawerLayout;
    BottomBar bottomBar;
    public static final String ANONYMOUS = "anonymous";
    public static String TAG = "Groups";

    public FloatingActionMenu fab_ask;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    View navHeader;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        final View f = findViewById(R.id.layout_fab_ask);
        fab_ask = f.findViewById(R.id.menu);

        FloatingActionButton fab_call = fab_ask.findViewById(R.id.menu_item2);
        fab_call.setColorNormal(R.color.colorBlue);
        fab_call.setColorRipple(R.color.colorBlue);
        fab_call.setColorPressed(R.color.colorBlue);
        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling intent
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("8130726590"));
//                startActivity(callIntent);
                Toast.makeText(getApplicationContext(), "Calling..", LENGTH_SHORT).show();

            }
        });

        FloatingActionButton fab_fidget = fab_ask.findViewById(R.id.menu_item);
        fab_fidget.setColorNormal(R.color.colorBlue);
        fab_fidget.setColorRipple(R.color.colorBlue);
        fab_fidget.setColorPressed(R.color.colorBlue);

        fab_fidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling intent

                Toast.makeText(getApplicationContext(), "Loading Spinner...", LENGTH_SHORT).show();

                TAG = "FidgetSpinner";

                Fragment fragment = new FidgetFragment();
                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, TAG);
                transaction.commit();

            }
        });


        nav = (NavigationView) findViewById(R.id.navigation);
        if (nav != null) {
            LinearLayout mParent = (LinearLayout) nav.getHeaderView(0);

            if (mParent != null) {
                // Set your values to the image and text view by declaring and setting as you need to here.

                SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);
                String photoUrl = prefs.getString("photo_url", null);
                String user_name = prefs.getString("name", "User");
                String user_email = prefs.getString("email", "showcube03@gmail.com");

                if (photoUrl != null) {
                    Log.e("Photo Url: ", photoUrl);

                    TextView userName = mParent.findViewById(R.id.user_name);
                    userName.setText(user_name);

                    TextView text_view_user_email = mParent.findViewById(R.id.header_user_email);
                    text_view_user_email.setText(user_email);

                    ImageView user_imageView = mParent.findViewById(R.id.avatar);

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_user_24dp);
                    requestOptions.error(R.drawable.ic_user_24dp);

                    Glide.with(this).load(photoUrl)
                            .apply(requestOptions).thumbnail(0.5f).into(user_imageView);

                }
            }
        }

        if (mFirebaseUser == null) {

            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        bottomBar = (BottomBar) findViewById(R.id.bottom_navigation);
        setUi();
    }

    public void setUi() {

        setActionBar();
        setDrawer();
        setBottomNavigation();

    }

    private void setActionBar() {
        actionBar = (Toolbar) findViewById(R.id.actionBar);
        actionBar.setTitle("Chat");
        setSupportActionBar(actionBar);
    }

    private void setDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, actionBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setBottomNavigation() {

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_chat:
                        actionBar.setTitle("Chat");
                        setFragmentChat();
                        break;
                    case R.id.tab_groups:
                        actionBar.setTitle("Groups");
                        setFragmentGroups();
                        break;
                }
            }
        });

    }

    public void setFragmentChat() {

        TAG = "Chat";

        Fragment fragment = new ChatFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.commit();

    }

    public void setFragmentGroups() {

        TAG = "Groups";

        Fragment fragment = new GroupsFragment();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, TAG);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

            mFirebaseAuth.signOut();
            //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_drawer_info){
        }else
        if(id == R.id.nav_drawer_share){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey,\n Look at the stress-release app on google playstore.");
            startActivity(Intent.createChooser(intent, "Share with"));

        }else
        if(id==R.id.nav_drawer_logout){

            mFirebaseAuth.signOut();
            //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mUsername = ANONYMOUS;
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
