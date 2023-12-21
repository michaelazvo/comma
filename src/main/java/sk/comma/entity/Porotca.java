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

    private String sol;

    public Porotca() {

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

    public Porotca(String meno, String priezvisko, String uzivatelskeMeno, String heslo, boolean jeAdmin, String sol) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.uzivatelskeMeno = uzivatelskeMeno;
        this.heslo = heslo;
        this.jeAdmin = jeAdmin;
        this.sol = sol;
    }

    public Porotca(Long id, String meno, String priezvisko, String uzivatelskeMeno, String heslo, boolean jeAdmin, String sol) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.uzivatelskeMeno = uzivatelskeMeno;
        this.heslo = heslo;
        this.jeAdmin = jeAdmin;
        this.sol = sol;
    }



    @Override
    public String toString() {
        return meno + ", " + priezvisko;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Porotca porotca = (Porotca) o;
        return jeAdmin == porotca.jeAdmin && Objects.equals(id, porotca.id) && Objects.equals(meno, porotca.meno) && Objects.equals(priezvisko, porotca.priezvisko) && Objects.equals(uzivatelskeMeno, porotca.uzivatelskeMeno) && Objects.equals(heslo, porotca.heslo);
    }
}
