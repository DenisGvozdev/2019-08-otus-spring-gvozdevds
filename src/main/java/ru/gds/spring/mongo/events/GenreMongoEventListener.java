package ru.gds.spring.mongo.events;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

@Component
public class GenreMongoEventListener extends AbstractMongoEventListener<Genre> {

    private static final Logger logger = Logger.getLogger(GenreMongoEventListener.class);

    private final BookReactiveRepository bookReactiveRepository;

    GenreMongoEventListener(BookReactiveRepository bookReactiveRepository) {
        this.bookReactiveRepository = bookReactiveRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        Flux<Book> books = bookReactiveRepository.findAllByGenresId(id);
        books.map(book -> {
            if (book != null && StringUtils.isBlank(book.getId())) {
                throw new ForeignKeyException("Error delete Genre because Book: "
                        + book.getName() + " related to Genre: " + book.getGenres());
            }
            return book;
        }).log();

        logger.debug("GenreMongoEventListener delete");
    }
}
