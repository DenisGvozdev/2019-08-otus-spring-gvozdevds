package ru.gds.spring.microservice.params;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ParamsBookContent {

    @JsonProperty("bookId")
    private String bookId;

    @JsonProperty("bookName")
    private String bookName;

    @JsonProperty("startPage")
    private int startPage;

    @JsonProperty("countPages")
    private int countPages;

    @JsonProperty("fileTitle")
    private MultipartFile fileTitle;

    @JsonProperty("fileContent")
    private MultipartFile fileContent;

    public ParamsBookContent() {
    }

    public ParamsBookContent(String bookId, String bookName, int pageStart, int countPages,
                             MultipartFile fileTitle, MultipartFile fileContent) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.startPage = pageStart;
        this.countPages = countPages;
        this.fileTitle = fileTitle;
        this.fileContent = fileContent;
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

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }

    public MultipartFile getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(MultipartFile fileTitle) {
        this.fileTitle = fileTitle;
    }

    public MultipartFile getFileContent() {
        return fileContent;
    }

    public void setFileContent(MultipartFile fileContent) {
        this.fileContent = fileContent;
    }
}
