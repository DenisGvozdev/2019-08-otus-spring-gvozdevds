package ru.gds.spring.dto;

import ru.gds.spring.domain.Author;

import java.util.Date;

public class AuthorDto {

    private long id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthDate;
    private long selected;

    public AuthorDto(long id, String firstName, String secondName, String thirdName, Date birthDate, long selected){
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

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getSecondName(),
                author.getThirdName(),
                author.getBirthDate(),
                0
        );
    }
}
