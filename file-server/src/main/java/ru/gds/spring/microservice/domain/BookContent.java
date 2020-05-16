package ru.gds.spring.microservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "book-content")
public class BookContent {

    @Id
    private String id;
    private String bookId;
    private String bookName;
    private Date createDate;
    private String contentFilePath;
    private String contentFileName;
    private int countPages;
    private List<String> pages;

    public BookContent() {
    }

    public BookContent(String id, String bookId, String bookName, Date createDate,
                       String contentFilePath, String contentFileName, int countPages, List<String> pages) {
        this.id = id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.createDate = createDate;
        this.contentFilePath = contentFilePath;
        this.contentFileName = contentFileName;
        this.countPages = countPages;
        this.pages = (pages != null) ? pages : new ArrayList<>();
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

    public String getContentFilePath() {
        return contentFilePath;
    }

    public void setContentFilePath(String contentFilePath) {
        this.contentFilePath = contentFilePath;
    }

    public String getContentFileName() {
        return contentFileName;
    }

    public void setContentFileName(String contentFileName) {
        this.contentFileName = contentFileName;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public List<String> getPages() {
        return (pages == null || pages.isEmpty()) ? new ArrayList<>() : pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
