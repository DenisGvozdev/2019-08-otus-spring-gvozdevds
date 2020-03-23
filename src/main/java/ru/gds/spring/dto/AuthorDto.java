package ru.gds.spring.dto;

import java.util.Date;

public class AuthorDto {

    private long id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthDate;
    private boolean selected;

    public AuthorDto(long id, String firstName, String secondName, String thirdName, Date birthDate, boolean selected){
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthDate = birthDate;
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
