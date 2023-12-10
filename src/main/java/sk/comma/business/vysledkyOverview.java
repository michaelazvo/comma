package sk.comma.business;

import lombok.Data;

import java.util.List;


@Data
public class vysledkyOverview {
    private String umiestnenie;
    private long id;
    private String nazov;
    private String hudba;
    private String klub;
    private String tanecnici;
    private List<Integer> hodnotenie;
    public vysledkyOverview(String umiestnenie, long id, String nazov,
                            String hudba, String klub, String tanecnici, List<Integer> hodnotenie){
        this.umiestnenie = umiestnenie;
        this.id = id;
        this.nazov = nazov;
        this.hudba = hudba;
        this.klub = klub;
        this.tanecnici = tanecnici;
        this.hodnotenie = hodnotenie;
    }
}


