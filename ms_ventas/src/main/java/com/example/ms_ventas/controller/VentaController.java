package com.example.ms_ventas.controller;

import com.example.ms_ventas.dto.VentaDTO;
import com.example.ms_ventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // ✅ Registrar una venta
    @PostMapping
    public ResponseEntity<VentaDTO> registrarVenta(@RequestBody VentaDTO ventaDTO) {
        VentaDTO nuevaVenta = ventaService.registrarVenta(ventaDTO);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED); // 201
    }

    // ✅ Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVenta(@PathVariable Long id) {
        VentaDTO venta = ventaService.obtenerVentaPorId(id);
        return ResponseEntity.ok(venta); // 200
    }

    // ✅ Listar todas las ventas
    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarVentas()); // 200
    }

    // ✅ Actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizarVenta(@PathVariable Long id, @RequestBody VentaDTO ventaDTO) {
        VentaDTO ventaActualizada = ventaService.actualizarVenta(id, ventaDTO);
        return ResponseEntity.ok(ventaActualizada); // 200
    }

    // ✅ Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
