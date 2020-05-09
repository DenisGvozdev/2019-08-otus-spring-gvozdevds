package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.StatusDto;
import ru.gds.spring.microservice.params.ParamsStatus;
import ru.gds.spring.microservice.services.StatusService;

import java.util.List;

@RestController
public class StatusController {

    private final StatusService statusService;

    StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/statuses")
    public List<StatusDto> findStatusDtoListLight() {
        return statusService.findAllLight();
    }

    @GetMapping("/statuses/{bookId}")
    public List<StatusDto> findStatusDtoListLight(@RequestParam String bookId) {
        return statusService.findAllByBookId(bookId);
    }

    @GetMapping("/statuses/{statusId}")
    public StatusDto findById(@RequestParam String statusId) {
        return statusService.findById(statusId);
    }

    @PostMapping("/statuses")
    public StatusDto add(ParamsStatus params) {
        return statusService.save(params);
    }

    @PutMapping("statuses/{id}")
    public StatusDto update(ParamsStatus params) {
        return statusService.save(params);
    }

    @DeleteMapping("statuses/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        return statusService.deleteById(id);
    }
}
