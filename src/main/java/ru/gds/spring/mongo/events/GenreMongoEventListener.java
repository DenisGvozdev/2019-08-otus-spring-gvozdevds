package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.ArrayList;
import java.util.List;

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

        List<Book> bookList = new ArrayList<>();
        Flux<Book> books = bookReactiveRepository.findAllByGenresId(id);
        books.subscribe(bookList::add);
        if (!bookList.isEmpty())
            throw new ForeignKeyException("Error delete Genre because Book: "
                    + bookList.get(0).getName() + " related to Genre: " + bookList.get(0).getGenres());
        logger.debug("GenreMongoEventListener delete");
    }
}
