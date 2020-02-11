package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Book;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.BookRepositoryCustom;

import javax.persistence.*;

@Transactional
@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private BookRepository bookRepository;

    BookRepositoryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findById(long id) {
        return bookRepository.findById(id);
    }
}
