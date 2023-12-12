package sk.comma;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.KategoriaDao;
import sk.comma.dao.SutazDao;
import sk.comma.dao.TanecneTelesoDao;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// NEVIEM CI TO NEMA BYT TANECNETELESOEDIT ? NETUSIM AKO TO JE KED V JEDNO OKNE TO JE PRAZDNE A V DRUHOM VYPLNENE

public class TanecneTelesoController {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField hudbaTextField;

    @FXML
    private TextField klubTextField;

    @FXML
    private TextField nazovTelesaTextField;


    @FXML
    private TextArea tanecniciTextField;

    @FXML
    private TextField telefonneCisloTextFied;

    @FXML
    private ComboBox<String> stylCombobox;

    @FXML
    private ComboBox<String> vekCombobox;

    @FXML
    private ComboBox<String> velkostnaSkupinaCombobox;

    @FXML
    private Button ulozitTelesoButton;

    @FXML
    private Button prihlasitTelesoButton;

    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();
    private ObservableList<Kategoria> kategoriaModel;
    private List<Kategoria> kategorie;

    private TanecneTelesoDao tanecneTelesoDao = DaoFactory.INSTANCE.getTanecneTelesoDao();
    private TanecneTelesoFxModel tanecneTelesoModel;
    private TanecneTeleso savedTanecneTeleso;
    private Kategoria savedKategoria;

    TanecneTeleso vybraneTeleso;

    // aktualna sutaz
    private int sutazId;
    boolean adminMode;




    public TanecneTelesoController(){

    }

    public TanecneTelesoController(TanecneTeleso vybraneTanecneTeleso, boolean adminMode){
        this.vybraneTeleso = vybraneTanecneTeleso;
        this.adminMode = adminMode;
    }

    // metoda pouzita v MainSceneController odkial si zapamatavame id sutaze
    public void setSutazId(Sutaz sutaz) {
        this.sutazId = sutaz.getId();
    }


    // ak kategoria existuje, vrati jej id, ak nie, vyvtori sa nova kategoria a vrati jej id
    private long getKategoriaId(String styl, String velkostnaSkupina, String vekovaSkupina) {
        long kategoriaId = 0;
        boolean existing = false;
        if (kategorie.isEmpty()) {
            Kategoria kategoriaNova = new Kategoria();
            kategoriaNova.setStyl(styl);
            kategoriaNova.setVekovaSkupina(vekovaSkupina);
            kategoriaNova.setVelkostnaSkupina(velkostnaSkupina);

            savedKategoria = kategoriaDao.insert(kategoriaNova);

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

            savedKategoria = kategoriaDao.insert(kategoriaNova);

            kategoriaId = kategoriaNova.getId();
        }
        return kategoriaId;
    }

    @FXML
    void prihlasitTelesoButtonClick(ActionEvent event) {
        try {
            // Create a TanecneTeleso object from the input fields
            TanecneTeleso teleso = new TanecneTeleso();
            teleso.setNazov(nazovTelesaTextField.getText());
            teleso.setHudba(hudbaTextField.getText());
            teleso.setKlub(klubTextField.getText());
            teleso.setEmail(emailTextField.getText());
            teleso.setTelefonneCislo(telefonneCisloTextFied.getText());
            teleso.setTanecnici(tanecniciTextField.getText());
            String selectedStyl = stylCombobox.getValue();
            String selectedVelkostnaSkupina = velkostnaSkupinaCombobox.getValue();
            String selectedVekovaSkupina = vekCombobox.getValue();

            //pouzitie metody na vyhladanie id kategorie
            teleso.setKategoriaId(getKategoriaId(selectedStyl, selectedVelkostnaSkupina, selectedVekovaSkupina));

            //priradenie Id aktualnej sutaze
            teleso.setSutazId(sutazId);

            // Save the TanecneTeleso object to the database
            savedTanecneTeleso = tanecneTelesoDao.insert(teleso);
            prihlasitTelesoButton.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions that may occur during the save operation
            // Optionally, you can display an error message to the user
        }


    }

    @FXML
    void ulozitTelesoButtonClick(ActionEvent event) {
        vybraneTeleso.setNazov(nazovTelesaTextField.getText());
        vybraneTeleso.setHudba(hudbaTextField.getText());
        vybraneTeleso.setKlub(klubTextField.getText());
        vybraneTeleso.setEmail(emailTextField.getText());
        vybraneTeleso.setTelefonneCislo(telefonneCisloTextFied.getText());
        vybraneTeleso.setTanecnici(tanecniciTextField.getText());
        String selectedStyl = stylCombobox.getValue();
        String selectedVelkostnaSkupina = velkostnaSkupinaCombobox.getValue();
        String selectedVekovaSkupina = vekCombobox.getValue();

        //pouzitie metody na vyhladanie id kategorie
        vybraneTeleso.setKategoriaId(getKategoriaId(selectedStyl, selectedVelkostnaSkupina, selectedVekovaSkupina));
        tanecneTelesoDao.update(vybraneTeleso);
        ulozitTelesoButton.getScene().getWindow().hide();
    }

// toto neviem ci vyuzijeme, mozno dakedy hej
//    public TanecneTelesoController(TanecneTeleso tanecneTeleso){
//        tanecneTelesoModel=new TanecneTelesoFxModel();
//    }

    @FXML
    void initialize() {
        kategorie = kategoriaDao.findAll();

        stylCombobox.getItems().addAll(Kategoria.getStylTypes());
        vekCombobox.getItems().addAll(Kategoria.getVekovaSkupinaTypes());
        velkostnaSkupinaCombobox.getItems().addAll(Kategoria.getVelkostnaSkupinaTypes());

        ulozitTelesoButton.setVisible(adminMode);

        if(vybraneTeleso!=null){
            prihlasitTelesoButton.setVisible(false);
            nazovTelesaTextField.setText(vybraneTeleso.getNazov());
            Long kategoriaId = vybraneTeleso.getKategoriaId();
            Kategoria kategoria = kategoriaDao.findById(kategoriaId);
            stylCombobox.setValue(kategoria.getStyl());
            vekCombobox.setValue(kategoria.getVekovaSkupina());
            velkostnaSkupinaCombobox.setValue(kategoria.getVelkostnaSkupina());
            hudbaTextField.setText(vybraneTeleso.getHudba());
            klubTextField.setText(vybraneTeleso.getKlub());
            emailTextField.setText(vybraneTeleso.getEmail());
            telefonneCisloTextFied.setText(vybraneTeleso.getTelefonneCislo());
            tanecniciTextField.setText(vybraneTeleso.getTanecnici());

        } else {


            // Optionally, select default items if available
            stylCombobox.getSelectionModel().selectFirst();
            vekCombobox.getSelectionModel().selectFirst();
            velkostnaSkupinaCombobox.getSelectionModel().selectFirst();


        }

    }


}
