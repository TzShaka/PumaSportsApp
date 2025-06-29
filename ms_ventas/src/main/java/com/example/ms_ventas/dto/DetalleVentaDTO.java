package com.example.ms_ventas.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {
    private Long id;
    private Long productoId;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Double impuesto;
    private Double descuento;
    private Double totalItem;
}
