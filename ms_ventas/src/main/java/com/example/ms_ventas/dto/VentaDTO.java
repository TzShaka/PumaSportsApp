package com.example.ms_ventas.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long clienteId;        // Si el cliente ya existe
    private String clienteNombre;  // Si el cliente es nuevo, lo envías aquí
    private String clienteEmail;
    private String clienteTelefono;
    private Double total;
    private List<DetalleVentaDTO> detalles;
}

