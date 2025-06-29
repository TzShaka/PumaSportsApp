package com.example.ms_ventas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private Long productoId;

    private String descripcion;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;

    private Double impuesto;

    private Double descuento;

    private Double totalItem;
}
