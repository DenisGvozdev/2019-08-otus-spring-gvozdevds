package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.mongo.domain.Book;
import ru.gds.spring.mongo.domain.Genre;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;
import ru.gds.spring.mongo.interfaces.BookRepository;

import java.util.List;

@Component
public class GenreMongoEventListener extends AbstractMongoEventListener<Genre> {

    private static final Logger logger = Logger.getLogger(GenreMongoEventListener.class);

    private final BookRepository bookRepository;

    GenreMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<Book> books = bookRepository.findAllByGenresId(id);
        if (!books.isEmpty())
            throw new ForeignKeyException("Error delete Genre because Book: "
                    + books.get(0).getName() + " related to Genre: " + books.get(0).getGenres());
        logger.debug("GenreMongoEventListener delete");
    }
}
