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

public class DeptActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept);

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
                                Intent i = new Intent(DeptActivity.this, HomeActivity.class);
                                startActivity(i);
                                break;
                            case R.id.dept:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent x = new Intent(DeptActivity.this, DeptActivity.class);
                                startActivity(x);
                                break;
                            case R.id.doc:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent k = new Intent(DeptActivity.this, DocActivity.class);
                                startActivity(k);
                                break;
                            case R.id.user:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent l = new Intent(DeptActivity.this, UserActivity.class);
                                startActivity(l);
                                break;
                            case R.id.pass:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent g = new Intent(DeptActivity.this, PassActivity.class);
                                startActivity(g);
                                break;
                            case R.id.logout:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent j = new Intent(DeptActivity.this, MainActivity.class);
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

    public void HRbutton(View view){
        Intent i = new Intent(this, HRpage.class);
        startActivity(i);
    }
    public void Fbutton(View view){
        Intent i = new Intent(this, Fpage.class);
        startActivity(i);
    }
    public void Mbutton(View view){
        Intent i = new Intent(this, Mpage.class);
        startActivity(i);
    }
    public void Tbutton(View view){
        Intent i = new Intent(this, Tpage.class);
        startActivity(i);
    }
}
