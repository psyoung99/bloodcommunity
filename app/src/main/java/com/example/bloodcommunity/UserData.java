package com.example.bloodcommunity;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 데이터 저장
 */

public class UserData {
    String id, pwd, phoneNum, name, location;

    public UserData() { }

    public UserData(String id, String pwd, String phoneNum, String name, String location) {
        this.id = id;
        this.pwd = pwd;
        this.phoneNum = phoneNum;
        this.name = name;
        this.location = location;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("password", pwd);
        result.put("phoneNumber", phoneNum);
        result.put("name", name);
        result.put("location", location);

        return result;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return pwd;
    }

    public String getPhonenumber() {
        return phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return location;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.pwd = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phoneNum = phonenumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.location = address;
    }

}
