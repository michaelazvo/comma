package sk.comma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.PorotcaDao;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Porotca;
import sk.comma.entity.Sutaz;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrihlasenieController {


    @FXML
    private TextField uzivatelskeMenoTextField;
    @FXML
    private PasswordField hesloPasswordField;

    @FXML
    private Button prihlasenieButton;

    private List<Sutaz> sutaze;

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();

    // aktualna sutaz
    private int sutazId;

    // metoda pouzita v MainSceneController odkial si zapamatavame id sutaze
    public void setSutazId(Sutaz sutaz) {
        this.sutazId = sutaz.getId();
    }

    private boolean errorOccurred = false;

    public PrihlasenieController(List<Sutaz> sutaze) {
        this.sutaze = sutaze;
    }

    @FXML
    private void initialize() {
        errorOccurred = false;

        uzivatelskeMenoTextField.setOnKeyPressed(this::handleEnterKeyPressed);
        hesloPasswordField.setOnKeyPressed(this::handleEnterKeyPressed);
    }


    @FXML
    void prihlasenieButtonClick(ActionEvent event) {
        String meno = uzivatelskeMenoTextField.getText();
        String heslo = hesloPasswordField.getText();
        PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();
        boolean existujeUzivatel = porotcaDao.existingUser(heslo, meno);
        List<Porotca> porota = porotcaDao.getPorotcoviaPreSutaz(sutazId);
        List<String> menaPorotcov = new ArrayList<>();
        for (Porotca porotca : porota) {
            menaPorotcov.add(porotca.getUzivatelskeMeno());
        }
        if (existujeUzivatel) {
            // uzivatel existuje, skontrolovat spravnost hesla
            boolean jeSpravneHeslo = porotcaDao.isPasswordCorrect(heslo, meno);
            boolean jeAdmin = porotcaDao.isAdmin(heslo, meno);

            if (jeAdmin && jeSpravneHeslo) {
                SutazController controller = new SutazController();
                otvoritAdminOkno(controller);
            } else if (!jeAdmin && jeSpravneHeslo && menaPorotcov.contains(meno) && sutazDao.findById(sutazId).jeSutazAktualna()) {
                HodnoteniePorotaController controller = new HodnoteniePorotaController();
                Porotca porotca = null;
                for (Porotca p : porota) {
                    if (p.getUzivatelskeMeno().equals(meno)) {
                        porotca = p;
                        break;
                    }
                }
                if (porotca != null) {
                    controller.setPorotcaId(porotca.getId());
                }
                controller.setSutazId(sutazId);
                otvoritPorotcaOkno(controller);
            } else {
                if (!sutazDao.findById(sutazId).jeSutazAktualna()) {
                    zobrazitChybovyAlert("Súťaž nie je aktuálna. Prihlasovanie porotcov nie je povolené");
                } else {
                    zobrazitChybovyAlert("Nesprávne heslo. Skúste to znova.");
                }
            }
        } else {
            // uzivatel neexistuje
            zobrazitChybovyAlert("Užívateľ s týmto užívateľským menom neexistuje.");
        }
        if (!errorOccurred) {
            Stage stage = (Stage) prihlasenieButton.getScene().getWindow();
            stage.hide();
        }
    }

    private void otvoritAdminOkno(SutazController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("Sutaz.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage PridanieSutazeStage = new Stage();
            PridanieSutazeStage.getIcons().add(new Image(Objects.requireNonNull(PrihlasenieController.class.getResourceAsStream("comma_logo.png"))));
            PridanieSutazeStage.setScene(new Scene(parent));
            PridanieSutazeStage.setTitle("Sutaz");
            PridanieSutazeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void otvoritPorotcaOkno(HodnoteniePorotaController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("HodnoteniePorota.fxml"));
            loader.setController(controller);
            Parent parent = loader.load();
            Stage HodnotenieStage = new Stage();
            HodnotenieStage.getIcons().add(new Image(Objects.requireNonNull(PrihlasenieController.class.getResourceAsStream("comma_logo.png"))));
            HodnotenieStage.setScene(new Scene(parent));
            HodnotenieStage.setTitle("Hodnotenie");
            HodnotenieStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void zobrazitChybovyAlert(String chybovaSprava) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Chyba prihlásenia");
        alert.setHeaderText(null);
        alert.setContentText(chybovaSprava);
        alert.showAndWait();

        errorOccurred = true;
    }

    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            prihlasenieButtonClick(new ActionEvent());
        }
    }

}
