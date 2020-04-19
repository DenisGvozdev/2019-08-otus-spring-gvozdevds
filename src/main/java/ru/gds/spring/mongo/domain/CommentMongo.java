package ru.gds.spring.mongo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Document(collection = "comments")
public class CommentMongo {

    @Id
    private String id;

    private BookMongo book;

    private String comment;

    private Date createDate;

    public CommentMongo(BookMongo book, String comment, Date createDate) {
        this.book = book;
        this.comment = comment;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BookMongo getBook() {
        return book;
    }

    public void setBook(BookMongo book) {
        this.book = book;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
