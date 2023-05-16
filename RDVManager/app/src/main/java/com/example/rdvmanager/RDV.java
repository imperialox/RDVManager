package com.example.rdvmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class RDV implements Parcelable{
    private long id;
    private String title;
    private String date;
    private String time;
    private String contact;
    private String address;
    private String phoneNumber;
    private boolean isDone;

    // Constructor
    public RDV(String title, String date, String time, String contact,  String phoneNumber,String address, boolean isDone) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isDone = isDone;
    }
    public RDV(long id, String title, String date, String time, String contact,  String phoneNumber,String address, boolean isDone) {
        this.id=id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isDone = isDone;
    }

    // Getters and setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(contact);
        dest.writeString(phoneNumber);
        dest.writeString(address);

       //dest.writeBoolean(isDone);

    }
    public static final Parcelable.Creator<RDV> CREATOR = new Parcelable.Creator<RDV>(){
        @Override
        public RDV createFromParcel(Parcel parcel) {
            return new RDV(parcel);
        }
        @Override
        public RDV[] newArray(int size) {
            return new RDV[size];
        }
    };
    public RDV(Parcel parsel){
        id=parsel.readLong();
        time=parsel.readString();
        title=parsel.readString();
        date=parsel.readString();
        contact= parsel.readString();
        phoneNumber=parsel.readString();
        address =parsel.readString();
       // isDone = parsel.readBoolean();

    }

}
