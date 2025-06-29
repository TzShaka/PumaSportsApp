package com.example.msdisenio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUsadoDTO {
    private String nombreMaterial;
    private Long idMaterial;
    private Double cantidadUsada;
}
