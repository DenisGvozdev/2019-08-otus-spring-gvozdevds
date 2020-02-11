package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Book;

public interface BookRepositoryCustom {

    Book findById(long id);
}
