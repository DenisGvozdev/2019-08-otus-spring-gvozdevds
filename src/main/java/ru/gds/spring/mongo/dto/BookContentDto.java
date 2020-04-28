package ru.gds.spring.mongo.dto;

import java.util.List;

public class BookContentDto {

    private List<PageDto> pageList;

    public BookContentDto(List<PageDto> pageList){
        this.pageList = pageList;
    }

    public List<PageDto> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageDto> pageList) {
        this.pageList = pageList;
    }

    public List<PageDto> print(){
        System.out.println(pageList);
        return pageList;
    }
}
