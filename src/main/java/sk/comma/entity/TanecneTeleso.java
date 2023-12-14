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

    public TanecneTeleso(){

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
}
