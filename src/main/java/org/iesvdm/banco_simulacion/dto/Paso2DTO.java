package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paso2DTO {
    @NotNull(message = "no debe de ser nulo")
    private Integer codigo;
}
