package sk.comma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.PorotcaDao;
import sk.comma.entity.Sutaz;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.List;

public class PrihlasenieController {


    @FXML
    private TextField uzivatelskeMenoTextField;
    @FXML
    private PasswordField hesloPasswordField;

    private List<Sutaz> sutaze;

    public PrihlasenieController(List<Sutaz> sutaze) {
        this.sutaze = sutaze;
    }




    @FXML
    void prihlasenieButtonClick(ActionEvent event) {
       String meno = uzivatelskeMenoTextField.getText();
       String heslo = hesloPasswordField.getText();
       PorotcaDao porotcaDao = DaoFactory.INSTANCE.getPorotcaDao();
        boolean existujeUzivatel = porotcaDao.existingUser(heslo,meno);

        if (existujeUzivatel) {
            // Uživatel existuje, zkontrolovat správnost hesla
            boolean jeSpravneHeslo = porotcaDao.isPasswordCorrect(heslo, meno);
            boolean jeAdmin = porotcaDao.isAdmin(heslo, meno);

            if (jeAdmin && jeSpravneHeslo) {
                SutazController controller = new SutazController();
                otvoritAdminOkno(controller);
            } else if (!jeAdmin && jeSpravneHeslo) {
                HodnoteniePorotaController controller = new HodnoteniePorotaController();
                otvoritPorotcaOkno(controller);
            } else {
                zobrazChybovyAlert("Chybné jméno nebo heslo. Zkuste to znovu.");
            }
        } else {
            // Uživatel neexistuje
            zobrazChybovyAlert("Uživatel s tímto jménem neexistuje.");
        }


    }

    private void otvoritAdminOkno(SutazController controller){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("Sutaz.fxml"));
            loader.setController(controller);
            Parent parent= loader.load();
            Stage PridanieSutazeStage = new Stage();
            PridanieSutazeStage.setScene(new Scene(parent));
            PridanieSutazeStage.setTitle("Sutaz");
            PridanieSutazeStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void otvoritPorotcaOkno(HodnoteniePorotaController controller){
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("HodnoteniePorota.fxml"));
            loader.setController(controller);
            Parent parent= loader.load();
            Stage HodnotenieStage = new Stage();
            HodnotenieStage.setScene(new Scene(parent));
            HodnotenieStage.setTitle("Hodnotenie");
            HodnotenieStage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void zobrazChybovyAlert(String chybovaZprava) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Chyba přihlášení");
        alert.setHeaderText(null);
        alert.setContentText(chybovaZprava);
        alert.showAndWait();
    }

}
