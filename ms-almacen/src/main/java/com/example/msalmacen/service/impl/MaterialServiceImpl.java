package com.example.msalmacen.service.impl;

import com.example.msalmacen.dto.MaterialDTO;
import com.example.msalmacen.entity.Material;
import com.example.msalmacen.repository.MaterialRepository;
import com.example.msalmacen.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;

    // Convierte entidad a DTO
    private MaterialDTO toDTO(Material material) {
        return MaterialDTO.builder()
                .idMaterial(material.getIdMaterial())
                .nombreMaterial(material.getNombreMaterial())
                .tipoMaterial(material.getTipoMaterial())
                .composicion(material.getComposicion())
                .propiedadesClave(material.getPropiedadesClave())
                .usosComunes(material.getUsosComunes())
                .requiereMezcla(material.getRequiereMezcla())
                .proveedoresSugeridos(material.getProveedoresSugeridos())
                .costoUnitarioAprox(material.getCostoUnitarioAprox())
                .unidadMedida(material.getUnidadMedida())
                .stockDisponible(material.getStockDisponible())
                .build();
    }

    // Convierte DTO a entidad
    private Material toEntity(MaterialDTO dto) {
        return Material.builder()
                .nombreMaterial(dto.getNombreMaterial())
                .tipoMaterial(dto.getTipoMaterial())
                .composicion(dto.getComposicion())
                .propiedadesClave(dto.getPropiedadesClave())
                .usosComunes(dto.getUsosComunes())
                .requiereMezcla(dto.getRequiereMezcla())
                .proveedoresSugeridos(dto.getProveedoresSugeridos())
                .costoUnitarioAprox(dto.getCostoUnitarioAprox())
                .unidadMedida(dto.getUnidadMedida())
                .stockDisponible(dto.getStockDisponible())
                .build();
    }

    @Override
    public MaterialDTO create(MaterialDTO dto) {
        Material material = repository.findByNombreMaterial(dto.getNombreMaterial())
                .map(existing -> {
                    // Si ya existe, sumamos el stock nuevo
                    existing.setStockDisponible(
                            existing.getStockDisponible() + dto.getStockDisponible()
                    );
                    return existing;
                })
                .orElseGet(() -> toEntity(dto));  // Si no existe, creamos uno nuevo

        Material saved = repository.save(material);
        return toDTO(saved);
    }

    @Override
    public List<MaterialDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MaterialDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + id));
    }

    @Override
    public MaterialDTO update(Long id, MaterialDTO dto) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + id));
        material.setNombreMaterial(dto.getNombreMaterial());
        material.setTipoMaterial(dto.getTipoMaterial());
        material.setComposicion(dto.getComposicion());
        material.setPropiedadesClave(dto.getPropiedadesClave());
        material.setUsosComunes(dto.getUsosComunes());
        material.setRequiereMezcla(dto.getRequiereMezcla());
        material.setProveedoresSugeridos(dto.getProveedoresSugeridos());
        material.setCostoUnitarioAprox(dto.getCostoUnitarioAprox());
        material.setUnidadMedida(dto.getUnidadMedida());
        material.setStockDisponible(dto.getStockDisponible());
        Material updated = repository.save(material);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void aumentarStock(Long id, Double cantidad) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + id));
        material.setStockDisponible(material.getStockDisponible() + cantidad);
        repository.save(material);
    }

    @Override
    public void disminuirStock(Long id, Double cantidad) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + id));
        if (material.getStockDisponible() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para este material.");
        }
        material.setStockDisponible(material.getStockDisponible() - cantidad);
        repository.save(material);
    }

    @Override
    public Double obtenerStock(Long id) {
        Material material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material no encontrado: " + id));
        return material.getStockDisponible();
    }
}