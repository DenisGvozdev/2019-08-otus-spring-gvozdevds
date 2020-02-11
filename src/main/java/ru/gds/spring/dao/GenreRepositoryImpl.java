package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.GenreRepositoryCustom;

@Transactional
@Repository
public class GenreRepositoryImpl implements GenreRepositoryCustom {

    private GenreRepository genreRepository;

    GenreRepositoryImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findById(long id) {
        return genreRepository.findById(id);
    }
}
