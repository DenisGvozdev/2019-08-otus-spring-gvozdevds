package ru.gds.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.BookRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorCascadeEventListener extends AbstractMongoEventListener<Author> {

    //@Autowired
//    private final BookRepository bookRepository;
//
//    AuthorCascadeEventListener(BookRepository bookRepository){
//        this.bookRepository = bookRepository;
//    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        event.getDocument();
//        val source = event.getSource();
//        val id = source.get("_id").toString();
//        List<Book> list = bookRepository.findAllByAuthorId(id);
//        if(list!=null && !list.isEmpty()){
//            throw new ForeignKeyException("ForeignKeyException!");
//        }
    }

}
