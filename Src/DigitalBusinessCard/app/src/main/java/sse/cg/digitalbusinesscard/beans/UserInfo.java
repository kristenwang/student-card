package sse.cg.digitalbusinesscard.beans;

import java.io.Serializable;

import android.graphics.Bitmap;

public class UserInfo implements Serializable {

    private String name = "";
    private int age = -1;
    private String address = "";
    private String gender = "";
    private String company = "";
    private String career = "";
    private String tel = "";
    private String email = "";

    private Bitmap QrImage = null;

    public UserInfo(String name, int age, String address, String gender,
                    String company, String career, String tel, String email) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.gender = gender;
        this.company = company;
        this.career = career;
        this.tel = tel;
        this.email = email;

    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }


    public String getCareer() {
        return career;
    }


    public void setCareer(String career) {
        this.career = career;
    }


    public String getTel() {
        return tel;
    }


    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Bitmap getQrImage() {
        return this.QrImage;
    }


    public void setQrImage(Bitmap qr) {
        this.QrImage = qr;
    }

}
