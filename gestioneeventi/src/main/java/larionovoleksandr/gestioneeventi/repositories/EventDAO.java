package larionovoleksandr.gestioneeventi.repositories;

import larionovoleksandr.gestioneeventi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {
}
