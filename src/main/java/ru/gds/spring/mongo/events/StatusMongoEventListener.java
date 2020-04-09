package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusMongoEventListener extends AbstractMongoEventListener<Status> {

    private static final Logger logger = Logger.getLogger(StatusMongoEventListener.class);

    private final BookReactiveRepository bookReactiveRepository;

    StatusMongoEventListener(BookReactiveRepository bookReactiveRepository) {
        this.bookReactiveRepository = bookReactiveRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Status> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<Book> bookList = new ArrayList<>();
        Flux<Book> books = bookReactiveRepository.findAllByStatusId(id);
        books.subscribe(bookList::add);
        if (!bookList.isEmpty())
            throw new ForeignKeyException("Error delete Status because Book: "
                    + bookList.get(0).getName() + " related to Status: " + bookList.get(0).getStatus().getName());
        logger.debug("StatusMongoEventListener delete");
    }
}
