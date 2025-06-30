package com.example.msdisenio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUsadoDTO {
    private Long idMaterial;
    private String nombreMaterial;
    private Double cantidadUsada;
    private String unidadMedida;
}