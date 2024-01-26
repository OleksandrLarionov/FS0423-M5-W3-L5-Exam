package larionovoleksandr.gestioneeventi.repositories;

import larionovoleksandr.gestioneeventi.entities.Event;
import larionovoleksandr.gestioneeventi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {

}
