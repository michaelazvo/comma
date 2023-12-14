package sk.comma.entity;

import lombok.Data;

@Data
public class Kategoria {
    private Long id;
    private String styl;
    private String vekovaSkupina;
    private String velkostnaSkupina;

    public Kategoria() {

    }

    public Kategoria(Long id, String styl, String vekovaSkupina, String velkostnaSkupina) {
        this.id = id;
        this.styl = styl;
        this.vekovaSkupina = vekovaSkupina;
        this.velkostnaSkupina = velkostnaSkupina;
    }
    public static final String[] STYL_TYPES = {"Breakdance", "Disco Dance", "Show Dance", "Contemporary"};
    public static final String[] VEK_TYPES = {"Deti", "Juniori", "Dospeli"};
    public static final String[] VELKOST_TYPES = {"Solo", "Duo", "Skupina", "Formacia"};

    public static String[] getStylTypes() {
        return STYL_TYPES;
    }

    public static String[] getVekovaSkupinaTypes() {
        return VEK_TYPES;
    }

    public static String[] getVelkostnaSkupinaTypes() {
        return VELKOST_TYPES;
    }

}
