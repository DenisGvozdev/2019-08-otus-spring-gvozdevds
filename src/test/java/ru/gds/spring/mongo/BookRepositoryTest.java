package ru.gds.spring.mongo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
//import ru.gds.spring.mongo.domain.AuthorMongo;
//import ru.gds.spring.mongo.domain.BookMongo;
//import ru.gds.spring.mongo.domain.GenreMongo;
//import ru.gds.spring.mongo.domain.StatusMongo;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class BookRepositoryTest {

//    @Autowired
//    BookMongoRepository bookRepository;
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    @Test
//    void insertBookTest() {
//        BookMongo book = new BookMongo(
//                "Мастер и Маргарита",
//                new Date(),
//                "Классика",
//                FileUtils.getFileBytes("images/MBulgakov_MasterIMargarita.jpg"),
//                getGenreList(),
//                getAuthorList(),
//                getFirstStatus());
//        book = bookRepository.save(book);
//        assumeTrue(book.getId() != null);
//    }
//
//    @Test
//    void updateBookTest() {
//        BookMongo book = getFirstBook();
//        assumeTrue(book != null);
//
//        String bookId = book.getId();
//        String bookName = "Кольцо тьмы обновление";
//        book.setName(bookName);
//        book.setDescription("Сказки");
//        book.setGenres(getGenreList());
//        book.setAuthors(getAuthorList());
//        book.setStatus(getFirstStatus());
//        book.setImage(FileUtils.getFileBytes("images/NPerumov_KoltsoTmy.jpg"));
//        book = bookRepository.save(book);
//        assumeTrue(bookName.equals(book.getName()));
//    }
//
//    @Test
//    void deleteBookTest() {
//        int countAuthors = getAuthorList().size();
//        int countGenres = getGenreList().size();
//        int countStatuses = getStatusList().size();
//
//        BookMongo book = getFirstBook();
//        assumeTrue(book != null);
//
//        bookRepository.deleteById(book.getId());
//        book = getBookById(book.getId());
//        assumeTrue(book == null);
//
//        // Убеждаемся что не отработало каскадное удаление
//        assumeTrue(getAuthorList().size() == countAuthors);
//        assumeTrue(getGenreList().size() == countGenres);
//        assumeTrue(getStatusList().size() == countStatuses);
//    }
//
//    private BookMongo getBookById(String id) {
//        return bookRepository.findById(id).orElse(null);
//    }
//
//    private BookMongo getFirstBook() {
//        List<BookMongo> bookList = bookRepository.findAll();
//        return bookList.get(0);
//    }
//
//    private List<AuthorMongo> getAuthorList() {
//        Query query = Query.query(Criteria.where("thirdName").is("Перумов"));
//        return mongoTemplate.find(query, AuthorMongo.class);
//    }
//
//    private List<GenreMongo> getGenreList() {
//        Query query = Query.query(Criteria.where("name").is("Фэнтези"));
//        return mongoTemplate.find(query, GenreMongo.class);
//    }
//
//    private StatusMongo getFirstStatus() {
//        List<StatusMongo> list = mongoTemplate.findAll(StatusMongo.class, "statuses");
//        return (list.isEmpty()) ? null : list.get(0);
//    }
//
//    private List<StatusMongo> getStatusList() {
//        return mongoTemplate.findAll(StatusMongo.class, "statuses");
//    }
}