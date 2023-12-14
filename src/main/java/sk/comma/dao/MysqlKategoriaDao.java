package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import sk.comma.entity.Kategoria;

import java.sql.*;
import java.util.List;

public class MysqlKategoriaDao implements KategoriaDao {

    private JdbcTemplate jdbcTemplate;


    public MysqlKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Kategoria> kategoriaRM() {
        return new RowMapper<Kategoria>() {
            @Override
            public Kategoria mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String styl = rs.getString("styl");
                String vekova_skupina = rs.getString("vekova_skupina");
                String velkostna_skupina = rs.getString("velkostna_skupina");

                Kategoria kategoria= new Kategoria(id, styl, vekova_skupina,velkostna_skupina);
                return kategoria;
            }
        };
    }

    @Override
    public Kategoria findById(long id) {

        String sql = "SELECT id, styl, vekova_skupina, velkostna_skupina FROM kategoria" + " WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, kategoriaRM());

    }

    @Override
    public List<Kategoria> findAll() {

        String query = "SELECT id, styl, vekova_skupina, velkostna_skupina FROM kategoria " + "ORDER BY id DESC";
        List<Kategoria> result = jdbcTemplate.query(query, new RowMapper<Kategoria>() {

            @Override
            public Kategoria mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String styl = rs.getString("styl");
                String vekova_skupina = rs.getString("vekova_skupina");
                String velkostna_skupina = rs.getString("velkostna_skupina");
                return new Kategoria(id, styl, vekova_skupina, velkostna_skupina);
            }
        });

        return result;
    }

    @Override
    public Kategoria insert(Kategoria kategoria) {
        String query = "INSERT INTO kategoria (styl, vekova_skupina, velkostna_skupina) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, kategoria.getStyl());
                statement.setString(2, kategoria.getVekovaSkupina());
                statement.setString(3, kategoria.getVelkostnaSkupina());
                return statement;
            }
        }, keyHolder);
        kategoria.setId(keyHolder.getKey().longValue());

        return kategoria;
    }


}
