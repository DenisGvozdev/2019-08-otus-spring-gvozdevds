package ru.gds.spring.mongo.config;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.gds.spring.jpa.domain.*;
import ru.gds.spring.jpa.interfaces.AuthorJpaRepository;
import ru.gds.spring.jpa.interfaces.BookJpaRepository;
import ru.gds.spring.jpa.interfaces.GenreJpaRepository;
import ru.gds.spring.jpa.interfaces.StatusJpaRepository;
import ru.gds.spring.mongo.domain.*;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings("all")
@EnableBatchProcessing
@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("JobConfig");

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String INPUT_FILE_NAME = "inputFileName";
    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    AuthorJpaRepository authorJpaRepository;

    @Autowired
    GenreJpaRepository genreJpaRepository;

    @Autowired
    StatusJpaRepository statusJpaRepository;

    @Autowired
    BookJpaRepository bookJpaRepository;

    /* ------------ Миграция авторов ------------ */

    @Bean
    //@Primary
    @Qualifier("importAuthorJob")
    public Job importAuthorJob(@Qualifier("stepAuthors") Step stepAuthors) {
        return jobBuilderFactory.get("importAuthorJob")
                //.preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(stepAuthors)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job authors");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job authors");
                    }
                })
                .build();
    }

    @Bean

    public Step stepAuthors(JdbcBatchItemWriter<Author> writer, ItemReader<AuthorMongo> reader, ItemProcessor processorAuthor) {
        return stepBuilderFactory.get("stepAuthors")
                .chunk(5)
                .reader(reader)
                .processor(processorAuthor)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения AuthorMongo");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения AuthorMongo");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения AuthorMongo");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи Author");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи Author");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи Author");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки Author");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки Author");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки Author");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки Author");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки Author");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки Author");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<AuthorMongo> mongoReaderAuthors(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("mongoReaderAuthors")
                .sorts(new HashMap<>())
                .jsonQuery("{}")
                .template(mongoTemplate)
                .collection("authors")
                .targetType(AuthorMongo.class)
                .build();
    }

    @Bean
    public ItemProcessor<AuthorMongo, Author> processorAuthor() {
        return (ItemProcessor<AuthorMongo, Author>) authorMongo -> {
            Author author = new Author();
            author.setId(authorMongo.getId().hashCode());
            author.setFirstName(authorMongo.getFirstName());
            author.setSecondName(authorMongo.getSecondName());
            author.setThirdName(authorMongo.getThirdName());
            author.setBirthDate(authorMongo.getBirthDate());
            return author;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Author> writerAuthor(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Author>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Author>())
                .sql("INSERT INTO AUTHORS (ID, FIRSTNAME, SECONDNAME, THIRDNAME, BIRTH_DATE) " +
                        "VALUES (:id, :firstName, :secondName, :thirdName, :birthDate)")
                .dataSource(dataSource)
                .build();
    }

    /* ------------ Миграция жанров ------------ */

    @Bean
    @Qualifier("importGenreJob")
    public Job importGenreJob(@Qualifier("stepGenres") Step stepGenres) {
        return jobBuilderFactory.get("importGenreJob")
                //.preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(stepGenres)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job genres");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job genres");
                    }
                })
                .build();
    }

    @Bean
    public Step stepGenres(JdbcBatchItemWriter<Genre> writer, ItemReader<GenreMongo> reader, ItemProcessor processorGenre) {
        return stepBuilderFactory.get("stepGenres")
                .chunk(5)
                .reader(reader)
                .processor(processorGenre)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения GenreMongo");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения GenreMongo");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения GenreMongo");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи Genre");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи Genre");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи Genre");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки Genre");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки Genre");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки Genre");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки Genre");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки Genre");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки Genre");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<GenreMongo> mongoReaderGenres(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("mongoReaderGenres")
                .sorts(new HashMap<>())
                .jsonQuery("{}")
                .template(mongoTemplate)
                .collection("genres")
                .targetType(GenreMongo.class)
                .build();
    }

    @Bean
    public ItemProcessor<GenreMongo, Genre> processorGenre() {
        return (ItemProcessor<GenreMongo, Genre>) genreMongo -> {
            Genre genre = new Genre();
            genre.setId(genreMongo.getId().hashCode());
            genre.setName(genreMongo.getName());
            return genre;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Genre> writerGenre(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Genre>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Genre>())
                .sql("INSERT INTO GENRES (ID, `NAME`) VALUES (:id, :name)")
                .dataSource(dataSource)
                .build();
    }

    /* ------------ Миграция статусов ------------ */

    @Bean
    @Qualifier("importStatusJob")
    public Job importStatusJob(@Qualifier("stepStatuses") Step stepStatuses) {
        return jobBuilderFactory.get("importStatusJob")
                //.preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(stepStatuses)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job statuses");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job statuses");
                    }
                })
                .build();
    }

    @Bean
    public Step stepStatuses(JdbcBatchItemWriter<Status> writer, ItemReader<StatusMongo> reader, ItemProcessor processorStatus) {
        return stepBuilderFactory.get("stepStatuses")
                .chunk(5)
                .reader(reader)
                .processor(processorStatus)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения StatusMongo");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения StatusMongo");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения StatusMongo");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи Status");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи Status");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи Status");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки Status");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки Status");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки Status");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки Status");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки Status");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки Status");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<StatusMongo> mongoReaderStatuses(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<StatusMongo>()
                .name("mongoReaderStatuses")
                .sorts(new HashMap<>())
                .jsonQuery("{}")
                .template(mongoTemplate)
                .collection("statuses")
                .targetType(StatusMongo.class)
                .build();
    }

    @Bean
    public ItemProcessor<StatusMongo, Status> processorStatus() {
        return (ItemProcessor<StatusMongo, Status>) statusMongo -> {
            Status status = new Status();
            status.setId(statusMongo.getId().hashCode());
            status.setName(statusMongo.getName());
            return status;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Status> writerStatus(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Status>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Status>())
                .sql("INSERT INTO STATUSES (ID, `NAME`) VALUES (:id, :name)")
                .dataSource(dataSource)
                .build();
    }

    /* ------------ Миграция книг ------------ */

    @Bean
    @Qualifier("importBookJob")
    public Job importBookJob(@Qualifier("stepBooks") Step stepBooks) {
        return jobBuilderFactory.get("importBookJob")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(stepBooks)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job books");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job books");
                    }
                })
                .build();
    }

    @Bean
    public Step stepBooks(JpaItemWriter<Book> writer, ItemReader<BookMongo> reader, ItemProcessor processorBook) {
        return stepBuilderFactory.get("stepBooks")
                .chunk(5)
                .reader(reader)
                .processor(processorBook)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения BookMongo");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения BookMongo");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения BookMongo");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи Book");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи Book");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи Book");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки Book");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки Book");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки Book");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки Book");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки Book");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки Book");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<BookMongo> mongoReaderBooks(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("mongoReaderBooks")
                .sorts(new HashMap<>())
                .jsonQuery("{}")
                .template(mongoTemplate)
                .collection("books")
                .targetType(BookMongo.class)
                .build();
    }

    @Bean
    public ItemProcessor<BookMongo, Book> processorBook() {
        return (ItemProcessor<BookMongo, Book>) bookMongo -> {

            List<Long> genreIdList = bookMongo.getGenres()
                    .stream()
                    .map(genreMongo -> Long.valueOf(genreMongo.getId().hashCode()))
                    .collect(Collectors.toList());
            List<Genre> genreList = genreJpaRepository.findAllById(genreIdList);

            List<Long> authorIdList = bookMongo.getAuthors()
                    .stream()
                    .map(authorMongo -> Long.valueOf(authorMongo.getId().hashCode()))
                    .collect(Collectors.toList());
            List<Author> authorList = authorJpaRepository.findAllById(authorIdList);

            Status status = statusJpaRepository.findById(Long.valueOf(bookMongo.getStatus().getId().hashCode())).orElse(null);

            if (genreList.isEmpty() || authorList.isEmpty() || status == null)
                throw new ServiceException("error migrate book " + bookMongo.getName()
                        + " because authors or genres or status is empty");

            Book book = new Book();
            book.setId(bookMongo.getId().hashCode());
            book.setName(bookMongo.getName());
            book.setImage(bookMongo.getImage());
            book.setCreateDate(bookMongo.getCreateDate());
            book.setDescription(bookMongo.getDescription());
            book.setStatus(status);
            book.setAuthors(new HashSet<Author>(authorList));
            book.setGenres(new HashSet<Genre>(genreList));

            return book;
        };
    }

    @Bean
    public JpaItemWriter<Book> writerBook(DataSource dataSource) {
        JpaItemWriter<Book> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    /* ------------ Миграция комментариев ------------ */

    @Bean
    @Qualifier("importCommentJob")
    public Job importCommentJob(@Qualifier("stepComments") Step stepComments) {
        return jobBuilderFactory.get("importCommentJob")
                //.preventRestart()
                .incrementer(new RunIdIncrementer())
                .flow(stepComments)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job comments");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job comments");
                    }
                })
                .build();
    }

    @Bean
    public Step stepComments(JdbcBatchItemWriter<Comment> writer, ItemReader<CommentMongo> reader, ItemProcessor processorComment) {
        return stepBuilderFactory.get("stepComments")
                .chunk(5)
                .reader(reader)
                .processor(processorComment)
                .writer(writer)
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения CommentMongo");
                    }

                    public void afterRead(Object o) {
                        logger.info("Конец чтения CommentMongo");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения CommentMongo");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи Comment");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи Comment");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи Comment");
                    }
                })
                .listener(new ItemProcessListener() {
                    public void beforeProcess(Object o) {
                        logger.info("Начало обработки Comment");
                    }

                    public void afterProcess(Object o, Object o2) {
                        logger.info("Конец обработки Comment");
                    }

                    public void onProcessError(Object o, Exception e) {
                        logger.info("Ошбка обработки Comment");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {
                        logger.info("Начало пачки Comment");
                    }

                    public void afterChunk(ChunkContext chunkContext) {
                        logger.info("Конец пачки Comment");
                    }

                    public void afterChunkError(ChunkContext chunkContext) {
                        logger.info("Ошибка пачки Comment");
                    }
                })
                .build();
    }

    @Bean
    public ItemReader<CommentMongo> mongoReaderComments(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<CommentMongo>()
                .name("mongoReaderComments")
                .sorts(new HashMap<>())
                .jsonQuery("{}")
                .template(mongoTemplate)
                .collection("comments")
                .targetType(CommentMongo.class)
                .build();
    }

    @Bean
    public ItemProcessor<CommentMongo, Comment> processorComment() {
        return (ItemProcessor<CommentMongo, Comment>) commentMongo -> {

            List<Book> bookList = bookJpaRepository.findAllByName(commentMongo.getBook().getName());
            if (bookList.isEmpty())
                throw new ServiceException("error migrate comment for book "
                        + commentMongo.getBook().getName()
                        + " because book not found");

            Comment comment = new Comment();
            comment.setId(commentMongo.hashCode());
            comment.setCreateDate(commentMongo.getCreateDate());
            comment.setComment(commentMongo.getComment());
            comment.setBook(bookList.get(0));
            return comment;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Comment> writerComment(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Comment>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Comment>())
                .sql("INSERT INTO COMMENTS (ID, BOOK_ID, COMMENT, CREATE_DATE) " +
                        " VALUES (:id, :book.id, :comment, :createDate)")
                .dataSource(dataSource)
                .build();
    }
}
