package ru.gds.spring.mongo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.util.*;

@Data
@Document(collection = "books")
public class BookMongo {

    @Id
    private String id;

    private String name;

    private Date createDate;

    private String description;

    private byte[] image;

    @DBRef
    private StatusMongo status;

    @DBRef
    private List<GenreMongo> genres;

    @DBRef
    private List<AuthorMongo> authors;

    public BookMongo(String name, Date createDate,
                     String description, byte[] image,
                     List<GenreMongo> genres, List<AuthorMongo> authors, StatusMongo status) {
        this.name = name;
        this.createDate = createDate;
        this.description = description;
        this.image = (image != null) ? image : new byte[]{};
        this.genres = (genres == null) ? new ArrayList<GenreMongo>() : genres;
        this.status = status;
        this.authors = (authors == null) ? new ArrayList<AuthorMongo>() : authors;
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

    public StatusMongo getStatus() {
        return status;
    }

    public void setStatus(StatusMongo status) {
        this.status = status;
    }

    public List<GenreMongo> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreMongo> genres) {
        this.genres = genres;
    }

    public List<AuthorMongo> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorMongo> authors) {
        this.authors = authors;
    }
}