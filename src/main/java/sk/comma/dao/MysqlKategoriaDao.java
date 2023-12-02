package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import sk.comma.entity.Kategoria;

import java.util.List;

public class MysqlKategoriaDao implements KategoriaDao {

    private JdbcTemplate jdbcTemplate;

    // toto mi robilo chybu ako som pracovala so MysqlSutazDao, cize to asi nema byt
    //private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();

    public MysqlKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Kategoria findById(long id) {
        return null;
    }

    @Override
    public List<Kategoria> findAll() {
        return null;
    }

    @Override
    public Kategoria insert(Kategoria kategoria) {
        return null;
    }

    @Override
    public Kategoria update(Kategoria kategoria) {
        return null;
    }

    @Override
    public boolean delete(Kategoria kategoria) {
        return false;
    }
}
