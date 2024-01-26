package larionovoleksandr.gestioneeventi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(@Email(message = "Insert valid email address like: john.doe@mail.com")
                            String email,
                         @NotEmpty(message = "Can not be empty")
                            @Size(min = 2, max = 20, message = "Name must be Longer")
                            String name,
                         @NotEmpty(message = "Can not be empty")
                            @Size(min = 2, max = 20, message = "Surname must be longer")
                            String surname,
                         @NotEmpty
                            String password){
}
