package sk.comma.entity;
import lombok.Data;
@Data
public class Hodnotenie {
    private Long id;
    private int hodnotenie;
    private long porotcaId;
    private long tanecneTelesoId;
}
