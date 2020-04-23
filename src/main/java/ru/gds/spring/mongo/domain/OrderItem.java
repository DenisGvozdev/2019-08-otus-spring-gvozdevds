package ru.gds.spring.mongo.domain;

public class OrderItem {

    private String bookId;

    public OrderItem(String bookId) {
        this.bookId = bookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
