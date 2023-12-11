package sk.comma.dao;

import sk.comma.entity.Hodnotenie;

import java.util.List;

public interface HodnotenieDao {

    Hodnotenie findById(long id);

    List<Hodnotenie> findAll();

    Hodnotenie insert(Hodnotenie hodnotenie);

    Hodnotenie update(Hodnotenie hodnotenie);

    Hodnotenie findByPorotcaIdAndTelesoId(long porotcaId, long tanecneTelesoId);


    boolean delete(Hodnotenie hodnotenie);
}
