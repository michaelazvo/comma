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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.SutazDao;
import sk.comma.dao.TanecneTelesoDao;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.io.IOException;
import java.util.List;

public class SutazController {

    @FXML
    private TextField idTelesaTextField;

    @FXML
    private Label nazovTelesaLabel;

    @FXML
    private ComboBox<Sutaz> sutazCombobox;

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
    private ObservableList<Sutaz> sutazModel;
    private List<Sutaz> sutaze;

    @FXML
    public void initialize() {
        sutaze = sutazDao.findAll();
        sutazModel = FXCollections.observableList(sutaze);
        sutazCombobox.setItems(sutazModel);
        sutazCombobox.getSelectionModel().selectFirst();


        idTelesaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateNazovTelesaLabel();
            }
        });
    }

    private void updateNazovTelesaLabel() {
        String idTelesa = idTelesaTextField.getText();
        try {
            Long id = Long.parseLong(idTelesa);
            TanecneTeleso tanecneTeleso = tanecneTelesoDao.findById(id);
            if (tanecneTeleso != null) {
                nazovTelesaLabel.setText(tanecneTeleso.getNazov());
            }

        } catch (NumberFormatException e) {
            // Handle the case when the entered ID is not a valid number
            nazovTelesaLabel.setText("Invalid ID");
        }
    }

    @FXML
    void pridatSutazButtonClick(ActionEvent event) {
        SutazEditController sutazPridanie = new SutazEditController();
        pridanieSutaze(sutazPridanie);
    }


    private void pridanieSutaze(SutazEditController controller){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SutazEdit.fxml"));
            loader.setController(controller);
            Parent parent= loader.load();
            Stage PridanieSutazeStage = new Stage();
            PridanieSutazeStage.setScene(new Scene(parent));
            PridanieSutazeStage.setTitle("Pridanie sutaze");
            PridanieSutazeStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void editSutazeButtonClick(ActionEvent event) {
        Sutaz selectedSutaz = sutazCombobox.getSelectionModel().getSelectedItem();
        SutazEditController controller = new SutazEditController(selectedSutaz);
        otvorenieEditacieSutaze(controller);
    }

    private void otvorenieEditacieSutaze(SutazEditController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SutazEdit.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editacia Sutaze");
            stage.showAndWait();

            Sutaz vybrataSutaz = controller.vybrataSutaz;


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void editTelesaButtonClick(ActionEvent event) {
        String idTelesa = idTelesaTextField.getText();
         Long id = Long.parseLong(idTelesa);
        TanecneTeleso tanecneTeleso = tanecneTelesoDao.findById(id);;
        TanecneTelesoController controller = new TanecneTelesoController(tanecneTeleso, true);
        otvorenieEditacieTanecnehoTelesa(controller);
    }

    private void otvorenieEditacieTanecnehoTelesa(TanecneTelesoController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("TanecneTeleso.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editacia tanecneho telesa");
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @FXML
    void zmazanieTelesaButtonClick(ActionEvent event) {

    }
}
