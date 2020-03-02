package ru.gds.spring.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{_id: { $in: ?0 } })")
    List<Book> findAllById(List<String> ids, Sort sort);

    @Query("{genres._id: { $in: ?0 } })")
    List<Book> findAllByGenreId(String genreId);

    @Query("{authors._id: { $in: ?0 } })")
    List<Book> findAllByAuthorId(String authorId);
}
