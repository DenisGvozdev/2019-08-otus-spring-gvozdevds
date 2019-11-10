package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcBookRepository implements BookRepository {

    private NamedParameterJdbcTemplate jdbc;

    private final String INSERT = "INSERT INTO BOOKS (`NAME`, CREATE_DATE, DESCRIPTION, `IMAGE`, GENRE, STATUS, AUTHOR) " +
            "VALUES (:NAME, :CREATE_DATE, :DESCRIPTION, :IMAGE, :GENRE, :STATUS, :AUTHOR)";

    private final String SELECT_ALL = "SELECT b.ID, b.NAME, b.CREATE_DATE, b.DESCRIPTION, b.IMAGE, " +
            " g.ID AS GENRE_ID, g.NAME AS GENRE_NAME, " +
            " s.ID AS STATUS_ID, s.NAME AS STATUS_NAME, " +
            " a.ID AS AUTHOR_ID, a.FIRSTNAME AS AUTHOR_FIRSTNAME, " +
            " a.SECONDNAME AS AUTHOR_SECONDNAME, a.THIRDNAME AS AUTHOR_THIRDNAME, " +
            " a.BIRTH_DATE AS AUTHOR_BIRTH_DATE, " +
            " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME" +
            " FROM BOOKS b " +
            " LEFT JOIN GENRES g ON b.GENRE = g.ID " +
            " LEFT JOIN AUTHORS a ON b.AUTHOR = a.ID " +
            " LEFT JOIN STATUSES s ON b.STATUS = s.ID " +
            " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID ";

    private final String SELECT_BY_ID = "SELECT b.ID, b.NAME, b.CREATE_DATE, b.DESCRIPTION, b.IMAGE, " +
            " g.ID AS GENRE_ID, g.NAME AS GENRE_NAME, " +
            " s.ID AS STATUS_ID, s.NAME AS STATUS_NAME, " +
            " a.ID AS AUTHOR_ID, a.FIRSTNAME AS AUTHOR_FIRSTNAME, " +
            " a.SECONDNAME AS AUTHOR_SECONDNAME, a.THIRDNAME AS AUTHOR_THIRDNAME, " +
            " a.BIRTH_DATE AS AUTHOR_BIRTH_DATE, " +
            " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME" +
            " FROM BOOKS b " +
            " LEFT JOIN GENRES g ON b.GENRE = g.ID " +
            " LEFT JOIN AUTHORS a ON b.AUTHOR = a.ID " +
            " LEFT JOIN STATUSES s ON b.STATUS = s.ID " +
            " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID " +
            " WHERE b.ID = :ID";

    String DELETE_BY_ID = "DELETE FROM BOOKS WHERE ID = :ID";

    String UPDATE_BY_ID = "UPDATE BOOKS SET" +
            " NAME = :NAME," +
            " CREATE_DATE = :CREATE_DATE," +
            " DESCRIPTION = :DESCRIPTION," +
            " IMAGE = :IMAGE," +
            " GENRE = :GENRE," +
            " STATUS = :STATUS," +
            " AUTHOR = :AUTHOR" +
            " WHERE ID = :ID";

    @Autowired
    JdbcBookRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbc = namedJdbcTemplate;
    }

    @Override
    public boolean insert(Book book) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("NAME", book.getName());
            params.put("CREATE_DATE", book.getCreateDate());
            params.put("DESCRIPTION", book.getDescription());
            params.put("IMAGE", book.getImage());
            params.put("GENRE", (book.getGenre() != null) ? book.getGenre().getId() : null);
            params.put("STATUS", (book.getStatus() != null) ? book.getStatus().getId() : null);
            params.put("AUTHOR", (book.getAuthor() != null) ? book.getAuthor().getId() : null);
            jdbc.update(INSERT, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Book> getAll() {
        try {
            return jdbc.getJdbcOperations().query(SELECT_ALL, new BookMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

    @Override
    public Book getById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            return jdbc.queryForObject(SELECT_BY_ID, params, new BookMapper());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            jdbc.update(DELETE_BY_ID, params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Book book) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", book.getId());
            params.put("NAME", book.getName());
            params.put("CREATE_DATE", book.getCreateDate());
            params.put("DESCRIPTION", book.getDescription());
            params.put("IMAGE", book.getImage());
            params.put("GENRE", (book.getGenre() != null) ? book.getGenre().getId() : null);
            params.put("STATUS", (book.getStatus() != null) ? book.getStatus().getId() : null);
            params.put("AUTHOR", (book.getAuthor() != null) ? book.getAuthor().getId() : null);
            jdbc.update(UPDATE_BY_ID, params);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            Date createDate = resultSet.getDate("CREATE_DATE");
            String description = resultSet.getString("DESCRIPTION");
            byte[] image = resultSet.getBytes("IMAGE");

            Genre genre = new Genre();
            genre.setId(resultSet.getLong("GENRE_ID"));
            genre.setName(resultSet.getString("GENRE_NAME"));

            Status status = new Status();
            status.setId(resultSet.getLong("STATUS_ID"));
            status.setName(resultSet.getString("STATUS_NAME"));

            Author author = new Author();
            author.setId(resultSet.getLong("AUTHOR_ID"));
            author.setFirstName(resultSet.getString("AUTHOR_FIRSTNAME"));
            author.setSecondName(resultSet.getString("AUTHOR_SECONDNAME"));
            author.setThirdName(resultSet.getString("AUTHOR_THIRDNAME"));
            author.setBirthDate(resultSet.getDate("AUTHOR_BIRTH_DATE"));

            Status authorStatus = new Status();
            authorStatus.setId(resultSet.getLong("AUTHOR_STATUS_ID"));
            authorStatus.setName(resultSet.getString("AUTHOR_STATUS_NAME"));
            author.setStatus(authorStatus);

            return new Book(id, name, createDate, description, image, genre, status, author);
        }
    }
}
