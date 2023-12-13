package sk.comma.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Porotca {
    private Long id;
    private String meno;
    private String priezvisko;
    private String uzivatelskeMeno;
    private String heslo;

    private boolean jeAdmin;


    public Porotca(){

    }

    public Porotca(String meno, String priezvisko, String uzivatelskeMeno, String heslo, boolean jeAdmin) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.uzivatelskeMeno = uzivatelskeMeno;
        this.heslo = heslo;
        this.jeAdmin = jeAdmin;
    }


    public Porotca(Long id, String meno, String priezvisko, String uzivatelskeMeno, String heslo, boolean jeAdmin) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.uzivatelskeMeno = uzivatelskeMeno;
        this.heslo = heslo;
        this.jeAdmin = jeAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public String getUzivatelskeMeno() {
        return uzivatelskeMeno;
    }

    public void setUzivatelskeMeno(String uzivatelskeMeno) {
        this.uzivatelskeMeno = uzivatelskeMeno;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public boolean isJeAdmin() {
        return jeAdmin;
    }

    public void setJeAdmin(boolean jeAdmin) {
        this.jeAdmin = jeAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meno, priezvisko, uzivatelskeMeno, heslo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Porotca porotca = (Porotca) o;
        return jeAdmin == porotca.jeAdmin && Objects.equals(id, porotca.id) && Objects.equals(meno, porotca.meno) && Objects.equals(priezvisko, porotca.priezvisko) && Objects.equals(uzivatelskeMeno, porotca.uzivatelskeMeno) && Objects.equals(heslo, porotca.heslo);
    }

    @Override
    public String toString() {
        return meno + ", " + priezvisko;
    }
}
