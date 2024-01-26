package larionovoleksandr.gestioneeventi.controllers;

import larionovoleksandr.gestioneeventi.entities.Event;
import larionovoleksandr.gestioneeventi.entities.User;
import larionovoleksandr.gestioneeventi.exceptions.BadRequestException;
import larionovoleksandr.gestioneeventi.exceptions.NoMorePlacesException;
import larionovoleksandr.gestioneeventi.payloads.*;
import larionovoleksandr.gestioneeventi.services.AuthService;
import larionovoleksandr.gestioneeventi.services.EventService;
import larionovoleksandr.gestioneeventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static larionovoleksandr.gestioneeventi.exceptions.ExceptionsHandler.newDateAndHour;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private EventService eventService;
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser){
        return currentUser;
    }
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    @GetMapping("/me/eventList")
    public List<Event> getUserEventList(@AuthenticationPrincipal User currentUser) {
        return currentUser.getEventBooked();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EVENT_MANAGER')")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getUsers(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponceDTO saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            User newUser = authService.saveUser(body);
            return new NewUserResponceDTO(newUser.getId());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    public AddedEventResponceDTO addEventParticipation(@PathVariable Long id, @RequestBody AddEventPayloadDTO payload){
        Event event = eventService.findById(payload.id());
        if (event.getActualParticipants() < event.getMaxNumberOfParticipants()) {
            event.setActualParticipants(event.getActualParticipants() + 1);
            userService.addEvent(id, event);
            return new AddedEventResponceDTO("Your participation in the event " + event.getTitle() + " was successful");
        } else {
            throw  new NoMorePlacesException("No more places available at ");
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('EVENT_MANAGER','USER')")
    public void findByIdAndDelete(@PathVariable Long id) {
        userService.delete(id);
    }

}
