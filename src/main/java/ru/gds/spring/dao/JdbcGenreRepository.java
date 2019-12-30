package ru.gds.spring.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private static final String INSERT = "INSERT INTO GENRES (`NAME`) VALUES (:NAME)";

    private static final String SELECT_ALL = "SELECT ID, `NAME` FROM GENRES";

    private static final String SELECT_BY_ID = "SELECT g.ID, g.NAME FROM GENRES g WHERE g.ID = :ID";

    private static final String DELETE_BY_ID = "DELETE FROM GENRES WHERE ID = :ID";

    private static final String UPDATE_BY_ID = "UPDATE GENRES SET `NAME` = :NAME WHERE ID = :ID";

    private static final Logger logger = Logger.getLogger(JdbcGenreRepository.class);

    private final NamedParameterJdbcTemplate jdbc;

    JdbcGenreRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        jdbc = namedJdbcTemplate;
    }

    @Override
    public boolean insert(Genre genre) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("NAME", genre.getName());
            jdbc.update(INSERT, params);
            return true;

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return false;
    }

    @Override
    public List<Genre> getAll() {
        try {
            return jdbc.getJdbcOperations().query(SELECT_ALL, new JdbcGenreRepository.GenreMapper());

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return new ArrayList<Genre>();
    }

    @Override
    public Genre getById(long id) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", id);
            return jdbc.queryForObject(SELECT_BY_ID, params, new JdbcGenreRepository.GenreMapper());

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
    public boolean update(Genre genre) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ID", genre.getId());
            params.put("NAME", genre.getName());
            jdbc.update(UPDATE_BY_ID, params);
            return true;

        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return false;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");

            return new Genre(id, name);
        }
    }
}
