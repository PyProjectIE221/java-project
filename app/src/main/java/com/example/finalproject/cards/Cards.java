package com.example.finalproject.cards;

public class Cards {
    private String userid;
    private String name;
    private String profileImageUrl;
    private String age;
    public Cards(String userid,String name, String profileImageUrl, String age){
        this.userid = userid;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
