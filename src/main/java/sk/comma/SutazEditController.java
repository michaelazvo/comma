package sk.comma;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.PorotcaDao;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Porotca;
import sk.comma.entity.Sutaz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SutazEditController {

    @FXML
    private DatePicker datumDoPicker;

    @FXML
    private DatePicker datumOdPicker;

    @FXML
    private TextField hesloTextField;

    @FXML
    private TextField menoPorotcuTextField;

    @FXML
    private TextField nazovSutazeTextField;

    @FXML
    private ListView<Porotca> porotaListView;

    @FXML
    private Button ulozitButtonClick;

    @FXML
    private TextField priezviskoPorotcuTextField;

    @FXML
    private TextField uzivatelskeMenoTextField;

    @FXML
    private Button pridatPorotcuButton;

    private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();
    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    private ObservableList<Porotca> porotcovia = FXCollections.observableArrayList();

    Sutaz vybrataSutaz;
    private BooleanProperty isButtonDisabled = new SimpleBooleanProperty(true);

    private BooleanProperty jePridatButtonVypnuty = new SimpleBooleanProperty(true);

    public SutazEditController() {

    }

    public SutazEditController(Sutaz selectedSutaz) {
        this.vybrataSutaz = selectedSutaz;
    }

    @FXML
    public void initialize() {
        if (vybrataSutaz != null) {
            ulozitButtonClick.setDisable(false);
            nazovSutazeTextField.setText(vybrataSutaz.getNazov());
            datumOdPicker.setValue(vybrataSutaz.getOdDatum());
            datumDoPicker.setValue(vybrataSutaz.getDoDatum());
            List<Porotca> porotcovia = porotcaDao.getPorotcoviaPreSutaz(vybrataSutaz.getId());

            porotaListView.getItems().addAll(porotcovia);

            porotaListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Porotca>() {
                @Override
                public void changed(ObservableValue<? extends Porotca> observable, Porotca oldValue, Porotca newValue) {
                    if (newValue != null) {
                        menoPorotcuTextField.setText(newValue.getMeno());
                        priezviskoPorotcuTextField.setText(newValue.getPriezvisko());
                        uzivatelskeMenoTextField.setText(newValue.getUzivatelskeMeno());
                        hesloTextField.setText(newValue.getHeslo());
                    }
                }
            });
        } else {


            nazovSutazeTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
            datumOdPicker.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
            datumDoPicker.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
            porotaListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> checkFields());
            ulozitButtonClick.disableProperty().bind(isButtonDisabled);
        }
        menoPorotcuTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        priezviskoPorotcuTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        uzivatelskeMenoTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        hesloTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());

        pridatPorotcuButton.disableProperty().bind(jePridatButtonVypnuty);

    }

    private void checkFieldsForPridatPorotcu() {
        boolean ziadnePrazdnePole = !menoPorotcuTextField.getText().trim().isEmpty() &&
                !priezviskoPorotcuTextField.getText().trim().isEmpty() &&
                !uzivatelskeMenoTextField.getText().trim().isEmpty() &&
                !hesloTextField.getText().trim().isEmpty();

        jePridatButtonVypnuty.set(!ziadnePrazdnePole);
    }

    private void checkFields() {
        LocalDate datumOd = datumOdPicker.getValue();
        LocalDate datumDo = datumDoPicker.getValue();
        boolean ziadnePrazdnePole = !nazovSutazeTextField.getText().trim().isEmpty() &&
                datumOd != null && datumDo != null &&
                !porotaListView.getItems().isEmpty(); // kontrola, či je ListView s porotcami vyplnený

        isButtonDisabled.set(!ziadnePrazdnePole);

    }

    @FXML
    void pridatPorotcuButtonClick(ActionEvent event) {

        String meno = menoPorotcuTextField.getText().trim();
        String priezvisko = priezviskoPorotcuTextField.getText().trim();
        String uzivatelskeMeno = uzivatelskeMenoTextField.getText().trim();
        String heslo = hesloTextField.getText().trim();
        String sol = BCrypt.gensalt();
        Porotca porotca = new Porotca(meno, priezvisko, uzivatelskeMeno, Hashovanie.hashovanie(heslo, sol), false, sol);

        porotcovia.add(porotca);

        porotaListView.getItems().add(porotca);
        porotcaDao.insert(porotca);
        menoPorotcuTextField.clear();
        priezviskoPorotcuTextField.clear();
        uzivatelskeMenoTextField.clear();
        hesloTextField.clear();
    }


    @FXML
    void ulozitSutazButtonClick(ActionEvent event) {
        String nazov = nazovSutazeTextField.getText().trim();
        LocalDate datumOd = datumOdPicker.getValue();
        LocalDate datumDo = datumDoPicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (datumOd != null && datumDo != null && (datumOd.isBefore(LocalDate.now()) || datumDo.isBefore(LocalDate.now()))) {
            showAlert("Neplatné dátumy", "Vyberte dátumy z budúcnosti.");
            return;
        }

        Sutaz sutaz = new Sutaz(nazov, datumOd, datumDo);
        if (vybrataSutaz != null) {
            vybrataSutaz.setNazov(nazov);
            vybrataSutaz.setOdDatum(datumOd);
            vybrataSutaz.setDoDatum(datumDo);
            sutazDao.update(vybrataSutaz);
            for (Porotca porotca : porotcovia) {
                porotcaDao.pridajPorotcuDoSutaze(porotca.getId(), vybrataSutaz.getId());
            }
        } else {
            sutazDao.insert(sutaz);
            for (Porotca porotca : porotcovia) {
                porotcaDao.pridajPorotcuDoSutaze(porotca.getId(), sutaz.getId());
            }
        }

        ulozitButtonClick.getScene().getWindow().hide();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void zmazatPorotcuButtonClick(ActionEvent event) {
        Porotca vybranyPorotca = porotaListView.getSelectionModel().getSelectedItem();

        if (vybranyPorotca != null) {
            Alert confirmDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDeleteAlert.setTitle("Potvrdenie zmazania");
            confirmDeleteAlert.setHeaderText("Ste si istý/á, že chcete vymazať porotcu?");
            confirmDeleteAlert.setContentText("Vyberte možnosť:");

            ButtonType deleteButton = new ButtonType("Vymazať");
            ButtonType cancelButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmDeleteAlert.getButtonTypes().setAll(deleteButton, cancelButton);

            confirmDeleteAlert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == deleteButton) {
                    porotcovia.remove(vybranyPorotca);
                    porotcaDao.delete(vybranyPorotca);
                    porotaListView.getItems().remove(vybranyPorotca);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Vymazanie úspešné");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Porotca bol úspešne vymazaný.");
                    successAlert.showAndWait();
                }
            });
        }
    }


}
