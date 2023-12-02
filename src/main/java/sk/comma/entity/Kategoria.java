package sk.comma.entity;

import lombok.Data;

@Data
public class Kategoria {
    private Long id;
    private String styl;
    private String vekovaSkupina;
    private String velkostnaSkupina;

    public Kategoria(Long id, String styl, String vekovaSkupina, String velkostnaSkupina) {
        this.id = id;
        this.styl = styl;
        this.vekovaSkupina = vekovaSkupina;
        this.velkostnaSkupina = velkostnaSkupina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStyl() {
        return styl;
    }

    public void setStyl(String styl) {
        this.styl = styl;
    }

    public String getVekovaSkupina() {
        return vekovaSkupina;
    }

    public void setVekovaSkupina(String vekovaSkupina) {
        this.vekovaSkupina = vekovaSkupina;
    }

    public String getVelkostnaSkupina() {
        return velkostnaSkupina;
    }

    public void setVelkostnaSkupina(String velkostnaSkupina) {
        this.velkostnaSkupina = velkostnaSkupina;
    }
}
