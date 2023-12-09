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
    //private BooleanProperty isButtonDisabled = new SimpleBooleanProperty(true);

    //private BooleanProperty jePridatButtonVypnuty = new SimpleBooleanProperty(true);

    public SutazEditController(){

    }

    public SutazEditController(Sutaz selectedSutaz) {
        this.vybrataSutaz = selectedSutaz;
    }

    @FXML
    public void initialize() {
        if(vybrataSutaz!=null){
            nazovSutazeTextField.setText(vybrataSutaz.getNazov());
            datumOdPicker.setValue(vybrataSutaz.getOdDatum());
            datumDoPicker.setValue(vybrataSutaz.getDoDatum());
            List<Porotca> porotcovia = porotcaDao.getPorotcoviaPreSutaz(vybrataSutaz.getId());


            // Přidání porotců do ListView
            porotaListView.getItems().addAll(porotcovia);

            porotaListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Porotca>() {
                @Override
                public void changed(ObservableValue<? extends Porotca> observable, Porotca oldValue, Porotca newValue) {
                    // Zde můžete aktualizovat textová pole podle vybraného porotce
                    if (newValue != null) {
                        menoPorotcuTextField.setText(newValue.getMeno());
                        priezviskoPorotcuTextField.setText(newValue.getPriezvisko());
                        uzivatelskeMenoTextField.setText(newValue.getUzivatelskeMeno());
                        hesloTextField.setText(newValue.getHeslo());
                    }
                }
            });
        }


        /*
        nazovSutazeTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        datumOdPicker.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
        datumDoPicker.valueProperty().addListener((observable, oldValue, newValue) -> checkFields());
        porotaListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> checkFields());

        menoPorotcuTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        priezviskoPorotcuTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        uzivatelskeMenoTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());
        hesloTextField.textProperty().addListener((observable, oldValue, newValue) -> checkFieldsForPridatPorotcu());

        ulozitButtonClick.disableProperty().bind(isButtonDisabled);
        pridatPorotcuButton.disableProperty().bind(jePridatButtonVypnuty);
        */


    }
    /*
    private void checkFieldsForPridatPorotcu() {
        boolean ziadnePrazdnePole = !menoPorotcuTextField.getText().trim().isEmpty() &&
                !priezviskoPorotcuTextField.getText().trim().isEmpty() &&
                !uzivatelskeMenoTextField.getText().trim().isEmpty() &&
                !hesloTextField.getText().trim().isEmpty();

        // Set the value of isButtonDisabled based on the condition
        jePridatButtonVypnuty.set(!ziadnePrazdnePole);
    }

    private void checkFields() {
        LocalDate datumOd = datumOdPicker.getValue();
        LocalDate datumDo = datumDoPicker.getValue();
        boolean ziadnePrazdnePole = !nazovSutazeTextField.getText().trim().isEmpty() &&
                datumOd != null && datumDo != null &&
                !porotaListView.getSelectionModel().getSelectedItems().isEmpty();

        // Set the value of isButtonDisabled based on the condition
        if (vybrataSutaz != null) {
            isButtonDisabled.set(!ziadnePrazdnePole);
        }
    }
    */
    @FXML
    void pridatPorotcuButtonClick(ActionEvent event) {

        String meno = menoPorotcuTextField.getText().trim();
        String priezvisko = priezviskoPorotcuTextField.getText().trim();
        String uzivatelskeMeno = uzivatelskeMenoTextField.getText().trim();
        String heslo = hesloTextField.getText().trim();
        Porotca porotca = new Porotca(meno, priezvisko, uzivatelskeMeno, heslo, false);
        //checkFieldsForPridatPorotcu();
        porotcovia.add(porotca);

        // Aktualizovat ListView
        porotaListView.getItems().add(porotca);
        porotcaDao.insert(porotca);
        // Nyní můžete provést další operace, např. vyčistit pole pro zadávání nového porotce
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        Sutaz sutaz =new Sutaz(nazov, datumOd, datumDo);
        if(vybrataSutaz!=null){
            sutaz = vybrataSutaz;
            sutazDao.update(vybrataSutaz);
        } else {
            sutazDao.insert(sutaz);
        }
        for (Porotca porotca : porotcovia) {
            System.out.println(porotca.getId());
            porotcaDao.pridajPorotcuDoSutaze(porotca.getId(),sutaz.getId());
        }

        ulozitButtonClick.getScene().getWindow().hide();
    }

    @FXML
    void zmazatPorotcuButtonClick(ActionEvent event) {
        Porotca vybranyPorotca = porotaListView.getSelectionModel().getSelectedItem();

        if (vybranyPorotca != null) {
            // Vytvořit Alert s výběrem možností
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrdenie zmazania");
            alert.setHeaderText("Chcete zmazať porotcu?");
            alert.setContentText("Vyberte možnosť:");

            // Přidat tlačítka pro výběr možnosti
            ButtonType vymazatCelkovoButton = new ButtonType("Vymazať úplne zo záznamu");
            ButtonType vymazatZoSutazeButton = new ButtonType("Vymazať zo súťaže");
            ButtonType cancelButton = new ButtonType("Zrušit", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(vymazatCelkovoButton, vymazatZoSutazeButton, cancelButton);

            // Zobrazit alert a zpracovat výsledek
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == vymazatCelkovoButton) {
                    porotcovia.remove(vybranyPorotca);
                    porotcaDao.delete(vybranyPorotca);
                    porotaListView.getItems().remove(vybranyPorotca);
                } else if (buttonType == vymazatZoSutazeButton) {
                    porotcovia.remove(vybranyPorotca);
                    porotcaDao.vymazPorotcuZoSutaze(vybranyPorotca.getId(), vybrataSutaz.getId());
                    porotaListView.getItems().remove(vybranyPorotca);
                }
            });
        }
    }

}
