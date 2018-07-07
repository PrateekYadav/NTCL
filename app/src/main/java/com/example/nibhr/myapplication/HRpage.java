package com.example.nibhr.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HRpage extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_hr);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        switch (id) {
                            case R.id.home:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent r = new Intent(HRpage.this, HomeActivity.class);
                                startActivity(r);
                                break;
                            case R.id.HR:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent i = new Intent(HRpage.this, HRpage.class);
                                startActivity(i);
                                break;
                            case R.id.mark:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent x = new Intent(HRpage.this, Mpage.class);
                                startActivity(x);
                                break;
                            case R.id.tech:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent k = new Intent(HRpage.this, Tpage.class);
                                startActivity(k);
                                break;
                            case R.id.fin:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent l = new Intent(HRpage.this, Fpage.class);
                                startActivity(l);
                                break;
                            case R.id.logout:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent j = new Intent(HRpage.this, MainActivity.class);
                                startActivity(j);

                                break;

                        }





                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void fup(View view){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        database.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                completeinfo completeinfos = dataSnapshot.getValue(completeinfo.class);
                int power;
                power = completeinfos.getPower();
                if (power == 1) {
                    Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                    i.putExtra("String", "HR");
                    startActivity(i);
                } else if (power == 0) {
                    Toast.makeText(getApplicationContext(), "You do not have the privileges to perform this action", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
            }
        });
    }

    public void fview(View view){
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("String", "HR");
        startActivity(i);
    }
}
