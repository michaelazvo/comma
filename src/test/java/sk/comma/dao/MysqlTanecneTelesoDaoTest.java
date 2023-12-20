package sk.comma.dao;

import junit.framework.TestCase;
import org.junit.Test;

import sk.comma.entity.TanecneTeleso;

public class MysqlTanecneTelesoDaoTest extends TestCase {

    TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();

    public void testFindById() {
        long existingId = 14;
        TanecneTeleso actualTanecneTeleso = tanecneTelesoDao.findById(existingId);
        TanecneTeleso expectedTanecneTeleso = new TanecneTeleso(14L, "Salonky", "ChristmasSong", "BLABLA", 14, 3, "56569895", "lala@gmail.com",  "Martin, Jakub, Petra, Zuzana, Sofia");


        assertEquals(expectedTanecneTeleso, actualTanecneTeleso);
    }

    public void testFindAll() {
    }

    public void testFindAllBySutazId() {
    }

    public void testFindAllBySutazIdKategoriaId() {
    }

    public void testFindAllByKategoriaId() {
    }

    @Test

    public void testInsert() {
        TanecneTeleso teleso = new TanecneTeleso();
        teleso.setNazov("Jahodky");
        teleso.setHudba("wawa");
        teleso.setKlub("Ovocie");
        teleso.setEmail("lala@gmail.com");
        teleso.setTelefonneCislo("000444");
        teleso.setTanecnici("Alzbeta, Kamil");
        teleso.setKategoriaId(11);
        teleso.setSutazId(3);

        TanecneTeleso result = tanecneTelesoDao.insert(teleso);
        assertEquals(teleso, result);
        tanecneTelesoDao.delete(result);

    }

    public void testUpdate() {
    }

    public void testDelete() {
    }
}