package ru.gds.spring.microservice.interfaces;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.BookContentDto;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RequestMapping(value = "/content")
@FeignClient(value = "file-server")
public interface BookContentServiceProxy {

    @GetMapping("/bookId/{bookId}/pageStart/{pageStart}/countPages/{countPages}")
    String findByBookId(
            @RequestParam("bookId") String bookId,
            @RequestParam("pageStart") int pageStart,
            @RequestParam("countPages") int countPages);

    @GetMapping("/bookId/{bookId}")
    @ResponseBody
    String findFileByBookId(@RequestParam("bookId") String bookId);

    @PostMapping(path = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    BookContentDto add(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "type") String type,
            @RequestPart(value = "file") MultiValueMap<String, Object> file
    );
}
