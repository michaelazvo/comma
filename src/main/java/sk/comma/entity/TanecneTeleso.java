package sk.comma.entity;

import lombok.Data;

@Data
public class TanecneTeleso {
    private Long id;
    private String nazov;
    private int dlzka;
    private String umiestnenie;
    private String hudba;
    private String klub;
    private long kategoriaId;
    private long sutazId;
    private String email;
    private String telefonneCislo;
}
