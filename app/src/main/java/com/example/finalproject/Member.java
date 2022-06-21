package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String password;
    private double latitude;
    private double longitude;

    public Member(String id, String name, String phone, String address, String password, double latitude, double longitude) {
        super();
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    protected Member(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        password = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(password);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }


}