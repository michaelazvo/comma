package sk.comma.business;

import sk.comma.dao.DaoFactory;
import sk.comma.dao.HodnotenieDao;
import sk.comma.dao.KategoriaDao;
import sk.comma.dao.TanecneTelesoDao;
import sk.comma.entity.Hodnotenie;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.util.ArrayList;
import java.util.List;

public class OverviewManagerImpl implements OverviewManager {


    //tu bude potrebne prepocitat hodnotenia od porotcov a zaroven urcit umiestnenie

    @Override
    public List<vysledkyOverview> getOverviews(Kategoria kategoria, Sutaz sutaz) {
        HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();
        TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
        List<TanecneTeleso> tanecneTelesa = tanecneTelesoDao.findAllBySutazIdKategoriaId(sutaz.getId(),kategoria.getId());


        List<vysledkyOverview> result = new ArrayList<vysledkyOverview>();
        for (TanecneTeleso teleso: tanecneTelesa) {
            List<Hodnotenie> hodnotenia = hodnotenieDao.findAllByTelesoId(teleso.getId());
            int sucetBodov = 0;
            for (Hodnotenie h:hodnotenia) {
                sucetBodov+=h.getHodnotenie();
            }
            result.add(new vysledkyOverview(teleso.getUmiestnenie(), teleso.getId(), teleso.getNazov(), teleso.getHudba(), teleso.getKlub(), teleso.getTanecnici(),sucetBodov));
        }
        return result;
    }
}
