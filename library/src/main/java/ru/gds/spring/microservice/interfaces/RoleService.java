package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.RoleDto;
import ru.gds.spring.microservice.params.ParamsRole;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAll();

    RoleDto findByRole(String role);

    List<RoleDto> findByUsername(String username);

    RoleDto save(ParamsRole params);

    String deleteByRole(String role);
}
