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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.HodnotenieDao;
import sk.comma.dao.SutazDao;
import sk.comma.dao.TanecneTelesoDao;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SutazController {

    @FXML
    private TextField idTelesaTextField;

    @FXML
    private Label nazovTelesaLabel;

    @FXML
    private ComboBox<Sutaz> sutazCombobox;

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();

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

    @FXML
    void editTelesaButtonClick(ActionEvent event) {
        String idTelesa = idTelesaTextField.getText();
        long id = Long.parseLong(idTelesa);
        TanecneTeleso tanecneTeleso = tanecneTelesoDao.findById(id);
        ;
        TanecneTelesoController controller = new TanecneTelesoController(tanecneTeleso, true);
        otvorenieEditacieTanecnehoTelesa(controller);
    }


    private void pridanieSutaze(SutazEditController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SutazEdit.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage PridanieSutazeStage = new Stage();
            PridanieSutazeStage.setScene(new Scene(parent));
            PridanieSutazeStage.setTitle("Pridanie sutaze");
            PridanieSutazeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Editacia tanecneho telesa");
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void zmazanieTelesaButtonClick(ActionEvent event) {
        String idTelesa = idTelesaTextField.getText();

        try {
            long id = Long.parseLong(idTelesa);

            TanecneTeleso telesoToDelete = tanecneTelesoDao.findById(id);

            if (telesoToDelete != null) {
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
                        idTelesaTextField.setText("");
                        nazovTelesaLabel.setText("");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Chyba pri vymazávaní");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Nastala chyba pri vymazávaní telesa z databázy.");
                        errorAlert.showAndWait();
                    }
                }
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
            invalidIdAlert.setContentText("Zadajte platné ID telesa na vymazanie.");
            invalidIdAlert.showAndWait();
        }
    }
}
