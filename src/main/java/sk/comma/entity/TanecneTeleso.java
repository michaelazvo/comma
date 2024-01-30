package sk.comma.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class TanecneTeleso {
    private Long id;
    private String nazov;
    private String umiestnenie;
    private String hudba;
    private String klub;
    private long kategoriaId;
    private long sutazId;
    private String email;
    private String telefonneCislo;
    private String tanecnici;

    public TanecneTeleso(){

    }
    public TanecneTeleso(Long id, String nazov, String hudba, String klub, long kategoriaId, long sutazId, String email, String telefonneCislo, String tanecnici) {
        this.id = id;
        this.nazov = nazov;
        this.hudba = hudba;
        this.klub = klub;
        this.kategoriaId = kategoriaId;
        this.sutazId = sutazId;
        this.email = email;
        this.telefonneCislo = telefonneCislo;
        this.tanecnici = tanecnici;
    }


    public TanecneTeleso(Long id, String nazov, String umiestnenie, String hudba, String klub, long kategoriaId, long sutazId, String email, String telefonneCislo, String tanecnici) {
        this.id = id;
        this.nazov = nazov;
        this.umiestnenie = umiestnenie;
        this.hudba = hudba;
        this.klub = klub;
        this.kategoriaId = kategoriaId;
        this.sutazId = sutazId;
        this.email = email;
        this.telefonneCislo = telefonneCislo;
        this.tanecnici = tanecnici;
    }


    @Override
    public String toString() {
        return nazov;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TanecneTeleso that = (TanecneTeleso) o;
        return kategoriaId == that.kategoriaId && sutazId == that.sutazId && Objects.equals(nazov, that.nazov) && Objects.equals(umiestnenie, that.umiestnenie) && Objects.equals(hudba, that.hudba) && Objects.equals(klub, that.klub) && Objects.equals(email, that.email) && Objects.equals(telefonneCislo, that.telefonneCislo) && Objects.equals(tanecnici, that.tanecnici);
    }

}
