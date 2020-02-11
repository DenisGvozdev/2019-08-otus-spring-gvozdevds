package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Author;

public interface AuthorRepositoryCustom {

    Author findById(long id);
}
