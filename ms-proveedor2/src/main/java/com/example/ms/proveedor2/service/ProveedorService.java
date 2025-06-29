////package com.example.jpc.student.service;
////import com.example.jpc.student.service.StudentService;
////import java.util.List;
////import java.util.Optional;
////
////
////import com.example.jpc.student.dto.StudentDTO;
////import java.util.List;
////
////public interface StudentService {
////    List<StudentDTO> getAllStudents();
////    StudentDTO getStudentById(Long id);
////    StudentDTO createStudent(StudentDTO studentDTO);
////    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
////    void deleteStudent(Long id);
////}
//
//
//package com.example.ms.proveedor.service;
//
//import com.example.ms.proveedor.dto.ProveedorDTO;
//import com.example.ms.proveedor.entity.Proveedor;
//import com.example.ms.proveedor.exception.DuplicateEmailException;
//import com.example.ms.proveedor.exception.ProveedorNotFoundException;
//import com.example.ms.proveedor.repository.ProveedorRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ProveedorService {
//
//
//    @Autowired
//    private ProveedorRepository proveedorRepository;
//
//    // Obtener todos los proveedores
//    @Transactional(readOnly = true)
//    public List<ProveedorDTO> getAllProveedores() {
//        return proveedorRepository.findAll()
//                .stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Obtener proveedor por ID
//    @Transactional(readOnly = true)
//    public ProveedorDTO getProveedorById(Long id) {
//        Proveedor proveedor = proveedorRepository.findById(id)
//                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));
//        return entityToDTO(proveedor);
//    }
//
//    // Crear nuevo proveedor
//    public ProveedorDTO createProveedor(ProveedorDTO proveedorDTO) {
//        // Verificar si ya existe un proveedor con ese correo
//        if (proveedorDTO.getCorreoElectronico() != null &&
//                proveedorRepository.existsByCorreoElectronico(proveedorDTO.getCorreoElectronico())) {
//            throw new DuplicateEmailException("Ya existe un proveedor con el correo: " + proveedorDTO.getCorreoElectronico());
//        }
//
//        Proveedor proveedor = dtoToEntity(proveedorDTO);
//        Proveedor savedProveedor = proveedorRepository.save(proveedor);
//        return entityToDTO(savedProveedor);
//    }
//
//    // Actualizar proveedor
//    public ProveedorDTO updateProveedor(Long id, ProveedorDTO proveedorDTO) {
//        Proveedor existingProveedor = proveedorRepository.findById(id)
//                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id));
//
//        // Verificar correo duplicado (solo si es diferente al actual)
//        if (proveedorDTO.getCorreoElectronico() != null &&
//                !proveedorDTO.getCorreoElectronico().equals(existingProveedor.getCorreoElectronico()) &&
//                proveedorRepository.existsByCorreoElectronico(proveedorDTO.getCorreoElectronico())) {
//            throw new DuplicateEmailException("Ya existe un proveedor con el correo: " + proveedorDTO.getCorreoElectronico());
//        }
//
//        // Actualizar campos
//        existingProveedor.setNombreEmpresa(proveedorDTO.getNombreEmpresa());
//        existingProveedor.setContacto(proveedorDTO.getContacto());
//        existingProveedor.setCorreoElectronico(proveedorDTO.getCorreoElectronico());
//        existingProveedor.setTelefono(proveedorDTO.getTelefono());
//        existingProveedor.setDireccion(proveedorDTO.getDireccion());
//        existingProveedor.setCiudad(proveedorDTO.getCiudad());
//        existingProveedor.setPais(proveedorDTO.getPais());
//
//        Proveedor updatedProveedor = proveedorRepository.save(existingProveedor);
//        return entityToDTO(updatedProveedor);
//    }
//
//    // Eliminar proveedor
//    public void deleteProveedor(Long id) {
//        if (!proveedorRepository.existsById(id)) {
//            throw new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id);
//        }
//        proveedorRepository.deleteById(id);
//    }
//
//    // Buscar proveedores por nombre de empresa
//    @Transactional(readOnly = true)
//    public List<ProveedorDTO> searchByNombreEmpresa(String nombreEmpresa) {
//        return proveedorRepository.findByNombreEmpresaContainingIgnoreCase(nombreEmpresa)
//                .stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Buscar proveedores por país
//    @Transactional(readOnly = true)
//    public List<ProveedorDTO> getProveedoresByPais(String pais) {
//        return proveedorRepository.findByPaisIgnoreCase(pais)
//                .stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Buscar proveedores por ciudad
//    @Transactional(readOnly = true)
//    public List<ProveedorDTO> getProveedoresByCiudad(String ciudad) {
//        return proveedorRepository.findByCiudadIgnoreCase(ciudad)
//                .stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Métodos de conversión
//    private ProveedorDTO entityToDTO(Proveedor proveedor) {
//        return new ProveedorDTO(
//                proveedor.getId(),
//                proveedor.getNombreEmpresa(),
//                proveedor.getContacto(),
//                proveedor.getCorreoElectronico(),
//                proveedor.getTelefono(),
//                proveedor.getDireccion(),
//                proveedor.getCiudad(),
//                proveedor.getPais()
//        );
//    }
//
//    private Proveedor dtoToEntity(ProveedorDTO dto) {
//        return new Proveedor(
//                dto.getId(),
//                dto.getNombreEmpresa(),
//                dto.getContacto(),
//                dto.getCorreoElectronico(),
//                dto.getTelefono(),
//                dto.getDireccion(),
//                dto.getCiudad(),
//                dto.getPais()
//        );
//    }
//
//
//}

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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

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

        Proveedor proveedor = dtoToEntity(proveedorDTO);
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

        // Actualizar campos
        existingProveedor.setNombreEmpresa(proveedorDTO.getNombreEmpresa());
        existingProveedor.setContacto(proveedorDTO.getContacto());
        existingProveedor.setCorreoElectronico(proveedorDTO.getCorreoElectronico());
        existingProveedor.setTelefono(proveedorDTO.getTelefono());
        existingProveedor.setDireccion(proveedorDTO.getDireccion());
        existingProveedor.setCiudad(proveedorDTO.getCiudad());
        existingProveedor.setPais(proveedorDTO.getPais());

        Proveedor updatedProveedor = proveedorRepository.save(existingProveedor);
        return entityToDTO(updatedProveedor);
    }

    // Eliminar proveedor
    public void deleteProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ProveedorNotFoundException("Proveedor no encontrado con ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    // Buscar proveedores por nombre de empresa
    @Transactional(readOnly = true)
    public List<ProveedorDTO> searchByNombreEmpresa(String nombreEmpresa) {
        return proveedorRepository.findByNombreEmpresaContainingIgnoreCase(nombreEmpresa)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
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

    public List<ProveedorDTO> obtenerActivos() {
        return proveedorRepository.findByActivoTrue()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    // Métodos de conversión - CORREGIDOS
    private ProveedorDTO entityToDTO(Proveedor proveedor) {
        return new ProveedorDTO(
                proveedor.getId(),
                proveedor.getNombreEmpresa(),
                proveedor.getContacto(),
                proveedor.getCorreoElectronico(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                proveedor.getCiudad(),
                proveedor.getPais()
        );
    }

    // MÉTODO CORREGIDO - Usar constructor sin parámetros y setters
    private Proveedor dtoToEntity(ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(dto.getId());
        proveedor.setNombreEmpresa(dto.getNombreEmpresa());
        proveedor.setContacto(dto.getContacto());
        proveedor.setCorreoElectronico(dto.getCorreoElectronico());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setCiudad(dto.getCiudad());
        proveedor.setPais(dto.getPais());
        // El campo activo se setea automáticamente en @PrePersist
        return proveedor;
    }
}