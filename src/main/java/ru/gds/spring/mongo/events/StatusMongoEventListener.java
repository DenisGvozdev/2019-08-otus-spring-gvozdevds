package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.mongo.domain.BookMongo;
import ru.gds.spring.mongo.domain.StatusMongo;
import ru.gds.spring.mongo.interfaces.BookMongoRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;

@Component
public class StatusMongoEventListener extends AbstractMongoEventListener<StatusMongo> {

    private static final Logger logger = Logger.getLogger(StatusMongoEventListener.class);

    private final BookMongoRepository bookRepository;

    StatusMongoEventListener(BookMongoRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<StatusMongo> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<BookMongo> books = bookRepository.findAllByStatusId(id);
        if (!books.isEmpty())
            throw new ForeignKeyException("Error delete Status because Book: "
                    + books.get(0).getName() + " related to Status: " + books.get(0).getStatus().getName());
        logger.debug("StatusMongoEventListener delete");
    }
}
