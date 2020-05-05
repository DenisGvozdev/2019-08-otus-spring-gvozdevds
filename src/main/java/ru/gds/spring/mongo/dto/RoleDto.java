package ru.gds.spring.mongo.dto;

import ru.gds.spring.mongo.domain.Role;

import java.util.List;

public class RoleDto {

    private String role;
    private String description;
    private boolean selected;

    public RoleDto() {}

    public RoleDto(String role) {
        this.role = role;
        this.description = null;
        this.selected = false;
    }

    public RoleDto(String role, String description, boolean selected) {
        this.role = role;
        this.description = description;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static RoleDto toDto(Role role) {
        if (role == null)
            return null;

        return new RoleDto(
                role.getRole(),
                role.getDescription(),
                false
        );
    }

    public static RoleDto toDtoWithSelect(Role role, List<String> selectedRoles) {
        if (role == null || selectedRoles == null)
            return null;

        return new RoleDto(role.getRole(), role.getDescription(), selectedRoles.contains(role.getRole()));
    }
}
