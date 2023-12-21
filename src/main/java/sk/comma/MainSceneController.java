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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import sk.comma.dao.DaoFactory;
import sk.comma.dao.PorotcaDao;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Sutaz;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MainSceneController {
    @FXML
    private ComboBox<Sutaz> sutazCombobox;

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    private ObservableList<Sutaz> sutazModel;
    private List<Sutaz> sutaze;

    private PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();

    @FXML
    private Button prihlasitTelesoButton;

    @FXML
    void initialize() {
        sutaze = sutazDao.findAll();
        sutazModel = FXCollections.observableList(sutaze);
        sutazCombobox.setItems(sutazModel);
        sutazCombobox.getSelectionModel().selectFirst();

        sutazCombobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sutaz>() {
            @Override
            public void changed(ObservableValue<? extends Sutaz> observable, Sutaz oldValue, Sutaz newValue) {
                updatePrihlasitButtonState(newValue);
            }
        });

        updatePrihlasitButtonState(sutazCombobox.getValue());

    }

    @FXML
    void prihlasenieButtonClick(ActionEvent event) {
        PrihlasenieController controller = new PrihlasenieController(sutaze);
        controller.setSutazId(sutazCombobox.getValue());
        otvoritPrihlasovanieOkno(controller);
    }

    @FXML
    void prihlasitTanecneTelesoButtonClick(ActionEvent event) {
        TanecneTelesoController controller = new TanecneTelesoController();
        // ulozenie zvolenej sutaze z comboboxu pre dalsi controller
        controller.setSutazId(sutazCombobox.getValue());
        otvoritTanecneTelesoOkno(controller);
    }

    @FXML
    void zobrazitVysledkyButtonClick(ActionEvent event) {
        VysledkyTabulkaController controller = new VysledkyTabulkaController();
        // ulozenie zvolenej sutaze z comboboxu pre dalsi controller
        controller.setSutazId(sutazCombobox.getValue());
        otvoritVysledkyOkno(controller);

    }

    private void updatePrihlasitButtonState(Sutaz selectedSutaz) {
        if (selectedSutaz != null) {
            boolean isButtonDisabled = isPrihlasitButtonDisabled(selectedSutaz);
            prihlasitTelesoButton.setDisable(isButtonDisabled);
        }
    }


    private boolean isPrihlasitButtonDisabled(Sutaz sutaz) {
        return sutaz.getOdDatum().isBefore(LocalDate.now()) || sutaz.getDoDatum().isBefore(LocalDate.now());
    }



    private void otvoritPrihlasovanieOkno(PrihlasenieController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("Prihlasenie.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage PrihlasovanieStage = new Stage();
            PrihlasovanieStage.getIcons().add(new Image(Objects.requireNonNull(MainSceneController.class.getResourceAsStream("comma_logo.png"))));
            PrihlasovanieStage.setScene(new Scene(parent));
            PrihlasovanieStage.setTitle("Prihlasovanie");
            PrihlasovanieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void otvoritTanecneTelesoOkno(TanecneTelesoController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("TanecneTeleso.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage TanecneTelesoStage = new Stage();
            TanecneTelesoStage.getIcons().add(new Image(Objects.requireNonNull(MainSceneController.class.getResourceAsStream("comma_logo.png"))));
            TanecneTelesoStage.setScene(new Scene(parent));
            TanecneTelesoStage.setTitle("Tanecne teleso");
            TanecneTelesoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void otvoritVysledkyOkno(VysledkyTabulkaController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("VysledkyTabulka.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage VysledkyStage = new Stage();
            VysledkyStage.getIcons().add(new Image(Objects.requireNonNull(MainSceneController.class.getResourceAsStream("comma_logo.png"))));
            VysledkyStage.setScene(new Scene(parent));
            VysledkyStage.setTitle("Výsledky");
            VysledkyStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
