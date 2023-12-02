package sk.comma.entity;

public class Hodnotenie {
    private Long id;
    private int hodnotenie;
    private long porotcaId;
    private long tanecneTelesoId;

    public Hodnotenie(Long id, int hodnotenie, long porotcaId, long tanecneTelesoId) {
        this.id = id;
        this.hodnotenie = hodnotenie;
        this.porotcaId = porotcaId;
        this.tanecneTelesoId = tanecneTelesoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHodnotenie() {
        return hodnotenie;
    }

    public void setHodnotenie(int hodnotenie) {
        this.hodnotenie = hodnotenie;
    }

    public long getPorotcaId() {
        return porotcaId;
    }

    public void setPorotcaId(long porotcaId) {
        this.porotcaId = porotcaId;
    }

    public long getTanecneTelesoId() {
        return tanecneTelesoId;
    }

    public void setTanecneTelesoId(long tanecneTelesoId) {
        this.tanecneTelesoId = tanecneTelesoId;
    }
}
