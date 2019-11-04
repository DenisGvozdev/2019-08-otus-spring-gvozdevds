package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.BookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Boolean insert(String name, Date createDate,
                          String description, byte[] image,
                          long genre, long status, long author) {

        String sql = "INSERT INTO BOOKS (`NAME`, CREATE_DATE, DESCRIPTION, `IMAGE`, GENRE, STATUS, AUTHOR)" +
                " VALUES (?,?,?,?,?,?,?)";

        jdbc.update(sql, name, createDate, description, image, genre, status, author);
        return true;
    }

    @Override
    public List<Book> getAll() {

        String sql = "SELECT b.ID, b.NAME, b.CREATE_DATE, b.DESCRIPTION, b.IMAGE, " +
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

        return jdbc.query(sql, new BookMapper());
    }

    @Override
    public Book getById(long id) {

        String sql = "SELECT b.ID, b.NAME, b.CREATE_DATE, b.DESCRIPTION, b.IMAGE, " +
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
                " WHERE b.ID = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, new BookMapper());
    }

    @Override
    public Boolean removeById(long id) {

        String sql = "DELETE FROM BOOKS WHERE ID = ?";

        jdbc.update(sql, id);
        return true;
    }

    @Override
    public Boolean update(
            long id,
            String name,
            Date createDate,
            String description,
            byte[] image,
            long genre,
            long status,
            long author) {

        String sql = "UPDATE BOOKS SET" +
                " NAME = ?," +
                " CREATE_DATE = ?," +
                " DESCRIPTION = ?," +
                " IMAGE = ?," +
                " GENRE = ?," +
                " STATUS = ?," +
                " AUTHOR = ?" +
                " WHERE ID = ?";

        jdbc.update(sql, name, createDate, description, image, genre, status, author, id);
        return true;
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
