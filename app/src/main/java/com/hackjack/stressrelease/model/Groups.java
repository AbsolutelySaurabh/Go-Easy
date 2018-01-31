package com.hackjack.stressrelease.model;

/**
 * Created by absolutelysaurabh on 30/1/18.
 */

public class Groups {

    public String grpName;
    public String grpAvatarUrl;
    public String grpTotalMembers;
    private String gpId;

    public Groups(String grpName, String grpAvatarUrl, String grpTotalMembers, String gpId){
        this.grpName = grpName;
        this.grpAvatarUrl = grpAvatarUrl;
        this.grpTotalMembers = grpTotalMembers;
        this.gpId = gpId;
    }
    public Groups(){
        //empty constructor
    }

    public String getGrpName(){
        return grpName;
    }
    public String getGrpAvatarUrl(){
        return grpAvatarUrl;
    }

    public String getGrpTotalMembers(){
        return grpTotalMembers;
    }

    public String getGpId(){
        return gpId;
    }
    public void setGpId(String gpId){
        this.gpId = gpId;
    }
}
