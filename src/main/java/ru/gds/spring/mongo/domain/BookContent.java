package ru.gds.spring.mongo.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.util.*;

@Data
@Document(collection = "book-content")
public class BookContent {

    @Id
    private String id;

    private String bookId;

    private Date createDate;

    private String description;

    private String text;

    private Integer page;

    private byte[] fileContent;

    public BookContent(String id, String bookId, Date createDate,
                       String description, String text, Integer page, byte[] fileContent) {
        this.id = id;
        this.bookId = bookId;
        this.createDate = createDate;
        this.description = description;
        this.text = text;
        this.page = page;
        this.fileContent = (fileContent != null) ? fileContent : new byte[]{};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
}
