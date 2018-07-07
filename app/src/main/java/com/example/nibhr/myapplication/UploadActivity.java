package com.example.nibhr.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.net.Uri;
import android.Manifest;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    String type_val, office_val, dept_val, subject_doc, uploader_name, url, entered_dept;
    EditText subject;
    Spinner type, office, dept;
    TextView selectdept;
    private FirebaseAuth firebaseAuth;
    DatabaseReference userdatabase;
    DatabaseReference filesdatabase;
    ProgressBar progressBar;
    StorageReference storageref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entered_dept = (String) getIntent().getSerializableExtra("String");
        setContentView(R.layout.activity_up);
        firebaseAuth = FirebaseAuth.getInstance();
        userdatabase = FirebaseDatabase.getInstance().getReference("Users");
        storageref = FirebaseStorage.getInstance().getReference();
        filesdatabase = FirebaseDatabase.getInstance().getReference("Files");
        mDrawerLayout = findViewById(R.id.drawer_layout);
        progressBar = findViewById(R.id.progressbar);
        selectdept = (TextView)findViewById(R.id.selectdept);
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
                                Intent i = new Intent(UploadActivity.this, HomeActivity.class);
                                startActivity(i);
                                break;
                            case R.id.dept:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent x = new Intent(UploadActivity.this, DeptActivity.class);
                                startActivity(x);
                                break;
                            case R.id.doc:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent k = new Intent(UploadActivity.this, DocActivity.class);
                                startActivity(k);
                                break;
                            case R.id.user:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent l = new Intent(UploadActivity.this, UserActivity.class);
                                startActivity(l);
                                break;
                            case R.id.pass:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent g = new Intent(UploadActivity.this, PassActivity.class);
                                startActivity(g);
                                break;
                            case R.id.logout:
                                // set item as selected to persist highlight
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                Intent j = new Intent(UploadActivity.this, MainActivity.class);
                                startActivity(j);

                                break;
                        }





                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        Spinner dropdown = findViewById(R.id.spinner1);

        final String[] items = new String[]{"Select", "Comparison Chart", "External Circular", "External Note", "External Order", "Internal Circular" ,
                                      "Internal Note", "Internal Order", "Note", "Purchase Order", "Tender", "Work Order"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);


        Spinner dropdown2 = findViewById(R.id.spinner2);

        final String[] items2 = new String[]{"Select", "HO", "SRO", "WRO"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

        dropdown2.setAdapter(adapter2);

        Spinner dropdown3 = findViewById(R.id.spinner3);

        final String[] items3 = new String[]{"Select", "Accounts", "Admin & PR", "Asset sales", "Company Secretary", "Costing", "Cotton" ,
                "DataCentre", "Finance", "General", "Hindi", "HR", "IT", "Internal Audit", "Legal", "Marketing", "Materials", "Production",
                "Sale Purchase", "Stores", "Technical", "Vigilance"};

        if(!TextUtils.isEmpty(entered_dept)){
            dropdown3.setVisibility(View.GONE);
            selectdept.setVisibility(View.GONE);
            dept_val = entered_dept;
        }
        else{
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
            dropdown3.setAdapter(adapter3);
        }
        type=(Spinner)findViewById(R.id.spinner1);
        office=(Spinner)findViewById(R.id.spinner2);
        dept=(Spinner)findViewById(R.id.spinner3);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_val = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                office_val = items2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept_val = items3[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        subject=(EditText)findViewById(R.id.subject);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

    }
    private void addfilesinfo(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        userdatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                completeinfo infofile = dataSnapshot.getValue(completeinfo.class);
                uploader_name = infofile.getName();
                String date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                filesdb filesdbs = new filesdb(subject_doc, type_val, office_val, dept_val, uploader_name, url, date);
                String id = filesdatabase.push().getKey();
                filesdatabase.child(id).setValue(filesdbs);
                //Toast.makeText(getApplicationContext(), "File uploaded", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "File not uploaded", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getfile(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            //Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            //startActivity(intent);
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
        subject_doc = subject.getText().toString();
        if(TextUtils.isEmpty(subject_doc)){
            Toast.makeText(getApplicationContext(),"Please enter Subject",Toast.LENGTH_LONG).show();
            return;
        }

        if(type_val.equals("Select")){
            Toast.makeText(getApplicationContext(),"Please select type",Toast.LENGTH_LONG).show();
            return;
        }
        if(office_val.equals("Select")){
            Toast.makeText(getApplicationContext(),"Please select office",Toast.LENGTH_LONG).show();
            return;
        }
        if(dept_val.equals("Select")){
            Toast.makeText(getApplicationContext(),"Please select department",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2342);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2342&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            if(data.getData()!=null){
                uploadfile(data.getData());
            }
            else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void uploadfile(Uri data){
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sref = storageref.child("Uploads/" + subject_doc + ".pdf");
        sref.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        url = taskSnapshot.getDownloadUrl().toString();
                        addfilesinfo();
                        Toast.makeText(getApplicationContext(), "File Upload Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred());
                            progressBar.setProgress((int)progress);
                    }
                });
    }
    public void uploadbutton(View view){
        getfile();
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
