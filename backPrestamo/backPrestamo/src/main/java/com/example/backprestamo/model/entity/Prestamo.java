package com.example.backprestamo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "prestamo")
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo", nullable = false)
    private Long id_prestamo;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "status", nullable = false)
    private Boolean status;


}
