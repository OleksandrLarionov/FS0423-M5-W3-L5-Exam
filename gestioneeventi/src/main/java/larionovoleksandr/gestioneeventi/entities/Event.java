package larionovoleksandr.gestioneeventi.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String place;
    private String description;
    private LocalDate date;
    private String eventImage;
    private int maxNumberOfParticipants;
    private int actualParticipants;
    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;

    public Event(String title, String place, String description, LocalDate date, int maxNumberOfParticipants) {
        this.title = title;
        this.place = place;
        this.description = description;
        this.date = date;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.actualParticipants = 0;
    }
}
