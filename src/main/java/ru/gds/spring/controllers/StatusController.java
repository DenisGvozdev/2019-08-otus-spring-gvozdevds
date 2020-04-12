package ru.gds.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.services.StatusService;

@RestController
public class StatusController {

    private final StatusService statusService;

    StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/statuses")
    public Flux<StatusDto> findStatusDtoListLight() {
        return statusService.findAllLight();
    }

    @GetMapping("/statuses/{bookId}")
    public Flux<StatusDto> findStatusDtoListLight(@RequestParam String bookId) {
        return statusService.findAllByBookId(bookId);
    }
}
