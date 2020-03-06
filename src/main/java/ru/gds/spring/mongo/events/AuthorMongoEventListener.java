package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AuthorMongoEventListener extends AbstractMongoEventListener<Author> {

    private static final Logger logger = Logger.getLogger(AuthorMongoEventListener.class);

    private final BookRepository bookRepository;

    AuthorMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<Book> booksTest = bookRepository.findAllByAuthorsId(id);
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            List<Author> authors = book.getAuthors().stream()
                    .filter(a -> id.equals(a.getId()))
                    .collect(Collectors.toList());

            if (!authors.isEmpty())
                throw new ForeignKeyException("Error delete Author because Book: "
                        + book.getName() + " related to Author: " + authors.get(0));
        }
        logger.debug("AuthorMongoEventListener delete");
    }
}
