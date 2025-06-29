package com.example.msalmacen.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDTO {
    private Long idMaterial;
    private String nombreMaterial;
    private String tipoMaterial;
    private String composicion;
    private String propiedadesClave;
    private String usosComunes;
    private Boolean requiereMezcla;
    private String proveedoresSugeridos;
    private Double costoUnitarioAprox;
    private String unidadMedida;
    private Double stockDisponible;
}
