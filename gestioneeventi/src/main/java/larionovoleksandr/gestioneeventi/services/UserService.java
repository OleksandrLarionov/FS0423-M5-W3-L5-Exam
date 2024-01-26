package larionovoleksandr.gestioneeventi.services;

import larionovoleksandr.gestioneeventi.entities.Event;
import larionovoleksandr.gestioneeventi.entities.User;
import larionovoleksandr.gestioneeventi.exceptions.NotFoundException;
import larionovoleksandr.gestioneeventi.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findById(Long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void delete(Long id) {
        User found = this.findById(id);
        userDAO.delete(found);

    }

    public User findByIdAndUpdate(Long id, User body) {
        User found = this.findById(id);
        found.setId(id);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());

        return userDAO.save(found);
    }
    public User addEvent(Long id, Event event){
        User found = this.findById(id);
        found.setId(id);
        found.addEvent(event);
        found.setEventBooked(found.getEventBooked());
        return userDAO.save(found);
    }
    public User findByEmail(String email) throws NotFoundException {
        return userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("USer with  email " + email + " not found!"));
    }

}
