package com.example.msdisenio.service.impl;

import com.example.msdisenio.client.MaterialClient;
import com.example.msdisenio.dto.DisenioDTO;
import com.example.msdisenio.dto.MaterialDTO;
import com.example.msdisenio.dto.MaterialUsadoDTO;
import com.example.msdisenio.entity.Disenio;
import com.example.msdisenio.entity.MaterialUsado;
import com.example.msdisenio.repository.DisenioRepository;
import com.example.msdisenio.service.DisenioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisenioServiceImpl implements DisenioService {

    private final DisenioRepository disenioRepository;
    private final MaterialClient materialClient;

    @Override
    public DisenioDTO registrarDisenio(DisenioDTO dto) {
        Disenio disenio = new Disenio();
        disenio.setNombrePrenda(dto.getNombrePrenda());
        disenio.setDescripcion(dto.getDescripcion());
        disenio.setFechaCreacion(new Date());

        List<MaterialUsado> usados = new ArrayList<>();

        // 🔧 VALIDACIÓN AGREGADA: Verificar si materialesUsados no es null
        if (dto.getMaterialesUsados() != null && !dto.getMaterialesUsados().isEmpty()) {
            for (MaterialUsadoDTO matDTO : dto.getMaterialesUsados()) {
                // Restar stock usando FeignClient
                materialClient.restarStock(matDTO.getIdMaterial(), matDTO.getCantidadUsada());

                // Agregar a la lista de materiales usados
                MaterialUsado m = new MaterialUsado();
                m.setNombreMaterial(matDTO.getNombreMaterial());
                m.setIdMaterial(matDTO.getIdMaterial());
                m.setCantidadUsada(matDTO.getCantidadUsada());
                m.setDisenio(disenio);
                usados.add(m);
            }
        }

        disenio.setMaterialesUsados(usados);
        disenioRepository.save(disenio);

        dto.setIdDisenio(disenio.getIdDisenio());
        dto.setFechaCreacion(disenio.getFechaCreacion());
        return dto;
    }

    @Override
    public List<DisenioDTO> listarDisenios() {
        return disenioRepository.findAll().stream().map(d -> {
            List<MaterialUsadoDTO> materiales = new ArrayList<>();

            // 🔧 VALIDACIÓN AGREGADA: Verificar si materialesUsados no es null
            if (d.getMaterialesUsados() != null && !d.getMaterialesUsados().isEmpty()) {
                materiales = d.getMaterialesUsados().stream().map(mu -> {
                    MaterialUsadoDTO m = new MaterialUsadoDTO();
                    m.setIdMaterial(mu.getIdMaterial());
                    m.setCantidadUsada(mu.getCantidadUsada());

                    try {
                        MaterialDTO material = materialClient.obtenerMaterialPorId(mu.getIdMaterial());
                        m.setNombreMaterial(material.getNombreMaterial());
                    } catch (Exception e) {
                        m.setNombreMaterial("No encontrado");
                    }

                    return m;
                }).collect(Collectors.toList());
            }

            return new DisenioDTO(
                    d.getIdDisenio(),
                    d.getNombrePrenda(),
                    d.getDescripcion(),
                    d.getFechaCreacion(),
                    materiales
            );
        }).collect(Collectors.toList());
    }

    @Override
    public DisenioDTO obtenerDisenioPorId(Long id) {
        Disenio d = disenioRepository.findById(id).orElseThrow();

        List<MaterialUsadoDTO> materiales = new ArrayList<>();

        // 🔧 VALIDACIÓN AGREGADA: Verificar si materialesUsados no es null
        if (d.getMaterialesUsados() != null && !d.getMaterialesUsados().isEmpty()) {
            materiales = d.getMaterialesUsados().stream().map(mu -> {
                MaterialUsadoDTO m = new MaterialUsadoDTO();
                m.setIdMaterial(mu.getIdMaterial());
                m.setCantidadUsada(mu.getCantidadUsada());

                try {
                    MaterialDTO material = materialClient.obtenerMaterialPorId(mu.getIdMaterial());
                    m.setNombreMaterial(material.getNombreMaterial());
                } catch (Exception e) {
                    m.setNombreMaterial("No encontrado");
                }

                return m;
            }).collect(Collectors.toList());
        }

        return new DisenioDTO(
                d.getIdDisenio(),
                d.getNombrePrenda(),
                d.getDescripcion(),
                d.getFechaCreacion(),
                materiales
        );
    }
}