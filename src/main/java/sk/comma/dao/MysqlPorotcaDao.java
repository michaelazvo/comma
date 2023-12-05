package sk.comma.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Porotca;

import java.util.List;

public class MysqlPorotcaDao implements PorotcaDao {

    private JdbcTemplate jdbcTemplate;

    // toto mi robilo chybu ako som pracovala so MysqlSutazDao, cize to asi nema byt
    //private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();

    public MysqlPorotcaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Porotca findById(long id) {
        return null;
    }

    @Override
    public List<Porotca> findAll() {
        return null;
    }

    @Override
    public Porotca insert(Porotca porotca) {
        return null;
    }

    @Override
    public Porotca update(Porotca porotca) {
        return null;
    }

    @Override
    public boolean delete(Porotca porotca) {
        return false;
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
