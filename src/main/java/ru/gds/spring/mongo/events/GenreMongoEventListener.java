package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            List<Genre> genres = book.getGenres().stream()
                    .filter(g -> id.equals(g.getId()))
                    .collect(Collectors.toList());

            if (!genres.isEmpty())
                throw new ForeignKeyException("Error delete Genre because Book: "
                        + book.getName() + " related to Genre: " + genres.get(0));
        }
        logger.debug("GenreMongoEventListener delete");
    }
}
