package larionovoleksandr.gestioneeventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewEventDTO(@NotEmpty String title,
                          @NotEmpty String place,
                          @NotEmpty String description,
                          @NotNull LocalDate date,
                          @NotNull int maxNumberOfParticipants) {
}
