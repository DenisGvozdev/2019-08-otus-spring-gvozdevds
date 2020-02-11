package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Comment;
import ru.gds.spring.interfaces.CommentRepository;
import ru.gds.spring.interfaces.CommentRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private CommentRepository commentRepository;

    CommentRepositoryImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findById(long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c "
                + " join fetch c.book where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
