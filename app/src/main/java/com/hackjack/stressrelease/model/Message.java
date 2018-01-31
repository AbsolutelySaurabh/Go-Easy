package com.hackjack.stressrelease.model;

/**
 * Created by saurabh on 3/8/17.
 */

public class Message {

    private String text;
    private String name;
    private String photoUrl;
    public String user_id;

    public Message() {
    }

    //this will be used for grp message recyclerview only message and name.
    public Message(String text, String name) {
        this.text = text;
        this.name = name;
    }
    public Message(String text, String name, String photoUrl) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
    }
    public Message(String text, String name, String photoUrl, String user_id) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.user_id = user_id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
