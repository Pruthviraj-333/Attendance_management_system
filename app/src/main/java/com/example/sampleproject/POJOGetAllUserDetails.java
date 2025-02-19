package com.example.sampleproject;

public class POJOGetAllUserDetails {

    String id,name,profile_pic,mobile_no,email_id,username;

    public POJOGetAllUserDetails(String id,String name,String profile_pic,String mobile_no,String email_id,String username)
    {
        this.id=id;
        this.name=name;
        this.profile_pic=profile_pic;
        this.mobile_no=mobile_no;
        this.email_id=email_id;
        this.username=username;
    }

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

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
