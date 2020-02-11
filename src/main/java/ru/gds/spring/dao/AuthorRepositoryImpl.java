package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.AuthorRepositoryCustom;

@Transactional
@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    private AuthorRepository authorRepository;

    AuthorRepositoryImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(long id) {
        return authorRepository.findById(id);
    }
}
