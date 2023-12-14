package sk.comma.dao;

import sk.comma.entity.Kategoria;

import java.util.List;

public interface KategoriaDao {

    Kategoria findById(long id);

    List<Kategoria> findAll();

    Kategoria insert(Kategoria kategoria);

}
