package com.example.msdisenio.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisenioDTO {
    private Long idDisenio;
    private String nombrePrenda;
    private String descripcion;
    private Date fechaCreacion;
    private List<MaterialUsadoDTO> materialesUsados;
}
