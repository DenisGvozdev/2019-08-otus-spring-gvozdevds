package ru.gds.spring.microservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gds.spring.microservice.interfaces.Sender;

@Controller
public class BookContentController {

    private final Sender sender;

    BookContentController(Sender sender) {
        this.sender = sender;
    }

    @GetMapping("/content")
    public String findBookById(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "pageStart") int pageStart,
            @RequestParam(value = "countPages") int countPages,
            Model model) {
        String response = sender.get(bookId, pageStart, countPages);
        model.addAttribute("book", response);
        return "/read";
    }

    @GetMapping("content/bookId")
    @ResponseBody
    public String findFileByBookId(@RequestParam(value = "bookId") String bookId) {
        return sender.findFileByBookId(bookId);
    }
}
