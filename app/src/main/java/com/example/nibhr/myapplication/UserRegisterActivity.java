package com.example.nibhr.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.EditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRegisterActivity extends AppCompatActivity{
    EditText email, pass, name, designation, phone;
    private FirebaseAuth firebaseAuth;
    DatabaseReference database;
    Spinner type, office, dept;
    String UID, type_val, office_val, dept_val;
    List<completeinfo> completeinfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");
        setContentView(R.layout.activity_user_register);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.newpass);
        name = (EditText)findViewById(R.id.name);
        designation = (EditText)findViewById(R.id.designation);
        phone = (EditText)findViewById(R.id.phone);
        completeinfos = new ArrayList<>();
        type = (Spinner)findViewById(R.id.type);
        final String[] typeitems = new String[]{"--Select Type--", "Admin", "User"};
        ArrayAdapter<String> typeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeitems);
        type.setAdapter(typeadapter);
        office = (Spinner)findViewById(R.id.office);
        final String[] officeitems = new String[]{"--Select Office--", "HO", "SRO", "WRO"};
        ArrayAdapter<String> officeadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, officeitems);
        office.setAdapter(officeadapter);
        dept = (Spinner)findViewById(R.id.dept);
        final String[] deptitems = new String[]{"--Select Department--", "Accounts", "Admin and PR", "Asset Sales", "Company Secretary", "Costing", "Finance", "Hindi", "Human Resources", "Information Technology", "Internal Audit", "Legal", "Marketing", "Materials", "Stores", "Technical", "Vigilance"};
        ArrayAdapter<String> deptadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deptitems);
        dept.setAdapter(deptadapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_val = typeitems[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type_val="no";
            }
        });
        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                office_val = officeitems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                office_val = "no";
            }
        });
        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dept_val = deptitems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dept_val = "no";
            }
        });
    }
    private void addcompleteinfo(String name, String designation, String number){
        int power;
        if(type_val.equals("Admin"))
            power = 1;
        else
            power = 0;
        String username = email.getText().toString().trim();
        completeinfo completeinfo = new completeinfo(username, power, name, designation, number, office_val, dept_val);
        database.child(UID).setValue(completeinfo);
    }
    public void send_verification_mail(final String name1, final String designation1, final String phone1, String UID){
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            addcompleteinfo(name1, designation1, phone1);
                            UserProfileChangeRequest updatename = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name1)
                                    .build();
                            firebaseUser.updateProfile(updatename);
                            firebaseAuth.signOut();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    public void confirm(View view){
        String username = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        final String name1 = name.getText().toString();
        final String designation1 = designation.getText().toString();
        final String phone1 = phone.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name1)){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(designation1)){
            Toast.makeText(this,"Please enter designation",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phone.getText().toString().trim())){
            Toast.makeText(this,"Please enter phone",Toast.LENGTH_LONG).show();
            return;
        }
        if(type_val.equals("--Select Type--")){
            Toast.makeText(this,"Please select type",Toast.LENGTH_LONG).show();
            return;
        }
        if(office_val.equals("--Select Office--")){
            Toast.makeText(this,"Please select office",Toast.LENGTH_LONG).show();
            return;
        }
        if(dept_val.equals("--Select Department--")){
            Toast.makeText(this,"Please select department",Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UID = task.getResult().getUser().getUid();
                                send_verification_mail(name1, designation1, phone1, UID);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }
}