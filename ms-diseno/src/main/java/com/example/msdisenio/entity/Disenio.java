package com.example.msdisenio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "disenios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disenio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisenio;

    private String nombrePrenda;
    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @OneToMany(mappedBy = "disenio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MaterialUsado> materialesUsados = new ArrayList<>();
}
