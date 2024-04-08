package com.ecard.ecardwebshop.user;

public class User {

    private long id;
    private String name;
    private String email;
    private String userName;
    private String password;
    private int enabled;
    private String role;
    private String userStatus;

    public User() {
    }

    public User(int enabled, String role) {
        this.enabled = enabled;
        this.role = role;
    }

    public User(String userName, int enabled, String role) {
        this.userName = userName;
        this.enabled = enabled;
        this.role = role;
    }

    public User(long id, String name, String userName, int enabled, String role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.enabled = enabled;
        this.role = role;
    }

    public User(long id, String name, String email, String userName, String password, int enabled, String role, String userStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.userStatus = userStatus;
    }

    public int getEnabled() {
        return enabled;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
