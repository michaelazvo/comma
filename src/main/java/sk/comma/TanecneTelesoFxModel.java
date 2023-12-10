package sk.comma;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import lombok.Data;
import sk.comma.entity.TanecneTeleso;

@Data

public class TanecneTelesoFxModel {
    private Long id;
    private StringProperty nazov = new SimpleStringProperty();
    private StringProperty umiestnenie = new SimpleStringProperty();
    private StringProperty hudba = new SimpleStringProperty();
    private StringProperty klub = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty telefonneCislo = new SimpleStringProperty();
    private StringProperty tanecnici = new SimpleStringProperty();
    private SimpleLongProperty kategoriaId = new SimpleLongProperty();
    private SimpleLongProperty sutazId = new SimpleLongProperty();

    public TanecneTelesoFxModel() {
    }

    public TanecneTelesoFxModel(TanecneTeleso teleso) {
        setNazov(teleso.getNazov());
        setKlub(teleso.getKlub());
        setEmail(teleso.getEmail());
        setHudba(teleso.getHudba());
        setEmail(teleso.getEmail());
        setTanecnici(teleso.getTanecnici());
        setTelefonneCislo(teleso.getTelefonneCislo());
        setKategoriaId(teleso.getKategoriaId());
        setSutazId(teleso.getSutazId());
        id = teleso.getId();

    }


    public String getNazov() {
        return nazov.get();
    }

    public void setNazov(String nazov) {
        this.nazov.setValue(nazov);
    }

    public StringProperty nazovProperty() {
        return nazov;
    }


    public String getHudba() {
        return hudba.get();
    }

    public void setHudba(String hudba) {
        this.hudba.setValue(hudba);
    }

    public StringProperty hudbaProperty() {
        return hudba;
    }

    public String getKlub() {
        return klub.get();
    }

    public void setKlub(String klub) {
        this.klub.setValue(klub);
    }

    public StringProperty klubProperty() {
        return klub;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getTelefonneCislo() {
        return telefonneCislo.get();
    }

    public void setTelefonneCislo(String telefonneCislo) {
        this.telefonneCislo.setValue(telefonneCislo);
    }

    public StringProperty telefonneCisloProperty() {
        return telefonneCislo;
    }

    public String getTanecnici() {
        return tanecnici.get();
    }

    public void setTanecnici(String tanecnici) {
        this.tanecnici.setValue(tanecnici);
    }

    public StringProperty tanecniciProperty() {
        return tanecnici;
    }
    public long getKategoriaId() {
        return kategoriaId.get();
    }

    public void setKategoriaId(long kategoriaId) {
        this.kategoriaId.set(kategoriaId);
    }

    public SimpleLongProperty kategoriaIdProperty() {
        return kategoriaId;
    }

    public long getSutazId() {
        return sutazId.get();
    }

    public void setSutazId(long sutazId) {
        this.sutazId.set(sutazId);
    }

    public SimpleLongProperty sutazIdProperty() {
        return sutazId;
    }

}


