package ru.gds.spring.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gds.spring.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
