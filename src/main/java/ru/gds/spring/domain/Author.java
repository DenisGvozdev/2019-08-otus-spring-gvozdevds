package ru.gds.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORS")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "SECONDNAME")
    private String secondName;

    @Column(name = "THIRDNAME")
    private String thirdName;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    public Author(String firstName, String secondName, String thirdName, Date birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthDate = birthDate;
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
}
