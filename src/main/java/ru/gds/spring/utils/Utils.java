package ru.gds.spring.utils;

import ru.gds.spring.mongo.dto.BookContentDto;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void printPages(BookContentDto bookContentDto) {
        bookContentDto.getPageList()
                .forEach(pageDto ->
                        System.out.println("\nPage: " + pageDto.getPage() +
                                "; Text: " + pageDto.getText()));
    }

    public static List<String> generateOrders() {
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        return items;
    }

}
