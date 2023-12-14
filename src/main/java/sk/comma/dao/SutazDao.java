package sk.comma.dao;

import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;

import java.util.List;

public interface SutazDao {
    Sutaz findById(int id);

    List<Sutaz> findAll();

    Sutaz insert(Sutaz sutaz);

    void update(Sutaz sutaz);

    boolean delete(Sutaz sutaz);
}
