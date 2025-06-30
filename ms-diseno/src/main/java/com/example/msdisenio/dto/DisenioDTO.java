package com.example.msdisenio.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DisenioDTO {
    private Long idDisenio;
    private String nombrePrenda;
    private String descripcion;
    private Date fechaCreacion;
    private List<MaterialUsadoDTO> materialesUsados;

    // Constructor explícito con todos los parámetros
    public DisenioDTO(Long idDisenio, String nombrePrenda, String descripcion,
                      Date fechaCreacion, List<MaterialUsadoDTO> materialesUsados) {
        this.idDisenio = idDisenio;
        this.nombrePrenda = nombrePrenda;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.materialesUsados = materialesUsados;
    }
}