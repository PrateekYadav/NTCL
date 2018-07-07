package com.example.nibhr.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@IgnoreExtraProperties
public class filesdb implements Serializable{
    private String subject, type, office, department, uploaded_by, url, date;
    public filesdb(){

    }
    public filesdb(String subject, String type, String office, String department, String uploaded_by, String url, String date){
        this.department = department;
        this.office = office;
        this.subject = subject;
        this.type = type;
        this.uploaded_by = uploaded_by;
        this.url = url;
        this.date = date;
    }
    public String getSubject(){
        return subject;
    }
    public String getOffice() {
        return office;
    }

    public String getDepartment() {
        return department;
    }

    public String getType() {
        return type;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }
}
