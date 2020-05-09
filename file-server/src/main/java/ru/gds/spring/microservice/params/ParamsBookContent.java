package ru.gds.spring.microservice.params;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ParamsBookContent {

    private String bookId;
    private String bookName;
    private int startPage;
    private int countPages;
    private MultipartFile file;

    public ParamsBookContent(){}

    public ParamsBookContent(String bookId, String bookName, int pageStart, int countPages, MultipartFile file){
        this.bookId = bookId;
        this.bookName = bookName;
        this.startPage = pageStart;
        this.countPages = countPages;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
