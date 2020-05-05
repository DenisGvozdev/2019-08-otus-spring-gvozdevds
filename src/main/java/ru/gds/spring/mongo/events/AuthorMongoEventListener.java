package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.microservice.domain.Author;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;
import ru.gds.spring.microservice.interfaces.BookRepository;

import java.util.List;

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

        List<Book> books = bookRepository.findAllByAuthorsId(id);
        if (!books.isEmpty())
            throw new ForeignKeyException("Error delete Author because Book: "
                    + books.get(0).getName() + " related to Author: " + books.get(0).getAuthors());

        logger.debug("AuthorMongoEventListener delete");
    }
}
