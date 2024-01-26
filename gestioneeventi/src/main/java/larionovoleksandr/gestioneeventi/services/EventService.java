package larionovoleksandr.gestioneeventi.services;

import larionovoleksandr.gestioneeventi.entities.Event;
import larionovoleksandr.gestioneeventi.exceptions.NotFoundException;
import larionovoleksandr.gestioneeventi.payloads.NewEventDTO;
import larionovoleksandr.gestioneeventi.repositories.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;

    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventDAO.findAll(pageable);
    }


    public Event saveEvent(NewEventDTO body) {
        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setPlace(body.place());
        newEvent.setDescription(body.description());
        newEvent.setDate(body.date());
        newEvent.setMaxNumberOfParticipants(body.maxNumberOfParticipants());
        return eventDAO.save(newEvent);
    }

    public Event findById(Long id) {
        return eventDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(Long id) {
        Event found = this.findById(id);
        eventDAO.delete(found);

    }

    public Event findByIdAndUpdate(Long id, Event body) {
        Event found = this.findById(id);
        found.setId(id);
        found.setDate(body.getDate());
        found.setDescription(body.getDescription());
        found.setPlace(body.getPlace());
        found.setTitle(body.getTitle());
        found.setEventImage(body.getEventImage());
        return found;
    }
}
