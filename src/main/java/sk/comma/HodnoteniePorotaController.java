package sk.comma;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class HodnoteniePorotaController {

    @FXML
    private TextField idTanecnehoTelesaTextField;

    @FXML
    private Label nazovTelesaLabel;


    @FXML
    private Label upozornenieLabel;

    @FXML
    private TextField pocetBodovTextField;

    private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
    private List<TanecneTeleso> tanecneTelesa;

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();
    private List<Hodnotenie> hodnotenia = new ArrayList<>();

    private Hodnotenie savedHodnotenie;


    // aktualna sutaz
    private int sutazId;

    // metoda pouzita v MainSceneController odkial si zapamatavame id sutaze
    public void setSutazId(int sutazId) {
        this.sutazId = sutazId;
    }

    private long porotcaId;

    public void setPorotcaId(long porotcaId) {
        this.porotcaId = porotcaId;
    }

    private void setHodnotenieByPorotcaIdTelesoId(long porotcaId, long tanecneTelesoId, int body) {
        long hodnotenieId = 0;
        boolean existing = false;
        if (hodnotenia.isEmpty()) {
            Hodnotenie hodnotenie = new Hodnotenie();
            hodnotenie.setPorotcaId(porotcaId);
            hodnotenie.setTanecneTelesoId(tanecneTelesoId);
            hodnotenie.setHodnotenie(body);
            savedHodnotenie = hodnotenieDao.insert(hodnotenie);
        }
        for (Hodnotenie hodnotenie : hodnotenia) {
            if (hodnotenie.getTanecneTelesoId() == tanecneTelesoId && hodnotenie.getPorotcaId() == porotcaId) {
                existing = true;
                hodnotenie.setHodnotenie(body);
                savedHodnotenie = hodnotenieDao.update(hodnotenie);
                break;
            }
        }
        if (!existing) {
            Hodnotenie hodnotenie = new Hodnotenie();
            hodnotenie.setPorotcaId(porotcaId);
            hodnotenie.setTanecneTelesoId(tanecneTelesoId);
            hodnotenie.setHodnotenie(body);

            savedHodnotenie = hodnotenieDao.insert(hodnotenie);

        }
    }

    @FXML
    void overitNazovButtonClick(ActionEvent event) {
        String s = idTanecnehoTelesaTextField.getText();
        List<TanecneTeleso> telesa = tanecneTelesoDao.findAllBySutazId(sutazId);


        if (!s.isEmpty()) {
            long l = Long.parseLong(s);
            if (tanecneTelesoDao.findById(l) != null) {
                TanecneTeleso teleso = tanecneTelesoDao.findById(l);
                if (telesa.contains(teleso)) {
                    nazovTelesaLabel.setText(teleso.getNazov());
                } else {
                    upozornenieLabel.setText("Nesprávne zadané číslo");
                }
            } else {
                upozornenieLabel.setText("Nesprávne zadané číslo");
            }
        } else {
            upozornenieLabel.setText("Neuvedené číslo tanečného telesa");
        }


    }

    @FXML
    void ulozitHodnotenieButtonClick(ActionEvent event) {

        TanecneTeleso teleso = null;

        String telesoIdString = idTanecnehoTelesaTextField.getText();
        List<TanecneTeleso> telesa = tanecneTelesoDao.findAllBySutazId(sutazId);
        String bodyString = pocetBodovTextField.getText();
        boolean existujeTeleso = false;

        if (!telesoIdString.isEmpty()) {
            long telesoId = Long.parseLong(telesoIdString);
            if (tanecneTelesoDao.findById(telesoId) != null) {
                teleso = tanecneTelesoDao.findById(telesoId);
                if (telesa.contains(teleso)) {
                    nazovTelesaLabel.setText(teleso.getNazov());
                    existujeTeleso = true;
                }
            }
        }


        if (!bodyString.isEmpty() && existujeTeleso) {
            int body = Integer.parseInt(bodyString);
            setHodnotenieByPorotcaIdTelesoId(porotcaId, teleso.getId(), body);
        }
    }

}

