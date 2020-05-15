package ru.gds.spring.microservice.dto;

import org.bson.types.ObjectId;
import ru.gds.spring.microservice.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String secondName;
    private String thirdName;
    private List<RoleDto> roles;

    public UserDto() {
    }

    private UserDto(
            String username,
            String password,
            String email,
            String phone,
            String firstName,
            String secondName,
            String thirdName,
            List<RoleDto> roles) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.roles = (roles == null) ? new ArrayList<>() : roles;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public List<RoleDto> getRoles() {
        if (roles == null)
            roles = new ArrayList<>();

        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public static UserDto toDtoLight(User user) {
        if (user == null)
            return null;

        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getFirstName(),
                user.getSecondName(),
                user.getThirdName(),
                new ArrayList<>()
        );
    }

    public static UserDto toDto(User user) {
        if (user == null)
            return null;

        List<RoleDto> roleDtoList = new ArrayList<>();
        user.getRoles().forEach(role -> roleDtoList.add(RoleDto.toDto(role)));

        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getFirstName(),
                user.getSecondName(),
                user.getThirdName(),
                roleDtoList
        );
    }

    public static boolean findRole(String role, List<RoleDto> roles) {
        RoleDto foundRoleDto = roles.stream()
                .filter(roleDto -> roleDto.getRole().equals(role))
                .findAny().orElse(null);
        return foundRoleDto != null;
    }
}
