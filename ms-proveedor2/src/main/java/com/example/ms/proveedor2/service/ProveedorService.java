package com.example.ms.proveedor2.service;

import com.example.ms.proveedor2.dto.ProveedorDTO;
import com.example.ms.proveedor2.entity.Proveedor;
import com.example.ms.proveedor2.exception.DuplicateEmailException;
import com.example.ms.proveedor2.exception.ProveedorNotFoundException;
import com.example.ms.proveedor2.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // ==================== MÉTODOS BÁSICOS CRUD ====================

    // Obtener todos los proveedores
    @Transactional(readOnly = true)
    public List<ProveedorDTO> getAllProveedores() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Obtener proveedor por ID
    @Transactional(readOnly = true)
    public ProveedorDTO getProveedorById(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));
        return entityToDTO(proveedor);
    }

    // Crear nuevo proveedor
    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
        // Verificar si ya existe un proveedor con ese correo
        if (proveedorDTO.getCorreoElectronico() != null &&
                proveedorRepository.existsByCorreoElectronico(proveedorDTO.getCorreoElectronico())) {
            throw new DuplicateEmailException("Ya existe un proveedor con el correo: " + proveedorDTO.getCorreoElectronico());
        }

        // ✅ Verificar código de proveedor duplicado
        if (proveedorDTO.getCodigoProveedor() != null &&
                proveedorRepository.existsByCodigoProveedor(proveedorDTO.getCodigoProveedor())) {
            throw new DuplicateEmailException("Ya existe un proveedor con el código: " + proveedorDTO.getCodigoProveedor());
        }

        Proveedor proveedor = dtoToEntity(proveedorDTO);

        // 🔥 IMPORTANTE: El @PrePersist se encargará de establecer fechaCreacion automáticamente
        // No necesitas setearla manualmente aquí

        Proveedor savedProveedor = proveedorRepository.save(proveedor);
        return entityToDTO(savedProveedor);
    }

    // Actualizar proveedor
    public ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO) {
        Proveedor existingProveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));

        // Verificar correo duplicado (solo si es diferente al actual)
        if (proveedorDTO.getCorreoElectronico() != null &&
                !proveedorDTO.getCorreoElectronico().equals(existingProveedor.getCorreoElectronico()) &&
                proveedorRepository.existsByCorreoElectronico(proveedorDTO.getCorreoElectronico())) {
            throw new DuplicateEmailException("Ya existe un proveedor con el correo: " + proveedorDTO.getCorreoElectronico());
        }

        // ✅ Verificar código duplicado (solo si es diferente al actual)
        if (proveedorDTO.getCodigoProveedor() != null &&
                !proveedorDTO.getCodigoProveedor().equals(existingProveedor.getCodigoProveedor()) &&
                proveedorRepository.existsByCodigoProveedor(proveedorDTO.getCodigoProveedor())) {
            throw new DuplicateEmailException("Ya existe un proveedor con el código: " + proveedorDTO.getCodigoProveedor());
        }

        // Actualizar campos (NO actualices fechaCreacion, debe mantenerse)
        existingProveedor.setCodigoProveedor(proveedorDTO.getCodigoProveedor());
        existingProveedor.setNombreEmpresa(proveedorDTO.getNombreEmpresa());
        existingProveedor.setContacto(proveedorDTO.getContacto());
        existingProveedor.setCorreoElectronico(proveedorDTO.getCorreoElectronico());
        existingProveedor.setTelefono(proveedorDTO.getTelefono());
        existingProveedor.setDireccion(proveedorDTO.getDireccion());
        existingProveedor.setCiudad(proveedorDTO.getCiudad());
        existingProveedor.setPais(proveedorDTO.getPais());
        // 🔥 IMPORTANTE: NO actualizar fechaCreacion en updates

        Proveedor updatedProveedor = proveedorRepository.save(existingProveedor);
        return entityToDTO(updatedProveedor);
    }

    // Eliminar proveedor (eliminación lógica)
    public void deleteProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));

        // ✅ Eliminación lógica en lugar de física
        proveedor.setActivo(false);
        proveedorRepository.save(proveedor);
    }

    // ✅ Eliminación física (si realmente necesitas borrar de la BD)
    public void deleteProveedorPermanently(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    // ==================== MÉTODOS DE BÚSQUEDA ====================

    // Buscar proveedores por nombre de empresa
    @Transactional(readOnly = true)
    public List<ProveedorDTO> searchByNombreEmpresa(String nombreEmpresa) {
        return proveedorRepository.findByNombreEmpresaContainingIgnoreCase(nombreEmpresa)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Buscar por código de proveedor
    @Transactional(readOnly = true)
    public ProveedorDTO getProveedorByCodigoProveedor(String codigoProveedor) {
        Proveedor proveedor = proveedorRepository.findByCodigoProveedor(codigoProveedor)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con código: " + codigoProveedor));
        return entityToDTO(proveedor);
    }

    // Buscar proveedores por país
    @Transactional(readOnly = true)
    public List<ProveedorDTO> getProveedoresByPais(String pais) {
        return proveedorRepository.findByPaisIgnoreCase(pais)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Buscar proveedores por ciudad
    @Transactional(readOnly = true)
    public List<ProveedorDTO> getProveedoresByCiudad(String ciudad) {
        return proveedorRepository.findByCiudadIgnoreCase(ciudad)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS POR ESTADO ====================

    // Obtener proveedores activos
    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerActivos() {
        return proveedorRepository.findByActivoTrue()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Obtener proveedores inactivos
    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerInactivos() {
        return proveedorRepository.findByActivoFalse()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Activar proveedor
    public ProveedorDTO activarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));

        proveedor.setActivo(true);
        Proveedor savedProveedor = proveedorRepository.save(proveedor);
        return entityToDTO(savedProveedor);
    }

    // ✅ Desactivar proveedor
    public ProveedorDTO desactivarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));

        proveedor.setActivo(false);
        Proveedor savedProveedor = proveedorRepository.save(proveedor);
        return entityToDTO(savedProveedor);
    }

    // ==================== MÉTODOS DE CONVERSIÓN ====================

    // ✅ CORREGIDO - Incluye fechaCreacion y activo
    private ProveedorDTO entityToDTO(Proveedor proveedor) {
        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setCodigoProveedor(proveedor.getCodigoProveedor());
        dto.setNombreEmpresa(proveedor.getNombreEmpresa());
        dto.setContacto(proveedor.getContacto());
        dto.setCorreoElectronico(proveedor.getCorreoElectronico());
        dto.setTelefono(proveedor.getTelefono());
        dto.setDireccion(proveedor.getDireccion());
        dto.setCiudad(proveedor.getCiudad());
        dto.setPais(proveedor.getPais());
        dto.setFechaCreacion(proveedor.getFechaCreacion()); // 🔥 NUEVO
        dto.setActivo(proveedor.getActivo()); // ✅ EXISTENTE

        return dto;
    }

    // ✅ CORREGIDO - Incluye todos los campos pero NO setea fechaCreacion (se hace automático)
    private Proveedor dtoToEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(dto.getId());
        proveedor.setCodigoProveedor(dto.getCodigoProveedor());
        proveedor.setNombreEmpresa(dto.getNombreEmpresa());
        proveedor.setContacto(dto.getContacto());
        proveedor.setCorreoElectronico(dto.getCorreoElectronico());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setCiudad(dto.getCiudad());
        proveedor.setPais(dto.getPais());
        proveedor.setActivo(dto.getActivo());
        // 🔥 NO setear fechaCreacion aquí - se establece automáticamente en @PrePersist

        return proveedor;
    }
}