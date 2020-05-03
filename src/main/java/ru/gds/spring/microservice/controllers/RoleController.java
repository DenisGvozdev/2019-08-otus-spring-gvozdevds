package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.mongo.dto.RoleDto;
import ru.gds.spring.mongo.services.RoleService;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/roles/{role}")
    public RoleDto findByRole(@RequestParam String role) {
        return roleService.findByRole(role);
    }

    @GetMapping("/roles/{username}")
    public List<RoleDto> findByUsername(@RequestParam String username) {
        return roleService.findByUsername(username);
    }
}
