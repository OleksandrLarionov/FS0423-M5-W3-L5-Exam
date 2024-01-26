package larionovoleksandr.gestioneeventi.controllers;

import larionovoleksandr.gestioneeventi.entities.Event;
import larionovoleksandr.gestioneeventi.exceptions.BadRequestException;
import larionovoleksandr.gestioneeventi.payloads.NewEventDTO;
import larionovoleksandr.gestioneeventi.payloads.NewEventResponceDTO;
import larionovoleksandr.gestioneeventi.services.AuthService;
import larionovoleksandr.gestioneeventi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private AuthService authService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return eventService.getEvents(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    public NewEventResponceDTO saveEvent(@RequestBody @Validated NewEventDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            Event newEvent = eventService.saveEvent(body);
            return new NewEventResponceDTO(newEvent.getId());
        }
    }

    @GetMapping("/{id}")
    public Event findById(@PathVariable Long id) {
        return eventService.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    public void findByIdAndDelete(@PathVariable Long id) {
        eventService.delete(id);
    }
}
