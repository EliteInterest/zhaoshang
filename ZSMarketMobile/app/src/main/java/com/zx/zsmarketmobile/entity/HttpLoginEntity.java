package com.zx.zsmarketmobile.entity;

import java.util.List;

/**
 * 命名方式:Http + 协议名 + Entity
 */
public class HttpLoginEntity {
    private int status;
    private String telephone;
    private String password;//未加密
    private String regionCode;
    private int gender;
    private String userId;
    private String department;
    private String username;
    private String realName;
    private String officeTel;
    private String region;
    private int age;
    private List<String> role;
    private String token;
    private String userHigher;
    private boolean isLogin;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = ("null").equals(telephone) ? null : telephone;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUserHigher() {
        return userHigher;
    }

    public void setUserHigher(String userHigher) {
        this.userHigher = ("null").equals(userHigher) ? null : userHigher;
    }

    public List<String> getRole() {
        return role;
    }

    public String getRoleString() {
        String roleString = "";
        for (String roleItem : role) {
            roleString = roleString + roleItem + ",";
        }
        return roleString.length() > 0 ? roleString.substring(0, roleString.length() - 1) : roleString;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = ("null").equals(password) ? null : password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = ("null").equals(username) ? null : username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = ("null").equals(department) ? null : department;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
//    @Override
//    public String toString() {
//        return "HttpLoginEntity [id=" + id + ", realname=" + realname + ", gender=" + gender + ", age=" + age
//                + ", username=" + username + ", duty=" + duty + ", department=" + department + ", desc=" + desc
//                + ", password=" + password + ", telephone=" + telephone + ", isLogin=" + isLogin + ", authority="
//                + authority + "]";
//    }

}
