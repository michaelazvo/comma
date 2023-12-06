package sk.comma.entity;

import lombok.Data;

@Data
public class Porotca {
    private Long id;
    private String meno;
    private String priezvisko;
    private String uzivatelskeMeno;
    private String heslo;

    public Porotca(Long id, String meno, String priezvisko, String uzivatelskeMeno, String heslo) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.uzivatelskeMeno = uzivatelskeMeno;
        this.heslo = heslo;
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
}
