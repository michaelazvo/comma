package sk.comma.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Sutaz {
    private int id;
    private String nazov;

    private LocalDate odDatum;
    private LocalDate doDatum;

    public Sutaz(String nazov, LocalDate odDatum, LocalDate doDatum) {
        this.nazov = nazov;
        this.odDatum = odDatum;
        this.doDatum = doDatum;
    }

    public Sutaz(int id, String nazov, LocalDate odDatum, LocalDate doDatum) {
        this.id = id;
        this.nazov = nazov;
        this.odDatum = odDatum;
        this.doDatum = doDatum;
    }

    public Sutaz(int id, String nazov, LocalDate odDatum) {
        this.id = id;
        this.nazov = nazov;
        this.odDatum = odDatum;
    }

    @Override
    public String toString() {
        return nazov + ", " + odDatum;
    }

}
