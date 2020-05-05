package ru.gds.spring.mongo.params;

public class ParamsRole {

    private String role;
    private String description;

    public ParamsRole() {
    }

    public ParamsRole(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
