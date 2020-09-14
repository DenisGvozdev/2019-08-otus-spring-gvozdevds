package ru.gds.spring.microservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.interfaces.BookContentService;
import ru.gds.spring.microservice.interfaces.Sender;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
public class BookContentController {

    //private final Sender sender;
    private final BookContentService contentService;

    BookContentController(/*Sender sender, */BookContentService contentService) {
        //this.sender = sender;
        this.contentService = contentService;
    }

//    @GetMapping("/{bookId}/{pageStart}/{countPages}")
//    public BookContentDto findByBookId(
//            @RequestParam("bookId") String bookId,
//            @RequestParam("pageStart") int pageStart,
//            @RequestParam("countPages") int countPages) {
//        return contentService.getPagesForBook(bookId, pageStart, countPages);
//    }

//    @GetMapping("/{bookId}")
//    @ResponseBody
//    public FileSystemResource findFileByBookId(@RequestParam("bookId") String bookId) {
//        return contentService.findFileByBookId(bookId);
//    }

    @PostMapping(path = "/{bookId}/{bookName}/{type}/{file}",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public BookContentDto add(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "type") String type,
            @RequestPart(name = "file") MultipartFile file) {
        return contentService.save(bookId, bookName, type, file);
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable(value = "bookId") String bookId) {
        return contentService.deleteByBookId(bookId);
    }

    @GetMapping("/content")
    public String findBookById(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "pageStart") int pageStart,
            @RequestParam(value = "countPages") int countPages,
            Model model) {
        String json = null;
        try {
            //String response = sender.get(bookId, pageStart, countPages);
            BookContentDto bookContentDto =  contentService.getPagesForBook(bookId, pageStart, countPages);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(bookContentDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("book", json);
        return "/read";
    }

    @GetMapping("content/bookId")
    @ResponseBody
    public String findFileByBookId(@RequestParam(value = "bookId") String bookId) {
        return contentService.findFileByBookId(bookId);
        //return sender.findFileByBookId(bookId);
    }
}
