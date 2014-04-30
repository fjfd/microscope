package com.vipshop.microscope.query.user;

/**
 * User info
 *
 * @author Xu Fei
 * @version 1.0
 */
public class User {

    private String name;
    private String type;
    private long number;
    private String chineseName;
    private String departmentName;
    private String departmentStruction;
    private String email;
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentStruction() {
        return departmentStruction;
    }

    public void setDepartmentStruction(String departmentStruction) {
        this.departmentStruction = departmentStruction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
