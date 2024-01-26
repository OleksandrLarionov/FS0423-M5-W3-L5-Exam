package larionovoleksandr.gestioneeventi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "event")
@Getter
@Setter
public class EVENT {
    @Id
    private Long id;
    private String title;
    private String place;
    private String description;
    private LocalDate date;

}
