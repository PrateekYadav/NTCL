package com.example.nibhr.myapplication;//package com.example.nibhr.myapplication;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    EditText username = (EditText)findViewById(R.id.editText1);
//    EditText password = (EditText)findViewById(R.id.editText2);
//
//    public void login(View view) {
//
//        if(username.getText().toString().equals("user") && password.getText().toString().equals("ntc")){
//
//            Intent i = new Intent(this, HomeActivity.class);
//            startActivity(i);
//
//        }else{
//            Toast.makeText(view.getContext(),"Wrong Combination",
//                    Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//
//
//}


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    public static String user;
    private FirebaseAuth firebaseAuth;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        usernameET = (EditText) findViewById(R.id.editText1);
        passwordET = (EditText) findViewById(R.id.editText2);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            usernameET.setText(loginPreferences.getString("username", ""));
            passwordET.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        //passwordET.setTransformationMethod(new AsteriskPasswordTransformationMethod());

    }

    public void SignIn(View view) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameET.getWindowToken(), 0);

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        user = username;
        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", username);
            loginPrefsEditor.putString("password", password);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
        firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                if(firebaseUser.isEmailVerified()){
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);
                                }
                                else{
                                        Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                        }
                    });
        /*if (username.equals("vivek.plawat@ntcltd.org") && password.equals("ntcl@123")) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);

        } else {
            Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
        }*/
    }
    public void passwordforget(View view){
        Intent i = new Intent(this , ForgotPasswordActivity.class);
        startActivity(i);
    }
    /*public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    };*/
}