package ru.gds.spring.domain;

import java.util.Date;

public class Author {

    private long id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthDate;
    private Status status;

    public Author() {
    }

    public Author(String firstName, String secondName, String thirdName, Date birthDate, Status status) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthDate = birthDate;
        this.status = status;
    }

    public Author(long id, String firstName, String secondName, String thirdName, Date birthDate, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthDate = birthDate;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {

        String status = (getStatus() != null) ? getStatus().getName() : "";

        return " id: " + getId() +
                " firstName: " + getFirstName() +
                " secondName: " + getSecondName() +
                " thirdName: " + getThirdName() +
                " birthDate: " + getBirthDate() +
                " status: " + status;
    }
}
