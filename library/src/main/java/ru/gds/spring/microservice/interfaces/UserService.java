package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.UserDto;
import ru.gds.spring.microservice.params.ParamsUser;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    List<UserDto> findByParam(ParamsUser params);

    UserDto save(ParamsUser params);

    String deleteByUsername(String username);
}
