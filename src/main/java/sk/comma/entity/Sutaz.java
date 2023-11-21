package sk.comma.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sutaz {
    private int id;
    private String nazov;
    private LocalDateTime odDatum;
    private LocalDateTime doDatum;
}
