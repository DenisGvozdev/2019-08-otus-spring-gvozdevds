package ru.gds.spring;

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
@ComponentScan({"ru.gds.spring"})
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void insertCommentTest() {
        Book book = getFirstBook();
        Comment comment = new Comment(book, "Тестовый комментарий", new Date());
        comment = commentRepository.save(comment);
        assumeTrue(comment.getId() != null);
    }

    @Test
    void findCommentByBookTest() {
        Comment comment = getFirstComment();
        assumeTrue(comment != null && comment.getBook() != null);

        List<Comment> commentList = commentRepository.findByBookId(comment.getBook().getId(), null);
        assumeTrue(!commentList.isEmpty());
    }

    @Test
    void deleteCommentTest() {
        List<Comment> commentList = getCommentList();
        int countCommets = commentList.size();
        assumeTrue(countCommets > 0);

        commentRepository.deleteById(commentList.get(0).getId());

        commentList = getCommentList();
        assumeTrue(commentList.size() == countCommets - 1);
    }

    private List<Comment> getCommentList() {
        return commentRepository.findAll();
    }

    private Comment getFirstComment() {
        List<Comment> listComment = commentRepository.findAll();
        return (!listComment.isEmpty()) ? listComment.get(0) : null;
    }

    private Book getFirstBook() {
        List<Book> listBook = mongoTemplate.findAll(Book.class, "books");
        return (listBook.isEmpty()) ? null : listBook.get(0);
    }
}
