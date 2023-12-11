package sk.comma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Sutaz;

import java.io.IOException;
import java.util.List;

public class MainSceneController {
    @FXML
    private ComboBox<Sutaz> sutazCombobox;


    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();
    private ObservableList<Sutaz> sutazModel;
    private List<Sutaz> sutaze;


    @FXML
    void initialize() {

        sutaze = sutazDao.findAll();
        sutazModel = FXCollections.observableList(sutaze);
        sutazCombobox.setItems(sutazModel);
        sutazCombobox.getSelectionModel().selectFirst();

    }


    @FXML
    void prihlasenieButtonClick(ActionEvent event) {
        PrihlasenieController controller = new PrihlasenieController(sutaze);
        controller.setSutazId(sutazCombobox.getValue());
        otvoritPrihlasovanieOkno(controller);
    }

    private void otvoritPrihlasovanieOkno(PrihlasenieController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("Prihlasenie.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage PrihlasovanieStage = new Stage();
            PrihlasovanieStage.setScene(new Scene(parent));
            PrihlasovanieStage.setTitle("Prihlasovanie");
            PrihlasovanieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void prihlasitTanecneTelesoButtonClick(ActionEvent event) {
        TanecneTelesoController controller = new TanecneTelesoController();
        // ulozenie zvolenej sutaze z comboboxu pre dalsi controller
        controller.setSutazId(sutazCombobox.getValue());
        otvoritTanecneTelesoOkno(controller);


    }

    private void otvoritTanecneTelesoOkno(TanecneTelesoController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("TanecneTeleso.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage TanecneTelesoStage = new Stage();
            TanecneTelesoStage.setScene(new Scene(parent));
            TanecneTelesoStage.setTitle("Tanecne teleso");
            TanecneTelesoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void zobrazitVysledkyButtonClick(ActionEvent event) {
        VysledkyTabulkaController controller = new VysledkyTabulkaController();
        // ulozenie zvolenej sutaze z comboboxu pre dalsi controller
        controller.setSutazId(sutazCombobox.getValue());
        otvoritVysledkyOkno(controller);

    }

    private void otvoritVysledkyOkno(VysledkyTabulkaController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("VysledkyTabulka.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage TanecneTelesoStage = new Stage();
            TanecneTelesoStage.setScene(new Scene(parent));
            TanecneTelesoStage.setTitle("Výsledky");
            TanecneTelesoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
