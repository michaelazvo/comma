package sk.comma.dao;

import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;

import java.util.List;

public interface SutazDao {
    Sutaz findById(long id);

    List<Sutaz> findAll();

    Sutaz insert(Sutaz sutaz);

    Sutaz update(Sutaz sutaz);

    boolean delete(Sutaz sutaz);
}
