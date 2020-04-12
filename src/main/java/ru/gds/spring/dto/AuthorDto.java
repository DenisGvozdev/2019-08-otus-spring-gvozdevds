package ru.gds.spring.dto;

import ru.gds.spring.domain.Author;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorDto {

    private String id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String fio;
    private Date birthDate;
    private boolean selected;

    private AuthorDto() {
    }

    private AuthorDto(String id, String firstName, String secondName, String thirdName, Date birthDate, boolean selected) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthDate = birthDate;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
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

    public static AuthorDto toDtoLight(Author author) {
        if (author == null)
            return new AuthorDto();

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFio(author.getFirstName() + " " + author.getSecondName() + " " + author.getThirdName());
        return authorDto;
    }

    public static AuthorDto toDtoWithSelect(Author author, List<Author> authorList) {
        if (author == null || authorList == null)
            return new AuthorDto();

        List<String> authorIds = new ArrayList<>();
        authorList.forEach((e) -> authorIds.add(e.getId()));

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setFio(author.getFirstName() + " " + author.getSecondName() + " " + author.getThirdName());
        authorDto.setSelected(authorIds.contains(author.getId()));
        return authorDto;
    }

    static AuthorDto toDto(Author author) {
        if (author == null)
            return new AuthorDto();

        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getSecondName(),
                author.getThirdName(),
                author.getBirthDate(),
                false
        );
    }
}
