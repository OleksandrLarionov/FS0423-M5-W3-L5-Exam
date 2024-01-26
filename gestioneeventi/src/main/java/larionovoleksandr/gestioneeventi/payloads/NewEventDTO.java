package larionovoleksandr.gestioneeventi.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record NewEventDTO(@NotEmpty String title,
                          @NotEmpty String place,
                          @NotEmpty String description,
                          @NotEmpty LocalDate date,
                          @NotEmpty int maxNumberOfParticipants) {
}
