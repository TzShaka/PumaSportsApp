package com.example.msalmacen.controller;

import com.example.msalmacen.dto.MaterialDTO;
import com.example.msalmacen.dto.StockUpdateDTO;
import com.example.msalmacen.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public MaterialDTO create(@RequestBody MaterialDTO dto) {
        return materialService.create(dto);
    }

    @GetMapping
    public List<MaterialDTO> findAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    public MaterialDTO findById(@PathVariable Long id) {
        return materialService.findById(id);
    }

    @PutMapping("/{id}")
    public MaterialDTO update(@PathVariable Long id, @RequestBody MaterialDTO dto) {
        return materialService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        materialService.delete(id);
    }

    // 🔼 Aumentar stock desde otro servicio
    @PostMapping("/aumentar-stock")
    public void aumentarStock(@RequestBody StockUpdateDTO dto) {
        materialService.aumentarStock(dto.getMaterialId(), dto.getCantidad());
    }

    // 🔽 Disminuir stock desde otro servicio
    @PostMapping("/disminuir-stock")
    public void disminuirStock(@RequestBody StockUpdateDTO dto) {
        materialService.disminuirStock(dto.getMaterialId(), dto.getCantidad());
    }

    // 🔄 Usado por Feign Client con @RequestParam (por ejemplo desde ms-producto)
    @PutMapping("/restar-stock")
    public void restarStock(
            @RequestParam("id") Long id,
            @RequestParam("cantidad") Double cantidad
    ) {
        materialService.disminuirStock(id, cantidad);
    }

    // ✅ Nuevo endpoint para obtener el stock actual de un material
    @GetMapping("/{id}/stock")
    public Double obtenerStock(@PathVariable Long id) {
        return materialService.obtenerStock(id);
    }
}


