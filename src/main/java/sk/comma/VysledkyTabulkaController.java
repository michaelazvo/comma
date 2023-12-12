package sk.comma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.comma.business.OverviewManager;
import sk.comma.business.OverviewManagerImpl;
import sk.comma.business.vysledkyOverview;
import sk.comma.dao.DaoFactory;
import sk.comma.dao.HodnotenieDao;
import sk.comma.dao.KategoriaDao;
import sk.comma.dao.SutazDao;
import sk.comma.entity.Hodnotenie;
import sk.comma.entity.Kategoria;
import sk.comma.entity.Sutaz;
import sk.comma.entity.TanecneTeleso;

import java.util.List;

public class VysledkyTabulkaController {

    @FXML
    private Button hladatVysledkyButton;

    @FXML
    private ComboBox<String> stylCombobox;

    @FXML
    private ComboBox<String> vekCombobox;

    @FXML
    private ComboBox<String> velkostCombobox;

    @FXML
    private TableView<vysledkyOverview> vysledkyTableView;

    private HodnotenieDao hodnotenieDao = DaoFactory.INSTANCE.getHodnotenieDao();
    private List<Hodnotenie> hodnotenia;

    private SutazDao sutazDao = DaoFactory.INSTANCE.getSutazDao();
    private List<Sutaz> sutaze;
    private KategoriaDao kategoriaDao = DaoFactory.INSTANCE.getKategoriaDao();
    private ObservableList<Kategoria> kategoriaModel;
    private List<Kategoria> kategorie;

    private OverviewManager overviewManager = new OverviewManagerImpl();


    // aktualna sutaz
    private int sutazId;

    // metoda pouzita v MainSceneController odkial si zapamatavame id sutaze
    public void setSutazId(Sutaz sutaz) {
        System.out.println(sutaz);
        System.out.println(sutaz.getId());
        this.sutazId = sutaz.getId();
    }

    private Kategoria savedKategoria;


    @FXML
    void initialize() {

        // Set items for each ComboBox using the predefined types in Kategoria
        stylCombobox.getItems().addAll(Kategoria.getStylTypes());
        vekCombobox.getItems().addAll(Kategoria.getVekovaSkupinaTypes());
        velkostCombobox.getItems().addAll(Kategoria.getVelkostnaSkupinaTypes());

        // Optionally, select default items if available
        stylCombobox.getSelectionModel().selectFirst();
        vekCombobox.getSelectionModel().selectFirst();
        velkostCombobox.getSelectionModel().selectFirst();

        kategorie = kategoriaDao.findAll();

        reloadTable();

        TableColumn<vysledkyOverview, String> umiestnenieCol = new TableColumn<>("Umiestnenie");
        umiestnenieCol.setCellValueFactory(new PropertyValueFactory<>("umiestnenie"));
        vysledkyTableView.getColumns().add(umiestnenieCol);

        TableColumn<vysledkyOverview, Long> idCol = new TableColumn<>("P. č.");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        vysledkyTableView.getColumns().add(idCol);

        TableColumn<vysledkyOverview, String> nazovCol = new TableColumn<>("Názov");
        nazovCol.setCellValueFactory(new PropertyValueFactory<>("nazov"));
        vysledkyTableView.getColumns().add(nazovCol);

        TableColumn<vysledkyOverview, String> hudbaCol = new TableColumn<>("Hudba");
        hudbaCol.setCellValueFactory(new PropertyValueFactory<>("hudba"));
        vysledkyTableView.getColumns().add(hudbaCol);

        TableColumn<vysledkyOverview, String> klubCol = new TableColumn<>("Klub");
        klubCol.setCellValueFactory(new PropertyValueFactory<>("klub"));
        vysledkyTableView.getColumns().add(klubCol);

        TableColumn<vysledkyOverview, String> tanecniciCol = new TableColumn<>("Tanečníci");
        tanecniciCol.setCellValueFactory(new PropertyValueFactory<>("tanecnici"));
        vysledkyTableView.getColumns().add(tanecniciCol);


        TableColumn<vysledkyOverview, Integer> hodnotenieCol = new TableColumn<>("Hodnotenie");
        hodnotenieCol.setCellValueFactory(new PropertyValueFactory<>("hodnotenie"));
        vysledkyTableView.getColumns().add(hodnotenieCol);


    }

    private void reloadTable() {
        String selectedStyl = stylCombobox.getValue();
        String selectedVelkostnaSkupina = velkostCombobox.getValue();
        String selectedVekovaSkupina = vekCombobox.getValue();
        long kategoriaId = getKategoriaId(selectedStyl, selectedVelkostnaSkupina, selectedVekovaSkupina);
        Kategoria kategoriaVyber = kategoriaDao.findById(kategoriaId);
        Sutaz sutazVyber = sutazDao.findById(sutazId);


        List<vysledkyOverview> overviews = overviewManager.getOverviews(kategoriaVyber, sutazVyber);
        vysledkyTableView.setItems(FXCollections.observableArrayList(overviews));

        overviews.sort((o1, o2) -> Integer.compare(o2.getHodnotenie(), o1.getHodnotenie()));

        // Assign umiestnenie based on the sorted order
        for (int i = 0; i < overviews.size(); i++) {
            overviews.get(i).setUmiestnenie(Integer.toString(i + 1));
        }

        vysledkyTableView.setItems(FXCollections.observableArrayList(overviews));
    }

    @FXML
    void hladatVysledkyButton(ActionEvent event) {
        reloadTable();
    }

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


}

