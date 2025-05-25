package com.example.backprestamo.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDTO {

    @Min(0)
    @Max(2)
    @NotNull(message = "El estado no puede ser nulo")
    private Integer status;
}
