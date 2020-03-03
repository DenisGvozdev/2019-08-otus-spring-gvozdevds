package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;

@Component
public class StatusMongoEventListener extends AbstractMongoEventListener<Status> {

    private static final Logger logger = Logger.getLogger(StatusMongoEventListener.class);

    private final BookRepository bookRepository;

    StatusMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Status> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            Status status = book.getStatus();
            if (status != null && status.getId().equals(id))
                throw new ForeignKeyException("Error delete Status because Book: "
                        + books.get(0).getName() + " related to Status: " + event.getSource().get("name"));
        }
        logger.debug("StatusMongoEventListener delete");
    }
}
