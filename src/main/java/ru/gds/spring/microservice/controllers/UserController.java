package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.util.CommonUtils;
import ru.gds.spring.mongo.dto.UserDto;
import ru.gds.spring.mongo.params.ParamsUser;
import ru.gds.spring.mongo.services.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("users/{param}")
    public List<UserDto> findByParam(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email) {
        ParamsUser params = new ParamsUser();
        params.setUsername(username);
        params.setEmail(email);
        return userService.findByParam(params);
    }

    @GetMapping("users/{active}")
    public UserDto getActiveUser() {
        return CommonUtils.getCurrentUser();
    }

    @PostMapping("/users")
    public UserDto add(ParamsUser params) {
        return userService.save(params);
    }

    @PutMapping("users/{id}")
    public UserDto update(ParamsUser params) {
        return userService.save(params);
    }

    @DeleteMapping("users/{username}")
    public String delete(@PathVariable(value = "username") String username) {
        return userService.deleteByUsername(username);
    }
}