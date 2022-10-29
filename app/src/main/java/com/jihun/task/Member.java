package com.jihun.task;

import java.io.Serializable;

public class Member implements Serializable {
    private String id;
    private String password;
    private String name;
    private String call;
    private String address;

    public Member(String id, String password, String name, String call, String address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.call = call;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
