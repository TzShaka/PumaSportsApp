package com.example.ms_ventas.service;

import com.example.ms_ventas.dto.VentaDTO;

import java.util.List;

public interface VentaService {
    VentaDTO registrarVenta(VentaDTO ventaDTO);
    VentaDTO obtenerVentaPorId(Long id);
    List<VentaDTO> listarVentas();
    void eliminarVenta(Long id);

    // ✅ Este método es esencial
    VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO);
}

