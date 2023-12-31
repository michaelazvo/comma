package sk.comma.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {

    INSTANCE;

    public static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/comma";
    public static final String DATABASE_USERNAME = "comma_user";
    public static final String DATABASE_PASSWORD = "admin";
    private HodnotenieDao hodnotenieDao;
    private KategoriaDao kategoriaDao;
    private PorotcaDao porotcaDao;
    private SutazDao sutazDao;
    private TanecneTelesoDao tanecneTelesoDao;
    private JdbcTemplate jdbcTemplate;

    private boolean testovaciaDatabaza = false;

    public void setTestovaciaDatabaza(boolean hodnota) {
        this.testovaciaDatabaza = hodnota;
    }

    private JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            if(testovaciaDatabaza) {
                dataSource.setUser("comma_test_user");
                dataSource.setPassword("12345");
                dataSource.setUrl("jdbc:mysql://localhost:3306/comma_test");
            } else {
                dataSource.setUser(DATABASE_USERNAME);
                dataSource.setPassword(DATABASE_PASSWORD);
                dataSource.setUrl(DATABASE_CONNECTION_URL);
            }
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    public HodnotenieDao getHodnotenieDao() {
        if (hodnotenieDao == null) {
            hodnotenieDao = new MysqlHodnotenieDao(getJdbcTemplate());
        }
        return hodnotenieDao;
    }

    public KategoriaDao getKategoriaDao() {
        if (kategoriaDao == null) {
            kategoriaDao = new MysqlKategoriaDao(getJdbcTemplate());
        }
        return kategoriaDao;
    }

    public PorotcaDao getPorotcaDao() {
        if (porotcaDao == null) {
            porotcaDao = new MysqlPorotcaDao(getJdbcTemplate());
        }
        return porotcaDao;
    }

    public SutazDao getSutazDao() {
        if (sutazDao == null) {
            sutazDao = new MysqlSutazDao(getJdbcTemplate());
        }
        return sutazDao;
    }

    public TanecneTelesoDao getTanecneTelesoDao() {
        if (tanecneTelesoDao == null) {
            tanecneTelesoDao = new MysqlTanecneTelesoDao(getJdbcTemplate());
        }
        return tanecneTelesoDao;
    }
}
