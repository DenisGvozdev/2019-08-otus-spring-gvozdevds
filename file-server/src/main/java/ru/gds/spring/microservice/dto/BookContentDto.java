package ru.gds.spring.microservice.dto;

import ru.gds.spring.microservice.domain.BookContent;

import java.util.ArrayList;
import java.util.List;

public class BookContentDto {

    private String bookId;
    private String bookName;
    private int startPage;
    private int countPages;
    private List<PageDto> pageList = new ArrayList<>();

    public BookContentDto() {
    }

    public BookContentDto(String bookId, String bookName, List<PageDto> pageList) {
        this.bookId = bookId;
        this.bookName = bookName;
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
        return bookContentDto;
    }
}
