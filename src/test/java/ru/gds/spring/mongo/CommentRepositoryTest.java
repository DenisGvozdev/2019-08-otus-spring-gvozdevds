package ru.gds.spring.mongo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
//import ru.gds.spring.mongo.domain.BookMongo;
//import ru.gds.spring.mongo.domain.CommentMongo;
//import ru.gds.spring.mongo.interfaces.CommentMongoRepository;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class CommentRepositoryTest {

//    @Autowired
//    CommentMongoRepository commentRepository;
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Test
//    void insertCommentTest() {
//        BookMongo book = getFirstBook();
//        CommentMongo comment = new CommentMongo(book, "Тестовый комментарий", new Date());
//        comment = commentRepository.save(comment);
//        assumeTrue(comment.getId() != null);
//    }
//
//    @Test
//    void findCommentByBookTest() {
//        CommentMongo comment = getFirstComment();
//        assumeTrue(comment != null && comment.getBook() != null);
//
//        List<CommentMongo> commentList = commentRepository.findByBookId(comment.getBook().getId(), null);
//        assumeTrue(!commentList.isEmpty());
//    }
//
//    @Test
//    void deleteCommentTest() {
//        List<CommentMongo> commentList = getCommentList();
//        int countCommets = commentList.size();
//        assumeTrue(countCommets > 0);
//
//        commentRepository.deleteById(commentList.get(0).getId());
//
//        commentList = getCommentList();
//        assumeTrue(commentList.size() == countCommets - 1);
//    }
//
//    private List<CommentMongo> getCommentList() {
//        return commentRepository.findAll();
//    }
//
//    private CommentMongo getFirstComment() {
//        List<CommentMongo> listComment = commentRepository.findAll();
//        return (!listComment.isEmpty()) ? listComment.get(0) : null;
//    }
//
//    private BookMongo getFirstBook() {
//        List<BookMongo> listBook = mongoTemplate.findAll(BookMongo.class, "books");
//        return (listBook.isEmpty()) ? null : listBook.get(0);
//    }
}
