package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private static final String INSERT = "INSERT INTO AUTHORS (FIRSTNAME, SECONDNAME, THIRDNAME, BIRTH_DATE, STATUS) " +
            " VALUES (:FIRSTNAME,:SECONDNAME,:THIRDNAME,:BIRTH_DATE,:STATUS)";

    private static final String SELECT_ALL = "SELECT a.ID, a.FIRSTNAME, a.SECONDNAME, a.THIRDNAME, a.BIRTH_DATE, a.STATUS, " +
            " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME " +
            " FROM AUTHORS a " +
            " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID ";

    private static final String SELECT_BY_ID = "SELECT a.ID, a.FIRSTNAME, a.SECONDNAME, a.THIRDNAME, a.BIRTH_DATE, a.STATUS, " +
            " ast.ID AS AUTHOR_STATUS_ID, ast.NAME AS AUTHOR_STATUS_NAME " +
            " FROM AUTHORS a " +
            " LEFT JOIN STATUSES ast ON a.STATUS = ast.ID " +
            " WHERE a.ID = :ID";

    private static final String DELETE_BY_ID = "DELETE FROM AUTHORS WHERE ID = :ID";

    private static final String UPDATE_BY_ID = "UPDATE AUTHORS SET" +
            " FIRSTNAME = :FIRSTNAME," +
            " SECONDNAME = :SECONDNAME," +
            " THIRDNAME = :THIRDNAME," +
            " BIRTH_DATE = :BIRTH_DATE," +
            " STATUS = :STATUS" +
            " WHERE ID = :ID";

    private static final Logger logger = Logger.getLogger(JdbcAuthorRepository.class);

    private final NamedParameterJdbcTemplate jdbc;

    JdbcAuthorRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        jdbc = namedJdbcTemplate;
    }

    @Override
    public boolean insert(Author author) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("FIRSTNAME", author.getFirstName());
            params.put("SECONDNAME", author.getSecondName());
            params.put("THIRDNAME", author.getThirdName());
            params.put("BIRTH_DATE", author.getBirthDate());
            params.put("STATUS", (author.getStatus() != null) ? author.getStatus().getId() : null);
            jdbc.update(INSERT, params);
            return true;

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return false;
    }

    @Override
    public List<Author> getAll() {
        try {
            return jdbc.getJdbcOperations().query(SELECT_ALL, new JdbcAuthorRepository.AuthorMapper());

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return new ArrayList<>();
    }

    @Override
    public Author getById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            return jdbc.queryForObject(SELECT_BY_ID, params, new JdbcAuthorRepository.AuthorMapper());

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
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
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return false;
    }

    @Override
    public boolean update(Author author) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", author.getId());
            params.put("FIRSTNAME", author.getFirstName());
            params.put("SECONDNAME", author.getSecondName());
            params.put("THIRDNAME", author.getThirdName());
            params.put("BIRTH_DATE", author.getBirthDate());
            params.put("STATUS", (author.getStatus() != null) ? author.getStatus().getId() : null);
            jdbc.update(UPDATE_BY_ID, params);
            return true;

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return false;
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
