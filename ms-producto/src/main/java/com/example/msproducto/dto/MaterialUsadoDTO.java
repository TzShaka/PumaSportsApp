package com.example.msproducto.dto;

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
