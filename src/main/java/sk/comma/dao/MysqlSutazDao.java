package sk.comma.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import sk.comma.entity.Sutaz;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class MysqlSutazDao implements SutazDao {

    private JdbcTemplate jdbcTemplate;


    public MysqlSutazDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Sutaz findById(long id) {
        return null;
    }



    public Sutaz save(Sutaz sutaz) {
        if (sutaz.getId() == 0) {
            // if ID == 0, then this is a new object to be inserted
            insert(sutaz);
        } else {
            // if ID != 0, then this is an existing object to be updated
            update(sutaz);
        }
        return sutaz;
    }

    @Override
    public List<Sutaz> findAll() {
        String query = "SELECT id, nazov, odDatum, doDatum FROM sutaz " + "ORDER BY odDatum DESC";
        List<Sutaz> result = jdbcTemplate.query(query, new RowMapper<Sutaz>() {

            @Override
            public Sutaz mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                LocalDate datumOd = rs.getDate("odDatum").toLocalDate();
                LocalDate datumDo = rs.getDate("doDatum").toLocalDate();
                return new Sutaz(id,nazov, datumOd, datumDo);
            }
        });
        // TO DO
        // teraz asi nesetujeme studentov / teda akoze tanecnikkov, az potom, len skusam ci ukaze nazvy sutazi
        /*
        for (Sutaz sutaz: result){
            sutaz.setSutaz(sutazDao.getAllBySubjectId(subj.getId()));
        }

         */

        return result;
    }

    @Override
    public Sutaz insert(Sutaz sutaz) {
        String query = "INSERT INTO sutaz (nazov, odDatum, doDatum) VALUES (?, ?, ?)";

        // Nastavení hodnot do databáze a získání přiděleného ID
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, sutaz.getNazov());
                statement.setDate(2, Date.valueOf(sutaz.getOdDatum()));
                statement.setDate(3, Date.valueOf(sutaz.getDoDatum()));
                return statement;
            }
        }, keyHolder);

        // Nastavení ID na objekt Sutaz
        sutaz.setId(keyHolder.getKey().intValue());

        return sutaz;
    }

    @Override
    public void update(Sutaz sutaz) {
        String query = "UPDATE sutaz SET nazov = ?, odDatum = ?, doDatum = ? WHERE id = ?";

        jdbcTemplate.update(query, sutaz.getNazov(), sutaz.getOdDatum(), sutaz.getDoDatum(), sutaz.getId());


    }

    @Override
    public boolean delete(Sutaz sutaz) {
        return false;
    }
}
