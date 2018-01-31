package com.hackjack.stressrelease.model;

public class User {

    private String name;
    private String email;
    private String contactNo;
    private String avatarUrl;
    public String user_id;

    public User(String name, String email, String contact, String avatarUrl){

        this.name = name;
        this.email = email;
        this.contactNo = contact;
        this.avatarUrl = avatarUrl;
    }

    public User(String name, String email, String contact, String avatarUrl, String user_id){

        this.name = name;
        this.email = email;
        this.contactNo = contact;
        this.avatarUrl = avatarUrl;
        this.user_id = user_id;
    }

    public User(){
        //empty constructor
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getContactNo(){
        return contactNo;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }
    public void setContactNo(String contactNo){
        this.contactNo = contactNo;
    }
}
