package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Comment;
import ru.gds.spring.microservice.dto.CommentDto;
import ru.gds.spring.microservice.dto.UserDto;
import ru.gds.spring.microservice.interfaces.BookRepository;
import ru.gds.spring.microservice.interfaces.CommentRepository;
import ru.gds.spring.microservice.interfaces.CommentService;
import org.springframework.util.StringUtils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.gds.spring.microservice.params.ParamsBookPageComment;
import ru.gds.spring.microservice.util.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = Logger.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final MongoTemplate mongoTemplate;
    private final CommonUtils commonUtils;

    CommentServiceImpl(
            CommentRepository commentRepository,
            BookRepository bookRepository,
            MongoTemplate mongoTemplate,
            CommonUtils commonUtils) {

        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.commonUtils = commonUtils;
    }

    @Override
    public CommentDto save(ParamsBookPageComment params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getBookId()))
                throw new Exception("BookId is empty");

            if (StringUtils.isEmpty(params.getPage()))
                throw new Exception("Page is empty");

            if (StringUtils.isEmpty(params.getComment()))
                throw new Exception("Comment is empty");

            Book book = bookRepository.findById(params.getBookId()).orElse(new Book());

            if (StringUtils.isEmpty(book.getId()))
                throw new Exception("Book not found");

            UserDto userDto = commonUtils.getCurrentUser();
            Date createDate = new Date();

            Comment comment = new Comment(book, params.getComment(), createDate, userDto.getUsername());
            return CommentDto.toDto(commentRepository.save(comment));

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new CommentDto();
    }

    @Override
    public CommentDto findById(String id) {
        try {
            if (StringUtils.isEmpty(id))
                return new CommentDto();

            return CommentDto.toDto(
                    commentRepository.findById(id).orElse(new Comment()));

        } catch (Exception e) {
            logger.error("Comment not found by id= " + id + ". Error: " + e.getMessage());
        }
        return new CommentDto();
    }

    @Override
    public List<CommentDto> findByParam(String bookId, String username, Date createDate) {
        try {
            Query query = new Query();

            if (!StringUtils.isEmpty(bookId)) {
                query.addCriteria(Criteria.where("bookId").is(bookId));
            }
            if (!StringUtils.isEmpty(username)) {
                query.addCriteria(Criteria.where("username").is(username));
            }
            if (createDate != null) {
                query.addCriteria(Criteria.where("createDate").is(createDate));
            }
            return mongoTemplate
                    .find(query, Comment.class)
                    .stream()
                    .map(CommentDto::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Comment not found: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
