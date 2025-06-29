package com.example.msdisenio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "materiales_usados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialUsado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreMaterial;
    private Long idMaterial;
    private Double cantidadUsada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disenio")
    private Disenio disenio;
}
