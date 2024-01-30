package sk.comma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField pocetBodovTextField;


    @FXML
    private Button hladatTelesaButton;


    @FXML
    private ComboBox<TanecneTeleso> zoznamTeliesCombobox;

    @FXML
    private ComboBox<String> stylCombobox;

    @FXML
    private ComboBox<String> vekCombobox;

    @FXML
    private ComboBox<String> velkostCombobox;


    @FXML
    private Button ulozitHodnotenieButton;

    private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();
    private List<Hodnotenie> hodnotenia = new ArrayList<>();

    private Hodnotenie savedHodnotenie;
    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();

    private List<Kategoria> kategorie;


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

    @FXML
    void initialize() {
        stylCombobox.getItems().addAll(Kategoria.getStylTypes());
        vekCombobox.getItems().addAll(Kategoria.getVekovaSkupinaTypes());
        velkostCombobox.getItems().addAll(Kategoria.getVelkostnaSkupinaTypes());

        stylCombobox.getSelectionModel().selectFirst();
        vekCombobox.getSelectionModel().selectFirst();
        velkostCombobox.getSelectionModel().selectFirst();

        kategorie = kategoriaDao.findAll();
        reloadList();

    }

    private void reloadList() {
        String selectedStyl = stylCombobox.getValue();
        String selectedVelkostnaSkupina = velkostCombobox.getValue();
        String selectedVekovaSkupina = vekCombobox.getValue();
        long kategoriaId = getKategoriaId(selectedStyl, selectedVelkostnaSkupina, selectedVekovaSkupina);

        List<TanecneTeleso> telesaList = tanecneTelesoDao.findAllBySutazIdKategoriaId(sutazId, kategoriaId);
        ObservableList<TanecneTeleso> telesaObservableList = FXCollections.observableArrayList(telesaList);
        zoznamTeliesCombobox.setItems(telesaObservableList);
    }

    private long getKategoriaId(String styl, String velkostnaSkupina, String vekovaSkupina) {
        long kategoriaId = 0;
        boolean existing = false;
        if (kategorie.isEmpty()) {
            Kategoria kategoriaNova = new Kategoria();
            kategoriaNova.setStyl(styl);
            kategoriaNova.setVekovaSkupina(vekovaSkupina);
            kategoriaNova.setVelkostnaSkupina(velkostnaSkupina);

            kategoriaId = kategoriaNova.getId();
            return kategoriaId;
        }
        for (Kategoria kategoria : kategorie) {
            if (kategoria.getStyl().equals(styl) && kategoria.getVelkostnaSkupina().equals(velkostnaSkupina) && kategoria.getVekovaSkupina().equals(vekovaSkupina)) {
                existing = true;
                kategoriaId = kategoria.getId();
                break;
            }
        }
        if (existing) {
            return kategoriaId;
        } else {
            Kategoria kategoriaNova = new Kategoria();
            kategoriaNova.setStyl(styl);
            kategoriaNova.setVekovaSkupina(vekovaSkupina);
            kategoriaNova.setVelkostnaSkupina(velkostnaSkupina);
            kategoriaDao.insert(kategoriaNova);
            kategoriaId = kategoriaNova.getId();
        }
        return kategoriaId;
    }

    private void setHodnotenieByPorotcaIdTelesoId(long porotcaId, long tanecneTelesoId, int body) {
        Hodnotenie existingHodnotenie = hodnotenieDao.findByPorotcaIdAndTelesoId(porotcaId, tanecneTelesoId);

        if (existingHodnotenie != null) {
            existingHodnotenie.setHodnotenie(body);
            savedHodnotenie = hodnotenieDao.update(existingHodnotenie);
        } else {
            Hodnotenie hodnotenie = new Hodnotenie();
            hodnotenie.setPorotcaId(porotcaId);
            hodnotenie.setTanecneTelesoId(tanecneTelesoId);
            hodnotenie.setHodnotenie(body);
            savedHodnotenie = hodnotenieDao.insert(hodnotenie);
        }
    }


    @FXML
    void hladatTelesaButtonClick(ActionEvent event) {
        reloadList();
    }

    @FXML
    void ulozitHodnotenieNewButtonClick(ActionEvent event) {
        TanecneTeleso teleso = zoznamTeliesCombobox.getValue();

        if (teleso == null) {
            showAlert("Žiadne zvolené teleso", "Prosím, vyberte tanečné teleso zo zoznamu.");
            return;
        }

        String bodyString = pocetBodovTextField.getText();

        if (bodyString.isEmpty()) {
            showAlert("Nepridelenie bodov", "Prosím, zadajte hodnotenie od 0 do 10.");
            return;
        }

        int body = 0;

        try {
            body = Integer.parseInt(bodyString);

            // Kontrola rozsahu hodnot
            if (body < 0 || body > 10) {
                // Neplatný rozsah hodnôt
                showAlert("Zlé zadanie bodov", "Hodnotenie musí byť v rozmedzí od 0 do 10.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Zlé zadanie bodov", "Prosím, zadajte platné hodnotenie.");
            return;
        }

        setHodnotenieByPorotcaIdTelesoId(porotcaId, teleso.getId(), body);

        showAlert(Alert.AlertType.INFORMATION, "Úspech", "Bodovanie úspešne uložené", "Bodovanie bolo úspešne pripísané k tanečnému telesu.");
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


/*    @FXML
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
        } else {
            showAlert("Prázdne pole ID", "Prosím, zadajte ID tanečného telesa.");
            return;
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
                showAlert(Alert.AlertType.INFORMATION, "Úspech", "Bodovanie úspešne uložené", "Bodovanie bolo úspešne pripísané k tanečnému telesu.");

            }

        }


    }

 */

}

