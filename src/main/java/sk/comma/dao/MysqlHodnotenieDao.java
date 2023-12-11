package sk.comma.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import sk.comma.entity.Hodnotenie;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class MysqlHodnotenieDao implements HodnotenieDao {

    private JdbcTemplate jdbcTemplate;


    public MysqlHodnotenieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Hodnotenie findById(long id) {
        return null;
    }

    @Override
    public List<Hodnotenie> findAll() {
        return null;
    }

    private RowMapper<Hodnotenie> hodnotenieRowMapper() {
        return new RowMapper<Hodnotenie>() {
            @Override
            public Hodnotenie mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                int hodnotenie = rs.getInt("hodnotenie");
                long porotcaId = rs.getLong("porotca_id");
                long tanecneTelesoId = rs.getLong("tanecne_teleso_id");

                Hodnotenie hodnotenieObject = new Hodnotenie(id, hodnotenie, porotcaId, tanecneTelesoId);
                return hodnotenieObject;
            }
        };
    }

    @Override
    public Hodnotenie insert(Hodnotenie hodnotenie) {
        if (hodnotenie.getId() == null) { //insert
            String query = "INSERT INTO hodnotenie (hodnotenie, porotca_id, tanecne_teleso_id) "
                    + "VALUES (?,?,?)";

            // tiez neviem ze co s tym
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, hodnotenie.getHodnotenie());
                    statement.setLong(2, hodnotenie.getPorotcaId());
                    statement.setLong(3, hodnotenie.getTanecneTelesoId());
                    return statement;
                }
            }, keyHolder);
            hodnotenie.setId(keyHolder.getKey().longValue());
        }
        return hodnotenie;
    }

    @Override
    public Hodnotenie update(Hodnotenie hodnotenie) {
        String query = "UPDATE hodnotenie SET hodnotenie = ? WHERE id = ?";

        jdbcTemplate.update(query, hodnotenie.getHodnotenie(), hodnotenie.getId());
        return null;
    }

    @Override
    public Hodnotenie findByPorotcaIdAndTelesoId(long porotcaId, long tanecneTelesoId) {
        String query = "SELECT * FROM hodnotenie WHERE porotca_id = ? AND tanecne_teleso_id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{porotcaId, tanecneTelesoId}, hodnotenieRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public boolean delete(Hodnotenie hodnotenie) {
        return false;
    }
}
