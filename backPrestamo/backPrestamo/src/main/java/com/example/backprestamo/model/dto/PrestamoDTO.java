package com.example.backprestamo.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDTO {
    private Long id_prestamo;

    @NotNull(message = "La fecha de la solicitud no puede ser nula")
    private LocalDate fechaSolicitud;

    @NotNull(message = "La fecha de entrega no puede ser nula")
    private LocalDate fechaEntrega;

    @NotNull(message = "El id del libro no puede ser nulo")
    private Long libroId;

    @NotNull(message = "El id del usuario no puede ser nulo")
    private Long usuarioId;

    @Min(value = 0)
    @Max(value = 2)
    @NotNull(message = "El status no puede ser nulo")
    private Integer status;

    @Pattern(regexp = "^(Efectivo|Tarjeta)$", message = "El tipo de pago debe ser 'Efectivo' o 'Tarjeta'")
    @NotNull(message = "El tipo de pago no puede ser nulo")
    private String tipoPago;


}
