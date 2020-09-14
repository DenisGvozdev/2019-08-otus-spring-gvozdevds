package ru.gds.spring.microservice.dto;

import ru.gds.spring.microservice.constant.Constant;
import ru.gds.spring.microservice.domain.BookContent;

import java.util.ArrayList;
import java.util.List;

public class BookContentDto {

    private String bookId;
    private String bookName;
    private int startPage;
    private int countPages;
    private List<PageDto> pageList = new ArrayList<>();
    private String fileName;
    private String filePath;
    private String status;
    private String message;

    public BookContentDto() {
    }

    public BookContentDto(String bookId, String bookName, String status, String message) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.status = status;
        this.message = message;
    }

    public BookContentDto(String bookId, String bookName, List<PageDto> pageList) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.pageList = pageList;
    }

    public BookContentDto(String bookId, String bookName, int startPage, int countPages, List<PageDto> pageList) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.startPage = startPage;
        this.countPages = countPages;
        this.pageList = pageList;
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

    public List<PageDto> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageDto> pageList) {
        this.pageList = pageList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static BookContentDto toDtoLight(BookContent bookContent) {
        BookContentDto bookContentDto = new BookContentDto();

        int pageNumber = 0;
        for (String page : bookContent.getPages()) {
            pageNumber += 1;
            bookContentDto.getPageList().add(new PageDto(pageNumber, null, page));
        }

        bookContentDto.setBookId(bookContent.getBookId());
        bookContentDto.setBookName(bookContent.getBookName());
        bookContentDto.setStartPage(1);
        bookContentDto.setCountPages(bookContentDto.getPageList().size());
        bookContentDto.setFileName(bookContent.getContentFileName());
        bookContentDto.setFilePath(bookContent.getContentFilePath());
        bookContentDto.setStatus(Constant.OK);
        return bookContentDto;
    }
}
