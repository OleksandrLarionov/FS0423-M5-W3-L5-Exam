package larionovoleksandr.gestioneeventi.controllers;

import larionovoleksandr.gestioneeventi.entities.User;
import larionovoleksandr.gestioneeventi.exceptions.BadRequestException;
import larionovoleksandr.gestioneeventi.payloads.NewUserDTO;
import larionovoleksandr.gestioneeventi.payloads.NewUserResponceDTO;
import larionovoleksandr.gestioneeventi.payloads.UserLogInDTO;
import larionovoleksandr.gestioneeventi.payloads.UserLogInResponceDTO;
import larionovoleksandr.gestioneeventi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public UserLogInResponceDTO login(@RequestBody UserLogInDTO body) {
        String accessToken = authService.authenticateUser(body);
        return new UserLogInResponceDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponceDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Error check payload!");
        } else {
            User newUser = authService.saveUser(newUserPayload);
            return new NewUserResponceDTO(newUser.getId());
        }
    }
}
