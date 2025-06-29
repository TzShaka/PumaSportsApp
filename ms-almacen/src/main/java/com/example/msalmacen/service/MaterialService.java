package com.example.msalmacen.service;

import com.example.msalmacen.dto.MaterialDTO;

import java.util.List;

public interface MaterialService {
    MaterialDTO create(MaterialDTO dto);
    List<MaterialDTO> findAll();
    MaterialDTO findById(Long id);
    MaterialDTO update(Long id, MaterialDTO dto);
    void delete(Long id);

    void aumentarStock(Long id, Double cantidad);
    void disminuirStock(Long id, Double cantidad);

    // ✅ NUEVO MÉTODO
    Double obtenerStock(Long id);
}
