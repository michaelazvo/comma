package sk.comma.dao;

import sk.comma.entity.Porotca;

import java.util.List;

public interface PorotcaDao {
    Porotca findById(long id);



    Porotca insert(Porotca porotca);

    void pridajPorotcuDoSutaze(Long porotcaId, int sutazId);

    List<Porotca> getPorotcoviaPreSutaz(int idSutaze);

    void update(Porotca porotca);

    boolean delete(Porotca porotca);

    boolean isPasswordCorrect(String heslo, String meno);

    boolean existingUser(String meno);

    boolean isAdmin(String heslo, String meno);

    String getSalt(String uzivatelskeMeno);

}
