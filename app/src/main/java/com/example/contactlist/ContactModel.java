package com.example.contactlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ContactModel implements Serializable {
    String Id;
    String Name;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAltphoneno() {
        return altphoneno;
    }

    public void setAltphoneno(String altphoneno) {
        this.altphoneno = altphoneno;
    }

    String Email;
String altphoneno;




    public ContactModel() {

    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    String phoneNo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



}
