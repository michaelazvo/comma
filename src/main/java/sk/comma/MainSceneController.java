package sk.comma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Sutaz;

import java.util.List;

public class MainSceneController {
    @FXML
    private Button prihlaseniePorotcaButton;
    @FXML
    private Button prihlasitTanecneTelesoButton;
    @FXML
    private ComboBox<Sutaz> sutazCombobox;
    @FXML
    private Button zobrazitVysledkyButton;

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();
    private ObservableList<Sutaz> sutazModel;


    @FXML
    void initialize() {

        List<Sutaz> sutaze = sutazDao.findAll();
        sutazModel = FXCollections.observableList(sutaze);
        sutazCombobox.setItems(sutazModel);
        sutazCombobox.getSelectionModel().selectFirst();

    }


    @FXML
    void porotaButtonClick(ActionEvent event) {

    }

    @FXML
    void prihlasitTanecneTelesoButtonClick(ActionEvent event) {

    }

    @FXML
    void spravcaButtonClick(ActionEvent event) {

    }

    @FXML
    void zobrazitVysledkyButtonClick(ActionEvent event) {

    }


}
