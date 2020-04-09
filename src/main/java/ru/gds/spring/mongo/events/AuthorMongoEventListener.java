package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMongoEventListener extends AbstractMongoEventListener<Author> {

    private static final Logger logger = Logger.getLogger(AuthorMongoEventListener.class);

    private final BookReactiveRepository bookReactiveRepository;

    AuthorMongoEventListener(BookReactiveRepository bookReactiveRepository) {
        this.bookReactiveRepository = bookReactiveRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<Book> bookList = new ArrayList<>();
        Flux<Book> books = bookReactiveRepository.findAllByAuthorsId(id);
        books.subscribe(bookList::add);
        if (!bookList.isEmpty())
            throw new ForeignKeyException("Error delete Author because Book: "
                    + bookList.get(0).getName() + " related to Author: " + bookList.get(0).getAuthors());

        logger.debug("AuthorMongoEventListener delete");
    }
}
