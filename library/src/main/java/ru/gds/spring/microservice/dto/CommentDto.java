package ru.gds.spring.microservice.dto;

import ru.gds.spring.microservice.domain.Comment;

import java.util.Date;

public class CommentDto {

    private String id;
    private String comment;
    private String bookId;
    private String bookName;
    private Date createDate;
    private String username;

    public CommentDto() {
    }

    private CommentDto(
            String id,
            String comment,
            String bookId,
            String bookName,
            Date createDate,
            String username) {

        this.id = id;
        this.comment = comment;
        this.bookId = bookId;
        this.bookName = bookName;
        this.createDate = createDate;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                comment.getBook().getId(),
                comment.getBook().getName(),
                comment.getCreateDate(),
                comment.getUsername()
        );
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
