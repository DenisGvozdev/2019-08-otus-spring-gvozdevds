package ru.gds.spring.microservice.dto;

public class PageDto {

    private int page;
    private String image;
    private String text;

    public PageDto() {
    }

    PageDto(int page, String image, String text) {
        this.page = page;
        this.image = image;
        this.text = text;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
