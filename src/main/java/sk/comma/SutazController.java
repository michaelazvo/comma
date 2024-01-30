package sk.comma;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.comma.dao.*;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SutazController {


    @FXML
    private Button hladatTelesaButton;

    @FXML
    private ComboBox<Sutaz> sutazCombobox;

    @FXML
    private ComboBox<String> stylCombobox;

    @FXML
    private ComboBox<String> vekCombobox;

    @FXML
    private ComboBox<String> velkostCombobox;

    @FXML
    private ComboBox<TanecneTeleso> zoznamTeliesCombobox;

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();

    private List<Kategoria> kategorie;

    private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
    private ObservableList<Sutaz> sutazModel;
    private List<Sutaz> sutaze;

    @FXML
    public void initialize() {
        sutaze = sutazDao.findAll();
        sutazModel = FXCollections.observableList(sutaze);
        sutazCombobox.setItems(sutazModel);
        sutazCombobox.getSelectionModel().selectFirst();

        stylCombobox.getItems().addAll(Kategoria.getStylTypes());
        vekCombobox.getItems().addAll(Kategoria.getVekovaSkupinaTypes());
        velkostCombobox.getItems().addAll(Kategoria.getVelkostnaSkupinaTypes());

        stylCombobox.getSelectionModel().selectFirst();
        vekCombobox.getSelectionModel().selectFirst();
        velkostCombobox.getSelectionModel().selectFirst();

        kategorie = kategoriaDao.findAll();
        reloadList();


    }

    @FXML
    void hladatTelesaButtonClick(ActionEvent event) {
        reloadList();
    }

    @FXML
    void editSutazeButtonClick(ActionEvent event) {
        Sutaz selectedSutaz = sutazCombobox.getSelectionModel().getSelectedItem();
        SutazEditController controller = new SutazEditController(selectedSutaz);
        otvorenieEditacieSutaze(controller);
    }

    @FXML
    void pridatSutazButtonClick(ActionEvent event) {
        SutazEditController sutazPridanie = new SutazEditController();
        pridanieSutaze(sutazPridanie);
    }

    /*
        @FXML
        void editTelesaButtonClick(ActionEvent event) {
            String idTelesa = idTelesaTextField.getText();

            if (idTelesa.trim().isEmpty()) {
                Alert emptyIdAlert = new Alert(Alert.AlertType.WARNING);
                emptyIdAlert.setTitle("Prázdne ID");
                emptyIdAlert.setHeaderText(null);
                emptyIdAlert.setContentText("Nebolo zadané ID telesa.");
                emptyIdAlert.showAndWait();
                return;
            }

            try {
                long id = Long.parseLong(idTelesa);
                TanecneTeleso tanecneTeleso = tanecneTelesoDao.findById(id);

                if (tanecneTeleso != null) {
                    TanecneTelesoController controller = new TanecneTelesoController(tanecneTeleso, true);
                    otvorenieEditacieTanecnehoTelesa(controller);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.WARNING);
                    notFoundAlert.setTitle("Teleso nenájdené");
                    notFoundAlert.setHeaderText(null);
                    notFoundAlert.setContentText("Teleso s daným ID nebolo nájdené.");
                    notFoundAlert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert invalidIdAlert = new Alert(Alert.AlertType.ERROR);
                invalidIdAlert.setTitle("Neplatné ID");
                invalidIdAlert.setHeaderText(null);
                invalidIdAlert.setContentText("Zadajte platné ID telesa.");
                invalidIdAlert.showAndWait();
            }
        }


     */
    @FXML
    void editTelesaNewButtonClick(ActionEvent event) {

        TanecneTeleso teleso = zoznamTeliesCombobox.getValue();

        if (teleso == null) {
            showAlert("Žiadne zvolené teleso", "Prosím, vyberte tanečné teleso zo zoznamu.");
            return;
        }

        TanecneTelesoController controller = new TanecneTelesoController(teleso, true);
        otvorenieEditacieTanecnehoTelesa(controller);

    }


    private void pridanieSutaze(SutazEditController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SutazEdit.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage PridanieSutazeStage = new Stage();
            PridanieSutazeStage.getIcons().add(new Image(Objects.requireNonNull(SutazController.class.getResourceAsStream("comma_logo.png"))));

            PridanieSutazeStage.setScene(new Scene(parent));
            PridanieSutazeStage.setTitle("Pridanie sutaze");
            PridanieSutazeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*

    private void updateNazovTelesaLabel() {
        String idTelesa = idTelesaTextField.getText();
        try {
            long id = Long.parseLong(idTelesa);
            TanecneTeleso tanecneTeleso = tanecneTelesoDao.findById(id);
            if (tanecneTeleso != null) {
                nazovTelesaLabel.setText(tanecneTeleso.getNazov());
            }

        } catch (NumberFormatException e) {
            nazovTelesaLabel.setText("Nespravne ID");
        }
    }

     */
    private void otvorenieEditacieSutaze(SutazEditController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SutazEdit.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(SutazController.class.getResourceAsStream("comma_logo.png"))));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editacia Sutaze");
            stage.showAndWait();

            Sutaz vybrataSutaz = controller.vybrataSutaz;


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void otvorenieEditacieTanecnehoTelesa(TanecneTelesoController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("TanecneTeleso.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(SutazController.class.getResourceAsStream("comma_logo.png"))));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editacia tanecneho telesa");
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadList() {
        Sutaz selectedSutaz = sutazCombobox.getValue();
        int sutazId = selectedSutaz.getId();
        String selectedStyl = stylCombobox.getValue();
        String selectedVelkostnaSkupina = velkostCombobox.getValue();
        String selectedVekovaSkupina = vekCombobox.getValue();
        long kategoriaId = getKategoriaId(selectedStyl, selectedVelkostnaSkupina, selectedVekovaSkupina);

        List<TanecneTeleso> telesaList = tanecneTelesoDao.findAllBySutazIdKategoriaId(sutazId, kategoriaId);
        ObservableList<TanecneTeleso> telesaObservableList = FXCollections.observableArrayList(telesaList);
        zoznamTeliesCombobox.setItems(telesaObservableList);
        zoznamTeliesCombobox.getSelectionModel().selectFirst();

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


    @FXML
    void zmazanieTelesaButtonClick(ActionEvent event) {

        TanecneTeleso telesoToDelete = zoznamTeliesCombobox.getValue();

        if (telesoToDelete == null) {
            showAlert("Žiadne zvolené teleso", "Prosím, vyberte tanečné teleso zo zoznamu.");
            return;
        }


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Potvrdenie vymazania");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Ste si istý, že chcete dané teleso vymazať?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            hodnotenieDao.deleteByTanecneTelesoId(telesoToDelete.getId());

            boolean deletionSuccess = tanecneTelesoDao.delete(telesoToDelete);

            if (deletionSuccess) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Vymazanie úspešné");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Teleso bolo úspešne vymazané z databázy.");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Chyba pri vymazávaní");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Nastala chyba pri vymazávaní telesa z databázy.");
                errorAlert.showAndWait();
            }
        }

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
