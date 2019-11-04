package ru.gds.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public boolean insert(String firstName, String secondName, String thirdName, Date birthDate, long status) {

        String sql = "INSERT INTO AUTHORS (FIRSTNAME, SECONDNAME, THIRDNAME, BIRTH_DATE, STATUS) " +
                " VALUES (?,?,?,?,?)";

        jdbc.update(sql, firstName, secondName, thirdName, birthDate, status);
        return true;
    }

    @Override
    public List<Author> getAll() {

        String sql = "SELECT a.ID, a.FIRSTNAME, a.SECONDNAME, a.THIRDNAME, a.BIRTH_DATE, a.STATUS, " +
                " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME " +
                " FROM AUTHORS a " +
                " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID ";

        return jdbc.query(sql, new JdbcAuthorRepository.AuthorMapper());
    }

    @Override
    public Author getById(long id) {

        String sql = "SELECT a.ID, a.FIRSTNAME, a.SECONDNAME, a.THIRDNAME, a.BIRTH_DATE, a.STATUS, " +
                " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME " +
                " FROM AUTHORS a " +
                " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID " +
                " WHERE a.ID = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, new JdbcAuthorRepository.AuthorMapper());
    }

    @Override
    public boolean removeById(long id) {

        String sql = "DELETE FROM AUTHORS WHERE ID = ?";

        jdbc.update(sql, id);
        return true;
    }

    @Override
    public boolean update(long id, String firstName, String secondName, String thirdName, Date birthDate, long status) {

        String sql = "UPDATE AUTHORS SET" +
                " FIRSTNAME = ?," +
                " SECONDNAME = ?," +
                " THIRDNAME = ?," +
                " BIRTH_DATE = ?," +
                " STATUS = ?" +
                " WHERE ID = ?";

        jdbc.update(sql, firstName, secondName, thirdName, birthDate, status, id);
        return true;
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {

            long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRSTNAME");
            String secondName = resultSet.getString("SECONDNAME");
            String thirdName = resultSet.getString("THIRDNAME");
            Date birthDate = resultSet.getDate("BIRTH_DATE");

            Status authorStatus = new Status();
            authorStatus.setId(resultSet.getLong("AUTHOR_STATUS_ID"));
            authorStatus.setName(resultSet.getString("AUTHOR_STATUS_NAME"));

            return new Author(id, firstName, secondName, thirdName, birthDate, authorStatus);
        }
    }
}
