package com.example.nibhr.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    String sub_val, dept;
    EditText subject;
    TextView searchbydept;
    Button searchbydeptbut;
    ListView listview;
    List<filesdb> filesdbList;
    String entered_dept;
    private DrawerLayout mDrawerLayout;
    DatabaseReference filedatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        entered_dept = (String) getIntent().getSerializableExtra("String");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        filedatabase = FirebaseDatabase.getInstance().getReference("Files");
        filesdbList = new ArrayList<>();
        listview = (ListView)findViewById(R.id.listview);
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
                                Intent i = new Intent(SearchActivity.this, HomeActivity.class);
                                startActivity(i);
                                break;
                            case R.id.dept:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent x = new Intent(SearchActivity.this, DeptActivity.class);
                                startActivity(x);
                                break;
                            case R.id.doc:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent k = new Intent(SearchActivity.this, DocActivity.class);
                                startActivity(k);
                                break;
                            case R.id.user:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent l = new Intent(SearchActivity.this, UserActivity.class);
                                startActivity(l);
                                break;
                            case R.id.pass:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent g = new Intent(SearchActivity.this, PassActivity.class);
                                startActivity(g);
                                break;
                            case R.id.logout:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent j = new Intent(SearchActivity.this, MainActivity.class);
                                startActivity(j);

                                break;
                        }





                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });





        Spinner dropdown4 = findViewById(R.id.spinner4);
        final String[] items4 = new String[]{"Select", "Accounts", "Admin & PR", "Asset sales", "Company Secretary", "Costing", "Cotton" ,
                "DataCentre", "Finance", "General", "Hindi", "HR", "IT", "Internal Audit", "Legal", "Marketing", "Materials", "Production",
                "Sale Purchase", "Stores", "Technical", "Vigilance"};
        searchbydept = (TextView)findViewById(R.id.searchbydept);
        searchbydeptbut = (Button)findViewById(R.id.searchbydeptbut);
        if(!TextUtils.isEmpty(entered_dept)){
            dropdown4.setVisibility(View.GONE);
            searchbydeptbut.setVisibility(View.GONE);
            searchbydept.setVisibility(View.GONE);
            dept = entered_dept;
        }
        else{
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
            dropdown4.setAdapter(adapter3);
        }
        dropdown4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept=items4[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filesdb filesdbs = filesdbList.get(position);
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse(filesdbs.getUrl()));
                Intent intent = new Intent(getApplicationContext(), DownloadActivity.class);
                intent.putExtra("filesdb", (Serializable) filesdbList.get(position));
                startActivity(intent);
            }
        });
        subject=(EditText)findViewById(R.id.subject);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }
    public void searchbydept(View view){
        filesdbList.clear();
        listview.setAdapter(null);
        sub_val = subject.getText().toString();
        filedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    filesdb filesdbs = postSnapshot.getValue(filesdb.class);
                    if (filesdbs.getDepartment().equals(dept)) {
                        filesdbList.add(filesdbs);
                    }
                }
                    String[] files = new String[filesdbList.size()];
                    //Toast.makeText(getApplicationContext(), "" + files.length, Toast.LENGTH_LONG).show();
                    for (int i = 0; i < files.length; i++) {
                        files[i] = filesdbList.get(i).getSubject();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, files);
                    listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void searchbysub(View view){
        filesdbList.clear();
        listview.setAdapter(null);
        sub_val = subject.getText().toString();
        filedatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    filesdb filesdbs = postSnapshot.getValue(filesdb.class);
                    if (filesdbs.getSubject().toLowerCase().replaceAll("\\s+","").contains(sub_val.toLowerCase().replaceAll("\\s+",""))) {
                        if(TextUtils.isEmpty(entered_dept)) {
                            filesdbList.add(filesdbs);
                        }
                        else{
                            if(filesdbs.getDepartment().equals(entered_dept)){
                                filesdbList.add(filesdbs);
                            }
                        }
                    }
                }
                String[] files = new String[filesdbList.size()];
                String[] ulby = new String[filesdbList.size()];
                //Toast.makeText(getApplicationContext(), "" + files.length, Toast.LENGTH_LONG).show();
                for(int i=0;i<files.length;i++){
                    files[i] = filesdbList.get(i).getSubject();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, files);
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
}
