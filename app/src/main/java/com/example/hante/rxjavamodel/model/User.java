package com.example.hante.rxjavamodel.model;

import java.io.Serializable;

/**
 * Created By HanTe
 */

public class User implements Serializable{

    private String name;
    private int phone;
    private int age;

    public User (String name, int phone, int age) {
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getPhone () {
        return phone;
    }

    public void setPhone (int phone) {
        this.phone = phone;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }
}
