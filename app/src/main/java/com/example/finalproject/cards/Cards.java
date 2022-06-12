package com.example.finalproject.cards;

public class Cards {
    private String userid;
    private String name;
    private String profileImageUrl;
    private String age;
    private String address;
    private String school;
    private String hobbies;
    private String introduce;
    private String userSex;
    public Cards(String userid,String name, String profileImageUrl, String age,String address,String school,
                 String hobbies,String introduce,String userSex){
        this.userid = userid;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.address = address;
        this.school = school;
        this.hobbies = hobbies;
        this.introduce = introduce;
        this.userSex = userSex;
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

    public String getAddress() {
        return address;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getSchool() {
        return school;
    }

    public String getUserSex() {
        return userSex;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
