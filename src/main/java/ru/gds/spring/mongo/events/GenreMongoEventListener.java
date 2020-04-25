package ru.gds.spring.mongo.events;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import ru.gds.spring.mongo.domain.BookMongo;
import ru.gds.spring.mongo.domain.GenreMongo;
import ru.gds.spring.mongo.interfaces.BookMongoRepository;
import ru.gds.spring.mongo.exceptions.ForeignKeyException;

import java.util.List;

@Component
public class GenreMongoEventListener extends AbstractMongoEventListener<GenreMongo> {

    private static final Logger logger = Logger.getLogger(GenreMongoEventListener.class);

    private final BookMongoRepository bookRepository;

    GenreMongoEventListener(BookMongoRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<GenreMongo> event) {
        super.onBeforeDelete(event);
        event.getDocument();
        String id = event.getSource().get("_id").toString();

        List<BookMongo> books = bookRepository.findAllByGenresId(id);
        if (!books.isEmpty())
            throw new ForeignKeyException("Error delete Genre because Book: "
                    + books.get(0).getName() + " related to Genre: " + books.get(0).getGenres());
        logger.debug("GenreMongoEventListener delete");
    }
}
