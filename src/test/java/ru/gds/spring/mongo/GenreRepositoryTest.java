package ru.gds.spring.mongo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
//import ru.gds.spring.mongo.domain.GenreMongo;
//import ru.gds.spring.mongo.interfaces.GenreMongoRepository;

import static org.junit.Assume.assumeTrue;

@DataMongoTest
@ComponentScan({"ru.gds.spring"})
class GenreRepositoryTest {

//    @Autowired
//    GenreMongoRepository genreRepository;
//
//    @Test
//    void insertGenreTest() {
//        GenreMongo genre = new GenreMongo("Исторический");
//        genre = genreRepository.save(genre);
//        assumeTrue(genre.getId() != null);
//    }
//
//    @Test
//    void updateGenreTest() {
//        GenreMongo genre = getGenreByName("Приключения");
//        assumeTrue(genre != null);
//
//        String genreName = "Приключения обновление";
//        genre.setName(genreName);
//        genre = genreRepository.save(genre);
//        assumeTrue(genreName.equals(genre.getName()));
//    }
//
//    @Test
//    void deleteGenreTest() {
//        GenreMongo genre = getGenreByName("Временный");
//        assumeTrue(genre != null);
//
//        genreRepository.deleteById(genre.getId());
//
//        genre = getGenreByName("Временный");
//        assumeTrue(genre == null);
//    }
//
//    private GenreMongo getGenreByName(String name) {
//        List<String> nameList = new ArrayList<>();
//        nameList.add(name);
//        List<GenreMongo> genreList = genreRepository.findAllByName(nameList, null);
//        return (!genreList.isEmpty()) ? genreList.get(0) : null;
//    }
}
