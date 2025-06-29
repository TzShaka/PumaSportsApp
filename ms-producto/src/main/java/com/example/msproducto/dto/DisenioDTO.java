package com.example.msproducto.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisenioDTO {
    private Long idDisenio;
    private String nombrePrenda;
    private String descripcion;
    private LocalDate fechaCreacion;
    private List<MaterialUsadoDTO> materialesUsados;
}
