package com.example.ms_ventas.mapper;

import com.example.ms_ventas.dto.VentaDTO;
import com.example.ms_ventas.dto.DetalleVentaDTO;
import com.example.ms_ventas.entity.Venta;
import com.example.ms_ventas.entity.DetalleVenta;

import java.util.List;
import java.util.stream.Collectors;

public class VentaMapper {

    public static VentaDTO toDTO(Venta venta) {
        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .clienteId(venta.getClienteId())
                .total(venta.getTotal())
                .detalles(venta.getDetalles() != null ?
                        venta.getDetalles().stream()
                                .map(VentaMapper::toDetalleDTO)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }

    public static Venta toEntity(VentaDTO dto) {
        Venta venta = new Venta();
        venta.setId(dto.getId());
        venta.setFecha(dto.getFecha());
        venta.setClienteId(dto.getClienteId());
        venta.setTotal(dto.getTotal());

        if (dto.getDetalles() != null) {
            List<DetalleVenta> detalles = dto.getDetalles().stream()
                    .map(VentaMapper::toDetalleEntity)
                    .collect(Collectors.toList());
            detalles.forEach(d -> d.setVenta(venta)); // establecer relación bidireccional
            venta.setDetalles(detalles);
        }

        return venta;
    }

    private static DetalleVentaDTO toDetalleDTO(DetalleVenta detalle) {
        return DetalleVentaDTO.builder()
                .id(detalle.getId())
                .productoId(detalle.getProductoId())
                .descripcion(detalle.getDescripcion())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .impuesto(detalle.getImpuesto())
                .descuento(detalle.getDescuento())
                .totalItem(detalle.getTotalItem())
                .build();
    }

    private static DetalleVenta toDetalleEntity(DetalleVentaDTO dto) {
        return DetalleVenta.builder()
                .id(dto.getId())
                .productoId(dto.getProductoId())
                .descripcion(dto.getDescripcion())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .subtotal(dto.getSubtotal())
                .impuesto(dto.getImpuesto())
                .descuento(dto.getDescuento())
                .totalItem(dto.getTotalItem())
                .build();
    }
}
