package sk.comma.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.comma.entity.Porotca;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MysqlPorotcaDao implements PorotcaDao {

    private JdbcTemplate jdbcTemplate;


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
            return null;
        }

    }


    @Override
    public Porotca insert(Porotca porotca) {
        Objects.requireNonNull(porotca, "Porotca cannoot be null");
        Objects.requireNonNull(porotca.getMeno(), "Meno cannot be null");
        Objects.requireNonNull(porotca.getPriezvisko(), "Priezvisko cannot be null");
        Objects.requireNonNull(porotca.getUzivatelskeMeno(), "UzivatelskeMeno cannot be null");
        Objects.requireNonNull(porotca.getHeslo(), "Heslo cannot be null");
        if (porotca.getId() == null) { //insert
            String query = "INSERT INTO porotca (meno, priezvisko, uzivatelske_meno, heslo, jeAdmin, sol) "
                    + "VALUES (?,?,?,?,?,?)";

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
                    statement.setString(6, porotca.getSol());
                    return statement;
                }
            }, keyHolder);
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
    public void pridajPorotcuDoSutaze(Long porotcaId, int sutazId) {
        String query = "INSERT INTO sutaz_porotca (sutaz_id, porotca_id) VALUES (?, ?)";
        jdbcTemplate.update(query, sutazId, porotcaId);
    }

    @Override
    public void update(Porotca porotca) {
        String query = "UPDATE porotca SET meno = ?, priezvisko = ?, uzivatelske_meno = ?, heslo = ?, sol = ? WHERE id = ?";
        jdbcTemplate.update(query, porotca.getMeno(), porotca.getPriezvisko(), porotca.getUzivatelskeMeno(), porotca.getHeslo(),porotca.getSol(), porotca.getId());
    }

    @Override
    public boolean delete(Porotca porotca) {
        try {
            String deleteSutazPorotcaQuery = "DELETE FROM sutaz_porotca WHERE porotca_id = ?";
            jdbcTemplate.update(deleteSutazPorotcaQuery, porotca.getId());

            String deleteHodnotenieQuery = "DELETE FROM hodnotenie WHERE porotca_id = ?";
            jdbcTemplate.update(deleteHodnotenieQuery, porotca.getId());

            String deletePorotcaQuery = "DELETE FROM porotca WHERE id = ?";
            int affectedRows = jdbcTemplate.update(deletePorotcaQuery, porotca.getId());

            return affectedRows == 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean isPasswordCorrect(String hashovane, String pouzivatelMeno) {
        String sql = "SELECT heslo from porotca where uzivatelske_meno = ?";
        String heslo = jdbcTemplate.queryForObject(sql, String.class, pouzivatelMeno);
        return heslo.equals(hashovane);
    }

    @Override
    public boolean existingUser(String meno) {
        String sql = "SELECT COUNT(*) FROM porotca WHERE uzivatelske_meno = ?";

        try {
            int pocet = jdbcTemplate.queryForObject(sql,
                    Integer.class, meno);

            return pocet == 1;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean isAdmin(String pouzivatelHeslo, String pouzivatelMeno) {
        String sql = "SELECT jeAdmin from porotca where uzivatelske_meno = ?";
        int admin = jdbcTemplate.queryForObject(sql, Integer.class, pouzivatelMeno);
        return admin == 1;
    }

    @Override
    public String getSalt(String uzivatelskeMeno) {
        String sql = "SELECT sol from porotca where uzivatelske_meno = ?";
        return jdbcTemplate.queryForObject(sql, String.class, uzivatelskeMeno);
    }

    public void aktualizujHesloASolPreAdmina() {
        String adminMeno = "admin";
        String sql = "SELECT id, heslo FROM porotca WHERE uzivatelske_meno = ?";

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, adminMeno);
            Long adminId = 1L;
            String existujuceHeslo = "admin";

            // Vygenerujte novú sol
            String novaSol = BCrypt.gensalt();

            // Znovu hashujte existujúce heslo s novou solou
            String noveHeslo = BCrypt.hashpw(existujuceHeslo, novaSol);

            // Aktualizujte údaje v databáze so zmeneným heslom a novou solou
            String updateSql = "UPDATE porotca SET heslo = ?, sol = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, noveHeslo, novaSol, adminId);
        } catch (EmptyResultDataAccessException e) {
            // Spracujte prípad, keď admin nie je v databáze
        }
    }


}
