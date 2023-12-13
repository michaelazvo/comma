package sk.comma.entity;

import lombok.Data;

@Data
public class Hodnotenie {
    private Long id;
    private int hodnotenie;
    private long porotcaId;
    private long tanecneTelesoId;

    public Hodnotenie(){

    }


    public Hodnotenie(int hodnotenie, long porotcaId, long tanecneTelesoId) {
        this.hodnotenie = hodnotenie;
        this.porotcaId = porotcaId;
        this.tanecneTelesoId = tanecneTelesoId;
    }

    public Hodnotenie(long id, int hodnotenie, long porotcaId, long tanecneTelesoId) {
        this.id = id;
        this.hodnotenie = hodnotenie;
        this.porotcaId = porotcaId;
        this.tanecneTelesoId = tanecneTelesoId;
    }
}
