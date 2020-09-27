package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.microservice.dto.CommentDto;
import ru.gds.spring.microservice.interfaces.CommentService;
import ru.gds.spring.microservice.params.ParamsBookPageComment;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/pagecomment")
    public CommentDto add(ParamsBookPageComment params) {
        return commentService.save(params);
    }

    @GetMapping("/pagecomment/{bookId}")
    public List<CommentDto> findAuthorDtoListLight(@RequestParam String bookId) {
        return commentService.findByParam(bookId, null, null);
    }
}
