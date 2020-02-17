package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Comment;
import ru.gds.spring.interfaces.CommentRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring.mongo"})
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger logger = Logger.getLogger(CommentRepositoryTest.class);

    @Test
    void insertCommentTest() {
        Book book = getFirstBook();
        Comment comment = new Comment(book, "Тестовый комментарий", new Date());
        commentRepository.save(comment);
        logger.debug("Комментарий добавлен");

        List<Comment> commentList = getCommentList();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 3);
    }

    @Test
    void findCommentByBookTest() {
        Comment comment = getFirstComment();
        assumeTrue(comment != null && comment.getBook() != null);

        List<Comment> commentList = commentRepository.findByBookId(comment.getBook().getId(), null);
        logger.debug("Все комментарии к книге " + comment.getBook().getName() + ": " + commentList);
        assumeTrue(commentList.size() == 2);
    }

    @Test
    void deleteCommentTest() {
        List<Comment> commentList = getCommentList();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 2);

        commentRepository.deleteById(commentList.get(0).getId());
        logger.debug("Комментарий удален");

        commentList = getCommentList();
        assumeTrue(commentList.size() == 1);
    }

    private List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    private Comment getFirstComment() {
        List<Comment> listComment = commentRepository.findAll();
        return (listComment.isEmpty()) ? null : listComment.get(0);
    }

    private Book getFirstBook() {
        List<Book> listBook = mongoTemplate.findAll(Book.class, "books");
        return (listBook.isEmpty()) ? null : listBook.get(0);
    }
}
