package sk.comma.entity;

import lombok.Data;

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

    public TanecneTeleso(Long id, String nazov, String hudba, String klub, long kategoriaId, long sutazId, String email, String tanecnici) {
        this.id = id;
        this.nazov = nazov;
        this.hudba = hudba;
        this.klub = klub;
        this.kategoriaId = kategoriaId;
        this.sutazId = sutazId;
        this.email = email;
        this.tanecnici = tanecnici;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getUmiestnenie() {
        return umiestnenie;
    }

    public void setUmiestnenie(String umiestnenie) {
        this.umiestnenie = umiestnenie;
    }

    public String getHudba() {
        return hudba;
    }

    public void setHudba(String hudba) {
        this.hudba = hudba;
    }

    public String getKlub() {
        return klub;
    }

    public void setKlub(String klub) {
        this.klub = klub;
    }

    public long getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(long kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    public long getSutazId() {
        return sutazId;
    }

    public void setSutazId(long sutazId) {
        this.sutazId = sutazId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonneCislo() {
        return telefonneCislo;
    }

    public void setTelefonneCislo(String telefonneCislo) {
        this.telefonneCislo = telefonneCislo;
    }

    public String getTanecnici() {
        return tanecnici;
    }

    public void setTanecnici(String tanecnici) {
        this.tanecnici = tanecnici;
    }
}
