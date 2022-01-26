package com.example.trainrail;

import java.io.Serializable;

public class CustomerDetail implements Serializable {
    public String cus_email;
    public String cus_phone;
    public String cus_name;
    public String cus_age;


    public CustomerDetail(String cus_email, String cus_phone, String cus_name,String cus_age) {
        this.cus_email = cus_email;
        this.cus_phone = cus_phone;
        this.cus_name=cus_name;
        this.cus_age = cus_age;
    }

    public CustomerDetail(){

    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_age() {
        return cus_age;
    }

    public void setCus_age(String cus_age) {
        this.cus_age = cus_age;
    }
}