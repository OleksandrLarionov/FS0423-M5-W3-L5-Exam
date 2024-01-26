package larionovoleksandr.gestioneeventi.services;

import larionovoleksandr.gestioneeventi.ROLE;
import larionovoleksandr.gestioneeventi.entities.User;
import larionovoleksandr.gestioneeventi.exceptions.BadRequestException;
import larionovoleksandr.gestioneeventi.exceptions.UnauthorizedException;
import larionovoleksandr.gestioneeventi.payloads.NewUserDTO;
import larionovoleksandr.gestioneeventi.payloads.UserLogInDTO;
import larionovoleksandr.gestioneeventi.repositories.UserDAO;
import larionovoleksandr.gestioneeventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UserDAO userDAO;

    public String authenticateUser(UserLogInDTO body) {
        User user = userService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("invalid credentials!");
        }
    }
    public User saveUser(NewUserDTO body) {
        userDAO.findByEmail(body.email()).ifPresent(employee -> {
            throw new BadRequestException("IS ALREADY EXIST " + employee.getEmail());
        });
        Random rndm = new Random();
        User user = new User();
        user.setUsername(body.name() + body.surname() + rndm.nextInt(1, 100000));
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setRole(ROLE.USER);
        return userDAO.save(user);
    }

}
