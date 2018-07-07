package com.example.nibhr.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {
    TextView subject, type, office, dept, uploaddate, uploaduser;
    String url;
    filesdb filesdbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        filesdbs = (filesdb) getIntent().getSerializableExtra("filesdb");
        subject = (TextView) findViewById(R.id.subjectz);
        type = (TextView) findViewById(R.id.typez);
        office = (TextView) findViewById(R.id.officez);
        dept = (TextView)findViewById(R.id.deptz);
        uploaddate = (TextView)findViewById(R.id.uploaddate);
        uploaduser = (TextView)findViewById(R.id.uploaduser);
        subject.setText("Subject : " +filesdbs.getSubject());
        type.setText("Type : " +filesdbs.getType());
        office.setText("Office : "+filesdbs.getOffice());
        dept.setText("Department : "+filesdbs.getDepartment());
        uploaddate.setText("Upload Date and Time : "+filesdbs.getDate());
        uploaduser.setText("Uploaded By User : "+filesdbs.getUploaded_by());
        url = filesdbs.getUrl();
    }
    public void downl(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
        finish();
    }
}
