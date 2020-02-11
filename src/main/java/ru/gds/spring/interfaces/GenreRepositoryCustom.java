package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Genre;

public interface GenreRepositoryCustom {

    Genre findById(long id);
}
