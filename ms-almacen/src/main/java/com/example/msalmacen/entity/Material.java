package com.example.msalmacen.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;

    @Column(length = 50)
    private String nombreMaterial;

    @Column(length = 50)
    private String tipoMaterial;

    @Column(length = 255)
    private String composicion;

    @Column(columnDefinition = "TEXT")
    private String propiedadesClave;

    @Column(columnDefinition = "TEXT")
    private String usosComunes;

    private Boolean requiereMezcla;

    @Column(length = 255)
    private String proveedoresSugeridos;

    private Double costoUnitarioAprox;

    @Column(length = 20)
    private String unidadMedida;

    @Column
    private Double stockDisponible;
}
