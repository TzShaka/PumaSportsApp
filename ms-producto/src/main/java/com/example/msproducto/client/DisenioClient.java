package com.example.msproducto.client;

import com.example.msproducto.dto.DisenioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-diseno", url = "http://localhost:8085")
public interface DisenioClient {

    @GetMapping("/api/disenos/{id}")
    DisenioDTO obtenerDisenioPorId(@PathVariable("id") Long id);
}
