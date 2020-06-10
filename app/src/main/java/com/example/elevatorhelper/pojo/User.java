package com.example.elevatorhelper.pojo;

/**
 * @author yhjzs
 *
 * @date 2020-6-3
 */
public class User {
    private Integer id;
    private String userName;
    private String userPhone;
    private String userPasswordHash;
    private String userHead;
    private Integer iconHead;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public Integer getIconHead() {
        return iconHead;
    }

    public void setIconHead(Integer iconHead) {
        this.iconHead = iconHead;
    }

}
