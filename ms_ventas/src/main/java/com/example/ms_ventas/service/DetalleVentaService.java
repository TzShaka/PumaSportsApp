package com.example.ms_ventas.service;

import com.example.ms_ventas.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface DetalleVentaService {
    List<DetalleVenta> obtenerPorVentaId(Long ventaId);
    Optional<DetalleVenta> obtenerPorId(Long id);
    DetalleVenta registrar(DetalleVenta detalleVenta);
    void eliminar(Long id);
}
