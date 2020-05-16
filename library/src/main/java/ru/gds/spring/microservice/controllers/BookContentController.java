package ru.gds.spring.microservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gds.spring.microservice.interfaces.Sender;

@Controller
public class BookContentController {

    private final Sender restClient;

    BookContentController(Sender restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/content")
    public String findBookById(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "pageStart") int pageStart,
            @RequestParam(value = "countPages") int countPages,
            Model model) {
        String uri = String.format("/content/?bookId=%s&pageStart=%d&countPages=%d", bookId, pageStart, countPages);
        String response = restClient.get(uri);
        model.addAttribute("book", response);
        return "/read";
    }

    @GetMapping("content/bookId")
    @ResponseBody
    public String findFileByBookId(@RequestParam(value = "bookId") String bookId) {
        String uri = String.format("/content/bookId?bookId=%s", bookId);
        return restClient.get(uri);
    }
}
