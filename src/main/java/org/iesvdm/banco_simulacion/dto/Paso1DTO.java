package org.iesvdm.banco_simulacion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paso1DTO {

    @NotNull(message = "debes selccionar un Iban")
    private Long cuentaOrigenId;

    @Size(min = 1,max = 29, message = "No se puede poner mas de 24 digitos")
    private String ibanDestino;

    private String concepto;
    @NotNull(message = "No puedes dejarlo vacio el importe")
    @Min(value=1, message = "Tienes que poner una cifra mínimo")
    private BigDecimal importe;
    private LocalDateTime fechaProgramada;


}
