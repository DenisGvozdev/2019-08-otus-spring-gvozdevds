package ru.gds.spring.mongo.params;

public class ParamsUser {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String roleIds;

    public ParamsUser(){}

    public ParamsUser(
            String username,
            String password,
            String email,
            String phone,
            String firstName,
            String secondName,
            String thirdName,
            String roleIds) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.roleIds = roleIds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
