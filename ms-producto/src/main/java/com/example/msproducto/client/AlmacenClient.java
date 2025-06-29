package com.example.msproducto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-almacen", url = "http://localhost:8085/api/materiales")
public interface AlmacenClient {

    @PutMapping("/restar-stock")
    void restarStock(@RequestParam("id") Long id, @RequestParam("cantidad") Double cantidad);

    @GetMapping("/{id}/stock")
    Double obtenerStock(@PathVariable("id") Long id);
}
