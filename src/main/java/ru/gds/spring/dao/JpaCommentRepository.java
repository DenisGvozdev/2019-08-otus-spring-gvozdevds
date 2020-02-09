package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Comment;
import ru.gds.spring.interfaces.CommentRepository;

import javax.persistence.*;
import java.util.List;

@Transactional
@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Comment comment) {
        Query query = em.createQuery("update Comment c" +
                " set c.book = :book, " +
                " c.comment = :comment, " +
                " c.createDate = :createDate " +
                " where c.id = :id");
        query.setParameter("book", comment.getBook());
        query.setParameter("comment", comment.getComment());
        query.setParameter("createDate", comment.getCreateDate());
        query.setParameter("id", comment.getId());
        query.executeUpdate();
        return true;
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c "
                + " join fetch c.book where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
