package com.example.nibhr.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class completeinfo {
    private String email, name, designation, office, department, phone_number;
    private int power;
    public completeinfo(){
    }
    public completeinfo(String email, int power, String name, String designation, String phone_number, String office, String department){
        this.email = email;
        this.power = power;
        this.name = name;
        this.designation = designation;
        this.phone_number = phone_number;
        this.office = office;
        this.department = department;
    }
    public String getEmail(){
        return email;
    }
    public int getPower(){
        return power;
    }
    public String getPhone_number(){
        return phone_number;
    }
    public String getDesignation() {
        return designation;
    }
    public String getName() {
        return name;
    }
    public String getDepartment() {
        return department;
    }
    public String getOffice() {
        return office;
    }
}
