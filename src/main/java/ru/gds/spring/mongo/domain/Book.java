package ru.gds.spring.mongo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private Date createDate;

    private String description;

    private byte[] image;

    @DBRef
    private Status status;

    @DBRef
    private List<Genre> genres;

    @DBRef
    private List<Author> authors;

    public Book(){}

    public Book(String name, Date createDate,
                String description, byte[] image,
                List<Genre> genres, List<Author> authors, Status status) {
        this.name = name;
        this.createDate = createDate;
        this.description = description;
        this.image = (image != null) ? image : new byte[]{};
        this.genres = (genres == null) ? new ArrayList<Genre>() : genres;
        this.status = status;
        this.authors = (authors == null) ? new ArrayList<Author>() : authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
