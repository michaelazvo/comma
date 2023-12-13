package sk.comma;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    @FXML
    private Button ulozitHodnotenieButton;

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
        Hodnotenie existingHodnotenie = hodnotenieDao.findByPorotcaIdAndTelesoId(porotcaId, tanecneTelesoId);

        if (existingHodnotenie != null) {
            // Update existing Hodnotenie
            existingHodnotenie.setHodnotenie(body);
            savedHodnotenie = hodnotenieDao.update(existingHodnotenie);
        } else {
            // Create a new Hodnotenie
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
            try {
                long l = Long.parseLong(s);
                if (tanecneTelesoDao.findById(l) != null) {
                    TanecneTeleso teleso = tanecneTelesoDao.findById(l);
                    if (telesa.contains(teleso)) {
                        nazovTelesaLabel.setText(teleso.getNazov());
                    } else {
                        nazovTelesaLabel.setText(" ");
                        showAlert(Alert.AlertType.WARNING, "Upozornenie", "Nesprávne zadané číslo", null);
                    }
                } else {
                    nazovTelesaLabel.setText(" ");
                    showAlert(Alert.AlertType.WARNING, "Upozornenie", "Nesprávne zadané číslo", null);
                }
            } catch (NumberFormatException e) {
                nazovTelesaLabel.setText(" ");
                showAlert(Alert.AlertType.ERROR, "Chyba", "Neplatné číslo", "Zadajte platné číslo.");
            }
        } else {
            nazovTelesaLabel.setText(" ");
            showAlert(Alert.AlertType.WARNING, "Upozornenie", "Neuvedené číslo tanečného telesa", null);
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
        if (bodyString.isEmpty()) {
            showAlert("Zlé zadanie bodov", "Prosím, zadajte hodnotenie od 0 do 10.");
        }

        if (!bodyString.isEmpty() && existujeTeleso) {

            int body = Integer.parseInt(bodyString);
            // Kontrola rozsahu hodnot
            if (body < 0 || body > 10) {
                // Neplatný rozsah hodnôt
                showAlert("Zlé zadanie bodov", "Hodnotenie musí byť v rozmedzí od 0 do 10.");
            } else {
                setHodnotenieByPorotcaIdTelesoId(porotcaId, teleso.getId(), body);
            }

        }

        ulozitHodnotenieButton.getScene().getWindow().hide();

    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

