package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Comment;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.CommentRepository;

import java.util.Date;
import java.util.List;

import static org.junit.Assume.assumeTrue;

@DataJpaTest
@Import({JpaBookRepository.class,
        JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    BookRepository jpaBookRepository;

    @Autowired
    CommentRepository jpaCommentRepository;

    private static final Logger logger = Logger.getLogger(JpaCommentRepositoryTest.class);

    @Test
    void insertCommentTest() {

        List<Book> bookList = jpaBookRepository.findAll();
        assumeTrue(!bookList.isEmpty());
        Book book = bookList.get(0);

        Comment comment = new Comment(book, "Тестовый комментарий", new Date());
        comment = jpaCommentRepository.save(comment);
        boolean result = comment.getId() > 0;
        logger.debug("Комментарий добавлен: " + result);
        assumeTrue(result);

        List<Comment> commentList = jpaCommentRepository.findAll();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 3);
    }

    @Test
    void findCommentByBokTest() {

        List<Book> bookList = jpaBookRepository.findAll();
        assumeTrue(!bookList.isEmpty());
        Book book = bookList.get(0);

        List<Comment> commentList = jpaCommentRepository.findByBookId(book.getId());
        logger.debug("Все комментарии к книге " + book.getName() + ": " + commentList);
        assumeTrue(commentList.size() == 1);
    }

    @Test
    void deleteCommentTest() {

        List<Comment> commentList = jpaCommentRepository.findAll();
        logger.debug("Все комментарии: " + commentList);
        assumeTrue(commentList.size() == 2);

        boolean result = jpaCommentRepository.deleteById(commentList.get(1).getId());
        logger.debug("Комментарий удален: " + result);
        assumeTrue(result);

        commentList = jpaCommentRepository.findAll();
        assumeTrue(commentList.size() == 1);
    }
}
