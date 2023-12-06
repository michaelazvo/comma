package sk.comma.entity;

import lombok.Data;

@Data
public class Porotca {
    private Long id;
    private String meno;
    private String priezvisko;
    private String uzivatelskeMeno;
    private String heslo;
}
