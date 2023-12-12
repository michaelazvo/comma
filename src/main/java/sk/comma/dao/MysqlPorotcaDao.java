package sk.comma.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import sk.comma.entity.Porotca;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class MysqlPorotcaDao implements PorotcaDao {

    private JdbcTemplate jdbcTemplate;

    // toto mi robilo chybu ako som pracovala so MysqlSutazDao, cize to asi nema byt
    //private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();

    public MysqlPorotcaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static class PorotcaRowMapper implements RowMapper<Porotca> {

        @Override
        public Porotca mapRow(ResultSet rs, int rowNum) throws SQLException {
            Porotca porotca = new Porotca();
            porotca.setId(rs.getLong("id"));
            porotca.setMeno(rs.getString("meno"));
            porotca.setPriezvisko(rs.getString("priezvisko"));
            porotca.setUzivatelskeMeno(rs.getString("uzivatelske_meno"));
            porotca.setHeslo(rs.getString("heslo"));
            porotca.setJeAdmin(rs.getBoolean("jeAdmin"));

            return porotca;
        }
    }

    @Override
    public Porotca findById(long id) {
        String query = "SELECT * FROM porotca WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new PorotcaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // If no result is found, return null or throw an exception as needed
            return null;
        }

    }

    @Override
    public List<Porotca> findAll() {
        return null;
    }

    @Override
    public Porotca insert(Porotca porotca) {
        Objects.requireNonNull(porotca, "Student cannoot be null");
        Objects.requireNonNull(porotca.getMeno(), "Surname cannot be null"); //podla databazy
        Objects.requireNonNull(porotca.getPriezvisko(), "Surname cannot be null");
        Objects.requireNonNull(porotca.getUzivatelskeMeno(), "Surname cannot be null");
        Objects.requireNonNull(porotca.getHeslo(), "Surname cannot be null");
        if (porotca.getId() == null) { //insert
            String query = "INSERT INTO porotca (meno, priezvisko, uzivatelske_meno, heslo, jeAdmin) "
                    + "VALUES (?,?,?,?,?)";

            // tiez neviem ze co s tym
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, porotca.getMeno());
                    statement.setString(2, porotca.getPriezvisko());
                    statement.setString(3, porotca.getUzivatelskeMeno());
                    statement.setString(4, porotca.getHeslo());
                    statement.setBoolean(5, porotca.isJeAdmin());
                    return statement;
                }
            }, keyHolder);
            //neviem ci treba dvakrat abo co :D ale ide to
                    porotca.setId(keyHolder.getKey().longValue());
            }
            return porotca;
    }

    public List<Porotca> getPorotcoviaPreSutaz(int idSutaze) {
        String query = "SELECT porotca.* FROM porotca " +
                "JOIN sutaz_porotca ON porotca.id = sutaz_porotca.porotca_id " +
                "WHERE sutaz_porotca.sutaz_id = ?";

        return jdbcTemplate.query(query, new Object[]{idSutaze}, new PorotcaRowMapper());
    }

    @Override
    public void vymazPorotcuZoSutaze(Long porotcaId, int sutazId) {
        String query = "DELETE FROM sutaz_porotca WHERE porotca_id = ? AND sutaz_id = ?";

        jdbcTemplate.update(query, porotcaId, sutazId);
    }

    @Override
    public void pridajPorotcuDoSutaze(Long porotcaId, int sutazId) {
        String query = "INSERT INTO sutaz_porotca (sutaz_id, porotca_id) VALUES (?, ?)";
        jdbcTemplate.update(query, sutazId, porotcaId);
    }

    @Override
    public void update(Porotca porotca) {
        String query = "UPDATE porotca SET meno = ?, priezvisko = ?, uzivatelske_meno = ?, heslo = ? WHERE id = ?";

        jdbcTemplate.update(query, porotca.getMeno(), porotca.getPriezvisko(), porotca.getUzivatelskeMeno(), porotca.getHeslo(), porotca.getId());
    }


    @Override
    public boolean delete(Porotca porotca) {
        String query = "DELETE FROM porotca WHERE id = ?";

        // Vrátí počet ovlivněných řádků (měl by být 1, pokud ID existuje)
        int affectedRows = jdbcTemplate.update(query, porotca.getId());

        // Pokud byl smazán právě jeden záznam, vracíme true, jinak false
        return affectedRows == 1;
    }

    @Override
    public boolean isPasswordCorrect(String pouzivatelHeslo, String pouzivatelMeno) {
        String sql = "SELECT heslo from porotca where uzivatelske_meno = ?";
        String heslo = jdbcTemplate.queryForObject(sql, String.class, pouzivatelMeno);
        if(heslo.equals(pouzivatelHeslo)){
            return true;
        }

        return false;
    }

    @Override
    public boolean existingUser(String heslo, String meno) {
        String sql = "SELECT COUNT(*) FROM porotca WHERE uzivatelske_meno = ?";

        try {
            // Zkontrolovat existenci uživatele ve vaší databázi
            int pocet = jdbcTemplate.queryForObject(sql,
                    Integer.class, meno);

            return pocet==1; // Uživatel existuje
        } catch (EmptyResultDataAccessException e) {
            return false; // Uživatel neexistuje
        }
    }

    @Override
    public boolean isAdmin(String pouzivatelHeslo, String pouzivatelMeno) {
        String sql = "SELECT jeAdmin from porotca where uzivatelske_meno = ?";
        int admin = jdbcTemplate.queryForObject(sql, Integer.class, pouzivatelMeno);
        if(admin == 1) {
            return true;
        } else {
            return false;
        }
     }


}
