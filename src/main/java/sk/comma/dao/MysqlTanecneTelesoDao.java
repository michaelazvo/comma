package sk.comma.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import sk.comma.entity.TanecneTeleso;

import java.sql.*;

import java.util.List;
import java.util.Objects;

public class MysqlTanecneTelesoDao implements TanecneTelesoDao {
    private JdbcTemplate jdbcTemplate;


    public MysqlTanecneTelesoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<TanecneTeleso> tanecneTelesoRM() {
        return new RowMapper<TanecneTeleso>() {
            @Override
            public TanecneTeleso mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                String umiestnenie = rs.getString("umiestnenie");
                String hudba = rs.getString("hudba");
                String klub = rs.getString("klub");
                String telefonne_cislo = rs.getString("telefonne_cislo");
                String tanecnici = rs.getString("tanecnici");
                String email = rs.getString("email");


                long kategoria_id = rs.getLong("kategoria_id");
                long sutaz_id = rs.getLong("sutaz_id");

                TanecneTeleso teleso = new TanecneTeleso(id, nazov, umiestnenie, hudba, klub, kategoria_id, sutaz_id, telefonne_cislo, email, tanecnici);
                ;
                return teleso;
            }
        };
    }

    @Override
    public TanecneTeleso findById(long id) {

        String sql = "SELECT id, nazov, umiestnenie, hudba, klub, telefonne_cislo, tanecnici, email, kategoria_id, sutaz_id FROM tanecne_teleso " + " WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, tanecneTelesoRM());

    }

    @Override
    public List<TanecneTeleso> findAll() {

        String query = "SELECT id, nazov, umiestnenie, hudba, klub, telefonne_cislo, tanecnici, email, kategoria_id, sutaz_id FROM tanecne_teleso " + "ORDER BY umiestnenie DESC";
        List<TanecneTeleso> result = jdbcTemplate.query(query, new RowMapper<TanecneTeleso>() {

            @Override
            public TanecneTeleso mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                String umiestnenie = rs.getString("umiestnenie");
                String hudba = rs.getString("hudba");
                String klub = rs.getString("klub");
                String telefonne_cislo = rs.getString("telefonne_cislo");
                String tanecnici = rs.getString("tanecnici");
                String email = rs.getString("email");


                long kategoria_id = rs.getLong("kategoria_id");
                long sutaz_id = rs.getLong("sutaz_id");
                return new TanecneTeleso(id, nazov, umiestnenie, hudba, klub, kategoria_id, sutaz_id, telefonne_cislo, email, tanecnici);
            }
        });

        return result;
    }

    @Override
    public List<TanecneTeleso> findAllBySutazId(int sutazId) {
        String query = "SELECT id, nazov, umiestnenie, hudba, klub, telefonne_cislo, tanecnici, email, kategoria_id, sutaz_id FROM tanecne_teleso " + " WHERE sutaz_id = " + sutazId + " ORDER BY id";
        List<TanecneTeleso> result = jdbcTemplate.query(query, new RowMapper<TanecneTeleso>() {

            @Override
            public TanecneTeleso mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                String umiestnenie = rs.getString("umiestnenie");
                String hudba = rs.getString("hudba");
                String klub = rs.getString("klub");
                String telefonne_cislo = rs.getString("telefonne_cislo");
                String tanecnici = rs.getString("tanecnici");
                String email = rs.getString("email");


                long kategoria_id = rs.getLong("kategoria_id");
                long sutaz_id = rs.getLong("sutaz_id");
                return new TanecneTeleso(id, nazov, umiestnenie, hudba, klub, kategoria_id, sutaz_id, telefonne_cislo, email, tanecnici);
            }
        });

        return result;
    }

    @Override
    public List<TanecneTeleso> findAllBySutazIdKategoriaId(int sutazId, Long kategoriaId) {
        String query = "SELECT id, nazov, umiestnenie, hudba, klub, tanecnici, telefonne_cislo, email, kategoria_id," +
                " sutaz_id FROM tanecne_teleso" + " WHERE sutaz_id = " + sutazId + " AND kategoria_id = " + kategoriaId + " ORDER BY id";
        List<TanecneTeleso> result = jdbcTemplate.query(query, new RowMapper<TanecneTeleso>() {

            @Override
            public TanecneTeleso mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                String umiestnenie = rs.getString("umiestnenie");
                String hudba = rs.getString("hudba");
                String klub = rs.getString("klub");
                String telefonne_cislo = rs.getString("telefonne_cislo");
                String tanecnici = rs.getString("tanecnici");
                String email = rs.getString("email");


                long kategoria_id = rs.getLong("kategoria_id");
                long sutaz_id = rs.getLong("sutaz_id");
                return new TanecneTeleso(id, nazov, umiestnenie, hudba, klub, kategoria_id, sutaz_id, telefonne_cislo, email, tanecnici);
            }
        });

        return result;
    }

    @Override
    public List<TanecneTeleso> findAllByKategoriaId(Long kategoriaId) {

        String query = "SELECT id, nazov, umiestnenie, hudba, klub, telefonne_cislo, email, kategoria_id, sutaz_id, tanecnici FROM tanecne_teleso" + " WHERE kategoria_id = " + kategoriaId + " ORDER BY id";
        List<TanecneTeleso> result = jdbcTemplate.query(query, new RowMapper<TanecneTeleso>() {

            @Override
            public TanecneTeleso mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getInt("id");
                String nazov = rs.getString("nazov");
                String umiestnenie = rs.getString("umiestnenie");
                String hudba = rs.getString("hudba");
                String klub = rs.getString("klub");
                String telefonne_cislo = rs.getString("telefonne_cislo");
                String tanecnici = rs.getString("tanecnici");
                String email = rs.getString("email");


                long kategoria_id = rs.getLong("kategoria_id");
                long sutaz_id = rs.getLong("sutaz_id");
                return new TanecneTeleso(id, nazov, umiestnenie, hudba, klub, kategoria_id, sutaz_id, telefonne_cislo, email, tanecnici);
            }
        });
        return result;
    }


    @Override
    public TanecneTeleso insert(TanecneTeleso tanecneTeleso) {
        Objects.requireNonNull(tanecneTeleso,
                "Tanecne teleso cannot be null");
        Objects.requireNonNull(tanecneTeleso.getNazov(),
                "Tanecne teleso name cannot be null");
        Objects.requireNonNull(tanecneTeleso.getKlub(),
                "Klub cannot be null");
        Objects.requireNonNull(tanecneTeleso.getTanecnici(),
                "Tanecnici cannot be null");
        Objects.requireNonNull(tanecneTeleso.getHudba(),
                "Hudba cannot be null");
        Objects.requireNonNull(tanecneTeleso.getTelefonneCislo(),
                "Telefonne cislo cannot be null");
        Objects.requireNonNull(tanecneTeleso.getEmail(),
                "Email cannot be null");
        String query = "INSERT INTO tanecne_teleso (nazov, umiestnenie, hudba, klub, telefonne_cislo, tanecnici, email, kategoria_id, sutaz_id)  "
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, tanecneTeleso.getNazov());
                statement.setString(2, tanecneTeleso.getUmiestnenie());
                statement.setString(3, tanecneTeleso.getHudba());
                statement.setString(4, tanecneTeleso.getKlub());
                statement.setString(5, tanecneTeleso.getTelefonneCislo());
                statement.setString(6, tanecneTeleso.getTanecnici());
                statement.setString(7, tanecneTeleso.getEmail());
                statement.setLong(8, tanecneTeleso.getKategoriaId());
                statement.setLong(9, tanecneTeleso.getSutazId());
                return statement;
            }
        }, keyHolder);

        // Nastavení ID na objekt tanecne teleso
        tanecneTeleso.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());


        return tanecneTeleso;
    }

    @Override
    public TanecneTeleso update(TanecneTeleso tanecneTeleso) {

        String query = "UPDATE tanecne_teleso SET nazov = ?, umiestnenie = ?, hudba = ?, klub = ?, email = ?, telefonne_cislo = ?, tanecnici = ?, " +
                "kategoria_id = ?, sutaz_id = ? WHERE id = ?";

        jdbcTemplate.update(query, tanecneTeleso.getNazov(), tanecneTeleso.getUmiestnenie(), tanecneTeleso.getHudba(), tanecneTeleso.getKlub(),
                tanecneTeleso.getEmail(), tanecneTeleso.getTelefonneCislo(), tanecneTeleso.getTanecnici(), tanecneTeleso.getKategoriaId(), tanecneTeleso.getSutazId(),
                tanecneTeleso.getId());

        return null;
    }

    public TanecneTeleso save(TanecneTeleso tanecneTeleso) {
        if (tanecneTeleso.getId() == 0) {
            // if ID == 0, then this is a new object to be inserted
            insert(tanecneTeleso);
        } else {
            // if ID != 0, then this is an existing object to be updated
            update(tanecneTeleso);
        }
        return tanecneTeleso;
    }

    @Override
    public boolean delete(TanecneTeleso tanecneTeleso) {
        return false;
    }


}
