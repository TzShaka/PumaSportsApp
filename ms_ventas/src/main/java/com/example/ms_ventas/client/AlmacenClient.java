package com.example.ms_ventas.client;

import com.example.ms_ventas.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ms-almacen-service" // Nombre del microservicio en Eureka o en tu configuración
)
public interface AlmacenClient {

    @GetMapping("/api/productos-terminados/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);

    @PutMapping("/api/productos-terminados/{id}/stock")
    void descontarStock(@PathVariable("id") Long id, @RequestParam("cantidad") int cantidad);
}
