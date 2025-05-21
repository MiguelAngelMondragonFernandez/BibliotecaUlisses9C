package com.example.backprestamo.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoEntregaDTO {
    @NotNull(message = "El id del préstamo no puede ser nulo")
    private Long id_prestamo;

    @NotNull(message = "La fecha de entrega no puede ser nula")
    private LocalDate fechaEntrega;
}
