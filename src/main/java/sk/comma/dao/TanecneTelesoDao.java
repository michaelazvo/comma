package sk.comma.dao;

import sk.comma.entity.TanecneTeleso;

import java.util.List;

public interface TanecneTelesoDao {

    TanecneTeleso findById(long id);

    List<TanecneTeleso> findAll();

    List<TanecneTeleso> findAllBySutazId(Long sutazId);

    List<TanecneTeleso> findAllByKategoriaId(Long kategoriaId);

    TanecneTeleso insert(TanecneTeleso tanecneTeleso);

    TanecneTeleso update(TanecneTeleso tanecneTeleso);

    boolean delete(TanecneTeleso tanecneTeleso);
}
