package ru.gds.spring.interfaces;

import ru.gds.spring.domain.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findAll();

    User findByLogin(String login);

    void deleteByLogin(String login);
}
