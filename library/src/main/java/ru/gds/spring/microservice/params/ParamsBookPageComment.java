package ru.gds.spring.microservice.params;

public class ParamsBookPageComment {

    private String bookId;
    private Integer page;
    private String comment;

    public ParamsBookPageComment() {
    }

    public ParamsBookPageComment(
            String bookId,
            Integer page,
            String comment) {

        this.bookId = bookId;
        this.page = page;
        this.comment = comment;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
