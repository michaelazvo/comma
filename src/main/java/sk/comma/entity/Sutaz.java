package sk.comma.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sutaz {
    private int id;
    private String nazov;
    // zmenila som z localdatetime na string
    private String odDatum;
    private String doDatum;

    public Sutaz(int id, String nazov, String odDatum) {
        this.id = id;
        this.nazov = nazov;
        this.odDatum = odDatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getOdDatum() {
        return odDatum;
    }

    public void setOdDatum(String odDatum) {
        this.odDatum = odDatum;
    }

    public String getDoDatum() {
        return doDatum;
    }

    public void setDoDatum(String doDatum) {
        this.doDatum = doDatum;
    }

    @Override
    public String toString() {
        return id + ", " + nazov + ", " + odDatum;
    }
}
