package ru.gds.spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.services.StatusService;

import java.util.List;

@RestController
public class StatusController {

    private static final Logger logger = Logger.getLogger(StatusController.class);

    private final StatusService statusService;

    StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/statuses")
    public List<StatusDto> findStatusDtoListLight() {
        return statusService.findAllLight();
    }

    @GetMapping("/statuses/{bookId}")
    public List<StatusDto> findStatusDtoListLight(@RequestParam long bookId) {
        return statusService.findAllByBookId(bookId);
    }
}
