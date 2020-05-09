package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.RoleDto;
import ru.gds.spring.microservice.params.ParamsRole;
import ru.gds.spring.microservice.services.RoleService;

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

    @PostMapping("/roles")
    public RoleDto add(ParamsRole params) {
        return roleService.save(params);
    }

    @PutMapping("roles/{id}")
    public RoleDto update(ParamsRole params) {
        return roleService.save(params);
    }

    @DeleteMapping("roles/{role}")
    public String delete(@PathVariable(value = "role") String role) {
        return roleService.deleteByRole(role);
    }
}
