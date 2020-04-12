package ru.gds.spring.mongo.events;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

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

        Flux<Book> books = bookReactiveRepository.findAllByStatusId(id);
        books.map(book -> {
            if (book != null && StringUtils.isBlank(book.getId())) {
                throw new ForeignKeyException("Error delete Status because Book: "
                        + book.getName() + " related to Status: " + book.getStatus().getName());
            }
            return book;
        }).log();

        logger.debug("StatusMongoEventListener delete");
    }
}
