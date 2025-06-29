package com.example.msdisenio.client;

import com.example.msdisenio.dto.MaterialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "material-client", url = "http://localhost:8085/api/materiales")
public interface MaterialClient {

    @PutMapping("/restar-stock")
    void restarStock(
            @RequestParam("id") Long idMaterial,
            @RequestParam("cantidad") Double cantidad
    );

    @GetMapping("/{id}")
    MaterialDTO obtenerMaterialPorId(@PathVariable("id") Long id);
}
