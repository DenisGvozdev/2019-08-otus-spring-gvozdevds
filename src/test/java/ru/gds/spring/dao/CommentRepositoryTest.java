package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Comment;
import ru.gds.spring.interfaces.CommentRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final Logger logger = Logger.getLogger(CommentRepositoryTest.class);

    @Test
    void insertCommentTest() {
        Book book = getFirstBook();
        assumeTrue(book != null);

        Comment comment = new Comment(book, "Тестовый комментарий", new Date());
        comment = commentRepository.save(comment);
        boolean result = comment.getId() > 0;
        logger.debug("Комментарий добавлен: " + result);
        assumeTrue(result);

        List<Comment> commentList = getCommentList();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 3);
    }

    @Test
    void findCommentByBookTest() {
        Book book = getFirstBook();
        assumeTrue(book != null);

        List<Comment> commentList = commentRepository.findByBookId(book.getId());
        logger.debug("Все комментарии к книге " + book.getName() + ": " + commentList);
        assumeTrue(commentList.size() == 1);
    }

    @Test
    void deleteCommentTest() {
        List<Comment> commentList = getCommentList();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 2);

        commentRepository.deleteById(commentList.get(1).getId());
        logger.debug("Комментарий удален");

        commentList = getCommentList();
        assumeTrue(commentList.size() == 1);
    }

    private List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    private Book getFirstBook() {
        List<Book> bookList = entityManager.getEntityManager()
                .createQuery("select b from Book b", Book.class)
                .getResultList();
        return (!bookList.isEmpty()) ? bookList.get(0) : null;
    }
}
