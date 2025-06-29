package com.example.msdisenio.controller;

import com.example.msdisenio.dto.DisenioDTO;
import com.example.msdisenio.service.DisenioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disenos")
@RequiredArgsConstructor
public class DisenioController {

    private final DisenioService disenioService;

    @PostMapping
    public DisenioDTO crearDisenio(@RequestBody DisenioDTO dto) {
        return disenioService.registrarDisenio(dto);
    }

    @GetMapping
    public List<DisenioDTO> listarDisenios() {
        return disenioService.listarDisenios();
    }

    @GetMapping("/{id}")
    public DisenioDTO obtenerPorId(@PathVariable Long id) {
        return disenioService.obtenerDisenioPorId(id);
    }
}
