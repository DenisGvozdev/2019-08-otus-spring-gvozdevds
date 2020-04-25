package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.mongo.domain.AuthorMongo;
import ru.gds.spring.mongo.domain.BookMongo;
import ru.gds.spring.mongo.interfaces.BookMongoRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;

@Component
public class AuthorMongoEventListener extends AbstractMongoEventListener<AuthorMongo> {

    private static final Logger logger = Logger.getLogger(AuthorMongoEventListener.class);

    private final BookMongoRepository bookRepository;

    AuthorMongoEventListener(BookMongoRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<AuthorMongo> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<BookMongo> books = bookRepository.findAllByAuthorsId(id);
        if (!books.isEmpty())
            throw new ForeignKeyException("Error delete Author because Book: "
                    + books.get(0).getName() + " related to Author: " + books.get(0).getAuthors());

        logger.debug("AuthorMongoEventListener delete");
    }
}
